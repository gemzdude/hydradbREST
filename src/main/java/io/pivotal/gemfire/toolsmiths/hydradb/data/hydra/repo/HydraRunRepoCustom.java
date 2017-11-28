package io.pivotal.gemfire.toolsmiths.hydradb.data.hydra.repo;

import io.pivotal.gemfire.toolsmiths.hydradb.data.hydra.HydraRun;

import java.util.List;
import java.util.Map;

public interface HydraRunRepoCustom {

  Map<Integer, HydraRun> populateHydraRuns(int id, int numRuns,
                                                   String gemfireVersion, String jdk, String jdkVendor, int svnRevision,
                                                   String branch, String buildUser);

  List<Integer> getHydraRunsForBatteryTest(int id, int numRuns);

  Map<Integer, HydraRun> getHydraRunSet(
      List<Integer> list,
      String gemfireVersion,
      String jdk,
      String jdkVendor,
      int svnRevision,
      String branch,
      String buildUser);

}
