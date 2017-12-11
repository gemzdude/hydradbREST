package io.pivotal.gemfire.toolsmiths.hydradb;

import io.pivotal.gemfire.toolsmiths.hydradb.data.hydra.Host;
import io.pivotal.gemfire.toolsmiths.hydradb.data.hydra.HydraRun;
import io.pivotal.gemfire.toolsmiths.hydradb.data.hydra.HydraTest;
import io.pivotal.gemfire.toolsmiths.hydradb.data.hydra.HydraTestDetail;
import io.pivotal.gemfire.toolsmiths.hydradb.data.hydra.HydraTestDetailExt;
import io.pivotal.gemfire.toolsmiths.hydradb.data.hydra.HydraTestsuite;
import io.pivotal.gemfire.toolsmiths.hydradb.data.hydra.HydraTestsuiteDetail;
import io.pivotal.gemfire.toolsmiths.hydradb.data.hydra.repo.HostRepo;
import io.pivotal.gemfire.toolsmiths.hydradb.data.hydra.repo.HydraHistoryRepo;
import io.pivotal.gemfire.toolsmiths.hydradb.data.hydra.repo.HydraRunRepo;
import io.pivotal.gemfire.toolsmiths.hydradb.data.hydra.repo.HydraTestDetailExtRepo;
import io.pivotal.gemfire.toolsmiths.hydradb.data.hydra.repo.HydraTestDetailRepo;
import io.pivotal.gemfire.toolsmiths.hydradb.data.hydra.repo.HydraTestRepo;
import io.pivotal.gemfire.toolsmiths.hydradb.data.hydra.repo.HydraTestsuiteDetailRepo;
import io.pivotal.gemfire.toolsmiths.hydradb.data.hydra.repo.HydraTestsuiteRepo;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.Date;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(value = "/HydraDB", produces = MediaType.APPLICATION_JSON_VALUE)
public class HydraDBEndpointService implements HydraDBService {

  private static Logger log = Logger.getLogger(HydraDBEndpointService.class);

//  private JdbcTemplate jdbcTemplate;

//  public void setDataSource(DataSource dataSource) {
//    this.jdbcTemplate = new JdbcTemplate(dataSource);
//  }

  @Autowired
  HostRepo hostRepo;

  @Autowired
  HydraRunRepo hydraRunRepo;

  @Autowired
  HydraTestRepo hydraTestRepo;

  @Autowired
  HydraTestDetailRepo hydraTestDetailRepo;

  @Autowired
  HydraTestDetailExtRepo hydraTestDetailExtRepo;

  @Autowired
  HydraTestsuiteRepo hydraTestsuiteRepo;

  @Autowired
  HydraTestsuiteDetailRepo hydraTestsuiteDetailRepo;

  @Autowired
  HydraHistoryRepo hydraHistoryRepo;

  /* Helper methods */

  private ResponseEntity setResponse(Object obj) {
    HttpStatus responseStatus = HttpStatus.NOT_FOUND;
    if (obj != null) {
      responseStatus = HttpStatus.OK;
    }
    return new ResponseEntity(obj, responseStatus);
  }

  private Host getHostFromList(List<Host> objList) {
    Host obj = null;
    if(objList != null && objList.size() > 0) {
      obj = objList.get(0);
    }
    return obj;
  }

  /* Host methods */

  @GetMapping(value="/Host/create")
  public ResponseEntity<Host> createHost(@RequestParam("name") String name,
                                         @RequestParam("osType") String osType,
                                         @RequestParam("osInfo") String osInfo) {
    Host host = new Host();
    host.setName(name);
    host.setOsType(osType);
    host.setOsInfo(osInfo);
    return setResponse(hostRepo.save(host));
  }

  @GetMapping(value="/Host/getOrCreate")
  public ResponseEntity<Host> getOrCreateHost(@RequestParam("name") String name,
                                         @RequestParam("osType") String osType,
                                         @RequestParam("osInfo") String osInfo) {
    Host[] hosts = findHost(name, osType, osInfo);
    if(hosts!=null && hosts.length!=0) return setResponse(hosts[0]);
    Host host = new Host();
    host.setName(name);
    host.setOsType(osType);
    host.setOsInfo(osInfo);
    return setResponse(hostRepo.save(host));
  }

