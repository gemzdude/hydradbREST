package io.pivotal.gemfire.toolsmiths.hdbr;

import io.pivotal.gemfire.toolsmiths.hdbr.data.gen.Host;
import io.pivotal.gemfire.toolsmiths.hdbr.data.gen.HydraRun;
import io.pivotal.gemfire.toolsmiths.hdbr.data.gen.HydraTestDetail;
import io.pivotal.gemfire.toolsmiths.hdbr.data.gen.HydraTestsuiteDetail;
import org.springframework.data.repository.query.Param;

import java.util.Date;

public interface HydraDBService {
  Integer maxRun();
//  List<String> regressionByUser(String userName);

  Integer maxHost();
  Host getHostByName(String name);
  Host getHostById(Integer id);
  void createHost(String name, String osType, String osInfo);

  HydraTestDetail createHydraTestDetail(String elapsedTime,
                                   String diskStr,
                                   String status,
                                   String error,
                                   String bugNumber,
                                   long testId,
                                   long testSuiteDetailId,
                                   int runId,
                                   String comment,
                                   String tags
  );

  HydraTestDetail getHydraTestDetail(long testId,
                                     long testSuiteDetailId,
                                     int runId
  );

  void updateHydraTestDetailBugNumber(String tags,
                                      Integer id
  );

  Integer getHydraTestId(String fullTestSpec,
                         Integer hydraTestsuiteId
  );

  Integer createHydraTest(String conf,
                          String fullTestSpec,
                          Integer hydraTestsuiteId
  );

  HydraTestsuiteDetail getHydraTestsuiteDetail(Integer hydraTestsuiteId,
                                               Integer hydraRunId,
                                               Integer hostId,
                                               Date date
  );

  Integer createHydraTestsuiteDetail(Date date,
                                     String elapsedTime,
                                     String diskUsage,
                                     String localConf,
                                     Integer hydraTestsuiteId,
                                     Integer hydraRunId,
                                     Integer hostId,
                                     String comment,
                                     String artifactLocation
  );

  Integer maxSuite();

  IdAndName getHydraTestsuiteIdAndNameById(@Param("id") Integer id);

  IdAndName getHydraTestsuiteIdAndNameByName(@Param("name") String name);

//  HydraRun getHydraRunById(Integer id);

  HydraRun createHydraRun(String userName,
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
                          String buildLocation
  );
}
