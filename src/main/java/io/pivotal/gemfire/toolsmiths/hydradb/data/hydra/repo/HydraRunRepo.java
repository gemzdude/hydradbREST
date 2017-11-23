package io.pivotal.gemfire.toolsmiths.hydradb.data.hydra.repo;

import io.pivotal.gemfire.toolsmiths.hydradb.data.hydra.HydraRun;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface HydraRunRepo extends JpaRepository<HydraRun, Long>, HydraRunRepoCustom {

  @Query
  HydraRun getHydraRunById(Integer id);

  // INSERT INTO HYDRA_RUN(ID, USER_NAME, PRODUCT_VERSION, BUILD_ID, SVN_REPOSITORY, SVN_REVISION, JAVA_VERSION, JAVA_VENDOR, JAVA_HOME, DATE, FULL_REGRESSION, REGRESSION_TYPE, COMMENTS, BUILD_LOCATION) VALUES (NEXTVAL('hydra_run_id_seq'), ?,?,?,?,?,?,?,?,?,?,?,?)

  @Query(value = "INSERT INTO HYDRA_RUN(user_name, product_version, build_id, svn_repository, svn_revision, java_version, java_vendor, java_home, full_regression, regression_type, comments, build_location) VALUES (:userName,:productVersion,:buildId,:svnRepository,:svnRevision,:javaVersion,:javaVendor,:javaHome,:fullRegression,:regressionType,:comments,:buildLocation)", nativeQuery = true)
  @Modifying
  @Transactional
  void createHydraRun(@Param("userName") String userName,
                          @Param("productVersion") String productVersion,
                          @Param("buildId") String buildId,
                          @Param("svnRepository") String svnRepository,
                          @Param("svnRevision") String svnRevision,
                          @Param("javaVersion") String javaVersion,
                          @Param("javaVendor") String javaVendor,
                          @Param("javaHome") String javaHome,
                          @Param("fullRegression") Boolean fullRegression,
                          @Param("regressionType") Integer regressionType,
                          @Param("comments") String comments,
                          @Param("buildLocation") String buildLocation
  );

  @Query(value = "select hr from HydraRun hr where userName = :userName and productVersion = :productVersion and buildId = :buildId and svnRepository = :svnRepository and svnRevision = :svnRevision and javaVersion = :javaVersion and javaVendor = :javaVendor")
  List<HydraRun> hydraRunSearch(@Param("userName") String userName,
                                @Param("productVersion") String productVersion,
                                @Param("buildId") String buildId,
                                @Param("svnRepository") String svnRepository,
                                @Param("svnRevision") String svnRevision,
                                @Param("javaVersion") String javaVersion,
                                @Param("javaVendor") String javaVendor
  );

  @Query(value = "select max(id) from HydraRun")
  Integer maxId();

}
