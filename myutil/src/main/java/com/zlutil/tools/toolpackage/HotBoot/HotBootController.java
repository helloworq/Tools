package com.zlutil.tools.toolpackage.HotBoot;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
public class HotBootController {
    @Autowired
    HotBootUtil hotBootUtil;

    /**
     * 只允许初始的时候调用一次！
     *
     * @throws IOException
     */
    @RequestMapping("/hotbootBootStrap")
    public void start() throws IOException {
        String filePath="C:\\Users\\12733\\Desktop\\HotBoot";
        hotBootUtil.initMonitorStatus(filePath);
    }

    /**
     * 只允许初始的时候调用一次！
     *
     * @throws IOException
     */
    @RequestMapping("/getFileStructureTree")
    public Object getFileStructureTree() throws IOException {
        String filePath="C:\\Users\\12733\\Desktop\\HotBoot";
        return hotBootUtil.dir2json(filePath);
    }
}
