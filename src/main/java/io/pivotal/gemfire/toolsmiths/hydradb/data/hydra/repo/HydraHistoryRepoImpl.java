package io.pivotal.gemfire.toolsmiths.hydradb.data.hydra.repo;

import io.pivotal.gemfire.toolsmiths.hydradb.HydraHistory;
import io.pivotal.gemfire.toolsmiths.hydradb.TestDetail;
import io.pivotal.gemfire.toolsmiths.hydradb.data.hydra.HydraRun;

import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.jdbc.core.ResultSetExtractor;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HydraHistoryRepoImpl implements HydraHistoryRepoCustom {
  private static Logger log = Logger.getLogger(HydraTestDetailImpl.class);

  public static final String SQL_HYDRA_HISTORY_BY_BATTERYTEST = "SELECT HTD.ID, HTD.ELAPSED_TIME, HTD.DISK_USAGE, "
      + " HTD.STATUS, HTD.ERROR,"
      + " HTD.BUG_NUMBER, HTD.HYDRA_TEST_ID, HTD.HYDRA_TESTSUITE_DETAIL_ID,"
      + " HTD.HYDRA_RUN_ID, HTD.COMMENT, HT.CONF, HT.FULL_TEST_SPEC,"
      + " HT.HYDRA_TESTSUITE_ID, HS.NAME, "
      + " LOG_LOCATION, TAGS "
      + " FROM HYDRA_TEST_DETAIL HTD LEFT OUTER JOIN  HYDRA_TEST_DETAIL_EXT HTDE ON ( HTD.ID = HTDE.ID ) , "
      + " HYDRA_TEST HT, HYDRA_TESTSUITE HS"
      + " WHERE HTD.HYDRA_TEST_ID=HT.ID "
      + " AND HS.ID = HT.HYDRA_TESTSUITE_ID "
      + " AND HT.HYDRA_TESTSUITE_ID=?";

  JdbcTemplate jdbcTemplate;


  public HydraHistory getHydraHistoryForBatteryTest(final int id, final Map<Integer, HydraRun> hydraRunList) throws
      DataAccessException {

    Integer[] runIdArray = new Integer[hydraRunList.size()];
    int i = 0;
    for (Integer key : hydraRunList.keySet()) {
      runIdArray[i++] = key;
    }

    return jdbcTemplate.query(prepareQuery(SQL_HYDRA_HISTORY_BY_BATTERYTEST
            + " AND HTD.HYDRA_RUN_ID IN (", runIdArray.length,") ORDER BY HT.ID, HTD.HYDRA_RUN_ID"),
        populateParams(id, runIdArray),
        new ResultSetExtractor<HydraHistory>() {

          @Override
          public HydraHistory extractData(ResultSet rs) throws SQLException {
            HydraHistory history = new HydraHistory();
            history.setHydraRunList(hydraRunList);
            List<TestDetail> list = new ArrayList<TestDetail>();

            int rowCount = -1, colCount = -1;

            Map<Long, Integer> testToRowMapping = new HashMap<Long, Integer>();
            Map<Integer, Integer> hydraRunToColMapping = new HashMap<Integer, Integer>();
            String bugNumber;
            long hydraTestId, hydraTestSuiteDetailId;
            int hydraRunId;
            String elapsedTime, diskUsage, status, error, comment, conf, fullTestSpec, logLocation, tags;

            while (rs.next()) {
              rs.getLong(1);
              elapsedTime = rs.getString(2);
              diskUsage = rs.getString(3);
              status = rs.getString(4);
              error = rs.getString(5);
              bugNumber = rs.getString(6);
              hydraTestId = rs.getLong(7);
              hydraTestSuiteDetailId = rs.getLong(8);
              hydraRunId = rs.getInt(9);
              comment = rs.getString(10);
              conf = rs.getString(11);
              fullTestSpec = rs.getString(12);
              rs.getInt(13);
              history.setBattryTest(rs.getString(14));
              logLocation = rs.getString(15);
              tags = rs.getString(16);

              TestDetail td = new TestDetail(
                  hydraTestSuiteDetailId,
                  elapsedTime,
                  diskUsage,
                  status,
                  error,
                  bugNumber,
                  hydraTestId,
                  hydraTestSuiteDetailId,
                  hydraRunId,
                  comment,
                  tags,
                  logLocation);
              list.add(td);

              if (!testToRowMapping.containsKey(hydraTestId)) {
                rowCount++;
                testToRowMapping.put(hydraTestId, rowCount);
                history.getTestConfMapping().put(hydraTestId, conf);
                history.getTestFullConfMapping().put(hydraTestId, fullTestSpec);
              }

              if (!hydraRunToColMapping.containsKey(hydraRunId)) {
                colCount++;
                hydraRunToColMapping.put(hydraRunId, colCount);
              }
            }

            history.setTestDetails(new TestDetail[testToRowMapping.size()][hydraRunToColMapping.size()]);
            for (TestDetail td : list) {
              int row = testToRowMapping.get(td.getTestId());
              int col = hydraRunToColMapping.get(td.getRunId());
              history.addTestDetail(row, col, td);
            }
            return history;
          }
        });
  }

  private String prepareQuery(String str1, int runIdArraySize, String str2) {

    StringBuilder sb = new StringBuilder(str1);
    for (int i = 0; i < runIdArraySize; i++) {
      sb.append(" ?,");
    }
    sb.deleteCharAt(sb.length()-1);
    sb.append(str2);
    return sb.toString();
  }

  private PreparedStatementSetter populateParams(final int id, final Integer[] runIdArray) {
    PreparedStatementSetter setter = new PreparedStatementSetter() {
      int i = 1;
      @Override
      public void setValues(PreparedStatement ps) throws SQLException {
        ps.setObject(i++, id);
        for(Integer runId : runIdArray) {
          ps.setObject(i++, runId);
        }
      }
    };
    return setter;
  }
}
