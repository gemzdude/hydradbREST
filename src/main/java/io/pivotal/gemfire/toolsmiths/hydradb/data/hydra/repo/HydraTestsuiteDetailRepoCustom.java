package io.pivotal.gemfire.toolsmiths.hydradb.data.hydra.repo;

import io.pivotal.gemfire.toolsmiths.hydradb.data.hydra.HydraTestsuiteDetail;

import java.util.Date;
import java.util.List;

public interface HydraTestsuiteDetailRepoCustom {
  HydraTestsuiteDetail getOrCreateTestSuiteDetail(Integer runId,
                                                         Integer hostId,
                                                         Integer testSuiteId,
                                                         Date date,
                                                         String elapsedTime,
                                                         String diskStr,
                                                         String localConf,
                                                         String btComment,
                                                         String artifactLogLocation);

  List<HydraTestsuiteDetail> getLastNTestSuiteDetails(long suiteId, int max);

  List<HydraTestsuiteDetail> getTestSuiteDetailsForRun(int runId);
}
