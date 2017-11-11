package io.pivotal.gemfire.toolsmiths.hdbr;

import io.pivotal.gemfire.toolsmiths.hdbr.data.gen.HydraTestsuite;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface HydraTestsuiteRepo extends JpaRepository<HydraTestsuite, Long> {

  @Query("SELECT max(id) FROM HydraTestsuite")
  Integer maxSuite();

  // SELECT ID, NAME FROM HYDRA_TESTSUITE WHERE ID=?
  @Query("SELECT s.id as id, s.name as name FROM HydraTestsuite s WHERE s.id = :id")
  IdAndName getHydraTestsuiteIdAndNameById(@Param("id") Integer id);

  // SELECT ID, NAME, OS_TYPE, OS_INFO FROM HOST WHERE NAME=?
  @Query("SELECT s.id as id, s.name as name FROM HydraTestsuite s WHERE s.name = :name")
  IdAndName getHydraTestsuiteIdAndNameByName(@Param("name") String name);
}
