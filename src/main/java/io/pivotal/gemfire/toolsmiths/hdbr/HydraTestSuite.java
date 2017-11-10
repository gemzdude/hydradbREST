package io.pivotal.gemfire.toolsmiths.hdbr;

//import io.pivotal.gemfire.toolsmiths.hdbr.data.TestSuiteDetail;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/hdb")
public class HydraTestSuite {

  @Autowired
  io.pivotal.gemfire.toolsmiths.hdbr.data.gen.HydraTestsuiteRepository dao;
//
//  RestTemplate restTemplate = new RestTemplate();

//  @GetMapping(value = {"/v1/getOrCreateTestSuiteDetail"},
//      params = {"runId",
//          "hostId",
//          "testSuiteId",
//          "date",
//          "elapsedTime",
//          "diskStr",
//          "localConf",
//          "btComment",
//          "artifactLogLocation"
//      }
//  )

//  TestSuiteDetail getOrCreateTestSuiteDetail(
//      @RequestParam("runId") int runId,
//      @RequestParam("hostId") int hostId,
//      @RequestParam("testSuiteId") long testSuiteId,
//      @RequestParam("date") Date date,
//      @RequestParam("elapsedTime") String elapsedTime,
//      @RequestParam("diskStr") String diskStr,
//      @RequestParam("localConf") String localConf,
//      @RequestParam("btComment") String btComment,
//      @RequestParam("artifactLogLocation") String artifactLogLocation)
//      throws DataAccessException {
//
//      Long id = 1l;
//      return new TestSuiteDetail(id, date, elapsedTime, diskStr,
//          localConf,testSuiteId, runId, hostId, btComment, artifactLogLocation);
//  }
//
  //@GetMapping(value = { "/test" })
  //@PathVariable("id")
  //@RequestParam("id")
//@RequestMapping(value="/test")
//  private HydraTestsuite doTest(@RequestParam("id") int id) {
//    HydraTestsuite hts = dao.findOne(id);
//
//    if(hts==null) {
//      System.out.println("SAJSAJ hts is null");
//    } else {
//      System.out.println("SAJSAJ" + hts.getName());
//    }
////  return new ResponseEntity<>(hydraTestsuiteDetail, HttpStatus.OK);
//    return hts;
//  }
//    Date date = new Date();
//    String dateStr = date.toString();
//    String elapsedTime = "some time";
//    String diskStr = "some diskStr";
//    String localConf = "some conf";
//    long testSuiteId = 151L;
//    int runId = 1664;
//    int hostId = 15703;
//    String btComment = "some comment";
//    String artifactLogLocation = "artifact location";
//
//    String myURI = UriComponentsBuilder.fromHttpUrl(dao.getURL() + "/getTestSuiteDetail")
//        .queryParam("date", dateStr)
//        .queryParam("elapsedTime", elapsedTime)
//        .queryParam("diskStr", diskStr)
//        .queryParam("localConf", localConf)
//        .queryParam("testSuiteId", testSuiteId)
//        .queryParam("runId", runId)
//        .queryParam("hostId", hostId)
//        .queryParam("btComment", btComment)
//        .queryParam("artifactLogLocation", artifactLogLocation)
//        .toUriString();
//
//    System.out.println("SAJURI=" + myURI);
//
//    TestSuiteDetail sajObj = restTemplate.getForObject(myURI, TestSuiteDetail.class);
//
//    return sajObj;
//  }
//
//  @GetMapping(value = { "/getTestSuiteDetail" })
//  //private TestSuiteDetail getTestSuiteDetail(@RequestParam(value="date") @DateTimeFormat(pattern="\"EEE MMM d HH:mm:ss zzz yyyy\"") Date date
//   private TestSuiteDetail getTestSuiteDetail(@RequestParam(value="date") @DateTimeFormat(pattern = "EEE%20MMM%20dd%20HH:mm:ss%20zzz%20yyyy") Date date,
//                                             @RequestParam(value="elapsedTime") String elapsedTime,
//                                             @RequestParam(value="diskStr") String diskStr,
//                                             @RequestParam(value="localConf") String localConf,
//                                             @RequestParam(value="testSuiteId") long testSuiteId,
//                                             @RequestParam(value="runId") int runId,
//                                             @RequestParam(value="hostId") int hostId,
//                                             @RequestParam(value="btComment") String btComment,
//                                             @RequestParam(value="artifactLogLocation") String artifactLogLocation)
//
//  {
//
//    // Thu Jun 18 20:56:02 EDT 2009
//    System.out.println("SAJDATE=" + date);
//
//    Date newDate = new Date();
//
//    TestSuiteDetail tsd;
////
////    try {
////      SimpleDateFormat parser = new SimpleDateFormat("EEE MMM d HH:mm:ss zzz yyyy");
////      newDate = parser.parse(dateStr);
////    } catch(Exception e) {
////      System.out.println("blew date conversion");
////    }
//
//      tsd = dao.getTemplate().queryForObject(
//          "SELECT ID FROM HYDRA_TESTSUITE_DETAIL WHERE HYDRA_TESTSUITE_ID=? AND HYDRA_RUN_ID=? AND HOST_ID=?",
//          new Object[] { testSuiteId, runId, hostId },
//          new RowMapper<TestSuiteDetail>() {
//            @Override
//            public TestSuiteDetail mapRow(ResultSet rs, int arg1) throws SQLException {
//              return new TestSuiteDetail(rs.getLong(1),
//                  date,
//                  elapsedTime,
//                  diskStr,
//                  localConf,
//                  testSuiteId,
//                  runId,
//                  hostId,
//                  btComment,
//                  artifactLogLocation);
//            }
//          });
//
//
//    return tsd;
//
//    }
//    //Set<TestSuiteInfo> getAllTestSuiteInfos() throws DataAccessException;
//
//  //TestSuiteInfo getOrCreateTestSuiteInfo(String name);
}
