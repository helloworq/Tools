package com.picserver.server.Config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class ValueConfig {

    @Value("${fastConfig.picSavePath}")
    public String picSavePath;

    @Value("${fastConfig.txtSavePath}")
    public String txtSavePath;

    @Value("${fastConfig.resourceHandlerPath}")
    public String ResourceHandlerPath;

}