  private Host[] findHost(String name, String osType, String osInfo) {
    return hostRepo.getHostByNameAndOsTypeAndOsInfo(name, osType, osInfo);
  }

  @GetMapping(value="/Host/getByName", params="name")
  public ResponseEntity<Host> getHostByName(String name) {
    Host[] hosts = hostRepo.getHostByName(name);
    return setResponse(hosts[0]);
  }

  @GetMapping(value="/Host/getById", params="id")
  public ResponseEntity<Host> getHostById(@RequestParam("id") Integer id) {
    return setResponse(hostRepo.getHostById(id));
  }

  @GetMapping(value="/Host/max")
  public ResponseEntity<Integer> maxHostId() {
    return setResponse(hostRepo.maxId());
  }

  /* HydraRun methods */

  @GetMapping(value="/HydraRun/max")
  public ResponseEntity<Integer> maxHydraRunId() {
    return setResponse(hydraRunRepo.maxId());
  }

  @GetMapping(value="/HydraRun/getById", params="id")
  public ResponseEntity<HydraRun> getHydraRunById(@RequestParam("id") Integer id) {
    return setResponse(hydraRunRepo.getHydraRunById(id));
  }

  @GetMapping(value="/HydraRun/getOrCreate", params={"userName", "productVersion", "buildId",
      "svnRepository", "svnRevision", "javaVersion", "javaVendor", "javaHome",
      "fullRegression", "regressionType", "comments", "buildLocation"})
  public ResponseEntity<HydraRun> getOrCreateHydraRun(String userName, String productVersion, String buildId,
                                                      String svnRepository, String svnRevision, String javaVersion,
                                                      String javaVendor, String javaHome, Boolean fullRegression,
                                                      Integer regressionType, String comments, String buildLocation)
  {
    List<HydraRun> runs = hydraRunRepo.hydraRunSearch(userName, productVersion, buildId,
        svnRepository, svnRevision, javaVersion, javaVendor);

    if(runs!=null && runs.size()!=0) return setResponse(runs.get(0));
    HydraRun run = createRun(userName, productVersion, buildId, svnRepository, svnRevision,
        javaVersion, javaVendor, javaHome, fullRegression, regressionType, comments, buildLocation);
    return setResponse(run);
  }

  @GetMapping(value="/HydraRun/create", params={"userName", "productVersion", "buildId",
      "svnRepository", "svnRevision", "javaVersion", "javaVendor", "javaHome",
      "fullRegression", "regressionType", "comments", "buildLocation"})
  public ResponseEntity<HydraRun> createHydraRun(String userName, String productVersion, String buildId,
                                                 String svnRepository, String svnRevision, String javaVersion,
                                                 String javaVendor, String javaHome, Boolean fullRegression,
                                                 Integer regressionType, String comments, String buildLocation)
  {

    HydraRun run = createRun(userName, productVersion, buildId, svnRepository, svnRevision,
        javaVersion, javaVendor, javaHome, fullRegression, regressionType, comments, buildLocation);
    return setResponse(run);
  }

