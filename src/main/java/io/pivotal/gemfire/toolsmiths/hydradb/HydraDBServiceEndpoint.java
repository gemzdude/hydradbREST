package io.pivotal.gemfire.toolsmiths.hydradb;

import io.pivotal.gemfire.toolsmiths.hydradb.data.hydra.Host;
import io.pivotal.gemfire.toolsmiths.hydradb.data.hydra.HydraRun;
import io.pivotal.gemfire.toolsmiths.hydradb.data.hydra.HydraTest;
import io.pivotal.gemfire.toolsmiths.hydradb.data.hydra.HydraTestDetail;
import io.pivotal.gemfire.toolsmiths.hydradb.data.hydra.HydraTestsuiteDetail;
import io.pivotal.gemfire.toolsmiths.hydradb.data.hydra.repo.HostRepo;
import io.pivotal.gemfire.toolsmiths.hydradb.data.hydra.repo.HydraHistoryRepo;
import io.pivotal.gemfire.toolsmiths.hydradb.data.hydra.repo.HydraRunRepo;
import io.pivotal.gemfire.toolsmiths.hydradb.data.hydra.repo.HydraTestDetailRepo;
import io.pivotal.gemfire.toolsmiths.hydradb.data.hydra.repo.HydraTestRepo;
import io.pivotal.gemfire.toolsmiths.hydradb.data.hydra.repo.HydraTestsuiteDetailRepo;
import io.pivotal.gemfire.toolsmiths.hydradb.data.hydra.repo.HydraTestsuiteRepo;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Date;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(value = "/HydraDB", produces = MediaType.APPLICATION_JSON_VALUE)
public class HydraDBServiceEndpoint implements HydraDBService {

  private static Logger log = Logger.getLogger(HydraDBServiceEndpoint.class);

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
  HydraTestsuiteRepo hydraTestsuiteRepo;

  @Autowired
  HydraTestsuiteDetailRepo hydraTestsuiteDetailRepo;

  @Autowired
  HydraHistoryRepo hydraHistoryRepo;

  /* Host methods */

  @GetMapping(value="/Host/create", params={"name", "osType", "osInfo"})
  public void createHost(@RequestParam("name") String name,
                         @RequestParam("osType") String osType,
                         @RequestParam("osInfo") String osInfo) {
    hostRepo.createHost(name, osType, osInfo);
  }

  @GetMapping(value="/Host/getByName", params="name")
  public List<Host> getHostByName(String name) {
    return hostRepo.getHostByName(name);
  }

  @GetMapping(value="/Host/getById", params="id")
  public Host getHostById(@RequestParam("id") Integer id) {
    return hostRepo.getHostById(id);
  }

  @GetMapping(value="/Host/max")
  public Integer maxHostId() {
    return hostRepo.maxId();
  }

  /* HydraRun methods */

  @GetMapping(value="/HydraRun/max")
  public Integer maxHydraRunId() {
    return hydraRunRepo.maxId();
  }

  @GetMapping(value="/HydraRun/getById", params="id")
  public HydraRun getHydraRunById(@RequestParam("id") Integer id) {
    return hydraRunRepo.getHydraRunById(id);
  }

  @GetMapping(value="/HydraRun/create", params={"userName", "productVersion", "buildId", "svnRepository", "svnRevision", "javaVersion", "javaVendor", "javaHome", "date", "regressionType", "comments", "buildLocation"})
  public HydraRun createHydraRun(
      @RequestParam("userName") String userName,
      @RequestParam("productVersion") String productVersion,
      @RequestParam("buildId") String buildId,
      @RequestParam("svnRepository") String svnRepository,
      @RequestParam("svnRevision") String svnRevision,
      @RequestParam("javaVersion") String javaVersion,
      @RequestParam("javaVendor") String javaVendor,
      @RequestParam("javaHome") String javaHome,
      @RequestParam("date") @DateTimeFormat(pattern="yyyy-MM-dd") Date date,
      @RequestParam("regressionType") Integer regressionType,
      @RequestParam("comments") String comments,
      @RequestParam("buildLocation") String buildLocation)
  {
    return hydraRunRepo.createHydraRun(userName,
        productVersion,
        buildId,
        svnRepository,
        svnRevision,
        javaVersion,
        javaVendor,
        javaHome,
        date,
        regressionType,
        comments,
        buildLocation);
  }

