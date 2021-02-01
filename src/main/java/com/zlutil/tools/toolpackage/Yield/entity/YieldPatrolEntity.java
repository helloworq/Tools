package com.zlutil.tools.toolpackage.Yield.entity;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Data
@Entity
@Table(name = "omms_YieldPatrol")
@SequenceGenerator(name = "ID_SEQ", sequenceName = "SEQ_OMMS_HIBERNATE", allocationSize = 1)
public class YieldPatrolEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ID_SEQ")
    @Column(name = "ID", nullable = false)
    private Long id;
    //镇
    private String town;
    //村
    private String county;
    //地块id
    private String yieldId;
    //原利用情况
    private String originalUtilization;
    //现利用情况
    private String nowlUtilization;
    //巡查时间
    private Date patrolTime;
    //巡查人
    private String patrolMan;
    //从创建开始到处理前间隔的天数
    private String periodTime;
}
