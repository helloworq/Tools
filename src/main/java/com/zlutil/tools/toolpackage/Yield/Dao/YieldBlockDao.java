package com.zlutil.tools.toolpackage.Yield.Dao;

import com.zlutil.tools.toolpackage.Yield.dto.YieldBlockDTO;
import com.zlutil.tools.toolpackage.Yield.entity.YieldBlockEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface YieldBlockDao extends CrudRepository<YieldBlockEntity, Long> {

    //找到当前用户所管下级全部地块信息
    @Query(value = "select * from (\n" +
            "                  select *\n" +
            "                  from OMMS_YIELD_HEAD\n" +
            "                  start with ID = ?1\n" +
            "                  connect by prior ID = LEADER\n" +
            "              )where YIELD_BLOCK_ID is not null ", nativeQuery = true)
    List<YieldBlockDTO> findSubordinateBlockInfo(String userId);
}
