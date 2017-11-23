package io.pivotal.gemfire.toolsmiths.hydradb;

import io.pivotal.gemfire.toolsmiths.hydradb.data.hydra.Host;
import io.pivotal.gemfire.toolsmiths.hydradb.data.hydra.HydraRun;
import io.pivotal.gemfire.toolsmiths.hydradb.data.hydra.HydraTest;
import io.pivotal.gemfire.toolsmiths.hydradb.data.hydra.HydraTestDetail;
import io.pivotal.gemfire.toolsmiths.hydradb.data.hydra.HydraTestsuiteDetail;

import java.util.Date;
import java.util.List;
import java.util.Map;

public interface HydraDBClient {

    void setPort(Integer port);
    Integer getPort();

    public Host getHostByName(String name);

    public Host getHostById(Integer id);

    public Host createHost(String name, String osType, String osInfo);

    public Integer maxHostId();

    public HydraRun getHydraRunById(Integer id);

    public Integer maxHydraRunId();

    public HydraRun createHydraRun(String userName, String productVersion, String buildId,
                                   String svnRepository, String svnRevision, String javaVersion,
                                   String javaVendor, String javaHome, Boolean fullRegression,
                                   Integer regressionType, String comments, String buildLocation);

    public List<HydraRun> hydraRunSearch(String userName, String productVersion, String buildId,
                                         String svnRepository, String svnRevision, String javaVersion,
                                         String javaVendor);

    public Map<Integer, HydraRun> getHydraRunSet(List<Integer> list, String gemfireVersion,
                                                 String jdk, String jdkVendor, int svnRevision,
                                                 String branch, String buildUser);

    public HydraTest getHydraTestById(Integer hydraTestsuiteId);

    public Integer createHydraTest(String conf, String fullTestSpec, Integer hydraTestsuiteId);

    public HydraTest getHydraTestByFullTestSpecAndHydraTestsuiteId(String fullTestSpec, Integer hydraTestsuiteId);

    public HydraTestDetail createHydraTestDetail(String elapsedTime, String diskStr, String status,
                                                 String error, String bugNumber, long testId,
                                                 long testSuiteDetailId, int runId, String comment,
                                                 String tags);

    public HydraTestDetail getHydraTestDetail(long testId, long testSuiteDetailId, int runId);

    public void updateHydraTestDetailBugNumber(String tags, Integer id);

    public IdAndName getHydraTestsuiteIdAndNameById(Integer id);

    public IdAndName getHydraTestsuiteIdAndNameByName(String name);

    public HydraTestsuiteDetail getHydraTestsuiteDetail(Integer hydraTestsuiteId, Integer hydraRunId, Integer hostId, Date date);

    public TestSuiteInfo getTestSuiteInfo(long suiteId);

    public HydraTestsuiteDetail getHydraTestsuiteDetailById(Integer hydraTestsuiteId);

    public HydraTestsuiteDetail createHydraTestsuiteDetail(Date date, String elapsedTime, String diskUsage,
                                              String localConf, Integer hydraTestsuiteId,
                                              Integer hydraRunId, Integer hostId, String comment,
                                              String artifactLocation);

    public HydraHistory getHydraHistoryForBatteryTest(int id, Map<Integer, HydraRun> hydraRunList);

    public List<Integer> getHydraRunsForBatteryTest(int id, int numRuns);
}

