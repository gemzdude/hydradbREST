package io.pivotal.gemfire.toolsmiths.hydradb.data.hydra.repo;

import io.pivotal.gemfire.toolsmiths.hydradb.TestSuiteInfo;
import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
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
}
