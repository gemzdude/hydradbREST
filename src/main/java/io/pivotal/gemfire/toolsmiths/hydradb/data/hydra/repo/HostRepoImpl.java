package io.pivotal.gemfire.toolsmiths.hydradb.data.hydra.repo;

import io.pivotal.gemfire.toolsmiths.hydradb.data.hydra.Host;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;

import java.util.List;

public class HostRepoImpl implements HostRepoCustom {

  private static Logger log = Logger.getLogger(HostRepoCustom.class);

  @Autowired
  HostRepo hostRepo;

  @Override
  public Host getOrCreateHost(String name, String osType, String osInfo) throws
      DataAccessException {
    List<Host> hosts;
    try {
      return hostRepo.getHostByName(name).get(0);
    } catch (EmptyResultDataAccessException e) {
      log.info("No Host found for name : " + name);
      hostRepo.createHost(name, osType, osInfo);
      return hostRepo.getHostByName(name).get(0);
    }
  }
}
