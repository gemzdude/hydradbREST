package io.pivotal.gemfire.toolsmiths.hydradb.data.hydra.repo;

import io.pivotal.gemfire.toolsmiths.hydradb.TestInfo;

public interface HydraTestRepoCustom {

  TestInfo getOrCreateTestInfo(String confName, String testspec, Integer testsuiteId);

}
