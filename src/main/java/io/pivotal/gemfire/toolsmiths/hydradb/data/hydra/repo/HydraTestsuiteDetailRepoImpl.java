package io.pivotal.gemfire.toolsmiths.hydradb.data.hydra.repo;

import org.springframework.beans.factory.annotation.Autowired;
import org.apache.log4j.Logger;

public class HydraTestsuiteDetailRepoImpl implements HydraTestsuiteDetailRepoCustom {

  private static Logger log = Logger.getLogger(HydraTestsuiteDetailRepoImpl.class);

  @Autowired
  HydraTestsuiteDetailRepo hydraTestsuiteDetailRepo;

}
