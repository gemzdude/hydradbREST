package io.pivotal.gemfire.toolsmiths.hydradb.data.hydra.repo;

import io.pivotal.gemfire.toolsmiths.hydradb.data.hydra.HydraTestDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface HydraTestDetailRepo extends JpaRepository<HydraTestDetail, Long> {

  // INSERT INTO HYDRA_TEST_DETAIL(ID, ELAPSED_TIME, DISK_USAGE, STATUS, ERROR, BUG_NUMBER, HYDRA_TEST_ID, HYDRA_TESTSUITE_DETAIL_ID, HYDRA_RUN_ID,COMMENT,TAGS) VALUES(NEXTVAL('hydra_test_detail_id_seq'),?,?,?,?,?,?,?,?,?,?)

  @Query(value = "INSERT INTO HydraTestDetail htd (id, elapsedTime, diskUsage, status, error, bugNumber, hydraTestId, hydraTestsuiteDetailId, hydraRunId,comment,tags) VALUES(NEXTVAL('hydra_test_detail_id_seq'),:elapsedTime,:diskStr,:status,:error,:bugNumber,:testId,:testSuiteDetailId,:runId,:comment,:tags)", nativeQuery = true)
  HydraTestDetail createHydraTestDetail(@Param("elapsedTime") String elapsedTime,
                           @Param("diskStr") String diskStr,
                           @Param("status") String status,
                           @Param("error") String error,
                           @Param("bugNumber") String bugNumber,
                           @Param("testId") long testId,
                           @Param("testSuiteDetailId") long testSuiteDetailId,
                           @Param("runId") int runId,
                           @Param("comment") String comment,
                           @Param("tags") String tags
                           );


  // SELECT HTD.ID, BUG_NUMBER,COMMENT, STATUS,TAGS FROM HYDRA_TEST_DETAIL HTD   WHERE HYDRA_TEST_ID=? AND HYDRA_TESTSUITE_DETAIL_ID=? AND HYDRA_RUN_ID=?

  @Query("SELECT htd FROM HydraTestDetail htd   WHERE testId=:testId AND testSuiteDetailId=:testSuiteDetailId AND runId=:runId")
  HydraTestDetail getHydraTestDetail(@Param("testId") long testId,
                           @Param("testSuiteDetailId") long testSuiteDetailId,
                           @Param("runId") int runId
  );

  // UPDATE HYDRA_TEST_DETAIL SET TAGS=? WHERE ID=?
  @Query("UPDATE HydraTestDetail SET TAGS=:tags WHERE ID=:id")
  void updateHydraTestDetailBugNumber(@Param("tags") String tags,
                                      @Param("id") Integer id
  );
}