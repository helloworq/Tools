package com.zlutil.tools.toolpackage.HotBoot;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Data
@Entity
@Table(name = "HotBoot")
public class HotBootEnity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ID", nullable = false)
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

    @Column(name = "fileSize", length = 64)
    private String fileSize;
}
