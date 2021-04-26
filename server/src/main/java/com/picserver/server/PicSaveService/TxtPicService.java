package com.picserver.server.PicSaveService;

import cn.hutool.core.io.FileUtil;
import com.picserver.server.Config.ValueConfig;
import com.picserver.server.Config.WebTool;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;

@Slf4j
@Service
public class TxtPicService implements PicService<Object, String> {

    @Autowired
    public ValueConfig valueConfig;

    @Autowired
    WebTool webTool;

    static {
        //提前将txt内数据加载进内存减少频繁打开文件的消耗

    }

    @Override
    public String get(Object key) {
        return FileUtil.readUtf8Lines(valueConfig.txtSavePath)
                .stream()
                .filter(ele -> ele.split(">>>")[0].equals(key.toString().trim()))
                .map(ele -> webTool.getUrl() + valueConfig.ResourceHandlerPath + "/" + ele.substring(ele.lastIndexOf("\\") + 1))
                .findFirst().orElse(null);
    }

    @Override
    public String set(Object key, String value) {
        FileUtil.writeLines(Arrays.asList(key + ">>>" + value), valueConfig.txtSavePath, "UTF-8", true);
        return "success";
    }
}
