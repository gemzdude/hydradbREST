package io.pivotal.gemfire.toolsmiths.hydradb.data.hydra.repo;

import io.pivotal.gemfire.toolsmiths.hydradb.TestSuiteInfo;
import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class HydraTestsuiteRepoImpl {

  private static Logger log = Logger.getLogger(HydraTestsuiteRepoImpl.class);

  public  static final String SELECT_HYDRA_TESTSUITE            = "SELECT ID, NAME FROM HYDRA_TESTSUITE";

  JdbcTemplate jdbcTemplate;
  public TestSuiteInfo getTestSuiteInfo(long suiteId) throws DataAccessException {
    return jdbcTemplate.queryForObject(SELECT_HYDRA_TESTSUITE + " WHERE ID=?", new Object[] { suiteId }, getTestSuiteInfoRowMapper());
  }

  private RowMapper<TestSuiteInfo> getTestSuiteInfoRowMapper() {
    return new RowMapper<TestSuiteInfo>() {
      @Override
      public TestSuiteInfo mapRow(ResultSet rs, int arg1) throws SQLException {
        return new TestSuiteInfo(rs.getLong(1), rs.getString(2));
      }
    };
  }

  @Override
  public TestSuiteInfo getOrCreateTestSuiteInfo(final String name) {
    try {
      TestSuiteInfo tsi = getTestSuiteByName(name);
      return tsi;
    } catch(EmptyResultDataAccessException e) {
      log.info("No TestSuitInfo found for name : " + name);
      jdbcTemplate.update(
          "INSERT INTO HYDRA_TESTSUITE(ID, NAME) VALUES(NEXTVAL('hydra_testsuite_id_seq'), ?);", new Object[] { name }, new int[] { 1 });
      return getTestSuiteByName(name);
    }
  }

  private TestSuiteInfo getTestSuiteByName(final String name) {
    return jdbcTemplate.queryForObject(SELECT_HYDRA_TESTSUITE + " WHERE NAME=?", new String[]{name}, getTestSuiteInfoRowMapper());
  }

}
