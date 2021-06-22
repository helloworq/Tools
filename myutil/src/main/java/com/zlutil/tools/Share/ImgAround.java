package com.zlutil.tools.Share;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.stream.Collectors;

/**
 * 处理图片读取
 */
@Aspect
@Component
public class ImgAround {

    @Autowired
    PicService picService;

    /**
     * 定义切入点，切入点为com.example.demo.aop.AopController中的所有函数
     * 通过@Pointcut注解声明频繁使用的切点表达式
     */
    @Pointcut("@annotation(com.zlutil.tools.Share.Img2UrlSpeedUp)")
    public void BrokerAspect() {
    }

    /**
     * @description 使用环绕通知
     */
    @Around("BrokerAspect()")
    public List<String> doAroundGame(ProceedingJoinPoint pjp) {
        try {
            //处理外置服务中已存在的文件
            //处理入参
            List<Object> picIds =
                    Arrays.stream(pjp.getArgs()[0]
                            .toString()
                            .replace("[", "")
                            .replace("]", "")
                            .split(","))
                            .map(String::trim).collect(Collectors.toList());

            List<Object> listUrl = new ArrayList<>();

            Iterator<Object> it = picIds.iterator();
            while (it.hasNext()) {
                String str = it.next().toString().trim();
                String url = picService.get(str);
                if (Objects.nonNull(url)) {
                    listUrl.add(url);
                    it.remove();
                }
            }

            //外置服务不存在的文件在原有代码中处理
            Object[] objects;

            if (picIds.size() > 0) {
                //重新包装入参
                objects = new Object[]{picIds};
                //获取返回值
                Object obj = pjp.proceed(objects);
                //组装数据
                listUrl.addAll((Collection<?>) obj);
            }
            return listUrl.stream().map(Object::toString).collect(Collectors.toList());
        } catch (Throwable e) {
            e.printStackTrace();
        }
        return null;
    }
}
