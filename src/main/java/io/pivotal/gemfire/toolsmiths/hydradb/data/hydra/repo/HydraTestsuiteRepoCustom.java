package io.pivotal.gemfire.toolsmiths.hydradb.data.hydra.repo;

import io.pivotal.gemfire.toolsmiths.hydradb.TestSuiteInfo;

public interface HydraTestsuiteRepoCustom {

  TestSuiteInfo getOrCreateTestSuiteInfo(String testsuiteName);

  public TestSuiteInfo getTestSuiteInfo(long suiteId);

}