  HydraRun createRun(String userName, String productVersion, String buildId,
                     String svnRepository, String svnRevision, String javaVersion,
                     String javaVendor, String javaHome, Boolean fullRegression,
                     Integer regressionType, String comments, String buildLocation)
  {
    HydraRun run = new HydraRun();
    run.setDate(new Date());
    run.setUserName(userName);
    run.setProductVersion(productVersion);
    run.setBuildId(buildId);
    run.setSvnRepository(svnRepository);
    run.setSvnRevision(svnRevision);
    run.setJavaVersion(javaVersion);
    run.setJavaVendor(javaVendor);
    run.setJavaHome(javaHome);
    run.setFullRegression(fullRegression);
    run.setRegressionType(regressionType);
    run.setComments(comments);
    run.setBuildLocation(buildLocation);
    return hydraRunRepo.save(run);
  }

//  @GetMapping(value="/HydraRun/getSet", params={"list", "gemfireVersion", "jdk", "jdkVendor", "svnRevision", "branch", "buildUser"})
//  public ResponseEntity<Map<Integer, HydraRun>> getHydraRunSet(
//      @RequestParam("list") List<Integer> list,
//      @RequestParam("gemfireVersion") String gemfireVersion,
//      @RequestParam("jdk") String jdk,
//      @RequestParam("jdkVendor") String jdkVendor,
//      @RequestParam("svnRevision") int svnRevision,
//      @RequestParam("branch") String branch,
//      @RequestParam("buildUser") String buildUser)
//  {
//    return setResponse(hydraRunRepo.getHydraRunSet(
//            list,
//            gemfireVersion,
//            jdk,
//            jdkVendor,
//            svnRevision,
//            branch,
//            buildUser));
//  }
//
//  @Override
//  public ResponseEntity<List<Integer>> getHydraRunsForBatteryTest(int id, int numRuns) {
//    return setResponse(hydraRunRepo.getHydraRunsForBatteryTest(id, numRuns));
//  }

  @Override
  public ResponseEntity<List<HydraRun>> hydraRunSearch(String userName, String productVersion, String buildId,
                                 String svnRepository, String svnRevision, String javaVersion,
                                 String javaVendor) {
    return setResponse(hydraRunRepo.hydraRunSearch(userName, productVersion, buildId,
        svnRepository, svnRevision, javaVersion, javaVendor));
  }

  /* HydraTest methods */

  @GetMapping(value="/HydraTest/getById", params="hydraTestsuiteId")
  public ResponseEntity<HydraTest> getHydraTestById(Integer hydraTestsuiteId) {
    return setResponse(hydraTestRepo.getHydraTestById(hydraTestsuiteId));
  }

//  @GetMapping(value="/HydraTestInfo/getById", params="id")
//  public TestInfo getTestInfo(int id)
//  {
//    return hydraTestRepo.;
//  }

  @GetMapping(value="/HydraTest/getId", params={"fullTestSpec", "hydraTestsuiteId"})
  public ResponseEntity<Integer> getHydraTestId(String fullTestSpec, Integer hydraTestsuiteId) {
    return setResponse(hydraTestRepo.getHydraTestId(fullTestSpec, hydraTestsuiteId));
  }

  @GetMapping(value="/HydraTest/create", params={"conf", "fullTestSpec", "hydraTestsuiteId"})
  public ResponseEntity<HydraTest> createHydraTest(String conf,
                                 String fullTestSpec,
                                 Integer hydraTestsuiteId) {
    return setResponse(createTest(conf, fullTestSpec, hydraTestsuiteId));
  }

  @GetMapping(value="/HydraTest/getOrCreate", params={"conf", "fullTestSpec", "hydraTestsuiteId"})
  public ResponseEntity<HydraTest> getOrCreateHydraTest(String conf,
                                                   String fullTestSpec,
                                                   Integer hydraTestsuiteId) {
    HydraTest ht = hydraTestRepo.getHydraTestByFullTestSpecAndHydraTestsuiteId(fullTestSpec, hydraTestsuiteId);
    if(ht==null) {
      ht = createTest(conf, fullTestSpec, hydraTestsuiteId);
    }
    return setResponse(ht);
  }

  HydraTest createTest(String conf,
                       String fullTestSpec,
                       Integer hydraTestsuiteId) {
    HydraTest ht = new HydraTest();
    ht.setConf(conf);
    ht.setFullTestSpec(fullTestSpec);
    ht.setHydraTestsuiteId(hydraTestsuiteId);
    return hydraTestRepo.save(ht);
  }

