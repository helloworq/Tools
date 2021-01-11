package com.zlutil.tools.toolpackage.HotBoot;

import lombok.Data;

@Data
public class HotBootDTO {
    public String id;
    public String modifyTime;
    public String FilePath;
    public String FileName;
    public String FileMd5;
    private String size;
    private String nodeLength;
    private String count;
    private String fileExtension;
    private String type;
}