  @GetMapping(value="/HydraRun/getSet", params={"list", "gemfireVersion", "jdk", "jdkVendor", "svnRevision", "branch", "buildUser"})
  public Map<Integer, HydraRun> getHydraRunSet(
      @RequestParam("list") List<Integer> list,
      @RequestParam("gemfireVersion") String gemfireVersion,
      @RequestParam("jdk") String jdk,
      @RequestParam("jdkVendor") String jdkVendor,
      @RequestParam("svnRevision") int svnRevision,
      @RequestParam("branch") String branch,
      @RequestParam("buildUser") String buildUser)
  {
    return hydraRunRepo.getHydraRunSet(
            list,
            gemfireVersion,
            jdk,
            jdkVendor,
            svnRevision,
            branch,
            buildUser);
  }

  @Override
  public List<Integer> getHydraRunsForBatteryTest(int id, int numRuns)
  {
    return null;
  }

  /* HydraTest methods */

  @GetMapping(value="/HydraTest/getById", params="hydraTestsuiteId")
  public HydraTest getHydraTestById(@RequestParam("hydraTestsuiteId") Integer hydraTestsuiteId) {
    return hydraTestRepo.getHydraTestById(hydraTestsuiteId);
  }

//  @GetMapping(value="/HydraTestInfo/getById", params="id")
//  public TestInfo getTestInfo(int id)
//  {
//    return hydraTestRepo.;
//  }

  @GetMapping(value="/HydraTest/getId", params={"fullTestSpec", "hydraTestsuiteId"})
  public Integer getHydraTestId(String fullTestSpec, Integer hydraTestsuiteId)
  {
    return hydraTestRepo.getHydraTestId(fullTestSpec, hydraTestsuiteId);
  }

  @GetMapping(value="/HydraTest/create", params={"conf", "fullTestSpec", "hydraTestsuiteId"})
  public Integer createHydraTest(String conf,
                                 String fullTestSpec,
                                 Integer hydraTestsuiteId)
  {
    return hydraTestRepo.createHydraTest(conf, fullTestSpec, hydraTestsuiteId);
  }

  @GetMapping(value="/HydraTest/getByTestSpecAndTestsuiteId", params={"conf", "fullTestSpec", "hydraTestsuiteId"})
  public HydraTest getHydraTestByFullTestSpecAndHydraTestsuiteId(String fullTestSpec,
                                                                 Integer hydraTestsuiteId) {
    return hydraTestRepo.getHydraTestByFullTestSpecAndHydraTestsuiteId(fullTestSpec, hydraTestsuiteId);
  }

  /* HydraTestDetail methods */

  @GetMapping(value="/HydraTestDetail/create", params={"elapsedTime", "diskStr", "status", "error", "bugNumber", "testId", "testsuiteDetailId", "runId", "comment", "tags"})
  public HydraTestDetail createHydraTestDetail(@RequestParam("elapsedTime")String elapsedTime,
                                               @RequestParam("diskStr")String diskStr,
                                               @RequestParam("status")String status,
                                               @RequestParam("error")String error,
                                               @RequestParam("bugNumber")String bugNumber,
                                               @RequestParam("testId")long testId,
                                               @RequestParam("testSuiteDetailId")long testSuiteDetailId,
                                               @RequestParam("runId")int runId,
                                               @RequestParam("comment")String comment,
                                               @RequestParam("tags")String tags)
  {
    return hydraTestDetailRepo.createHydraTestDetail(elapsedTime,
        diskStr,
        status,
        error,
        bugNumber,
        testId,
        testSuiteDetailId,
        runId,
        comment,
        tags);
  }

  @GetMapping(value="/HydraTestDetail/get", params={"testId", "testsuiteDetailId", "runId"})
  public HydraTestDetail getHydraTestDetail(@RequestParam("testId") long testId,
                                            @RequestParam("testSuiteDetailId") long testSuiteDetailId,
                                            @RequestParam("runId")int runId)
  {
    return hydraTestDetailRepo.getHydraTestDetail(testId, testSuiteDetailId, runId);
  }

  public void updateHydraTestDetailBugNumber(String tags, Integer id) {
    hydraTestDetailRepo.updateHydraTestDetailBugNumber(tags,id);
  }

  /* HydraTestSuite methods */

  @GetMapping(value="/HydraTestsuite/getInfo", params="suiteId")
  public TestSuiteInfo getTestSuiteInfo(@RequestParam("suiteId") long suiteId) {
    return hydraTestsuiteRepo.getTestSuiteInfo(suiteId);
  }