  @GetMapping(value="/HydraTest/getByTestSpecAndTestsuiteId", params={"conf", "fullTestSpec", "hydraTestsuiteId"})
  public ResponseEntity<HydraTest> getHydraTestByFullTestSpecAndHydraTestsuiteId(String fullTestSpec,
                                                                 Integer hydraTestsuiteId) {
    return setResponse(hydraTestRepo.getHydraTestByFullTestSpecAndHydraTestsuiteId(fullTestSpec, hydraTestsuiteId));
  }

  /* HydraTestDetail methods */

  @GetMapping(value="/HydraTestDetail/create", params={"elapsedTime", "diskStr", "status", "error", "bugNumber", "testId", "testSuiteDetailId", "runId", "comment", "tags"})
  public ResponseEntity<HydraTestDetail> createHydraTestDetail(String elapsedTime, String diskStr, String status,
                                                               String error, String bugNumber, Integer testId,
                                                               Integer testSuiteDetailId, Integer runId,
                                                               String comment, String tags)
  {
    HydraTestDetail htd = new HydraTestDetail();
    htd.setElapsedTime(elapsedTime);
    htd.setDiskUsage(diskStr);
    htd.setStatus(status);
    htd.setError(error);
    htd.setBugNumber(bugNumber);
    htd.setHydraTestId(testId);
    htd.setHydraTestsuiteDetailId(testSuiteDetailId);
    htd.setHydraRunId(runId);
    htd.setComment(comment);
    htd.setTags(tags);
    return setResponse(hydraTestDetailRepo.save(htd));
  }

  @GetMapping(value="/HydraTestDetail/get", params={"testId", "testsuiteDetailId", "runId"})
  public ResponseEntity<HydraTestDetail> getHydraTestDetail(Integer testId, Integer testSuiteDetailId, Integer runId)
  {
    return setResponse(hydraTestDetailRepo.getHydraTestDetail(testId, testSuiteDetailId, runId));
  }


  @GetMapping(value="/HydraTestDetailExt/create", params={"testDetailId", "logLocation"})
  public ResponseEntity<HydraTestDetailExt> createHydraTestDetailExt(Long testDetailId, String logLocation) {
    HydraTestDetailExt htdx = new HydraTestDetailExt();
    htdx.setId(testDetailId);
    htdx.setLogLocation(logLocation);
    return setResponse(hydraTestDetailExtRepo.save(htdx));
  }

  public void updateHydraTestDetailBugNumber(String tags, Integer id) {
    hydraTestDetailRepo.updateHydraTestDetailBugNumber(tags,id);
  }

  /* HydraTestSuite methods */

//  @GetMapping(value="/HydraTestsuite/getInfo", params="suiteId")
//  public TestSuiteInfo getTestSuiteInfo(@RequestParam("suiteId") long suiteId) {
//    return hydraTestsuiteRepo.getTestSuiteInfo(suiteId);
//  }

  @GetMapping(value="/HydraTestsuite/getOrCreate", params="name")
  public ResponseEntity<HydraTestsuite> getOrCreateHydraTestsuite(String name) {
    HydraTestsuite hts = hydraTestsuiteRepo.getHydraTestsuiteByName(name);
    if(hts!=null) return setResponse(hts);
    hts = new HydraTestsuite();
    hts.setName(name);
    return setResponse(hydraTestsuiteRepo.save(hts));
  }

  @GetMapping(value="/HydraTestsuite/getById", params="id")
  public ResponseEntity<HydraTestsuite> getHydraTestsuiteById(Integer id) {
    return setResponse(hydraTestsuiteRepo.getHydraTestsuiteById(id));
  }

  @GetMapping(value="/HydraTestsuite/getByName", params="name")
  public ResponseEntity<HydraTestsuite> getHydraTestsuiteByName(String name) {
    return setResponse(hydraTestsuiteRepo.getHydraTestsuiteByName(name));
  }

  /* HydraTestSuiteDetail methods */

  @GetMapping(value="/HydraTestsuiteDetail/getById", params="hydraTestsuiteId")
  public ResponseEntity<HydraTestsuiteDetail> getHydraTestsuiteDetailById(Integer hydraTestsuiteId) {
    return setResponse(hydraTestsuiteDetailRepo.getHydraTestsuiteDetailById(hydraTestsuiteId));
  }

