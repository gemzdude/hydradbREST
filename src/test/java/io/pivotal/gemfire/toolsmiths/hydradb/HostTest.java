package io.pivotal.gemfire.toolsmiths.hydradb;

import static io.restassured.RestAssured.given;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

import com.github.springtestdbunit.DbUnitTestExecutionListener;
import com.github.springtestdbunit.TransactionDbUnitTestExecutionListener;
import com.github.springtestdbunit.annotation.DatabaseSetup;
import com.github.springtestdbunit.annotation.DbUnitConfiguration;
import com.github.springtestdbunit.annotation.ExpectedDatabase;
import io.pivotal.gemfire.toolsmiths.hydradb.data.hydra.Host;
import io.pivotal.gemfire.toolsmiths.hydradb.data.hydra.repo.HostRepo;
import org.apache.http.HttpStatus;
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

//import static org.hamcrest.Matchers.*

@RunWith(SpringJUnit4ClassRunner.class)
//@WebAppConfiguration
@SpringBootTest(webEnvironment=RANDOM_PORT)
@TestPropertySource(
    locations = "classpath:config/saj.properties")
@DbUnitConfiguration(databaseConnection="hydraDataSource")
@TestExecutionListeners({
    DependencyInjectionTestExecutionListener.class,
    DirtiesContextTestExecutionListener.class,
    DbUnitTestExecutionListener.class,
    TransactionDbUnitTestExecutionListener.class
})
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class HostTest {

  @Autowired
  HostRepo hostRepo;

  @LocalServerPort
  private int port;

  Host host1;
  Host host2;

//  @Before
//  public void setUp() {
//    host1 = new Host("Mickey Mouse");
//    host2 = new Host("Mickey Mouse");
//    hostRepo.deleteAll();
//    hostRepo.save(Arrays.asList(host1, host2));
//  }

  @Test
  @DatabaseSetup(value="/hostData.xml")
  public void canFetchHost1() {

//    RestAssured.defaultParser = Parser.JSON;

    Integer theId = 1;
    given().port(port).
      param("id", theId).
    when().
      get("/HydraDB/Host/getById?id={id}", theId).
    then().
      statusCode(HttpStatus.SC_OK).
      body("name", Matchers.is("odin")).
      body("id", Matchers.is(theId));
  }

  @Test
  @DatabaseSetup(value="/hostData.xml")
  public void hostNotFound() {

//    RestAssured.defaultParser = Parser.JSON;

    Integer theId = 9999;
    given().port(port).
        param("id", theId).
        when().
        get("/HydraDB/Host/getById?id={id}", theId).
        then().
        statusCode(HttpStatus.SC_NOT_FOUND);
  }

  /*
  Passing a string when an int is expected returns bad request
 */
  @Test
  public void badRequest() {
    String theId = "notAint";
    given().port(port).
        param("id", theId).
        when().
        get("/HydraDB/Host/getById?id={id}", theId).
        then().
        statusCode(HttpStatus.SC_BAD_REQUEST);
  }

  /*
  Requesting an unmapped URL returns not found
  */
  @Test
  public void badRequest2() {
    String theId = "notAint";
    given().port(port).
        param("id", theId).
        when().
        get("/HydraDB/Host/getBySomethingNotMapped?id={id}", theId).
        then().
        statusCode(HttpStatus.SC_NOT_FOUND);
  }

  @Test
  @DatabaseSetup(value="/hostData.xml")
  @ExpectedDatabase(value = "/expectedHostData.xml", table = "Host")
  //@DatabaseTearDown(type = DELETE_ALL, value = "/hostData.xml")
  public void canFetchHost3() {

    Integer theId = 3;
    given().port(port).
      param("id", theId).
    when().
      get("/HydraDB/Host/getById?id={id}", theId).
    then().
      statusCode(HttpStatus.SC_OK).
      body("name", Matchers.is("stut")).
      body("id", Matchers.is(theId));
  }

  @Test
  @ExpectedDatabase(value = "/afterHostInsertData.xml", table = "Host")
  public void canCreateHost() {
    String theName = "newhost";
    String theOsType = "linux";
    String theOsInfo = "the os info";
    given().port(port).
      param("name", theName).
      param("osType", theOsType).
      param("osInfo", theOsInfo).
     when().
      get("/HydraDB/Host/create?name={name}&osType={osType}&osInfo={osInfo}", theName, theOsType, theOsInfo).
     then().
      statusCode(HttpStatus.SC_OK).
      body("name", Matchers.is(theName)).
      body("osType", Matchers.is(theOsType)).
      body("osInfo", Matchers.is(theOsInfo));

    Integer theId = 1;
    given().port(port).
      param("id", theId).
    when().
      get("/HydraDB/Host/getById?id={id}", theId).
    then().
      statusCode(HttpStatus.SC_OK).
      body("name", Matchers.is(theName)).
      body("osType", Matchers.is(theOsType)).
      body("osInfo", Matchers.is(theOsInfo));
  }
}
//  @Test
//  @DatabaseSetup("hostData.xml")
//  public void testFind() throws Exception {
//    List<Host> personList = this.hostRepo.find("hil");
//    assertEquals(1, personList.size());
//    assertEquals("Phillip", personList.get(0).getFirstName());
//  }
//
//  @Test
//  @DatabaseSetup("hostData.xml")
//  @ExpectedDatabase("expectedHostData.xml")
//  public void testRemove() throws Exception {
//    this.personService.remove(1);
//  }

