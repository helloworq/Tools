//package com.zlutil.tools;
//
//import com.alibaba.fastjson.JSON;
//import com.zlutil.tools.toolpackage.Yield.Dao.YieldBlockDao;
//import com.zlutil.tools.toolpackage.Yield.Dao.YieldHeadDao;
//import com.zlutil.tools.toolpackage.Yield.Dao.YieldPatrolDao;
//import com.zlutil.tools.toolpackage.Yield.dto.YieldBlockDTO;
//import com.zlutil.tools.toolpackage.Yield.entity.YieldBlockEntity;
//import com.zlutil.tools.toolpackage.Yield.entity.YieldHeadEntity;
//import com.zlutil.tools.toolpackage.Yield.entity.YieldPatrolEntity;
//import org.jodconverter.DocumentConverter;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.BeanUtils;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//
//import javax.servlet.http.HttpServletResponse;
//import java.io.File;
//import java.io.IOException;
//import java.util.ArrayList;
//import java.util.Arrays;
//import java.util.List;
//import java.util.stream.Collectors;
//
//@SpringBootTest
//public class ServiceApplicationTests {
//
//    @Autowired
//    YieldHeadDao yieldHeadDao;
//
//    @Autowired
//    YieldBlockDao yieldBlockDao;
//
//    @Autowired
//    YieldPatrolDao yieldPatrolDao;
//
//    @Autowired
//    private DocumentConverter converter;
//
//    @Autowired
//    private HttpServletResponse response;
//
//    @Test
//    void testConvent(){
//        File file = new File("C:\\Users\\12733\\Desktop\\遂宁市国土空间规划“一张图”实施监督信息系统项目-数据库设计说明书.doc");//需要转换的文件
//        try {
//            File newFile = new File("C:\\Users\\12733\\Desktop\\c.pdf");//转换之后文件生成的地址
//            if (!newFile.exists()) {
//                newFile.mkdirs();
//            }
//            //文件转化
//            converter.convert(file).to(newFile).execute();
//            //使用response,将pdf文件以流的方式发送的前段
////            ServletOutputStream outputStream = response.getOutputStream();
////            InputStream in = new FileInputStream(new File("D:/obj-pdf/hello.pdf"));// 读取文件
////            // copy文件
////            MyIOUtil.inputStreamWriteToOutputStream(in, outputStream);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//
//    @Test
//    void testBeanUtils() throws IOException {
//        List<YieldPatrolEntity> list = yieldPatrolDao.findAllOvertimeTask();
//        list.stream().forEach(e -> {
//            YieldPatrolEntity yieldPatrolEntity = new YieldPatrolEntity();
//            BeanUtils.copyProperties(e, yieldPatrolEntity, "id");
//            System.out.println(JSON.toJSONString(yieldPatrolEntity));
//        });
//    }
//
//    @Test
//    void findOvertimeTask() throws IOException {
//        List<YieldPatrolEntity> list = yieldPatrolDao.findAllOvertimeTask();
//        System.out.println(JSON.toJSONString(list));
//    }
//
//    @Test
//    void test() throws IOException {
//        String userId = yieldBlockDao.findFieldManager("2");
//        System.out.println("当前地块管理者: " + userId);
//        List<YieldHeadEntity> list = yieldHeadDao.findLeaderInfo(userId);
//        System.out.println(JSON.toJSONString(list));
//    }
//
//    @Test
//    void findSubordinateYieldBlockInfo() throws IOException {
//        List<String> yieldId = yieldBlockDao.findSubordinateYieldId(3L);
//        List<Long> filter = new ArrayList<>();
//        for (String e : yieldId) {
//            filter.addAll(new ArrayList<>(Arrays.asList(e.split(",")))
//                    .stream()
//                    .map(Long::valueOf)
//                    .collect(Collectors.toList()));
//        }
//        List<YieldBlockEntity> blockEntities = yieldBlockDao.findAllByIdIn(filter);
//        List<YieldBlockDTO> blockDTOS = blockEntities.stream().map(d -> {
//            YieldBlockDTO yieldBlockDTO = new YieldBlockDTO();
//            BeanUtils.copyProperties(d, yieldBlockDTO);
//            return yieldBlockDTO;
//        }).collect(Collectors.toList());
//        System.out.println(JSON.toJSONString(blockDTOS));
//    }
//
//    @Test
//        //增加田长信息
//    void addYieldHead() throws IOException {
//        for (int i = 0; i < 10; i++) {
//            YieldHeadEntity yieldHeadEntity = new YieldHeadEntity();
//            yieldHeadEntity.setName(String.valueOf(i));
//            yieldHeadEntity.setCounty(String.valueOf(i));
//            yieldHeadEntity.setTown(String.valueOf(i));
//            yieldHeadEntity.setVillage(String.valueOf(i));
//            yieldHeadEntity.setTitle(String.valueOf(i));
//            yieldHeadEntity.setLeader(String.valueOf(i));
//            yieldHeadEntity.setYieldBlockId(String.valueOf(i));
//            yieldHeadDao.save(yieldHeadEntity);
//        }
//    }
//
//}
