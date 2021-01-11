package com.zlutil.tools.toolpackage.HotBoot;

import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Entity
@Table(name = "HotBoot")
@Data
public class HotBootEnity {
    @Id
    @GenericGenerator(name = "idGenerator", strategy = "uuid")
    @GeneratedValue(generator = "idGenerator")
    private String id;

    @Column(name = "modifyTime", length = 64)
    private String modifyTime;

    @Column(name = "FilePath", length = 64)
    private String FilePath;

    @Column(name = "FileName", length = 64)
    private String FileName;

    @Column(name = "FileMd5", length = 256)
    private String FileMd5;

    @Column(name = "fileExtension", length = 64)
    private String fileExtension;

    @Column(name = "size", length = 64)
    private String size;
}
