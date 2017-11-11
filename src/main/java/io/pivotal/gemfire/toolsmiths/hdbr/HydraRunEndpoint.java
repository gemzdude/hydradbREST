package io.pivotal.gemfire.toolsmiths.hdbr;

import io.pivotal.gemfire.toolsmiths.hdbr.data.gen.HydraRun;
import io.pivotal.gemfire.toolsmiths.hdbr.data.gen.HydraRunRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/hydrarun")
public class HydraRunEndpoint {

  @Autowired
  HydraRunRepository dao;

  @Autowired
  HydraRunRepo hydraRunRepo;

  @GetMapping(value="/test", params="id")
  private HydraRun getHydraRunById(@RequestParam("id") int id) {
//  private HydraRun doTest(@RequestParam Map<String, String> myMap) {
//    if(myMap.size()==0) {
//      System.out.println("SAJSAJ no params");
//    } else {
//      for (Map.Entry<String, String> pair : myMap.entrySet()) {
//        System.out.println("SAJPARAM: " + pair.getKey() + "  SAJVALUE: " + pair.getValue());
//      }
//
//    }
//
//    int id = Integer.parseInt(myMap.get("id"));

//    HydraRun hr = dao.findOne(id);

//    if(hr!=null) {
//      System.out.println("BUILD LOCATION: " + hr.getBuildLocation());
//    }

    return hydraRunRepo.getHydraRunById(id);
  }

  @GetMapping(value="/test", params="loc")
  private List<Integer> doTest(@RequestParam("loc") String loc) {

    List<Integer> theList = dao.findByLoc(loc);

    return theList;
  }

  @GetMapping(value="/create", params={"userName", "productVersion", "buildId", "svnRepository", "svnRevision", "javaVersion", "javaVendor", "javaHome", "date", "regressionType", "comments", "buildLocation"})
  private HydraRun create(
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

    HydraRun hr = new HydraRun();

    Date nowDate = new Date();

    hr.setUserName(userName);
    hr.setProductVersion(productVersion);
    hr.setBuildId(buildId);
    hr.setSvnRepository(svnRepository);
    hr.setSvnRevision(svnRevision);
    hr.setJavaVersion(javaVersion);
    hr.setJavaVendor(javaVendor);
    hr.setJavaHome(javaHome);
    hr.setDate(new Timestamp(nowDate.getTime()));
    hr.setRegressionType(regressionType);
    hr.setComments(comments);
    hr.setBuildLocation(buildLocation);

    dao.save(hr);

    return hr;
  }

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

  @GetMapping("/max")
  private Integer max(RestTemplate restTemplate) {

    Integer hr = dao.findMax();

    return hr;
  }

  @Autowired
  HydraDBService service;

  @GetMapping("/max2")
  private Integer max2(RestTemplate restTemplate) {

    return service.maxRun();
  }

  @GetMapping("/user")
  private List<HydraRun> user(@RequestParam("name") String uname, RestTemplate restTemplate) {

    return hydraRunRepo.findFirst5ByUserNameOrderByDateDesc(uname);
  }

  @GetMapping("/maxsuite")
  private Integer maxSuite() {
    return service.maxSuite();
  }

  @GetMapping("/idAndName")
  private IdAndName idAndName(@RequestParam("id") Integer id) {

    IdAndName hts = service.getHydraTestsuiteIdAndNameById(id);

    return hts;
  }

}
