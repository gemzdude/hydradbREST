package io.pivotal.gemfire.toolsmiths.hydradb.data.hydra.repo;

import io.pivotal.gemfire.toolsmiths.hydradb.TestInfo;
import io.pivotal.gemfire.toolsmiths.hydradb.data.hydra.HydraTest;
import org.apache.log4j.Logger;

public class HydraTestRepoImpl implements HydraTestRepoCustom {

  private static Logger log = Logger.getLogger(HydraTestRepoImpl.class);

  HydraTestRepo hydraTestRepo;

  public TestInfo getOrCreateTestInfo(String conf, String fullTestSpec, Integer testsuiteId) {
    HydraTest ht = hydraTestRepo.getHydraTestByFullTestSpecAndHydraTestsuiteId(fullTestSpec, testsuiteId);
    if(ht==null) {
      ht = new HydraTest();
      ht.setConf(conf);
      ht.setFullTestSpec(fullTestSpec);
      ht.setHydraTestsuiteId(testsuiteId);
      hydraTestRepo.save(ht);
    }
    return new TestInfo(ht.getId(), ht.getConf(), ht.getFullTestSpec(), ht.getHydraTestsuiteId());
  }
}
