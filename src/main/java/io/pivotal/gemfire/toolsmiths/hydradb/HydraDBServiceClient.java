package io.pivotal.gemfire.toolsmiths.hydradb;

import io.pivotal.gemfire.toolsmiths.hydradb.data.hydra.Host;
import io.pivotal.gemfire.toolsmiths.hydradb.data.hydra.HydraRun;
import io.pivotal.gemfire.toolsmiths.hydradb.data.hydra.HydraTest;
import io.pivotal.gemfire.toolsmiths.hydradb.data.hydra.HydraTestDetail;
import io.pivotal.gemfire.toolsmiths.hydradb.data.hydra.HydraTestsuiteDetail;
import org.springframework.dao.DataAccessException;

import java.util.Date;
import java.util.List;
import java.util.Map;

public class HydraDBServiceClient implements HydraDBService {

  private static final String endPointURL = "http://localhost:8080/hdb";

  public String getURL() { return endPointURL; }

  @Override
  public List<Host> getHostByName(String name) {
    return null;
  }

  @Override
  public Integer maxHostId() {
    return null;
  }

  @Override
  public Integer maxHydraRunId() {
    return null;
  }

  @Override
  public TestSuiteInfo getTestSuiteInfo(long suiteId) {
    return null;
  }

//  @Override
//  public HydraHistory getHistoryForBatteryTest(int id, int numRuns) {
//    return null;
//  }
//
//  @Override
//  public HydraHistory getHistoryForBatteryTest(int id, int numRuns, String gemfireVersion,
//                                               String jdk, String jdkVendor, int svnRevision,
//                                               String branch, String buildUser) {
//    return null;
//  }

  @Override
  public Map<Integer, HydraRun> getHydraRunSet(List<Integer> list, String gemfireVersion,
                                               String jdk, String jdkVendor, int svnRevision,
                                               String branch, String buildUser)
      throws DataAccessException {
    return null;
  }

  @Override
  public HydraHistory getHydraHistoryForBatteryTest(int id, Map<Integer, HydraRun> hydraRunList) {
    return null;
  }

  @Override
  public List<Integer> getHydraRunsForBatteryTest(int id, int numRuns) {
    return null;
  }

//  @Override
//  public List<TestDetail> getLastNTestDetailsForTestId(TestInfo test, int max, String status,
//                                                       String branch) {
//    return null;
//  }

//  @Override
//  public TestInfo getTestInfo(int id) {
//    return null;
//  }

  @Override
  public Host getHostById(Integer id) {
    return null;
  }

  @Override
  public void createHost(String name, String osType, String osInfo) {

  }

  @Override
  public HydraTestDetail createHydraTestDetail(String elapsedTime, String diskStr, String status,
                                               String error, String bugNumber, long testId,
                                               long testSuiteDetailId, int runId, String comment,
                                               String tags) {
    return null;
  }

  @Override
  public HydraTestDetail getHydraTestDetail(long testId, long testSuiteDetailId, int runId) {
    return null;
  }

  @Override
  public void updateHydraTestDetailBugNumber(String tags, Integer id) {

  }

  @Override
  public HydraTest getHydraTestByFullTestSpecAndHydraTestsuiteId(String fullTestSpec,
                                                                 Integer hydraTestsuiteId) {
    return null;
  }

  @Override
  public HydraTest getHydraTestById(Integer hydraTestsuiteId) {
    return null;
  }

  @Override
  public Integer createHydraTest(String conf, String fullTestSpec, Integer hydraTestsuiteId) {
    return null;
  }

  @Override
  public HydraTestsuiteDetail getHydraTestsuiteDetail(Integer hydraTestsuiteId, Integer hydraRunId,
                                                      Integer hostId, Date date) {
    return null;
  }

  @Override
  public HydraTestsuiteDetail getHydraTestsuiteDetailById(Integer hydraTestsuiteId) {
    return null;
  }

  @Override
  public Integer createHydraTestsuiteDetail(Date date, String elapsedTime, String diskUsage,
                                            String localConf, Integer hydraTestsuiteId,
                                            Integer hydraRunId, Integer hostId, String comment,
                                            String artifactLocation) {
    return null;
  }

  @Override
  public IdAndName getHydraTestsuiteIdAndNameById(Integer id) {
    return null;
  }

  @Override
  public IdAndName getHydraTestsuiteIdAndNameByName(String name) {
    return null;
  }

  @Override
  public HydraRun getHydraRunById(Integer id) {
    return null;
  }

  @Override
  public HydraRun createHydraRun(String userName, String productVersion, String buildId,
                                 String svnRepository, String svnRevision, String javaVersion,
                                 String javaVendor, String javaHome, Date date,
                                 Integer regressionType, String comments, String buildLocation) {
    return null;
  }
}
