package io.pivotal.gemfire.toolsmiths.hydradb.data.hydra.repo;

import io.pivotal.gemfire.toolsmiths.hydradb.data.hydra.HydraTestDetail;
import io.pivotal.gemfire.toolsmiths.hydradb.data.hydra.HydraTestsuiteDetail;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

public class HydraTestsuiteDetailRepoImpl implements HydraTestsuiteDetailRepoCustom {

  private static Logger log = Logger.getLogger(HydraTestsuiteDetailRepoImpl.class);

  @Autowired
  HydraTestsuiteDetailRepo hydraTestsuiteDetailRepo;

  @Override
  public HydraTestsuiteDetail getOrCreateTestSuiteDetail(Integer runId,
                                                    Integer hostId,
                                                    Integer testSuiteId,
                                                    Date date,
                                                    String elapsedTime,
                                                    String diskStr,
                                                    String localConf,
                                                    String btComment,
                                                    String artifactLogLocation)
  {
    HydraTestsuiteDetail htsd = hydraTestsuiteDetailRepo.getHydraTestsuiteDetail(testSuiteId, runId, hostId, date);
    if(htsd!=null) {
      return htsd;
    }
    hydraTestsuiteDetailRepo.createHydraTestsuiteDetail(new Timestamp(date.getTime()),
        elapsedTime,
        diskStr,
        localConf,
        testSuiteId,
        runId,
        hostId,
        btComment,
        artifactLogLocation);
    return hydraTestsuiteDetailRepo.getHydraTestsuiteDetail(testSuiteId, runId, hostId, date);
  }

  @Override
  public List<HydraTestsuiteDetail> getLastNTestSuiteDetails(long suiteId, int max) throws DataAccessException {
    String sql = SELECT_HYDRA_TESTSUITE_DETAIL + " WHERE HYDRA_TESTSUITE_ID=? ORDER BY DATE DESC LIMIT ?";
    return jdbcTemplate.query(sql, new Object[] { suiteId, max },
        new ResultSetExtractor<List<TestSuiteDetail>>() {
          List<TestSuiteDetail> tsds = new ArrayList<TestSuiteDetail>();
          @Override
          public List<TestSuiteDetail> extractData(ResultSet rs) throws SQLException {
            while (rs.next()) {
              tsds.add(getTestSuiteDetailFromResultSet(rs));
            }
            return tsds;
          }
        });
  }

  @Override
  public List<HydraTestsuiteDetail> getTestSuiteDetailsForRun(int runId) throws DataAccessException {
    final Map<Long, HydraTestsuiteDetail> testDetailToRunMap = new HashMap<Long, HydraTestsuiteDetail>();
    final Map<Long, Set<HydraTestDetail>> testDetailListToRunMap = new HashMap<Long, Set<HydraTestDetail>>();
    final List<HydraTestsuiteDetail> tsds = new ArrayList<HydraTestsuiteDetail>();
    jdbcTemplate.query(
        SELECT_HYDRA_TESTSUITE_DETAIL + " WHERE HYDRA_RUN_ID=?",
        new Object[] { runId }, new ResultSetExtractor<String>() {
          @Override
          public String extractData(ResultSet rs) throws SQLException {
            while (rs.next()) {
              TestSuiteDetail td = getTestSuiteDetailFromResultSet(rs);
              tsds.add(td);
              long testSuiteDetailId = rs.getLong(1);
              testDetailToRunMap.put(testSuiteDetailId, td);
              testDetailListToRunMap.put(testSuiteDetailId, new TreeSet<TestDetail>(new TestDetailStateAndIdComparator()));
            }
            return null;
          }
        });

    // fire another query to get all getTestDetailsForSuiteDetail stuff and
    // create lists and then assign to testSuiteDetails object

    jdbcTemplate.query(
        SELECT_HYDRA_TEST_DETAIL_SQL + " WHERE HYDRA_RUN_ID=?",
        new Object[] { runId }, new ResultSetExtractor<String>() {

          @Override
          public String extractData(ResultSet rs) throws SQLException {
            while (rs.next()) {
              long testSuiteDetailId = rs.getLong(8);
              Set<TestDetail> details = testDetailListToRunMap.get(testSuiteDetailId);
              if(details == null) {
                log.warn("List of Test Details is coming null for " + testSuiteDetailId + ", it should not come null... need to verify (Viren)");
                continue;
              }
              TestDetail td = getTestDetailFromResultSet(rs);
              details.add(td);
              TestSuiteDetail tsd = testDetailToRunMap.get(testSuiteDetailId);
              tsd.setTestDetails(details);
            }
            return null;
          }
        });

    return tsds;
  }

}
