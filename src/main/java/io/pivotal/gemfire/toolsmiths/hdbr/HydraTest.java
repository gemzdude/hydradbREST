package io.pivotal.gemfire.toolsmiths.hdbr;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@RequestMapping("/hdb")
public class HydraTest {

  @Autowired
  HydraDao dao;

  @GetMapping(value = { "/v1" })
  HydraTest.Response doClientCall(UriComponentsBuilder builder) {

    System.out.println("SAJHELLO=" + dao.sayHello());

    JdbcTemplate jdbc = dao.getTemplate();

    HydraTest.Response resp = new HydraTest.Response();

    try {
      String sql =
          "SELECT ARTIFACT_LOCATION FROM HYDRA_TESTSUITE_DETAIL WHERE HYDRA_TESTSUITE_ID=151 AND HYDRA_RUN_ID=1664 AND HOST_ID=15703";
      String loc = jdbc.queryForObject(sql, String.class);
      resp.someText = loc;
      resp.otherText = "otherText";
    } catch (EmptyResultDataAccessException e) {
      System.out.println("SAJ BLEWIT");
    }

    return resp;
  }

  static class Response {
    public String someText;
    public String otherText;
  }
}
