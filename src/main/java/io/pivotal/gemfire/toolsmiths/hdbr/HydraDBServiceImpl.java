package io.pivotal.gemfire.toolsmiths.hdbr;

import io.pivotal.gemfire.toolsmiths.hdbr.data.gen.Host;
import io.pivotal.gemfire.toolsmiths.hdbr.data.gen.HostRepository;
import io.pivotal.gemfire.toolsmiths.hdbr.data.gen.HydraRunRepository;
import io.pivotal.gemfire.toolsmiths.hdbr.data.gen.HydraTestDetailExtRepository;
import io.pivotal.gemfire.toolsmiths.hdbr.data.gen.HydraTestDetailRepository;
import io.pivotal.gemfire.toolsmiths.hdbr.data.gen.HydraTestRepository;
import io.pivotal.gemfire.toolsmiths.hdbr.data.gen.HydraTestsuiteDetailRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HydraDBServiceImpl implements HydraDBService {
  @Autowired
  HydraRunRepo repo;

  @Autowired
  HostRepository hostRepository;

  @Autowired
  HydraRunRepository hydraRunRepository;

  @Autowired
  HydraTestsuiteRepo hydraTestsuiteRepo;

  @Autowired
  HydraTestsuiteDetailRepository hydraTestsuiteDetailRepository;

  @Autowired
  HydraTestRepository hydraTest;

  @Autowired
  HydraTestDetailRepository hydraTestDetailRepository;

  @Autowired
  HydraTestDetailExtRepository hydraTestDetailExtRepository;

  @Autowired
  HostRepo hostRepo;

  public Integer maxRun() {
    return repo.maxRun();
  }

  public List<String> regressionByUser(String uname) {
    return repo.regressionByUser(uname);
  }

  public IdAndName getIdAndNameById(Integer id) {
    return hydraTestsuiteRepo.getIdAndNameById(id);
  }

  public Integer maxSuite() {
    return hydraTestsuiteRepo.maxSuite();
  }

//  public void createHost(String name, String osType, String osInfo) {
//    hostRepo.createHost(name, osType, osInfo);
//  }

  public Host getHostByName(String name) {
    return hostRepo.getHostByName(name);
  }

  public Integer maxHost() {
    return hostRepo.maxHost();
  }
}
