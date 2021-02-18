package com.zlutil.tools.toolpackage.Yield.service;

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

import javax.transaction.Transactional;
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
     * 获取指定地块的基本信息
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
            yieldPatrolEntity.setOriginalUtilization(originYieldBlockEntity.getSpecificUtilization());
            yieldPatrolEntity.setNowlUtilization(yieldBlockBasicInfo.getSpecificUtilization());
            yieldPatrolEntity.setPatrolTime(new Date());
            yieldPatrolEntity.setPatrolMan(blockManagerId);
            yieldPatrolEntity.setPeriodTime(0);
            yieldPatrolEntity.setHandleStatus(0);
            yieldPatrolEntity.setIsAppointTask(0);
            //发给直属上级
            yieldPatrolEntity.setAgent(yieldHeadDao.findLeader(blockManagerId));
            //地块气泡标记为红色
            yieldBlockBasicInfo.setBubbleColor(1);
            yieldPatrolDao.save(yieldPatrolEntity);
        }
        YieldBlockEntity yieldBlockEntity = new YieldBlockEntity();
        BeanUtils.copyProperties(yieldBlockBasicInfo, yieldBlockEntity);
        return yieldBlockDao.save(yieldBlockEntity);
    }

    /**
     * 1.定时任务，每天早上九点中触发一次，检测巡查任务列表未处理任务，将其periodTime加1，
     * 2.超过15天转交上级领导
     * 传任务的每一级都需要留痕
     */
    @Scheduled(cron = "0 0 9 * * ?")
    @Transactional
    public void timer() {
        yieldPatrolDao.incAllPeriodTime();
        //检测检查超过十五天的未处理任务，转发给上级领导
        List<YieldPatrolEntity> patrolEntities = yieldPatrolDao.findAllOvertimeTask();
        patrolEntities.forEach(element -> submitUnableHandleTask(element, null, true));
    }

    /**
     * 1.供定时器调用
     * 2.供判断自己无法处理时手动提交上级处理时调用
     * 上传任务的每一级都需要留痕
     */
    public YieldPatrolEntity submitUnableHandleTask(YieldPatrolEntity entity, String taskId, Boolean isTimerInvoke) {
        YieldPatrolEntity element = isTimerInvoke ? entity : yieldPatrolDao.findById(Long.valueOf(taskId)).get();
        //如果是指派任务超时直接将任务提交给沈科
        String leader = yieldHeadDao.findLeader(element.getAgent());
        if (Objects.isNull(leader)) {
            throw new RuntimeException("无法提交任务，无法找到当前用户的上一级领导");
        }
        //创建上传任务
        YieldPatrolEntity patrolEntity = new YieldPatrolEntity();
        BeanUtils.copyProperties(element, patrolEntity, "id");
        patrolEntity.setPatrolMan(element.getAgent());//巡查对象变为经办人
        patrolEntity.setAgent(element.getIsAppointTask() == 1 ? "0" : leader);
        patrolEntity.setPatrolTime(new Date());
        patrolEntity.setPeriodTime(0);
        patrolEntity.setPrevTaskId(element.getId());
        patrolEntity.setHandleStatus(0);
        yieldPatrolDao.save(patrolEntity);
        //修改自己的任务信息将当前任务的状态修改为"已办事宜" (留痕)
        element.setHandleStatus(1);
        return yieldPatrolDao.save(element);
    }

    /**
     * 上级指定任务给下级(超时任务直接上交给沈科)
     */
    public YieldPatrolEntity appointTask(String leaderId, String subordinateId, String taskId, String leaderSuggestion) {
        YieldPatrolEntity yieldPatrolEntity = new YieldPatrolEntity();
        BeanUtils.copyProperties(yieldPatrolDao.findById(Long.valueOf(taskId)).get(), yieldPatrolEntity);
        yieldPatrolEntity.setIsAppointTask(1);
        yieldPatrolEntity.setLeaderSuggestion(leaderSuggestion);
        yieldPatrolEntity.setPatrolMan(subordinateId);
        yieldPatrolEntity.setAgent(leaderId);
        return yieldPatrolDao.save(yieldPatrolEntity);
    }

    /**
     * 上级获取还未处理的下级提交上来的任务
     */
    public List<YieldPatrolEntity> getUnhandleSubordinateUploadTask(String userId) {
        return yieldPatrolDao.getUnhandleSubordinateUploadTask(userId);
    }

    /**
     * 办结任务(任务可能提交了多级，需要全部修改状态)
     */
    public String concludeTask(String taskId) {
        YieldPatrolEntity yieldPatrolEntity = yieldPatrolDao.findById(Long.valueOf(taskId)).get();
        if (yieldPatrolEntity.getHandleStatus().equals(2)) {
            return "当前任务已办结，请确定ID是否正确!";
        } else {
            yieldPatrolDao.concludeTask(taskId);
            return "办结完成";
        }
    }

    /**
     * 获取用户的待办任务 (待办任务可能是用户查看了之后正在处理的，或者是提交给上级之后等待上级处理的状态)
     *
     * @param userId
     * @return
     */
    public List<YieldPatrolEntity> getPendingTask(String userId) {
        return yieldPatrolDao.getPendingTask(userId);
    }
}
