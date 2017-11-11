package io.pivotal.gemfire.toolsmiths.hdbr;

import io.pivotal.gemfire.toolsmiths.hdbr.data.gen.HydraRun;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Date;
import java.util.List;

public interface HydraRunRepo extends JpaRepository<HydraRun, Long> {
  @Query("SELECT max(id) FROM HydraRun")
  Integer maxRun();

  @Query
  HydraRun getHydraRunById(Integer id);

  List<HydraRun> findFirst5ByUserNameOrderByDateDesc(String userName);

  // INSERT INTO HYDRA_RUN(ID, USER_NAME, PRODUCT_VERSION, BUILD_ID, SVN_REPOSITORY, SVN_REVISION, JAVA_VERSION, JAVA_VENDOR, JAVA_HOME, DATE, REGRESSION_TYPE, COMMENTS, BUILD_LOCATION) VALUES (NEXTVAL('hydra_run_id_seq'), ?,?,?,?,?,?,?,?,?,?,?,?)

  @Query(value = "INSERT INTO HydraRun(id, userName, productVersion, buildId, svnRepository, svnRevision, javaVersion, javaVendor, javaHome, date, regressionType, comments, buildLocation) VALUES (NEXTVAL('hydra_run_id_seq'), :userName,:productVersion,:buildId,:svnRepository,:svnRevision,:javaVersion,:javaVendor,:javaHome,:date,:regressionType,:comments,:buildLocation", nativeQuery = true)
  HydraRun createHydraRun(@Param("userName") String userName,
                          @Param("productVersion") String productVersion,
                          @Param("buildId") String buildId,
                          @Param("svnRepository") String svnRepository,
                          @Param("svnRevision") String svnRevision,
                          @Param("javaVersion") String javaVersion,
                          @Param("javaVendor") String javaVendor,
                          @Param("javaHome") String javaHome,
                          @Param("date") Date date,
                          @Param("regressionType") Integer regressionType,
                          @Param("comments") String comments,
                          @Param("buildLocation") String buildLocation
  );

}
