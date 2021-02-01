package com.zlutil.tools.toolpackage.Yield.entity;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "omms_YieldHead")
@SequenceGenerator(name = "ID_SEQ", sequenceName = "SEQ_OMMS_HIBERNATE", allocationSize = 1)
public class YieldHeadEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ID_SEQ")
    @Column(name = "ID", nullable = false)
    private Long id;
    //县城
    private String county;
    //镇
    private String town;
    //村
    private String village;
    //田长级别(县级，镇级，村级，村副级)
    //村副级以上的村，镇行政单位可为空
    private String title;
    //田长名字
    private String name;
    //上级领导
    private String leader;
    //负责的巡查地块id (地图空间参考json存储) 存储时由于可能有多个地块需要list转string
    //只有村级副田长有关联土地
    private String yieldBlockId;
}
