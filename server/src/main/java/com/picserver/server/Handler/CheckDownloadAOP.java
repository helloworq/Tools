package com.picserver.server.Handler;

import com.mongodb.client.gridfs.GridFSBucket;
import com.picserver.server.Annotation.CheckDownload;
import com.picserver.server.Config.ValueConfig;
import com.picserver.server.PicSaveService.TxtPicService;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileOutputStream;

@Slf4j
@Aspect
@Component
public class CheckDownloadAOP {

    @Autowired
    GridFSBucket gridFSBucket;

    @Autowired
    TxtPicService picService;

    @Autowired
    public ValueConfig valueConfig;

    /**
     * 定义切入点，切入点为com.example.demo.aop.AopController中的所有函数
     * 通过@Pointcut注解声明频繁使用的切点表达式
     */
    @Pointcut("@annotation(com.picserver.server.Annotation.CheckDownload)")
    public void BrokerAspect() {
    }

    /**
     * @description 使用环绕通知
     */
    @Around("BrokerAspect()")
    public String doAroundGame(ProceedingJoinPoint pjp) throws Throwable {
        Signature signature = pjp.getSignature();//此处joinPoint的实现类是MethodInvocationProceedingJoinPoint
        MethodSignature methodSignature = (MethodSignature) signature;//获取参数名
        CheckDownload checkDownload = methodSignature.getMethod().getAnnotation(CheckDownload.class);
        System.out.println(checkDownload.uploadObjectType());



        //获取picId
        Object picId = pjp.proceed();
        String id = picId.toString().trim();



        File file = new File(valueConfig.picSavePath + picId + ".jpg");
        if (!file.exists()) {
            FileOutputStream fileOutputStream = new FileOutputStream(file);

            gridFSBucket.downloadToStream(new ObjectId(id), fileOutputStream);

            //TODO 将文件id与路径信息存储到信息表
            picService.set(picId, file.getPath());

            fileOutputStream.close();
        }
        return picId.toString();
    }
}