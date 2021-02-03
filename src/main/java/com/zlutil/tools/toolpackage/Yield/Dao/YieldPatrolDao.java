package com.zlutil.tools.toolpackage.Yield.Dao;

import com.zlutil.tools.toolpackage.Yield.entity.YieldPatrolEntity;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import javax.transaction.Transactional;
import java.util.List;

@Transactional
public interface YieldPatrolDao extends CrudRepository<YieldPatrolEntity, Long> {

    @Modifying
    @Query(value = "update DIST.OMMS_YIELD_PATROL set PERIOD_TIME=(PERIOD_TIME+1) where HANDLE_STATUS=0", nativeQuery = true)
    void incAllPeriodTime();

    //获取全部未处理的超时任务
    @Query(value = "select * from DIST.OMMS_YIELD_PATROL where PERIOD_TIME>=15 and HANDLE_STATUS=0", nativeQuery = true)
    List<YieldPatrolEntity> findAllOvertimeTask();

    //获取村级及以上下级提交上来的任务
    @Query(value = "select * from DIST.OMMS_YIELD_PATROL where AGENT=?1 and HANDLE_STATUS=0", nativeQuery = true)
    List<YieldPatrolEntity> getUnhandleSubordinateUploadTask(String userId);

    //将指定id关联的多个任务状态修改为办结
    @Modifying
    @Query(value =
            "update OMMS_YIELD_PATROL\n" +
                    "set HANDLE_STATUS=2\n" +
                    "where id in (\n" +
                    "    select ID\n" +
                    "    from OMMS_YIELD_PATROL\n" +
                    "    start with ID = ?1\n" +
                    "    connect by prior PREV_TASK_ID = ID\n" +
                    ")", nativeQuery = true)
    void concludeTask(String taskId);

    @Query(value = "select * from DIST.OMMS_YIELD_PATROL where AGENT=?1 and HANDLE_STATUS=1",nativeQuery = true)
    List<YieldPatrolEntity> getPendingTask(String userId);
}
