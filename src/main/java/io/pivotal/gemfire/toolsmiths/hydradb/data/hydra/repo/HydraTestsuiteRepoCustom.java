package io.pivotal.gemfire.toolsmiths.hydradb.data.hydra.repo;

import io.pivotal.gemfire.toolsmiths.hydradb.TestSuiteInfo;

public interface HydraTestsuiteRepoCustom {

  public TestSuiteInfo getTestSuiteInfo(long suiteId);

}
