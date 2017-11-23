package io.pivotal.gemfire.toolsmiths.hydradb.data.hydra.repo;

import io.pivotal.gemfire.toolsmiths.hydradb.data.hydra.Host;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface HostRepo extends JpaRepository<Host, Long> {

  // INSERT INTO HOST(ID, NAME, OS_TYPE, OS_INFO) VALUES(NEXTVAL('host_id_seq'), ?,?,?)
  @Query(value = "INSERT INTO Host(name, os_Type, os_Info) VALUES(:name, :osType, :osInfo)", nativeQuery = true)
  @Modifying
  @Transactional
  void createHost(@Param("name") String name, @Param("osType") String osType, @Param("osInfo") String osInfo);

  // SELECT ID, NAME, OS_TYPE, OS_INFO FROM HOST WHERE NAME=?
//  @Query("SELECT h FROM Host h WHERE name = :name")
  List<Host> getHostByName(@Param("name") String name);

  // SELECT ID, NAME, OS_TYPE, OS_INFO FROM HOST WHERE ID=?
//  @Query(value = "SELECT h FROM Host h WHERE id = :id", nativeQuery = false)
  Host getHostById(@Param("id") Integer id);

  @Query(value = "select max(id) from Host")
  Integer maxId();

}
