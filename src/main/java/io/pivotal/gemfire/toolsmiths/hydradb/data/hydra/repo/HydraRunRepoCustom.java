package io.pivotal.gemfire.toolsmiths.hydradb.data.hydra.repo;

import io.pivotal.gemfire.toolsmiths.hydradb.data.hydra.HydraRun;

import java.util.List;
import java.util.Map;

public interface HydraRunRepoCustom {
  public Map<Integer, HydraRun> getHydraRunSet(
      List<Integer> list,
      String gemfireVersion,
      String jdk,
      String jdkVendor,
      int svnRevision,
      String branch,
      String buildUser);

  public List<Integer> getHydraRunsForBatteryTest(int id, int numRuns);

}
