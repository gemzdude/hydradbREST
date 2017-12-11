package io.pivotal.gemfire.toolsmiths.hydradb.data.hydra.repo;

import io.pivotal.gemfire.toolsmiths.hydradb.data.hydra.HydraTestsuiteDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Date;

public interface HydraTestsuiteDetailRepo extends JpaRepository<HydraTestsuiteDetail, Long> {

  // SELECT ID FROM HYDRA_TESTSUITE_DETAIL WHERE HYDRA_TESTSUITE_ID=? AND HYDRA_RUN_ID=? AND HOST_ID=? AND DATE=?

  @Query("SELECT htsd FROM HydraTestsuiteDetail htsd WHERE hydraTestsuiteId=:hydraTestsuiteId AND hydraRunId=:hydraRunId AND hostId=:hostId AND date=:date")
  HydraTestsuiteDetail getHydraTestsuiteDetail(@Param("hydraTestsuiteId") Integer hydraTestsuiteId,
                                          @Param("hydraRunId") Integer hydraRunId,
                                          @Param("hostId") Integer hostId,
                                          @Param("date") Date date
  );

  HydraTestsuiteDetail getHydraTestsuiteDetailById(@Param("hydraTestsuiteId") Integer hydraTestsuiteId);

  // INSERT INTO HYDRA_TESTSUITE_DETAIL(ID, DATE, ELAPSED_TIME, DISK_USAGE, LOCAL_CONF, HYDRA_TESTSUITE_ID, HYDRA_RUN_ID, HOST_ID, COMMENT, ARTIFACT_LOCATION) VALUES(NEXTVAL('hydra_testsuite_detail_id_seq'), ?,?,?,?,?,?,?,?, ?)

//  @Query(value = "INSERT INTO HydraTestsuiteDetail(id, date, elapsedTime, diskUsage, localConf, hydraTestsuiteId, hydraRunId, hostId, comment, artifactLocation) VALUES(NEXTVAL('hydra_testsuite_detail_id_seq'), :date,:elapsedTime,:diskUsage,:localConf,:hydraTestsuiteId,:hydraRunId,:hostId,:comment,:artifactLocation)", nativeQuery = true)
//  void createHydraTestsuiteDetail(@Param("date") Date date,
//                                     @Param("elapsedTime") String elapsedTime,
//                                     @Param("diskUsage") String diskUsage,
//                                     @Param("localConf") String localConf,
//                                     @Param("hydraTestsuiteId") Integer hydraTestsuiteId,
//                                     @Param("hydraRunId") Integer hydraRunId,
//                                     @Param("hostId") Integer hostId,
//                                     @Param("comment") String comment,
//                                     @Param("artifactLocation") String artifactLocation
//  );

}
