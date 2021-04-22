package com.zlutil.tools.toolpackage.aop;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class StrogeService {
    @Cut
    public List<String> createUrlByPicId(List<String> picIds) {
        System.out.println("本地处理");

        return picIds.stream().map(e -> "http://www.something.com/img/" + UUID.randomUUID())
                .collect(Collectors.toList());
    }
}
