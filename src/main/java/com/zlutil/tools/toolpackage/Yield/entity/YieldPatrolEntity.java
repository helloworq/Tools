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
    private String village;
    //地块id
    private String yieldId;
    //原利用情况
    private Integer originalUtilization;
    //现利用情况
    private Integer nowlUtilization;
    //巡查时间
    private Date patrolTime;
    //巡查人
    private String patrolMan;
    //经办人(此字段挂钩patrolMan的领导，从村级田长逐级到县级田长)
    private String agent;
    //从创建开始到处理前间隔的天数
    private Integer periodTime;
    //处理状态(0->未处理,1->待办,2->办结)
    private Integer handleStatus;
    //每一级的操作需要留痕，每一级都有权限处理任务，故设置一个任务id字段关联相关任务，达到链式处理效果
    private Long prevTaskId;
}
