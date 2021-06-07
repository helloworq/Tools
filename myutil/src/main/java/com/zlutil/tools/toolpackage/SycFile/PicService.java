package com.zlutil.tools.toolpackage.SycFile;

import java.io.IOException;

public interface PicService {

    void savePic(String md5, String contentType, byte[] bytes) throws IOException;

    String getPicRelativePath(String md5);
}
