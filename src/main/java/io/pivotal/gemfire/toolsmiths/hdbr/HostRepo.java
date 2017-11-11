package io.pivotal.gemfire.toolsmiths.hdbr;

import io.pivotal.gemfire.toolsmiths.hdbr.data.gen.Host;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface HostRepo extends JpaRepository<Host, Long> {

  // INSERT INTO HOST(ID, NAME, OS_TYPE, OS_INFO) VALUES(NEXTVAL('host_id_seq'), ?,?,?)
  @Query(value = "INSERT INTO Host(id, name, osType, osInfo) VALUES(NEXTVAL('host_id_seq'), :name, :osType, :osInfo)", nativeQuery = true)
  public void createHost(@Param("name") String name, @Param("osType") String osType, @Param("osInfo") String osInfo);

  // SELECT ID, NAME, OS_TYPE, OS_INFO FROM HOST WHERE NAME=?
  @Query("SELECT h FROM Host h WHERE name = :name")
  Host getHostByName(@Param("name") String name);

  // SELECT ID, NAME, OS_TYPE, OS_INFO FROM HOST WHERE ID=?
  @Query("SELECT h FROM Host h WHERE id = :id")
  Host getHostById(@Param("id") Integer id);

  @Query("SELECT max(id) FROM Host")
  Integer maxHost();

}
