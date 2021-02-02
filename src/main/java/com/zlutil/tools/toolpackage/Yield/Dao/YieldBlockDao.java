package com.zlutil.tools.toolpackage.Yield.Dao;

import com.zlutil.tools.toolpackage.Yield.entity.YieldBlockEntity;
import com.zlutil.tools.toolpackage.Yield.entity.YieldHeadEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface YieldBlockDao extends CrudRepository<YieldBlockEntity, Long> {

    //找到当前用户所管下级全部地块信息
    @Query(value =
            "select YIELD_BLOCK_ID\n" +
                    "from (\n" +
                    "         select *\n" +
                    "         from OMMS_YIELD_HEAD\n" +
                    "         start with ID = ?1\n" +
                    "         connect by prior ID = LEADER\n" +
                    "     )\n" +
                    "where YIELD_BLOCK_ID is not null", nativeQuery = true)
    List<String> findSubordinateYieldId(Long userId);

    List<YieldBlockEntity> findAllByIdIn(List<Long> yieldBlockIds);

    //获取指定地块的直接管理者(村级副田长)
    @Query(value =
            "select ID\n" +
                    "from (\n" +
                    "         select distinct regexp_substr(YIELD_BLOCK_ID, '[^,]+', 1, level) as YIELD_BLOCK_ID, ID\n" +
                    "         from OMMS_YIELD_HEAD\n" +
                    "         where YIELD_BLOCK_ID is not null\n" +
                    "         connect by level <= regexp_count(YIELD_BLOCK_ID, '[^,]+')\n" +
                    "     ) where YIELD_BLOCK_ID=?1", nativeQuery = true)
    String findFieldManager(String yieldBlockId);

}
