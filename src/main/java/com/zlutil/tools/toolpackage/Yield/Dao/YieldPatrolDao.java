package com.zlutil.tools.toolpackage.Yield.Dao;

import com.zlutil.tools.toolpackage.Yield.entity.YieldPatrolEntity;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import javax.transaction.Transactional;

@Transactional
public interface YieldPatrolDao extends CrudRepository<YieldPatrolEntity, Long> {

    @Modifying
    @Query(value = "update dist.OMMS_YIELD_PATROL set PERIOD_TIME=(PERIOD_TIME+1)", nativeQuery = true)
    void incAllPeriodTime();

    //15天未处理任务转交上级
    @Query(value =
            "update OMMS_YIELD_PATROL\n" +
                    "set agent = case\n" +
                    "                when PERIOD_TIME < 15 then AGENT\n" +
                    "                else  (\n" +
                    "                    select LEADER\n" +
                    "                    from OMMS_YIELD_HEAD\n" +
                    "                    where id = agent) end", nativeQuery = true)
    void checkUnhandleTaskAndUpload();
}
