package com.picserver.server.PicSaveService;

import com.picserver.server.Config.ValueConfig;
import com.picserver.server.Config.WebTool;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.Arrays;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

@Slf4j
@Service
public class TxtPicService implements PicService<Object, String> {

    public TxtPicService() {
    }

    @Autowired
    public ValueConfig valueConfig;

    @Autowired
    WebTool webTool;

    private Map<String, String> txtLines = null;

    @Override
    public String get(Object key) throws IOException {
        //将文件内容提前加载到内存加快处理速度，使用nio api减少第三方库依赖
        if (Objects.isNull(this.txtLines)) {
            //换用map存储，加快速度
            this.txtLines = Files.readAllLines(Paths.get(valueConfig.txtSavePath), StandardCharsets.UTF_8)
                    .stream()
                    .collect(Collectors.toMap(x -> x.split(">>>")[0], y -> y.split(">>>")[1]));
        }

        String path = this.txtLines.get(key.toString().trim());

        return webTool.getUrl() + valueConfig.ResourceHandlerPath + "/" + path.substring(path.lastIndexOf("\\") + 1);
    }

    @Override
    public String set(Object key, String value) throws IOException {
        Files.write(Paths.get(valueConfig.txtSavePath), Arrays.asList(key + ">>>" + value), StandardCharsets.UTF_8, StandardOpenOption.APPEND);
        return "success";
    }
}
