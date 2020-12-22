package com.zlutil.tools.controller;

import com.alibaba.fastjson.JSON;
import com.zlutil.tools.toolpackage.Feign.ImsRestConfig;
import com.zlutil.tools.toolpackage.Feign.IndexValueQuery;
import com.zlutil.tools.toolpackage.ResponseUtil.ResponseData;
import com.zlutil.tools.toolpackage.ResponseUtil.ResponseUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GetData {
    @Autowired
    ImsRestConfig imsRestConfig;

    @RequestMapping("/getIndexValueQuery")
    public ResponseData getIndexValueQuery(){
        System.out.println(imsRestConfig.getIndexValueQuery());
        return ResponseUtil.success(imsRestConfig.getIndexValueQuery());
    }
}
