package io.pivotal.gemfire.toolsmiths.hydradb.data.hydra.repo;

import io.pivotal.gemfire.toolsmiths.hydradb.data.hydra.HydraRun;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Repository
public class HydraRunRepoImpl implements HydraRunRepoCustom {

  private static Logger log = Logger.getLogger(HydraRunRepoImpl.class);

  public static final String SQL_HYDRA_RUN_SET = "SELECT ID, USER_NAME, PRODUCT_VERSION, BUILD_ID, "
      + "SVN_REPOSITORY, SVN_REVISION, JAVA_VERSION, JAVA_VENDOR,JAVA_HOME,DATE, REGRESSION_TYPE , COMMENTS, BUILD_LOCATION "
      + "FROM HYDRA_RUN WHERE ID IN (";

  public static final String SQL_HYDRA_RUNS_FOR_BATTERYTEST = "SELECT DISTINCT HTD.HYDRA_RUN_ID FROM HYDRA_TEST_DETAIL HTD , HYDRA_TEST HT "
      + "WHERE HTD.HYDRA_TEST_ID=HT.ID AND HT.HYDRA_TESTSUITE_ID=? "
      + "ORDER BY HTD.HYDRA_RUN_ID DESC LIMIT ?";

  @Autowired
  JdbcTemplate HydraJdbcTemplate;

  @Override
  public Map<Integer, HydraRun> populateHydraRuns(int id, int numRuns,
                                           String gemfireVersion, String jdk, String jdkVendor, int svnRevision,
                                           String branch, String buildUser) {
    List<Integer> list = getHydraRunsForBatteryTest(id, numRuns);
    Map<Integer, HydraRun> hydraRunList = getHydraRunSet(list,
        gemfireVersion, jdk, jdkVendor, svnRevision, branch, buildUser);
    return hydraRunList;
  }

  @Override
  public List<Integer> getHydraRunsForBatteryTest(int id, int numRuns) throws DataAccessException {
    return HydraJdbcTemplate.query(SQL_HYDRA_RUNS_FOR_BATTERYTEST, new Object[] { id, numRuns }, new ResultSetExtractor<List<Integer>>() {
      @Override
      public List<Integer> extractData(ResultSet rs) throws SQLException {
        List<Integer> list = new ArrayList<Integer>();
        while (rs.next()) {
          list.add(rs.getInt(1));
        }
        return list;
      }
    });
  }

  @Override
  public Map<Integer, HydraRun> getHydraRunSet(
      List<Integer> list,
      String gemfireVersion,
      String jdk,
      String jdkVendor,
      int svnRevision,
      String branch,
      String buildUser)
  {
    return HydraJdbcTemplate.query(
        prepareQueryForHydraRunSet(list,
            gemfireVersion,
            jdk,
            jdkVendor,
            svnRevision,
            branch,
            buildUser).toString(),
        prepareQueryParams(
            gemfireVersion,
            jdk,
            jdkVendor,
            svnRevision,
            branch,
            buildUser ),
        new ResultSetExtractor<Map<Integer, HydraRun>>() {

          @Override
          public Map<Integer, HydraRun> extractData(ResultSet runSet) throws SQLException {
            Map<Integer, HydraRun> hydraRunList = new HashMap<Integer, HydraRun>();
            while (runSet.next()) {
              int runId = runSet.getInt(1);
              HydraRun hRun = new HydraRun();
              hRun.setId(runId);
              hRun.setUserName(runSet.getString(2));
              hRun.setProductVersion(runSet.getString(3));
              hRun.setBuildId(runSet.getString(4));
              hRun.setSvnRepository(runSet.getString(5));
              hRun.setSvnRevision(runSet.getString(6));
              hRun.setJavaVersion(runSet.getString(7));
              hRun.setJavaVendor(runSet.getString(8));
              hRun.setJavaHome(runSet.getString(9));
              hRun.setDate(runSet.getTimestamp(10));
              hRun.setFullRegression(runSet.getBoolean(11));
              hRun.setRegressionType(runSet.getInt(12));
              hRun.setComments(runSet.getString(13));
              hRun.setBuildLocation(runSet.getString(14));
              hydraRunList.put(runId, hRun);
            }
            return hydraRunList;
          }
        });
  }

  private PreparedStatementSetter prepareQueryParams(final String gemfireVersion,
                                                     final String jdk, final String jdkVendor, final int svnRevision, final String branch,
                                                     final String buildUser) {

    PreparedStatementSetter setter = new PreparedStatementSetter() {
      int i = 1;
      @Override
      public void setValues(PreparedStatement ps) throws SQLException {
        if(gemfireVersion != null) {
          ps.setString(i++, gemfireVersion);
        }
        if (jdk != null) {
          ps.setString(i++, jdk);
        }
        if (jdkVendor != null) {
          ps.setObject(i++, jdkVendor);
        }
        if (svnRevision > 0) {
          ps.setObject(i++, svnRevision);
        }
        if (branch != null) {
          ps.setString(i++, branch);
        }
        if (buildUser != null) {
          ps.setString(i++, buildUser);
        }

      }
    };
    return setter;
  }

  private StringBuilder prepareQueryForHydraRunSet(List<Integer> list,
                                                   String gemfireVersion, String jdk, String jdkVendor, int svnRevision,
                                                   String branch, String buildUser) {
    StringBuilder sb = new StringBuilder();
    sb.append(SQL_HYDRA_RUN_SET);
    for (int i = 1; i <= list.size(); i++) {
      Integer runId = list.get(i - 1);
      sb.append(runId);
      if (i < list.size())
        sb.append(",");
    }
    sb.append(")");
    if (gemfireVersion != null) {
      sb.append(" AND ").append("PRODUCT_VERSION = ?");
    }
    if (jdk != null) {
      sb.append(" AND ").append("JAVA_VERSION = ?");
    }
    if (jdkVendor != null) {
      sb.append(" AND ").append("JAVA_VENDOR = ?");
    }
    if (svnRevision > 0) {
      sb.append(" AND ").append("SVN_REVISION = ?");
    }
    if (branch != null) {
      sb.append(" AND ").append("SVN_REPOSITORY = ?");
    }
    if (buildUser != null) {
      sb.append(" AND ").append("USER_NAME = ?");
    }

    log.debug("Hydra Run Query " + sb.toString());
    return sb;
  }
}
