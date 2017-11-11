package io.pivotal.gemfire.toolsmiths.hdbr;

import io.pivotal.gemfire.toolsmiths.hdbr.data.gen.HydraTest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface HydraTestRepo extends JpaRepository<HydraTest, Long> {

  // SELECT ID FROM HYDRA_TEST WHERE FULL_TEST_SPEC=? AND HYDRA_TESTSUITE_ID=?
  @Query("SELECT id FROM HydraTest where fullTestSpec=:fullTestSpec AND hydraTestsuiteId=:hydraTestsuiteId")
  Integer getHydraTestId(@Param("fullTestSpec") String fullTestSpec,
                    @Param("hydraTestsuiteId") Integer hydraTestsuiteId
  );

  //INSERT INTO HYDRA_TEST(ID, CONF, FULL_TEST_SPEC, HYDRA_TESTSUITE_ID) VALUES(NEXTVAL('hydra_test_id_seq'),?,?,?)
  @Query(value = "INSERT INTO HydraTest(id, conf, fullTestSpec, hydraTestsuiteId) VALUES(NEXTVAL('hydra_test_id_seq'),:conf,:fullTestSpec,:hydraTestsuiteId", nativeQuery = true)
  Integer createHydraTest(@Param("conf") String conf,
                      @Param("fullTestSpec") String fullTestSpec,
                      @Param("hydraTestsuiteId") Integer hydraTestsuiteId
                      );
}
