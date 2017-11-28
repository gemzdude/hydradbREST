package io.pivotal.gemfire.toolsmiths.hydradb;

import io.pivotal.gemfire.toolsmiths.hydradb.data.hydra.Host;
import io.pivotal.gemfire.toolsmiths.hydradb.data.hydra.HydraRun;
import io.pivotal.gemfire.toolsmiths.hydradb.data.hydra.HydraTest;
import io.pivotal.gemfire.toolsmiths.hydradb.data.hydra.HydraTestDetail;
import io.pivotal.gemfire.toolsmiths.hydradb.data.hydra.HydraTestsuite;
import io.pivotal.gemfire.toolsmiths.hydradb.data.hydra.HydraTestsuiteDetail;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.Date;
import java.util.List;
import java.util.Map;

public class HydraDBClientService implements HydraDBClient {

  private static Logger log = Logger.getLogger(HydraDBClientService.class);

  @Autowired
  private HydraUrlFactory urlFactory;

  private int port;

  String hydraEndpointUrl = null;

  RestTemplate restTemplate = new RestTemplate();

  public void setPort(Integer port) {
    urlFactory.setPort(port);
  }

  public Integer getPort() {
    return urlFactory.getPort();
  }

  private String getURL(String service) {
//    if(port==0) {
      return urlFactory.getURL() + service;
//    } else {
//      return urlFactory.getURL(port) + service;
//    }
  }

  private Object getResponse(ResponseEntity response) {
    Object obj = null;
    if (response.getStatusCode() == HttpStatus.OK) {
      obj = response.getBody();
    }
    return obj;
  }

  /*
  The methods below use the following technique for building a URI
  UriComponentsBuilder.build(false) -> UriComponent.toUri() -> URI
  Use build(false) if your uri IS NOT already encoded
  Use build(true) if your uri IS already encoded
   */
  @Override
  public Host getHostByName(String name) {
    URI theURI = UriComponentsBuilder.fromHttpUrl(getURL("/Host/getByName"))
        .queryParam("name", name)
        .build(false).toUri();
    ResponseEntity<Host> hostResponse = restTemplate.getForEntity(theURI, Host.class);
    return (Host)getResponse(hostResponse);
  }

  @Override
  public Host getHostById(Integer id) {
    URI theURI = UriComponentsBuilder.fromHttpUrl(getURL("/Host/getById"))
        .queryParam("id", id)
        .build(false).toUri();
    ResponseEntity<Host> hostResponse = restTemplate.getForEntity(theURI, Host.class);
    return (Host)getResponse(hostResponse);
  }

  @Override
  public Host createHost(String name, String osType, String osInfo) {
    URI uri = UriComponentsBuilder.fromHttpUrl(getURL("/Host/create"))
        .queryParam("name", name)
        .queryParam("osType", osType)
        .queryParam("osInfo", osInfo).build(false).toUri();
    ResponseEntity<Host> hostResponse = restTemplate.getForEntity(uri, Host.class);
    return (Host)getResponse(hostResponse);
  }

  @Override
  public Integer maxHostId() {
    URI uri = UriComponentsBuilder.fromHttpUrl(getURL("/Host/max"))
        .build(false).toUri();
    ResponseEntity<Integer> hostResponse = restTemplate.getForEntity(uri, Integer.class);
    return (Integer)getResponse(hostResponse);
  }

  @Override
  public HydraRun getHydraRunById(Integer id) {
    URI uri = UriComponentsBuilder.fromHttpUrl(getURL("/HydraRun/getById"))
        .queryParam("id", id)
        .build(false).toUri();
    ResponseEntity<HydraRun> hostResponse = restTemplate.getForEntity(uri, HydraRun.class);
    return (HydraRun)getResponse(hostResponse);
  }

  @Override
  public Integer maxHydraRunId() {
    URI uri = UriComponentsBuilder.fromHttpUrl(getURL("/HydraRun/max"))
        .build(false).toUri();
    ResponseEntity<Integer> hostResponse = restTemplate.getForEntity(uri, Integer.class);
    return (Integer)getResponse(hostResponse);
  }

  @Override
  public HydraRun createHydraRun(String userName, String productVersion, String buildId,
                                 String svnRepository, String svnRevision, String javaVersion,
                                 String javaVendor, String javaHome, Boolean fullRegression,
                                 Integer regressionType, String comments, String buildLocation) {
    URI theURI = UriComponentsBuilder.fromHttpUrl(getURL("/HydraRun/create"))
        .queryParam("userName", userName)
        .queryParam("productVersion", productVersion)
        .queryParam("buildId", buildId)
        .queryParam("svnRepository", svnRepository)
        .queryParam("svnRevision", svnRevision)
        .queryParam("javaVersion", javaVersion)
        .queryParam("javaVendor", javaVendor)
        .queryParam("javaHome", javaHome)
        .queryParam("fullRegression", fullRegression)
        .queryParam("regressionType", regressionType)
        .queryParam("comments", comments)
        .queryParam("buildLocation", buildLocation)
        .build(false).toUri();
    ResponseEntity<HydraRun> hostResponse = restTemplate.getForEntity(theURI, HydraRun.class);
    return (HydraRun)getResponse(hostResponse);
  }

