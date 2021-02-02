package com.zlutil.tools.toolpackage.Yield.service;

import com.alibaba.fastjson.JSON;
import com.zlutil.tools.toolpackage.Yield.Dao.YieldBlockDao;
import com.zlutil.tools.toolpackage.Yield.Dao.YieldHeadDao;
import com.zlutil.tools.toolpackage.Yield.Dao.YieldPatrolDao;
import com.zlutil.tools.toolpackage.Yield.dto.YieldBlockBasicInfo;
import com.zlutil.tools.toolpackage.Yield.dto.YieldBlockDTO;
import com.zlutil.tools.toolpackage.Yield.entity.YieldBlockEntity;
import com.zlutil.tools.toolpackage.Yield.entity.YieldHeadEntity;
import com.zlutil.tools.toolpackage.Yield.entity.YieldPatrolEntity;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Service
public class YieldService {

    @Autowired
    private YieldBlockDao yieldBlockDao;
    @Autowired
    private YieldHeadDao yieldHeadDao;
    @Autowired
    private YieldPatrolDao yieldPatrolDao;

    /**
     * 根据传入用户id获取其下级所管全部地块信息
     *
     * @param userId
     * @return
     */
    public List<YieldBlockDTO> findSubordinateYieldBlockInfo(String userId) {
        if (Objects.nonNull(userId)) {
            List<String> yieldId = yieldBlockDao.findSubordinateYieldId(Long.valueOf(userId));
            List<Long> filter = new ArrayList<>();
            for (String e : yieldId) {
                filter.addAll(new ArrayList<>(Arrays.asList(e.split(",")))
                        .stream()
                        .map(Long::valueOf)
                        .collect(Collectors.toList()));
            }
            List<YieldBlockEntity> blockEntities = yieldBlockDao.findAllByIdIn(filter);
            List<YieldBlockDTO> blockDTOS = blockEntities.stream().map(d -> {
                YieldBlockDTO yieldBlockDTO = new YieldBlockDTO();
                BeanUtils.copyProperties(d, yieldBlockDTO);
                return yieldBlockDTO;
            }).collect(Collectors.toList());
            return blockDTOS;
        } else {
            return null;
        }
    }

    /**
     * 获取指定图块的基本信息
     *
     * @param yieldId
     * @return
     */
    public YieldBlockBasicInfo findYieldBlockBasicInfo(String yieldId) {
        //获取当前地块信息
        YieldBlockEntity yieldBlockEntity = yieldBlockDao.findById(Long.valueOf(yieldId)).get();
        //获取当前地块管理者
        String userId = yieldBlockDao.findFieldManager(yieldId);
        //获取管理者的责任关系信息树
        List<YieldHeadEntity> leaderInfo = yieldHeadDao.findLeaderInfo(userId);
        //开始赋值
        YieldBlockBasicInfo yieldBlockBasicInfo = new YieldBlockBasicInfo();
        BeanUtils.copyProperties(yieldBlockEntity, yieldBlockBasicInfo);
        for (YieldHeadEntity entity : leaderInfo) {
            if (entity.getTitle().equals("村级副田长")) {
                yieldBlockBasicInfo.setVillage(entity.getVillage());
                yieldBlockBasicInfo.setViceFieldHead(entity.getName());
            }
            if (entity.getTitle().equals("村级田长")) {
                yieldBlockBasicInfo.setFieldHead(entity.getName());
            }
            if (entity.getTitle().equals("镇级田长")) {
                yieldBlockBasicInfo.setTownHead(entity.getName());
            }
            if (entity.getTitle().equals("县级田长")) {
                yieldBlockBasicInfo.setCountyHead(entity.getName());
            }
        }

        return yieldBlockBasicInfo;
    }

    /**
     * 村级副田长上传巡查日志信息
     *
     * @param yieldBlockBasicInfo
     * @return
     */
    public YieldBlockEntity uploadYieldBlockBasicInfo(YieldBlockBasicInfo yieldBlockBasicInfo) {
        //比对上传日志信息，如果比之前利用情况差则创建一个任务给上级
        YieldBlockEntity originYieldBlockEntity = yieldBlockDao.findById(yieldBlockBasicInfo.getId()).get();
        if (originYieldBlockEntity.getUtilization() - yieldBlockBasicInfo.getUtilization() < 0) {
            YieldPatrolEntity yieldPatrolEntity = new YieldPatrolEntity();
            String blockManagerId = yieldBlockDao.findFieldManager(String.valueOf(yieldBlockBasicInfo.getId()));
            YieldHeadEntity yieldHeadEntity = yieldHeadDao.findById(Long.valueOf(blockManagerId)).get();
            yieldPatrolEntity.setTown(yieldHeadEntity.getTown());
            yieldPatrolEntity.setVillage(yieldHeadEntity.getVillage());
            yieldPatrolEntity.setYieldId(yieldBlockBasicInfo.getId().toString());
            yieldPatrolEntity.setOriginalUtilization(originYieldBlockEntity.getUtilization());
            yieldPatrolEntity.setNowlUtilization(yieldBlockBasicInfo.getUtilization());
            yieldPatrolEntity.setPatrolTime(new Date());
            yieldPatrolEntity.setPatrolMan(blockManagerId);
            yieldPatrolEntity.setPeriodTime(0);
            //发给直属上级
            yieldPatrolEntity.setAgent(yieldHeadDao.findLeader(blockManagerId));
            yieldPatrolDao.save(yieldPatrolEntity);
        }
        YieldBlockEntity yieldBlockEntity = new YieldBlockEntity();
        BeanUtils.copyProperties(yieldBlockBasicInfo, yieldBlockEntity);
        return yieldBlockDao.save(yieldBlockEntity);
    }

    /**
     * 定时任务，每天早上九点中触发一次，检测巡查任务列表未处理任务，将其periodTime加1，超过15天转交上级领导
     */
    @Scheduled(cron = "0 0 9 * * ?")
    public void timer(){
        yieldPatrolDao.incAllPeriodTime();
        //检查超过十五天的未处理任务，转发给上级领导
        yieldPatrolDao.checkUnhandleTaskAndUpload();
    }
}
