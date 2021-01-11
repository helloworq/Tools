package com.zlutil.tools.toolpackage.HotBoot;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.RepositoryDefinition;

import javax.transaction.Transactional;

@RepositoryDefinition(domainClass = HotBootEnity.class, idClass = String.class)
public interface HotBootRepositry extends JpaRepository<HotBootEnity, String> {

    @Transactional
    @Modifying
    @Query(value = "delete from DIST.HOT_BOOT WHERE FILE_PATH=?1",nativeQuery = true)
    void deleteByFilePath(String filePath);
}