  @GetMapping(value="/HydraTestsuiteDetail/get", params={"hydraTestsuiteId", "hydraRunId", "hostId", "date"})
  public ResponseEntity<HydraTestsuiteDetail> getHydraTestsuiteDetail(Integer hydraTestsuiteId, Integer hydraRunId,
                                                      Integer hostId, Date date)
  {
    return setResponse(hydraTestsuiteDetailRepo.getHydraTestsuiteDetail(hydraTestsuiteId, hydraRunId, hostId, date));
  }

  @GetMapping(value="/HydraTestsuiteDetail/create", params={"date", "elapsedTime", "diskUsage", "localConf", "hydraTestsuiteId", "hydraRunId", "hostId", "comment", "artifactLocation"})
  public ResponseEntity<HydraTestsuiteDetail> createHydraTestsuiteDetail(Date date, String elapsedTime, String diskUsage,
                                     String localConf, Integer hydraTestsuiteId, Integer hydraRunId,
                                     Integer hostId, String comment, String artifactLocation)
  {
    HydraTestsuiteDetail htsd = new HydraTestsuiteDetail();
    htsd.setDate(date);
    htsd.setElapsedTime(elapsedTime);
    htsd.setDiskUsage(diskUsage);
    htsd.setLocalConf(localConf);
    htsd.setHydraTestsuiteId(hydraTestsuiteId);
    htsd.setHydraRunId(hydraRunId);
    htsd.setHostId(hostId);
    htsd.setComment(comment);
    htsd.setArtifactLocation(artifactLocation);
    return setResponse(hydraTestsuiteDetailRepo.save(htsd));
  }

  /* Hydra History methods */

  @GetMapping(value="/HydraTestsuiteDetail/getHydraHistoryForBatteryTest", params={"id", "hydraRunList"})
  public ResponseEntity<HydraHistory> getHydraHistoryForBatteryTest(int id, Map<Integer, HydraRun> hydraRunList)
  {
    return setResponse(hydraHistoryRepo.getHydraHistoryForBatteryTest(id, hydraRunList));
  }

  /* Misc methods */

  @Autowired
  HydraDBClient client;

