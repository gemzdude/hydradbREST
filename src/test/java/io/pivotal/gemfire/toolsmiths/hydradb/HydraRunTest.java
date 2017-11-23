package io.pivotal.gemfire.toolsmiths.hydradb;

import static io.restassured.RestAssured.given;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

import com.github.springtestdbunit.DbUnitTestExecutionListener;
import com.github.springtestdbunit.annotation.DatabaseSetup;
import com.github.springtestdbunit.annotation.DbUnitConfiguration;
import io.pivotal.gemfire.toolsmiths.hydradb.data.hydra.HydraRun;
import io.pivotal.gemfire.toolsmiths.hydradb.data.hydra.repo.HydraRunRepo;
import org.apache.http.HttpStatus;
import org.apache.log4j.Logger;
import org.hamcrest.Matchers;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.embedded.LocalServerPort;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.support.DirtiesContextTestExecutionListener;

import java.util.Date;
import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
//@WebAppConfiguration
@SpringBootTest(webEnvironment=RANDOM_PORT)
@TestPropertySource(
    locations = "classpath:config/saj.properties")
@DbUnitConfiguration(databaseConnection="hydraDataSource")
@TestExecutionListeners({
    DependencyInjectionTestExecutionListener.class,
    DirtiesContextTestExecutionListener.class,
    DbUnitTestExecutionListener.class
})
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)

public class HydraRunTest {

  private static Logger log = Logger.getLogger(HydraRunTest.class);

  @Autowired
  HydraRunRepo hydraRunRepo;

  @LocalServerPort
  private int port;

  HydraRun run1;
  HydraRun run2;

  String userName = "geode";
  String productVersion = "8.2.3";
  String buildId = "geode-94a83b259ec1ea64c111c4646353973b78d4d8f2";
  String svnRepository = "support/8.2";
  String svnRevision = "94a83b259ec1ea64c111c4646353973b78d4d8f2";
  String javaVersion = "1.8.0_112";
  String javaVendor = "Java HotSpot(TM) 64-Bit Server VM (build 25.112-b15, mixed //mode)";
  String javaHome = "/var/vcap/data/jdk/jdk1.8.0_112/bin/java";
  Date date = new Date(1486513481822L);
  String fullRegression = "";
  Integer regressionType = 2;
  String comments = "\"\"";
  String buildLocation = "/var/vcap/data/gemfire-build/Linux/snapshots.18371";

  @Test
  @DatabaseSetup(value="/runData.xml")
  public void canFetchRun1200() {

    Integer theId = 1200;
    given().port(port).
      param("id", theId).
    when().
      get("/HydraDB/HydraRun/getById").
    then().
      statusCode(HttpStatus.SC_OK).
      body("id", Matchers.is(theId)).
      body("userName", Matchers.is(userName)).
      body("productVersion", Matchers.is(productVersion)).
      body("buildId", Matchers.is(buildId)).
      body("svnRepository", Matchers.is(svnRepository)).
      body("svnRevision", Matchers.is(svnRevision)).
      body("javaVersion", Matchers.is(javaVersion)).
      body("javaVendor", Matchers.is(javaVendor)).
      body("javaHome", Matchers.is(javaHome)).
      body("fullRegression", Matchers.nullValue()).
      body("regressionType", Matchers.is(regressionType)).
      body("comments", Matchers.is(comments)).
      body("buildLocation", Matchers.is(buildLocation));
  }

  @Test
  public void canCreateHydraRun() {

    given().port(port).
      param("userName", userName).
      param("productVersion", productVersion).
      param("buildId", buildId).
      param("svnRepository", svnRepository).
      param("svnRevision", svnRevision).
      param("javaVersion", javaVersion).
      param("javaVendor", javaVendor).
      param("javaHome", javaHome).
      param("fullRegression", fullRegression).
      param("regressionType", regressionType).
      param("comments", comments).
      param("buildLocation", buildLocation).
    when().
      get("/HydraDB/HydraRun/create").
    then().
      statusCode(HttpStatus.SC_OK).
      body("userName", Matchers.is("geode")).
      body("productVersion", Matchers.is("8.2.3")).
      body("buildId", Matchers.is("geode-94a83b259ec1ea64c111c4646353973b78d4d8f2")).
      body("svnRepository", Matchers.is("support/8.2")).
      body("svnRevision", Matchers.is("94a83b259ec1ea64c111c4646353973b78d4d8f2")).
      body("javaVersion", Matchers.is("1.8.0_112")).
      body("javaVendor", Matchers.is("Java HotSpot(TM) 64-Bit Server VM (build 25.112-b15, mixed //mode)")).
      body("javaHome", Matchers.is("/var/vcap/data/jdk/jdk1.8.0_112/bin/java")).
      body("fullRegression", Matchers.nullValue()).
      body("regressionType", Matchers.is(2)).
      body("comments", Matchers.is("\"\"")).
      body("buildLocation", Matchers.is("/var/vcap/data/gemfire-build/Linux/snapshots.18371"));

    List<HydraRun> hrl = hydraRunRepo.hydraRunSearch(userName,
        productVersion,
        buildId,
        svnRepository,
        svnRevision,
        javaVersion,
        javaVendor);

    assertNotNull(hrl);
    assertEquals(1, hrl.size());
    log.info("SAJSAJ: " + hrl.get(0).toString());
  }

}
