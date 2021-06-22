package com.zlutil.tools;

import com.alibaba.fastjson.JSON;
import com.mongodb.client.gridfs.GridFSBucket;
import com.zlutil.tools.controller.FileInfo;
import com.zlutil.tools.toolpackage.SycFile.PicService;
import com.zlutil.tools.toolpackage.Yield.Dao.YieldBlockDao;
import com.zlutil.tools.toolpackage.Yield.Dao.YieldHeadDao;
import com.zlutil.tools.toolpackage.Yield.Dao.YieldPatrolDao;
import com.zlutil.tools.toolpackage.Yield.dto.YieldBlockDTO;
import com.zlutil.tools.toolpackage.Yield.entity.YieldBlockEntity;
import com.zlutil.tools.toolpackage.Yield.entity.YieldHeadEntity;
import com.zlutil.tools.toolpackage.Yield.entity.YieldPatrolEntity;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.gridfs.GridFsTemplate;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@SpringBootTest
public class ServiceApplicationTests {

    @Autowired
    YieldHeadDao yieldHeadDao;

    @Autowired
    YieldBlockDao yieldBlockDao;

    @Autowired
    YieldPatrolDao yieldPatrolDao;

    @Autowired
    MongoTemplate mongoTemplate;

    @Autowired
    GridFsTemplate gridFsTemplate;

    @Autowired
    GridFSBucket gridFSBucket;

    @Autowired
    PicService picService;

    @Autowired
    RestTemplate restTemplate;

    @Test
    void testConvent() throws IOException {
    }

    @Test
    void testConvent2() {
        String collectionName = "threadSafeTest";

        FileInfo fileInfo = new FileInfo("21", "33", 11L);
        new Thread(() -> mongoTemplate.save(fileInfo, collectionName)).start();
    }

    @Test
    void testBeanUtils() throws IOException {
        List<YieldPatrolEntity> list = yieldPatrolDao.findAllOvertimeTask();
        list.stream().forEach(e -> {
            YieldPatrolEntity yieldPatrolEntity = new YieldPatrolEntity();
            BeanUtils.copyProperties(e, yieldPatrolEntity, "id");
            System.out.println(JSON.toJSONString(yieldPatrolEntity));
        });
    }

    @Test
    void findOvertimeTask() throws IOException {
        List<YieldPatrolEntity> list = yieldPatrolDao.findAllOvertimeTask();
        System.out.println(JSON.toJSONString(list));
    }

    @Test
    void test() throws IOException {
        String userId = yieldBlockDao.findFieldManager("2");
        System.out.println("当前地块管理者: " + userId);
        List<YieldHeadEntity> list = yieldHeadDao.findLeaderInfo(userId);
        System.out.println(JSON.toJSONString(list));
    }

    @Test
    void findSubordinateYieldBlockInfo() throws IOException {
        List<String> yieldId = yieldBlockDao.findSubordinateYieldId(3L);
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
        System.out.println(JSON.toJSONString(blockDTOS));
    }

    @Test
        //增加田长信息
    void addYieldHead() throws IOException {
        for (int i = 0; i < 10; i++) {
            YieldHeadEntity yieldHeadEntity = new YieldHeadEntity();
            yieldHeadEntity.setName(String.valueOf(i));
            yieldHeadEntity.setCounty(String.valueOf(i));
            yieldHeadEntity.setTown(String.valueOf(i));
            yieldHeadEntity.setVillage(String.valueOf(i));
            yieldHeadEntity.setTitle(String.valueOf(i));
            yieldHeadEntity.setLeader(String.valueOf(i));
            yieldHeadEntity.setYieldBlockId(String.valueOf(i));
            yieldHeadDao.save(yieldHeadEntity);
        }
    }
}
