package io.pivotal.gemfire.toolsmiths.hydradb.data.hydra.repo;

import io.pivotal.gemfire.toolsmiths.hydradb.data.hydra.Host;

public interface HostRepoCustom {

  Host getOrCreateHost(String hostName, String osName, String osInfo);

}
