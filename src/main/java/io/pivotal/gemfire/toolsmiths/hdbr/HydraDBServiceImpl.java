package io.pivotal.gemfire.toolsmiths.hdbr;

import io.pivotal.gemfire.toolsmiths.hdbr.data.gen.Host;
import io.pivotal.gemfire.toolsmiths.hdbr.data.gen.HydraRun;
import io.pivotal.gemfire.toolsmiths.hdbr.data.gen.HydraTestDetail;
import io.pivotal.gemfire.toolsmiths.hdbr.data.gen.HydraTestsuiteDetail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class HydraDBServiceImpl implements HydraDBService {
  @Autowired
  HydraRunRepo hydraRunRepo;

  @Autowired
  HydraTestsuiteRepo hydraTestsuiteRepo;

  @Autowired
  HydraTestsuiteDetailRepo hydraTestsuiteDetailRepo;

  @Autowired
  HydraTestDetailRepo hydraTestDetailRepo;

  @Autowired
  HydraTestRepo hydraTestRepo;

  @Autowired
  HostRepo hostRepo;

  public Integer maxRun() {
    return hydraRunRepo.maxRun();
  }

  public HydraRun createHydraRun(String userName,
                          String productVersion,
                          String buildId,
                          String svnRepository,
                          String svnRevision,
                          String javaVersion,
                          String javaVendor,
                          String javaHome,
                          Date date,
                          Integer regressionType,
                          String comments,
                          String buildLocation)
  {
    return hydraRunRepo.createHydraRun(userName,
        productVersion,
        buildId,
        svnRepository,
        svnRevision,
        javaVersion,
        javaVendor,
        javaHome,
        date,
        regressionType,
        comments,
        buildLocation);
  }

/* Test methods above */

  public Integer getHydraTestId(String fullTestSpec, Integer hydraTestsuiteId)
  {
    return hydraTestRepo.getHydraTestId(fullTestSpec, hydraTestsuiteId);
  }

  public Integer createHydraTest(String conf,
                          String fullTestSpec,
                          Integer hydraTestsuiteId)
  {
    return hydraTestRepo.createHydraTest(conf, fullTestSpec, hydraTestsuiteId);
  }

  public HydraTestDetail createHydraTestDetail(String elapsedTime,
                                        String diskStr,
                                        String status,
                                        String error,
                                        String bugNumber,
                                        long testId,
                                        long testSuiteDetailId,
                                        int runId,
                                        String comment,
                                        String tags)
  {
    return hydraTestDetailRepo.createHydraTestDetail(elapsedTime,
                            diskStr,
                            status,
                            error,
                            bugNumber,
                            testId,
                            testSuiteDetailId,
                            runId,
                            comment,
                            tags);
  }

  public HydraTestDetail getHydraTestDetail(long testId,
                                long testSuiteDetailId,
                                int runId  )
  {
    return hydraTestDetailRepo.getHydraTestDetail(testId, testSuiteDetailId, runId);
  }

  public void updateHydraTestDetailBugNumber(String tags, Integer id) {
    hydraTestDetailRepo.updateHydraTestDetailBugNumber(tags,id);
  }

  public IdAndName getHydraTestsuiteIdAndNameById(Integer id) {
    return hydraTestsuiteRepo.getHydraTestsuiteIdAndNameById(id);
  }

  public IdAndName getHydraTestsuiteIdAndNameByName(String name) {
    return hydraTestsuiteRepo.getHydraTestsuiteIdAndNameByName(name);
  }

  public Integer maxSuite() {
    return hydraTestsuiteRepo.maxSuite();
  }

  public HydraTestsuiteDetail getHydraTestsuiteDetail(Integer hydraTestsuiteId,
                                               Integer hydraRunId,
                                               Integer hostId,
                                               Date date
  ) {
    return hydraTestsuiteDetailRepo.getHydraTestsuiteDetail(hydraTestsuiteId, hydraRunId, hostId, date);
  }

  public Integer createHydraTestsuiteDetail(Date date,
                                     String elapsedTime,
                                     String diskUsage,
                                     String localConf,
                                     Integer hydraTestsuiteId,
                                     Integer hydraRunId,
                                     Integer hostId,
                                     String comment,
                                     String artifactLocation
  ) {
    return hydraTestsuiteDetailRepo.createHydraTestsuiteDetail(date,
                                                              elapsedTime,
                                                              diskUsage,
                                                              localConf,
                                                              hydraTestsuiteId,
                                                              hydraRunId,
                                                              hostId,
                                                              comment,
                                                              artifactLocation);
  }

  public void createHost(String name, String osType, String osInfo) {
    hostRepo.createHost(name, osType, osInfo);
  }

  public Host getHostByName(String name) {
    return hostRepo.getHostByName(name);
  }

  public Host getHostById(Integer id) {
    return hostRepo.getHostById(id);
  }

  public Integer maxHost() {
    return hostRepo.maxHost();
  }
}
