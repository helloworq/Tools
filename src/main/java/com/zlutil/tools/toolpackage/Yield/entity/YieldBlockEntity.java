package com.zlutil.tools.toolpackage.Yield.entity;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "omms_YieldBlock")
@SequenceGenerator(name = "ID_SEQ", sequenceName = "SEQ_OMMS_HIBERNATE", allocationSize = 1)
public class YieldBlockEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ID_SEQ")
    @Column(name = "ID", nullable = false)
    private Long id;
    //归属于哪个组(用户输入后存储)
    private String yieldGroup;
    //土地利用情况(好，中，差，其它)
    private Integer utilization;
    //具体利用情况(好(0)->水稻，旱粮，蔬菜||中(1)->养虾，养鱼，果树||差(2)->草皮，苗木，抛荒，建设占用||其它)
    private String specificUtilization;
    //巡查地块 (地图空间参考json存储) 存储时由于可能有多个地块需要list转string
    @Column(columnDefinition = "CLOB")
    private String geometry;
    //地块面积
    private String area;
    //气泡颜色 1红色/2绿色
    private Integer bubbleColor;
}
