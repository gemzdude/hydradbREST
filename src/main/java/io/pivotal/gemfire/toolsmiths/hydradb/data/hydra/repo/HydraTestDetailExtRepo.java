package io.pivotal.gemfire.toolsmiths.hydradb.data.hydra.repo;

import io.pivotal.gemfire.toolsmiths.hydradb.data.hydra.HydraTestDetailExt;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HydraTestDetailExtRepo extends JpaRepository<HydraTestDetailExt, Long>,
                                                HydraTestDetailExtRepoCustom {
}
