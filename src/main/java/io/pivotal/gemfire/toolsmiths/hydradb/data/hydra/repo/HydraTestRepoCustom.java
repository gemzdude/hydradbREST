package io.pivotal.gemfire.toolsmiths.hydradb.data.hydra.repo;

import io.pivotal.gemfire.toolsmiths.hydradb.data.hydra.HydraTest;

public interface HydraTestRepoCustom {

  HydraTest getOrCreateTestInfo(String confName, String testspec, Integer testsuiteId);

}
