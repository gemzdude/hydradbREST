package io.pivotal.gemfire.toolsmiths.hydradb;

import static io.restassured.RestAssured.given;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

import com.github.springtestdbunit.DbUnitTestExecutionListener;
import com.github.springtestdbunit.TransactionDbUnitTestExecutionListener;
import com.github.springtestdbunit.annotation.DbUnitConfiguration;
import io.pivotal.gemfire.toolsmiths.hydradb.data.hydra.repo.HydraTestDetailExtRepo;
import org.apache.http.HttpStatus;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.embedded.LocalServerPort;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.support.DirtiesContextTestExecutionListener;
import org.springframework.web.util.UriComponentsBuilder;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(webEnvironment=RANDOM_PORT)
@DbUnitConfiguration(databaseConnection="hydraDataSource")
@TestExecutionListeners({
    DependencyInjectionTestExecutionListener.class,
    DirtiesContextTestExecutionListener.class,
    DbUnitTestExecutionListener.class,
    TransactionDbUnitTestExecutionListener.class
})
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class TestDetailExtTest {

  @Autowired
  HydraTestDetailExtRepo testRepo;

  @Autowired
  private HydraUrlFactory urlFactory;

  @LocalServerPort
  private int port;

  @Test
  public void badRequest() {

    Integer theId = 1;
    String
        theURI =
        UriComponentsBuilder.fromHttpUrl(urlFactory.getURL(port) + "/HydraTestDetailExt/create")
            .queryParam("id", theId)
            .build(false).toUriString();

    given().when().get(theURI).then().
        statusCode(HttpStatus.SC_BAD_REQUEST);
  }
}