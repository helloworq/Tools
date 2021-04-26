package com.zlutil.tools.toolpackage.ZipReader;

import lombok.Data;

import java.util.List;

@Data
public class FileEntity {
    private String id;
    private String fileName;
    private String filePath;//相对路径
    private List<FileEntity> child;
    private String parentCode;
}