  @Override
  public List<HydraRun> hydraRunSearch(String userName, String productVersion, String buildId,
                                       String svnRepository, String svnRevision, String javaVersion,
                                       String javaVendor) {
    URI theURI = UriComponentsBuilder.fromHttpUrl(getURL("/HydraRun/search"))
        .queryParam("userName", userName)
        .queryParam("productVersion", productVersion)
        .queryParam("buildId", buildId)
        .queryParam("svnRepository", svnRepository)
        .queryParam("svnRevision", svnRevision)
        .queryParam("javaVersion", javaVersion)
        .queryParam("javaVendor", javaVendor)
        .build(false).toUri();
    ResponseEntity<HydraRun> hostResponse = restTemplate.getForEntity(theURI, HydraRun.class);
    return (List<HydraRun>)getResponse(hostResponse);
  }

  @Override
  public Map<Integer, HydraRun> getHydraRunSet(List<Integer> list, String gemfireVersion,
                                               String jdk, String jdkVendor, int svnRevision,
                                               String branch, String buildUser) {
    URI theURI = UriComponentsBuilder.fromHttpUrl(getURL("/HydraRun/getSet"))
        .queryParam("list", list)
        .queryParam("gemfireVersion", gemfireVersion)
        .queryParam("jdk", jdk)
        .queryParam("jdkVendor", jdkVendor)
        .queryParam("svnRevision", svnRevision)
        .queryParam("branch", branch)
        .queryParam("buildUser", buildUser)
        .build(false).toUri();
    ResponseEntity hostResponse = restTemplate.getForEntity(theURI, Map.class);
    return (Map<Integer, HydraRun>)getResponse(hostResponse);
  }

  @Override
  public HydraTest getHydraTestById(Integer hydraTestsuiteId) {
    URI theURI = UriComponentsBuilder.fromHttpUrl(getURL("/HydraTest/getById"))
        .build(false).toUri();
    ResponseEntity<HydraTest> hostResponse = restTemplate.getForEntity(theURI, HydraTest.class);
    return (HydraTest)getResponse(hostResponse);
  }

  @Override
  public Integer createHydraTest(String conf, String fullTestSpec, Integer hydraTestsuiteId) {
    URI theURI = UriComponentsBuilder.fromHttpUrl(getURL("/HydraTest/create"))
        .queryParam("conf", conf)
        .queryParam("fullTestSpec", fullTestSpec)
        .queryParam("hydraTestsuiteId", hydraTestsuiteId)
        .build(false).toUri();
    ResponseEntity<Integer> hostResponse = restTemplate.getForEntity(theURI, Integer.class);
    return (Integer)getResponse(hostResponse);
  }

  @Override
  public HydraTest getHydraTestByFullTestSpecAndHydraTestsuiteId(String fullTestSpec,
                                                                 Integer hydraTestsuiteId) {
    URI theURI = UriComponentsBuilder.fromHttpUrl(getURL("/HydraTest/create"))
        .queryParam("fullTestSpec", fullTestSpec)
        .queryParam("hydraTestsuiteId", hydraTestsuiteId)
        .build(false).toUri();
    ResponseEntity<HydraTest> hostResponse = restTemplate.getForEntity(theURI, HydraTest.class);
    return (HydraTest)getResponse(hostResponse);
  }

  @Override
  public HydraTestDetail createHydraTestDetail(String elapsedTime, String diskStr, String status,
                                               String error, String bugNumber, long testId,
                                               long testSuiteDetailId, int runId, String comment,
                                               String tags) {
    URI theURI = UriComponentsBuilder.fromHttpUrl(getURL("/HydraTestDetail/create"))
        .queryParam("elapsedTime", elapsedTime)
        .queryParam("diskStr", diskStr)
        .queryParam("status", status)
        .queryParam("error", error)
        .queryParam("bugNumber", bugNumber)
        .queryParam("testId", testId)
        .queryParam("testSuiteDetailId", testSuiteDetailId)
        .queryParam("runId", runId)
        .queryParam("comment", comment)
        .queryParam("tags", tags)
        .build(false).toUri();
    ResponseEntity<HydraTestDetail> hostResponse = restTemplate.getForEntity(theURI, HydraTestDetail.class);
    return (HydraTestDetail)getResponse(hostResponse);
  }

  @Override
  public HydraTestDetail getHydraTestDetail(long testId, long testSuiteDetailId, int runId) {
    URI theURI = UriComponentsBuilder.fromHttpUrl(getURL("/HydraTestDetail/get"))
        .queryParam("testId", testId)
        .queryParam("testSuiteDetailId", testSuiteDetailId)
        .queryParam("runId", runId)
        .build(false).toUri();
    ResponseEntity<HydraTestDetail> hostResponse = restTemplate.getForEntity(theURI, HydraTestDetail.class);
    return (HydraTestDetail)getResponse(hostResponse);
  }

