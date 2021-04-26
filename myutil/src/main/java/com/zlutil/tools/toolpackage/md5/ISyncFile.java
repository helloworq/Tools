package com.zlutil.tools.toolpackage.md5;

import java.io.IOException;
import java.util.List;

public interface ISyncFile {

    /**
     * 获取文件md5值
     *
     * @param filePath
     * @return
     * @throws IOException
     */
    String getFileMd5Value(String filePath) throws IOException;

    /**
     * 获取路径下全部文件名
     *
     * @param filePath
     * @return
     */
    List<String> getFileList(String filePath);

    /**
     * 上传指定路径文件夹内文件 1.对比路径下全部文件的MD5值，相同跳过，不同则传入
     *
     * @param filePath
     */
    void uploadFolder(String filePath);
}
