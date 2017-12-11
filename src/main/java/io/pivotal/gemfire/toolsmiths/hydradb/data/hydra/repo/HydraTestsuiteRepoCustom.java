package io.pivotal.gemfire.toolsmiths.hydradb.data.hydra.repo;

import io.pivotal.gemfire.toolsmiths.hydradb.IdAndName;
import org.springframework.data.repository.query.Param;

public interface HydraTestsuiteRepoCustom {

  IdAndName getHydraTestsuiteIdAndNameById(@Param("id") Integer id);

  IdAndName getHydraTestsuiteIdAndNameByName(@Param("name") String name);

}