  @Override
  public void updateHydraTestDetailBugNumber(String tags, Integer id) {
    URI theURI = UriComponentsBuilder.fromHttpUrl(getURL("/HydraTestDetail/updateBugNumber"))
        .queryParam("tags", tags)
        .queryParam("id", id)
        .build(false).toUri();
    ResponseEntity<String> hostResponse = restTemplate.getForEntity(theURI, String.class);
  }

  @Override
  public IdAndName getHydraTestsuiteIdAndNameById(Integer id) {
    URI theURI = UriComponentsBuilder.fromHttpUrl(getURL("/HydraTestsuite/getIdAndNameById"))
        .queryParam("id", id)
        .build(false).toUri();
    ResponseEntity<HydraTestsuite> hostResponse = restTemplate.getForEntity(theURI, HydraTestsuite.class);
    return (IdAndName)getResponse(hostResponse);
  }

  @Override
  public IdAndName getHydraTestsuiteIdAndNameByName(String name) {
    URI theURI = UriComponentsBuilder.fromHttpUrl(getURL("/HydraTestsuite/getIdAndNameByName"))
        .queryParam("name", name)
        .build(false).toUri();
    ResponseEntity<HydraTestsuite> hostResponse = restTemplate.getForEntity(theURI, HydraTestsuite.class);
    return (IdAndName)getResponse(hostResponse);
  }

  @Override
  public HydraTestsuiteDetail getHydraTestsuiteDetail(Integer hydraTestsuiteId, Integer hydraRunId,
                                                      Integer hostId, Date date) {
    URI theURI = UriComponentsBuilder.fromHttpUrl(getURL("/HydraTestsuite/get"))
        .queryParam("hydraTestsuiteId", hydraTestsuiteId)
        .queryParam("hydraRunId", hydraRunId)
        .queryParam("hostId", hostId)
        .queryParam("date", date)
        .build(false).toUri();
    ResponseEntity<HydraTestsuiteDetail> hostResponse = restTemplate.getForEntity(theURI, HydraTestsuiteDetail.class);
    return (HydraTestsuiteDetail)getResponse(hostResponse);
  }

  @Override
  public TestSuiteInfo getTestSuiteInfo(long suiteId) {
    URI theURI = UriComponentsBuilder.fromHttpUrl(getURL("/TestSuiteInfo/getById"))
        .queryParam("suiteId", suiteId)
        .build(false).toUri();
    ResponseEntity<TestSuiteInfo> hostResponse = restTemplate.getForEntity(theURI, TestSuiteInfo.class);
    return (TestSuiteInfo)getResponse(hostResponse);
  }

  @Override
  public HydraTestsuiteDetail getHydraTestsuiteDetailById(Integer hydraTestsuiteId) {
    URI theURI = UriComponentsBuilder.fromHttpUrl(getURL("/HydraTestsuiteDetail/getById"))
        .queryParam("hydraTestsuiteId", hydraTestsuiteId)
        .build(false).toUri();
    ResponseEntity<HydraTestsuiteDetail> hostResponse = restTemplate.getForEntity(theURI, HydraTestsuiteDetail.class);
    return (HydraTestsuiteDetail)getResponse(hostResponse);
  }

  @Override
  public HydraTestsuiteDetail createHydraTestsuiteDetail(Date date, String elapsedTime, String diskUsage,
                                            String localConf, Integer hydraTestsuiteId,
                                            Integer hydraRunId, Integer hostId, String comment,
                                            String artifactLocation) {
    URI theURI = UriComponentsBuilder.fromHttpUrl(getURL("/HydraTestsuiteDetail/create"))
        .queryParam("date", date)
        .queryParam("elapsedTime", elapsedTime)
        .queryParam("diskUsage", diskUsage)
        .queryParam("localConf", localConf)
        .queryParam("hydraTestsuiteId", hydraTestsuiteId)
        .queryParam("hydraRunId", hydraRunId)
        .queryParam("hostId", hostId)
        .queryParam("comment", comment)
        .queryParam("artifactLocation", artifactLocation)
        .build(false).toUri();
    ResponseEntity<HydraTestsuiteDetail> hostResponse = restTemplate.getForEntity(theURI, HydraTestsuiteDetail.class);
    return (HydraTestsuiteDetail)getResponse(hostResponse);
  }

  @Override
  public HydraHistory getHydraHistoryForBatteryTest(int id, Map<Integer, HydraRun> hydraRunList) {
    URI theURI = UriComponentsBuilder.fromHttpUrl(getURL("/HydraHistory/get"))
        .queryParam("id", id)
        .queryParam("hydraRunList", hydraRunList)
        .build(false).toUri();
    ResponseEntity<HydraHistory> hostResponse = restTemplate.getForEntity(theURI, HydraHistory.class);
    return (HydraHistory)getResponse(hostResponse);
  }

  @Override
  public List<Integer> getHydraRunsForBatteryTest(int id, int numRuns) {
    URI theURI = UriComponentsBuilder.fromHttpUrl(getURL("/HydraHistory/getHydraRunsForBatteryTest"))
        .queryParam("id", id)
        .queryParam("numRuns", numRuns)
        .build(false).toUri();
    ResponseEntity hostResponse = restTemplate.getForEntity(theURI, List.class);
    return (List<Integer>)getResponse(hostResponse);
  }
}