  /* Test request example */
  @GetMapping("/sajtest")
  private ResponseEntity<String> doit(RestTemplate restTemplate) {

    String userName = "scott2";
    String productVersion = "sajtest productVersion";
    String buildId = "sajtest buildId";
    String svnRepository = "sajtest svnRepository";
    String svnRevision = "sajtest svnRevision";
    String javaVersion = "sajtest javaVersion";
    String javaVendor = "sajtest javaVendor";
    String javaHome = "sajtest javaHome";
//    String date = new Date();
    Boolean fullRegression = false;
    Integer regressionType = 2;
    String comments = "sajtest comment";
    String buildLocation = "sajtest buildLocation";
    HydraRun sajRun = client.getOrCreateHydraRun(userName, productVersion, buildId,
        svnRepository, svnRevision, javaVersion,
        javaVendor, javaHome, fullRegression,
        regressionType, comments, buildLocation);
    System.out.println("SAJRUN: " + sajRun);
    Integer runId = sajRun.getId();

    Host sajHost = client.getOrCreateHost("scott2", "linux", "scott linux2");
    System.out.println("SAJHOST: " + sajHost);
    Integer hostId = sajHost.getId();

    HydraTestsuite sajTestsuite = client.getOrCreateHydraTestsuite("scottTestsuite");
    System.out.println("SAJTESTSUITE: " + sajTestsuite);
    Integer testSuiteId = sajTestsuite.getId();

    Date date = new Date();
    String elapsedTime = "01:00:00";
    String diskUsage = "1234";
    String localConf = "scott localconf";
    String comment = "scott comment";
    String artifactLocation = "sajtest artifactLocation";
    HydraTestsuiteDetail htsd = client.createHydraTestsuiteDetail(date, elapsedTime, diskUsage,
        localConf, testSuiteId, runId, hostId, comment, artifactLocation);
    System.out.println("SAJTSD: " + htsd);
    Integer testSuiteDetailId = htsd.getId();

    String conf = "scott/sajtest.conf";
    String fullTestSpec = "scott/sajtest.conf edgeHosts=2 edgeVMsPerHost=1 edgeThreadsPerVM=10 bridgeHosts=4 bridgeVMsPerHost=1 bridgeThreadsPerVM=10 redundantCopies=1 numVMsToStop=1 heapMB=250";
    HydraTest ht = client.getOrCreateHydraTest(conf, fullTestSpec, testSuiteId);
    Integer testId = ht.getId();


    String htdElapsedTime = "00:03:04";
    String diskStr = "128K";
    String status = "P";
    String error = "";
    String bugNumber = "";
    String htdComment = "scott sajtest comment";
    String tags = "scott sajtest tags";
    HydraTestDetail htd = client.createHydraTestDetail(htdElapsedTime, diskStr, status, error, bugNumber, testId,
        testSuiteDetailId, runId, htdComment, tags);
    Long testDetailId = Integer.toUnsignedLong(htd.getId());

    String logLocation = "scott sajtest log location";
    HydraTestDetailExt htdx = client.createHydraTestDetailExt(testDetailId, logLocation);

    htdElapsedTime = "00:06:04";
    diskStr = "256K";
    status = "F";
    error = "Ooops mate!";
    bugNumber = "GEM-1014";
    htdComment = "scott sajtest failed";
    tags = "scott failed tags";
    htd = client.createHydraTestDetail(htdElapsedTime, diskStr, status, error, bugNumber, testId,
        testSuiteDetailId, runId, htdComment, tags);
    testDetailId = Integer.toUnsignedLong(htd.getId());

    logLocation = "scott sajtest failed log location";
    htdx = client.createHydraTestDetailExt(999999999L, logLocation);

    System.out.println("SAJERR99");

    return setResponse(htdx);

//    String myURI = UriComponentsBuilder.fromHttpUrl("http://localhost:8080/hydrarun/create")
//        .queryParam("userName", "CI")
//        .queryParam("productVersion", "9.3.0")
//        .queryParam("buildId", "CI-a8e1fab63848b064fdecb2c63fa2fa26e8f06cb4")
//        .queryParam("svnRepository", "develop")
//        .queryParam("svnRevision", "a8e1fab63848b064fdecb2c63fa2fa26e8f06cb4")
//        .queryParam("javaVersion", "1.8.0_151")
//        .queryParam("javaVendor", "Java HotSpot(TM) 64-Bit Server VM (build 25.151-b12, mixed mode)")
//        .queryParam("javaHome", "/var/vcap/data/jdk/jdk1.8.0_151/bin/java")
//        .queryParam("date", "2017-11-07")
//        .queryParam("fullRegression", false)
//        .queryParam("regressionType", 2)
//        .queryParam("comments", "")
//        .queryParam("buildLocation", "/var/vcap/data/gemfire-build").toUriString();
//    log.info("URI=" + myURI);
//    sajObj = restTemplate.getForObject(myURI, Host.class);
//    System.out.println("SAJHOST2: " + sajObj);
  }

  @ExceptionHandler({HttpClientErrorException.class})
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  public String handleAppException(HttpClientErrorException ex) {
//  public  ResponseEntity<?> handleAppException(HttpClientErrorException ex) {
//    HttpHeaders responseHeaders = new HttpHeaders();
//    responseHeaders.setContentType(MediaType.TEXT_HTML);
//    return new ResponseEntity<String>("<html><head></head><body><h1>WTF!</h1></body></html>",
//        responseHeaders, HttpStatus.NOT_ACCEPTABLE);
    return "";
  }

  @ExceptionHandler(HttpServerErrorException.class)
  @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
  public String handleAppException(HttpServerErrorException ex) {
    return "Foreign key violation";
  }

// HttpServerErrorException
//  @ExceptionHandler(Exception.class)
//  @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
//  public String handleAppException(Exception ex) {
//    return ex.toString();
//  }
}
