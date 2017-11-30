package io.pivotal.gemfire.toolsmiths.hydradb.data.hydra.repo;

import io.pivotal.gemfire.toolsmiths.hydradb.data.hydra.HydraTestDetail;

public interface HydraTestDetailRepoCustom {
  HydraTestDetail getOrCreateTestDetail(int runId,
                                               long testSuiteDetailsId,
                                               long testId,
                                               String elapsedTime,
                                               String diskStr,
                                               String status,
                                               String error,
                                               String bugNumber,
                                               String comment,
                                               String tags,
                                               String logLocation);
}
