package io.pivotal.gemfire.toolsmiths.hydradb.data.hydra.repo;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

public class HydraTestDetailExtRepoImpl implements HydraTestDetailExtRepoCustom {
  private static Logger log = Logger.getLogger(HydraTestDetailExtRepoImpl.class);

  @Autowired
  HydraTestDetailRepo hydraTestDetailExtRepo;
}

