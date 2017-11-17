package io.pivotal.gemfire.toolsmiths.hydradb;

import static io.restassured.RestAssured.given;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

import com.github.springtestdbunit.DbUnitTestExecutionListener;
import com.github.springtestdbunit.annotation.DatabaseSetup;
import com.github.springtestdbunit.annotation.DbUnitConfiguration;
import com.github.springtestdbunit.annotation.ExpectedDatabase;
import io.pivotal.gemfire.toolsmiths.hydradb.data.hydra.Host;
import io.pivotal.gemfire.toolsmiths.hydradb.data.hydra.repo.HostRepo;
import org.apache.http.HttpStatus;
import org.hamcrest.Matchers;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.embedded.LocalServerPort;
import org.springframework.boot.test.context.SpringBootTest;
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
    DbUnitTestExecutionListener.class
})
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

  @Ignore
  @Test
  @DatabaseSetup(value="/hostData.xml")
  @ExpectedDatabase(value = "/expectedData.xml", table = "Host")
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
//  @ExpectedDatabase("expectedData.xml")
//  public void testRemove() throws Exception {
//    this.personService.remove(1);
//  }
}
