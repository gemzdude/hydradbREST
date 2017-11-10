package io.pivotal.gemfire.toolsmiths.hdbr;

import io.pivotal.gemfire.toolsmiths.hdbr.data.gen.HydraRun;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface HydraRunRepo extends JpaRepository<HydraRun, Long> {
  @Query("SELECT max(id) FROM HydraRun")
  Integer maxRun();

  @Query("SELECT buildLocation FROM HydraRun where userName = :uname")
  List<String> regressionByUser(@Param("uname") String userName);

}
