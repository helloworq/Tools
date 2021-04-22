package com.zlutil.tools.toolpackage.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @desc: 经纪人切面
 * @author: CSH
 **/
@Aspect
@Component
public class AroundAOP {

    public static HashMap<String, String> map;

    static {
        map = new HashMap<>();
        map.put("1", "http://www.something.com/img/" + UUID.randomUUID());
        map.put("2", "http://www.something.com/img/" + UUID.randomUUID());
        map.put("3", "http://www.something.com/img/" + UUID.randomUUID());
        map.put("4", "http://www.something.com/img/" + UUID.randomUUID());
        map.put("5", "http://www.something.com/img/" + UUID.randomUUID());
        map.put("6", "http://www.something.com/img/" + UUID.randomUUID());
        map.put("7", "http://www.something.com/img/" + UUID.randomUUID());
        map.put("8", "http://www.something.com/img/" + UUID.randomUUID());
    }

    /**
     * 定义切入点，切入点为com.example.demo.aop.AopController中的所有函数
     * 通过@Pointcut注解声明频繁使用的切点表达式
     */
    @Pointcut("@annotation(com.zlutil.tools.toolpackage.aop.Cut)")
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
            //复制入参数据供剔除
            List<Object> handledPicIds = new LinkedList<>(picIds);
            //筛选出外置服务已有数据
            List<Object> listUrl = picIds.stream()
                    .map(ele -> {
                        String url = map.get(ele);
                        if (Objects.nonNull(url)) {
                            handledPicIds.remove(ele);
                        }
                        return url;
                    })
                    .filter(Objects::nonNull)
                    .collect(Collectors.toList());
            //外置服务不存在的文件在原有代码中处理
            Object[] objects;

            if (picIds.size() > 0) {
                //重新包装入参
                objects = new Object[]{handledPicIds};
                //获取返回值
                Object obj = pjp.proceed(objects);
                //组装数据
                listUrl.addAll((Collection<?>) obj);
            }

            return listUrl.stream().map(Object::toString).collect(Collectors.toList());
        } catch (Throwable e) {
            e.printStackTrace();
            System.out.println("异常通知");
        }
        return null;
    }
}
