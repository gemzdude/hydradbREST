package io.pivotal.gemfire.toolsmiths.hydradb.data.hydra.repo;

import io.pivotal.gemfire.toolsmiths.hydradb.data.hydra.HydraTestDetail;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HydraHistoryRepo extends JpaRepository<HydraTestDetail, Long>, HydraHistoryRepoCustom {

}