  @GetMapping(value="/HydraTestsuite/getInfoById", params="id")
  public IdAndName getHydraTestsuiteIdAndNameById(@RequestParam("id") Integer id) {
    return hydraTestsuiteRepo.getHydraTestsuiteIdAndNameById(id);
  }

  @GetMapping(value="/HydraTestsuite/getInfoByName", params="name")
  public IdAndName getHydraTestsuiteIdAndNameByName(@RequestParam("name") String name) {
    return hydraTestsuiteRepo.getHydraTestsuiteIdAndNameByName(name);
  }

  /* HydraTestSuiteDetail methods */

  @GetMapping(value="/HydraTestsuiteDetail/getById", params="hydraTestsuiteId")
  public HydraTestsuiteDetail getHydraTestsuiteDetailById(@RequestParam("hydraTestsuiteId") Integer hydraTestsuiteId) {
    return hydraTestsuiteDetailRepo.getHydraTestsuiteDetailById(hydraTestsuiteId);
  }

  @GetMapping(value="/HydraTestsuiteDetail/get", params={"hydraTestsuiteId", "hydraRunId", "hostId", "date"})
  public HydraTestsuiteDetail getHydraTestsuiteDetail(@RequestParam("hydraTestsuiteId") Integer hydraTestsuiteId,
                                                      @RequestParam("hydraRunId") Integer hydraRunId,
                                                      @RequestParam("hostId") Integer hostId,
                                                      @RequestParam("date") Date date)
  {
    return hydraTestsuiteDetailRepo.getHydraTestsuiteDetail(hydraTestsuiteId, hydraRunId, hostId, date);
  }


  @GetMapping(value="/HydraTestsuiteDetail/create", params={"date", "elapsedTime", "diskUsage", "localConf", "hydraTestsuiteId", "hydraRunId", "hostId", "comment", "artifactLocation"})
  public Integer createHydraTestsuiteDetail(@RequestParam("date") Date date,
                                     @RequestParam("elapsedTime") String elapsedTime,
                                     @RequestParam("diskUsage") String diskUsage,
                                     @RequestParam("localConf") String localConf,
                                     @RequestParam("hydraTestsuiteId") Integer hydraTestsuiteId,
                                     @RequestParam("hydraRunId") Integer hydraRunId,
                                     @RequestParam("hostId") Integer hostId,
                                     @RequestParam("comment") String comment,
                                     @RequestParam("artifactLocation") String artifactLocation)
  {
    return hydraTestsuiteDetailRepo.createHydraTestsuiteDetail(date,
                                                              elapsedTime,
                                                              diskUsage,
                                                              localConf,
                                                              hydraTestsuiteId,
                                                              hydraRunId,
                                                              hostId,
                                                              comment,
                                                              artifactLocation);
  }

  /* Hydra History methods */

  @GetMapping(value="/HydraTestsuiteDetail/create", params={"id", "hydraRunList"})
  public HydraHistory getHydraHistoryForBatteryTest(@RequestParam("id") int id,
                                                    @RequestParam("hydraRunList") Map<Integer, HydraRun> hydraRunList)
  {
    return hydraHistoryRepo.getHydraHistoryForBatteryTest(id, hydraRunList);
  }

  /* Misc methods */

  /* Test request example */
  @GetMapping("/sajtest")
  private HydraRun doit(RestTemplate restTemplate) {

    String myURI = UriComponentsBuilder.fromHttpUrl("http://localhost:8080/hydrarun/create")
        .queryParam("userName", "CI")
        .queryParam("productVersion", "9.3.0")
        .queryParam("buildId", "CI-a8e1fab63848b064fdecb2c63fa2fa26e8f06cb4")
        .queryParam("svnRepository", "develop")
        .queryParam("svnRevision", "a8e1fab63848b064fdecb2c63fa2fa26e8f06cb4")
        .queryParam("javaVersion", "1.8.0_151")
        .queryParam("javaVendor", "Java HotSpot(TM) 64-Bit Server VM (build 25.151-b12, mixed mode)")
        .queryParam("javaHome", "/var/vcap/data/jdk/jdk1.8.0_151/bin/java")
        .queryParam("date", "2017-11-07")
        .queryParam("fullRegression", false)
        .queryParam("regressionType", 2)
        .queryParam("comments", "")
        .queryParam("buildLocation", "/var/vcap/data/gemfire-build").toUriString();

    System.out.println("URI=" + myURI);

    HydraRun sajObj = restTemplate.getForObject(myURI, HydraRun.class);

    System.out.println("SAJCREATE: " + sajObj.getId());

    return sajObj;
  }


}
