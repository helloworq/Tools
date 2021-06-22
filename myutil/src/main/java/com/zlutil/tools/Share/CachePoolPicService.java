package com.zlutil.tools.Share;

import com.zlutil.tools.toolpackage.SycFile.PicUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class CachePoolPicService implements PicService<String, String> {

    @Autowired
    PicUtil picUtil;

    @Override
    public String get(String key) throws IOException {
        return picUtil.getPicRelativePath(key);
    }

    @Override
    public String set(String key, String value) throws IOException {
        return null;
    }
}
