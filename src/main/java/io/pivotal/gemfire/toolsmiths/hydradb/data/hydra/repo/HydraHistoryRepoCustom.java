package io.pivotal.gemfire.toolsmiths.hydradb.data.hydra.repo;

import io.pivotal.gemfire.toolsmiths.hydradb.HydraHistory;
import io.pivotal.gemfire.toolsmiths.hydradb.data.hydra.HydraRun;

import java.util.Map;

public interface HydraHistoryRepoCustom {
  public HydraHistory getHydraHistoryForBatteryTest(final int id, final Map<Integer, HydraRun> hydraRunList);
}
