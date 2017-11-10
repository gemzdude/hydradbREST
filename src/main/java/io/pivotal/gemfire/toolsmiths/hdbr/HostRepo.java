package io.pivotal.gemfire.toolsmiths.hdbr;

import io.pivotal.gemfire.toolsmiths.hdbr.data.gen.Host;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface HostRepo extends JpaRepository<Host, Long> {

//  @Query("INSERT INTO Host(id, name, osType, osInfo) VALUES(NEXTVAL('host_id_seq'), :name, :osType, :osInfo)")
//  public void createHost(@Param("name") String name, @Param("osType") String osType, @Param("osInfo") String osInfo);

  @Query("SELECT id, name, osType, osInfo FROM Host WHERE name = :name")
  Host getHostByName(@Param("name") String name);

  @Query("SELECT h FROM Host h WHERE id = :id")
  Host getHostById(@Param("id") Integer id);

  @Query("SELECT max(id) FROM Host")
  Integer maxHost();

}
