package io.pivotal.gemfire.toolsmiths.hdbr;

import io.pivotal.gemfire.toolsmiths.hdbr.data.gen.Host;

import java.util.List;

public interface HydraDBService {
  Integer maxRun();
  List<String> regressionByUser(String userName);

//  public Host getHostByName(int hostId);

  public IdAndName getIdAndNameById(Integer id);

  public Integer maxSuite();

//  public void createHost(String name, String osType, String osInfo);

  public Host getHostByName(String name);
  public Integer maxHost();


}
