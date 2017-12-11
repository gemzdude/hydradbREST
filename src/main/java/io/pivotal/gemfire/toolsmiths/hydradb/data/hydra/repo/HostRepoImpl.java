package io.pivotal.gemfire.toolsmiths.hydradb.data.hydra.repo;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

public class HostRepoImpl implements HostRepoCustom {

  private static Logger log = Logger.getLogger(HostRepoCustom.class);

  @Autowired
  HostRepo hostRepo;

}
