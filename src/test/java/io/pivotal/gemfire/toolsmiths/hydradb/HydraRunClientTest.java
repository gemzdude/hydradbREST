package io.pivotal.gemfire.toolsmiths.hydradb;

import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

import com.github.springtestdbunit.DbUnitTestExecutionListener;
import com.github.springtestdbunit.annotation.DatabaseSetup;
import com.github.springtestdbunit.annotation.DbUnitConfiguration;
import com.github.springtestdbunit.annotation.ExpectedDatabase;
import io.pivotal.gemfire.toolsmiths.hydradb.data.hydra.Host;
import io.pivotal.gemfire.toolsmiths.hydradb.data.hydra.HydraRun;
import org.apache.log4j.Logger;
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

public class HydraRunClientTest {

  private static Logger log = Logger.getLogger(HydraRunClientTest.class);

  @Autowired
  HydraDBClient hydraClient;

  @LocalServerPort
  private int port;

  @Test
  @DatabaseSetup(value="/runData.xml")
  public void canFetchRun1200() {
    hydraClient.setPort(port);
    HydraRun hr = hydraClient.getHydraRunById(1200);
  }

  @Test
  @DatabaseSetup(value="/hostData.xml")
  public void canFetchHost1() {
    hydraClient.setPort(port);
    Host host = hydraClient.getHostById(1);
  }

  @Test
  @ExpectedDatabase(value = "/createHostData.xml", table = "Host")
  public void createHost() {
    hydraClient.setPort(port);
    String name = "newhost";
    String osType = "linux";
    String osInfo = "the os info";
    Host host = hydraClient.createHost(name, osType, osInfo);
  }
}
