package com.zlutil.tools.toolpackage.Yield.Dao;

import com.zlutil.tools.toolpackage.Yield.entity.YieldHeadEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface YieldHeadDao extends CrudRepository<YieldHeadEntity, Long> {

    //根据用户id获取上级领导信息
    @Query(value =
            "select *\n" +
                    "from OMMS_YIELD_HEAD\n" +
                    "start with ID = ?1\n" +
                    "connect by ID = prior LEADER", nativeQuery = true)
    List<YieldHeadEntity> findLeaderInfo(String userId);

    @Query(value = "select leader from OMMS_YIELD_HEAD where id=?1", nativeQuery = true)
    String findLeader(String userId);
}
