package io.pivotal.gemfire.toolsmiths.hydradb;

import io.pivotal.gemfire.toolsmiths.hydradb.data.hydra.Host;
import io.pivotal.gemfire.toolsmiths.hydradb.data.hydra.HydraRun;
import io.pivotal.gemfire.toolsmiths.hydradb.data.hydra.HydraTest;
import io.pivotal.gemfire.toolsmiths.hydradb.data.hydra.HydraTestDetail;
import io.pivotal.gemfire.toolsmiths.hydradb.data.hydra.HydraTestDetailExt;
import io.pivotal.gemfire.toolsmiths.hydradb.data.hydra.HydraTestsuite;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Map;

public interface HydraDBService {

  /* Host methods */

  ResponseEntity<Host> getHostByName(String name);
  ResponseEntity<Host> getHostById(Integer id);
  ResponseEntity<Host> createHost(String name, String osType, String osInfo);
  ResponseEntity<Host> getOrCreateHost(String name, String osType, String osInfo);
  ResponseEntity<Integer> maxHostId();

  /* HydraRun methods */

  ResponseEntity<HydraRun> getHydraRunById(Integer id);

  ResponseEntity<Integer> maxHydraRunId();

  ResponseEntity<HydraRun> getOrCreateHydraRun(String userName,
                          String productVersion,
                          String buildId,
                          String svnRepository,
                          String svnRevision,
                          String javaVersion,
                          String javaVendor,
                          String javaHome,
                          Boolean fullRegression,
                          Integer regressionType,
                          String comments,
                          String buildLocation
  );

  ResponseEntity<List<HydraRun>> hydraRunSearch(String userName, String productVersion, String buildId,
                          String svnRepository, String svnRevision, String javaVersion,
                          String javaVendor);

//  ResponseEntity<Map<Integer, HydraRun>> getHydraRunSet(List<Integer> list,
//                                        String gemfireVersion, String jdk, String jdkVendor, int svnRevision,
//                                        String branch, String buildUser);
//
//  ResponseEntity<List<Integer>> getHydraRunsForBatteryTest(int id, int numRuns);

  /* HydraTest methods */

  ResponseEntity<HydraTest> getHydraTestById(Integer hydraTestsuiteId);

  ResponseEntity<HydraTest> getOrCreateHydraTest(String conf,
                                            String fullTestSpec,
                                            Integer hydraTestsuiteId
  );

  ResponseEntity<HydraTest> createHydraTest(String conf,
                          String fullTestSpec,
                          Integer hydraTestsuiteId
  );

  ResponseEntity<HydraTest> getHydraTestByFullTestSpecAndHydraTestsuiteId(String fullTestSpec,
                                                          Integer hydraTestsuiteId
  );

  /* HydraTestDetail methods */

  ResponseEntity<HydraTestDetail> createHydraTestDetail(String elapsedTime,
                                        String diskStr,
                                        String status,
                                        String error,
                                        String bugNumber,
                                        Integer testId,
                                        Integer testSuiteDetailId,
                                        Integer runId,
                                        String comment,
                                        String tags
  );

  ResponseEntity<HydraTestDetail> getHydraTestDetail(Integer testId,
                                     Integer testSuiteDetailId,
                                     Integer runId
  );

  ResponseEntity<HydraTestDetailExt> createHydraTestDetailExt(Long testDetailId, String logLocation);

  void updateHydraTestDetailBugNumber(String tags,
                                      Integer id
  );

  /* HydraTestSuite methods */

  ResponseEntity<HydraTestsuite> getHydraTestsuiteById(Integer id);
//
  ResponseEntity<HydraTestsuite> getHydraTestsuiteByName(String name);
//
//  HydraTestsuiteDetail getHydraTestsuiteDetail(Integer hydraTestsuiteId,
//                                               Integer hydraRunId,
//                                               Integer hostId,
//                                               Date date
//  );
//
//  public TestSuiteInfo getTestSuiteInfo(long suiteId);
//
//  /* HydraTestSuiteDetail methods */
//
//  HydraTestsuiteDetail getHydraTestsuiteDetailById(Integer hydraTestsuiteId);
//
//  HydraTestsuiteDetail createHydraTestsuiteDetail(Date date,
//                                     String elapsedTime,
//                                     String diskUsage,
//                                     String localConf,
//                                     Integer hydraTestsuiteId,
//                                     Integer hydraRunId,
//                                     Integer hostId,
//                                     String comment,
//                                     String artifactLocation
//  );

  /* Hydra History methods */

  ResponseEntity<HydraHistory> getHydraHistoryForBatteryTest(int id, Map<Integer, HydraRun> hydraRunList);

  /* Misc test detail methods */

  // public List<TestDetail> getLastNTestDetailsForTestId(TestInfo test, int max, String status, String branch);

//  TestInfo getTestInfo(int id);

  /* Various SQL clauses for building statements */

  public  static final String DELETE_HYDRA_TESTSUITE_DETAIL_SQL = "DELETE FROM HYDRA_TESTSUITE_DETAIL";
  public  static final String DELETE_HYDRA_TEST_SQL             = "DELETE FROM HYDRA_TEST_DETAIL";
  public  static final String DELETE_HYDRA_TEST_EXT_SQL         = "DELETE FROM HYDRA_TEST_DETAIL_EXT";
  public  static final String SELECT_HYDRA_TEST_SQL             = "SELECT ID, CONF, FULL_TEST_SPEC, HYDRA_TESTSUITE_ID FROM HYDRA_TEST";
  public  static final String SELECT_HYDRA_TEST_DETAIL_SQL      = " SELECT HTD.ID, ELAPSED_TIME, DISK_USAGE, STATUS, SUBSTR(ERROR,0,9000), BUG_NUMBER, HYDRA_TEST_ID, HYDRA_TESTSUITE_DETAIL_ID, HYDRA_RUN_ID, COMMENT, LOG_LOCATION, TAGS FROM HYDRA_TEST_DETAIL HTD LEFT OUTER JOIN  HYDRA_TEST_DETAIL_EXT HTDE ON ( HTD.ID = HTDE.ID )";
  public  static final String HYDRA_RUN_QUERY                   = "SELECT ID, USER_NAME, PRODUCT_VERSION, BUILD_ID, SVN_REPOSITORY, SVN_REVISION, JAVA_VERSION, JAVA_VENDOR, JAVA_HOME, DATE, REGRESSION_TYPE, COMMENTS, BUILD_LOCATION FROM HYDRA_RUN";
  public  static final String BUG_BY_BUG_NUMBER_SQL             = "SELECT ID, BUG_NUMBER, TAGS FROM HYDRA_TEST_DETAIL WHERE BUG_NUMBER = ? AND HYDRA_RUN_ID = ?";
  public  static final String SELECT_HYDRA_TESTSUITE_DETAIL     = "SELECT ID, DATE, ELAPSED_TIME, DISK_USAGE, LOCAL_CONF, HYDRA_TESTSUITE_ID, HYDRA_RUN_ID, HOST_ID, COMMENT, ARTIFACT_LOCATION FROM HYDRA_TESTSUITE_DETAIL";
  public  static final String SELECT_HOST                       = "SELECT ID, NAME, OS_TYPE, OS_INFO FROM HOST";
  public  static final String SELECT_HYDRA_TESTSUITE            = "SELECT ID, NAME FROM HYDRA_TESTSUITE";
}
