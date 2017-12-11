package io.pivotal.gemfire.toolsmiths.hydradb.data.hydra.repo;

import io.pivotal.gemfire.toolsmiths.hydradb.data.hydra.Host;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface HostRepo extends JpaRepository<Host, Long> {

  Host[] getHostByName(@Param("name") String name);

  Host getHostById(@Param("id") Integer id);

  Host[] getHostByNameAndOsTypeAndOsInfo(String name, String osType, String osInfo);

  @Query(value = "select max(id) from Host")
  Integer maxId();

}
