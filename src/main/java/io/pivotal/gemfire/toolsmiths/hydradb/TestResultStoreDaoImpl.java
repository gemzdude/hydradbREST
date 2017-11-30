package io.pivotal.gemfire.toolsmiths.hydradb;

import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.ConnectionCallback;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.StringTokenizer;
import java.util.TreeSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.sql.DataSource;

import hydradb.util.UtilityHelper;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.StringTokenizer;
import java.util.TreeSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.sql.DataSource;

import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.ConnectionCallback;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;

import resultsUtil.RegressionSummary;
import swarm.hydra.Host;
import swarm.hydra.HydraRun;
import swarm.hydra.HydraRunSearch;
import swarm.hydra.TestDetail;
import swarm.hydra.TestDetailState;
import swarm.hydra.TestDetailStateAndIdComparator;
import swarm.hydra.TestInfo;
import swarm.hydra.TestResultStoreManager;
import swarm.hydra.TestSuiteDetail;
import swarm.hydra.TestSuiteInfo;
import swarm.hydra.TicketDetail;


//import swarm.Database;

public class TestResultStoreDaoImpl implements TestResultStoreDao {

  public final static Logger log = Logger.getLogger(TestResultStoreDaoImpl.class);

  private JdbcTemplate jdbcTemplate;

  public void setDataSource(DataSource dataSource) {
    this.jdbcTemplate = new JdbcTemplate(dataSource);
  }

  @Override
  public List<TestDetail> getLastNTestDetailsForConfWithDiffPathButNotTestId(TestInfo test, int max, String status, String branch) throws
      DataAccessException {
    /*
     * Lets get the path, not the test spec, then lets search for any that match
     * those testIds, but not this one!
     */
    String confPath = test.getConf();
    String conf = test.getConf();
    int lastIdx = confPath.lastIndexOf("/");
    if (lastIdx != -1) {
      conf = confPath.substring(lastIdx);
    }
    String sql = SELECT_HYDRA_TEST_DETAIL_SQL + " JOIN HYDRA_TEST HT ON(HT.CONF != ? AND HT.CONF LIKE ? AND HT.ID != ? AND HT.ID = HTD.HYDRA_TEST_ID)  "
        + (branch != null ? ", HYDRA_RUN HR WHERE HR.ID = HTD.HYDRA_RUN_ID AND HR.SVN_REPOSITORY LIKE ? " : "")
        + (status != null ? (branch == null ? " WHERE STATUS = ? " : " AND STATUS = ? ") : "")
        + "ORDER BY STATUS ASC,ID DESC LIMIT ?";
    List<Object> params = new ArrayList<Object>();
    params.add(confPath);
    params.add("%" + conf);
    params.add(test.getId());
    if (branch != null) {
      params.add("%" + branch);
    }
    if (status != null) {
      params.add(status);
    }
    params.add(max);
    return jdbcTemplate.query(sql, params.toArray(), getTestDetailResultExtractor());
  }

  @Override
  public void deleteTestDetail(long testDetailId) throws DataAccessException {
    /* TODO: Need to be in transaction */
    log.debug("Trying to delete records from hydra_test_detail_ext where Id = " + testDetailId);
    int deleted = jdbcTemplate.update(DELETE_HYDRA_TEST_EXT_SQL + " where id=" + testDetailId);
    log.debug("Deleted " + deleted + " records from hydra_test_detail_ext where Id = " + testDetailId);

    log.debug("Trying to delete records from hydra_test_detail where Id = " + testDetailId);
    deleted = jdbcTemplate.update(DELETE_HYDRA_TEST_SQL + " where id=" + testDetailId);
    log.debug("Deleted " + deleted + " records from hydra_test_detail where Id = " + testDetailId);
  }

  @Override
  public void deleteTestsuiteDetail(long suiteDetailId) throws DataAccessException {
    /* TODO: Need to be in transaction */

    deleteDataFromExtTable("hydra_testsuite_detail_id", suiteDetailId);
    log.debug("Trying to delete records from hydra_test_detail where hydra_testsuite_detail_id = " + suiteDetailId);
    int deleted = jdbcTemplate.update(DELETE_HYDRA_TEST_SQL + " where hydra_testsuite_detail_id=" + suiteDetailId);
    log.debug("Deleted " + deleted + " records from hydra_test_detail where hydra_testsuite_detail_id = " + suiteDetailId);
    log.debug("Trying to delete records from hydra_test_suite_detail where Id = " + suiteDetailId);
    deleted = jdbcTemplate.update(DELETE_HYDRA_TESTSUITE_DETAIL_SQL + " where id=" + suiteDetailId);
    log.debug("Deleted " + deleted + " records from hydra_test_suite_detail where Id = " + suiteDetailId);
  }

  @Override
  public void deleteHydraRun(int runId) throws DataAccessException {
    /*
     * Database.executeUpdate("DELETE FROM hydra_test_detail where hydra_run_id="
     * +runId); Database.executeUpdate(
     * "DELETE FROM hydra_testsuite_detail where hydra_run_id="+runId);
     * Database.executeUpdate("DELETE FROM hydra_run where id="+runId);
     */
    /* TODO: Need to be in transaction */
    deleteDataFromExtTable("hydra_run_id", runId);
    log.debug("Trying to delete hydra Run for runId : " + runId);
    jdbcTemplate.update(DELETE_HYDRA_TEST_SQL + " where hydra_run_id=" + runId);
    jdbcTemplate.update(DELETE_HYDRA_TESTSUITE_DETAIL_SQL+ " where hydra_run_id=" + runId);
    jdbcTemplate.update("DELETE FROM hydra_run where id=" + runId);
    log.debug("Hydra Run deleted for runId : " + runId);
  }

  private void deleteDataFromExtTable(String filterName, long filterValue) {
    final Set<Long> ids = new HashSet<Long>();
    jdbcTemplate.query("SELECT ID FROM HYDRA_TEST_DETAIL where " + filterName + " = " + filterValue, new ResultSetExtractor<Set<Long>>(){
      @Override
      public Set<Long> extractData(ResultSet rs) throws SQLException, DataAccessException {
        while(rs.next()) {
          ids.add(rs.getLong("ID"));
        }
        return ids;
      }
    });

    if(ids.size() > 0) {
      jdbcTemplate.update(DELETE_HYDRA_TEST_EXT_SQL + " where id IN (" + UtilityHelper.join(ids, ',') + ")");
    }
  }

  @Override
  public List<TestDetail> getLastNTestDetailsForPathButNotTestId(TestInfo test, int max, String status, String branch) throws DataAccessException {

    /*
     * Lets get the path, not the test spec, then lets search for any that match
     * those testIds, but not this one!
     */
    String confPath = test.getConf();
    String sql = SELECT_HYDRA_TEST_DETAIL_SQL + " JOIN HYDRA_TEST HT ON(HT.CONF=? AND HT.ID !=? AND HT.ID=HTD.HYDRA_TEST_ID)  "
        + (branch != null ? ", HYDRA_RUN HR WHERE HR.ID = HTD.HYDRA_RUN_ID AND HR.SVN_REPOSITORY LIKE ? " : "")
        + (status != null ? (branch == null ? " WHERE status = ? " : " AND status = ? ") : "")
        + "ORDER BY STATUS ASC,ID DESC LIMIT ?";
    List<Object> params = new ArrayList<Object>();
    params.add(confPath);
    params.add(test.getId());
    if(branch != null) {
      params.add("%" + branch);
    }
    if(status != null) {
      params.add(status);
    }
    params.add(max);
    return jdbcTemplate.query(sql, params.toArray(), getTestDetailResultExtractor());
  }

  @Override
  public List<TestDetail> getLastNTestDetailsForBug(String bug, int max) throws DataAccessException {
    log.debug("Trying to get " + max + " Test Details for Bug : " + bug);
    String sql = SELECT_HYDRA_TEST_DETAIL_SQL + " WHERE BUG_NUMBER=? ORDER BY ID DESC LIMIT ?";
    return jdbcTemplate.query(sql, new Object[] { bug, max }, getTestDetailResultExtractor());
  }

  @Override
  public List<TestDetail> getLastNTestDetailsForTestId(TestInfo test, int max, String status, String branch) throws DataAccessException {
    log.debug("Trying to get " + max + " Test Details for testId : " + test.getId() + ", status  : " + status + " and branch : " + branch);
    String sql = SELECT_HYDRA_TEST_DETAIL_SQL
        + (branch == null ? " WHERE"
        : ",HYDRA_RUN HR WHERE HR.ID = HTD.HYDRA_RUN_ID AND HR.SVN_REPOSITORY LIKE ? AND ")
        + "  HYDRA_TEST_ID = ? " + (status == null ? "" : "AND STATUS = ?")
        + " ORDER BY ID DESC LIMIT ?";
    List<Object> params = new ArrayList<Object>();
    if (branch != null) {
      params.add("%" + branch);
    }
    params.add(test.getId());
    if (status != null) {
      params.add(status);
    }
    params.add(max);
    return jdbcTemplate.query(sql, params.toArray(), getTestDetailResultExtractor());
  }

  @Override
  public TestDetail getTestDetail(long detailId) throws DataAccessException {
    log.debug("Trying to get Test Details for detalId : " + detailId);
    String sql = SELECT_HYDRA_TEST_DETAIL_SQL + " WHERE HTD.ID =? ";
    return jdbcTemplate.query(sql, new Object[] { detailId }, getTestDetailResultExtractor()).get(0);
  }

  @Override
  public List<TestDetail> getTestDetailsForSuiteDetail(long testsuiteDetailId) throws DataAccessException {
    log.debug("Trying to get Test Details for testSuiteDetailId : " + testsuiteDetailId);

    String sql = SELECT_HYDRA_TEST_DETAIL_SQL + " WHERE  HYDRA_TESTSUITE_DETAIL_ID = ? ORDER BY STATUS ASC,ID DESC";
    return jdbcTemplate.query(sql, new Object[] { testsuiteDetailId }, getTestDetailResultExtractor());
  }

  @Override
  public TestDetail getOrCreateTestDetail(int runId,
                                          long testSuiteDetailId,
                                          long testId,
                                          String elapsedTime,
                                          String diskStr,
                                          String status,
                                          String error,
                                          String bugNumber,
                                          String comment,
                                          String tags,
                                          String logLocation)
      throws DataAccessException {
    TestDetail testDetail = updateHydraTestDetailIfRecordExist(
        runId,
        testSuiteDetailId,
        testId,
        elapsedTime,
        diskStr,
        status,
        error,
        bugNumber,
        comment,
        tags,
        logLocation);
    if(testDetail != null) {
      return testDetail;
    }
    log.info("Did not find any existing hydra test details for hydraTestId = " + testId
        + ", hydraRunId : " + runId + " and hydraTestSuiteDetailId : " + testSuiteDetailId);
    jdbcTemplate
        .update(
            "INSERT INTO HYDRA_TEST_DETAIL(ID, ELAPSED_TIME, DISK_USAGE, STATUS, ERROR, BUG_NUMBER, " +
                "HYDRA_TEST_ID, HYDRA_TESTSUITE_DETAIL_ID, HYDRA_RUN_ID,COMMENT,TAGS) " +
                "VALUES(NEXTVAL('hydra_test_detail_id_seq'),?,?,?,?,?,?,?,?,?,?)",
            elapsedTime, diskStr, status, error, bugNumber, testId,
            testSuiteDetailId, runId, comment, tags);

    Map<String, Object>
        output = jdbcTemplate.queryForMap("SELECT ID, BUG_NUMBER,COMMENT, STATUS,TAGS FROM HYDRA_TEST_DETAIL " +
            "WHERE HYDRA_TEST_ID=? AND HYDRA_TESTSUITE_DETAIL_ID=? AND HYDRA_RUN_ID=?",
        new Object[] { testId, testSuiteDetailId, runId });

    if(!"P".equalsIgnoreCase(status)) {
      jdbcTemplate.update(
          "INSERT INTO HYDRA_TEST_DETAIL_EXT(ID, LOG_LOCATION) VALUES(?,?)",
          Long.parseLong(output.get("ID") + ""), logLocation);
    }

    return new TestDetail(
        Long.parseLong(output.get("ID") + ""),
        elapsedTime,
        diskStr,
        status,
        error,
        bugNumber,
        testId,
        testSuiteDetailId,
        runId,
        comment,
        tags,
        logLocation);
  }

  private TestDetail updateHydraTestDetailIfRecordExist(int runId,
                                                        long testSuiteDetailId,
                                                        long testId,
                                                        String elapsedTime,
                                                        String diskStr,
                                                        String status,
                                                        String error,
                                                        String bugNumber,
                                                        String comment,
                                                        String tags,
                                                        String logLocation) {
    Map<String, Object> output;
    try {
      output = jdbcTemplate.queryForMap("SELECT HTD.ID, BUG_NUMBER,COMMENT, STATUS,TAGS FROM HYDRA_TEST_DETAIL HTD   " +
              "WHERE HYDRA_TEST_ID=? AND HYDRA_TESTSUITE_DETAIL_ID=? AND HYDRA_RUN_ID=?",
          new Object[] { testId, testSuiteDetailId, runId });

      if (output != null && !output.isEmpty()) {
        String hdbBugNumber = output.get("bug_number").toString();
        String hdbTags =  output.get("tags") + "";
        String hdbComment =  output.get("comment") + "";
        String hdbStatus =  output.get("status") + "";

        if ((bugNumber != null) && (!bugNumber.equals(hdbBugNumber))
            || (tags != null && !tags.equals(hdbTags))
            || (comment != null && !comment.equals(hdbComment))
            || (status != null && !status.equals(hdbStatus))) {
          int updateCount = jdbcTemplate
              .update(
                  "UPDATE HYDRA_TEST_DETAIL SET BUG_NUMBER=?, COMMENT=? , STATUS=? , TAGS=? " +
                      "WHERE HYDRA_TEST_ID=? AND HYDRA_TESTSUITE_DETAIL_ID=? AND HYDRA_RUN_ID=?",
                  bugNumber, comment, status, tags, testId, testSuiteDetailId,
                  runId);

          log.info("bug number updated from " + hdbBugNumber + " to "
              + bugNumber + " update count " + updateCount + " id ="
              + testId + " testsuite_detail_id" + testSuiteDetailId + " runid "
              + runId);
        }
      }
      return new TestDetail(
          Long.parseLong(output.get("ID") + ""),
          elapsedTime,
          diskStr,
          status,
          error,
          bugNumber,
          testId,
          testSuiteDetailId,
          runId,
          comment,
          tags,
          logLocation);
    } catch (DataAccessException e) {

    }
    return null;
  }

  @Override
  public List<TestInfo> getTestInfosForSuite(long suiteId) throws DataAccessException {
    return jdbcTemplate.query(SELECT_HYDRA_TEST_SQL + " WHERE HYDRA_TESTSUITE_ID = ?", new Object[] { suiteId },
        new ResultSetExtractor<List<TestInfo>>() {
          List<TestInfo> testInfos = new ArrayList<TestInfo>();
          @Override
          public List<TestInfo> extractData(ResultSet rs) throws SQLException {
            while (rs.next()) {
              testInfos.add(getTestInfoFromResultSet(rs));
            }
            return testInfos;
          }
        });
  }

  @Override
  public TestInfo getTestInfo(long testId) throws DataAccessException {

    return jdbcTemplate.queryForObject(SELECT_HYDRA_TEST_SQL + " WHERE ID=?", new Object[] { testId }, new RowMapper<TestInfo>() {

      @Override
      public TestInfo mapRow(ResultSet rs, int arg1) throws SQLException {
        return getTestInfoFromResultSet(rs);
      }
    });
  }

  @Override
  public TestInfo getOrCreateTestInfo(final String conf, final String fullTestSpec, final long testSuiteId) throws DataAccessException {
    try {
      TestInfo testInfo = getTestInfo(conf, fullTestSpec, testSuiteId);
      return testInfo;
    } catch(EmptyResultDataAccessException e) {
      log.info("No Test Info found for conf : " + conf + ", fullTestSpec : " + fullTestSpec + "and testSuiteId : " + testSuiteId);
      jdbcTemplate.update("INSERT INTO HYDRA_TEST(ID, CONF, FULL_TEST_SPEC, HYDRA_TESTSUITE_ID) VALUES(NEXTVAL('hydra_test_id_seq'),?,?,?)",
          new Object[] { conf, fullTestSpec, testSuiteId });
      return getTestInfo(conf, fullTestSpec, testSuiteId);
    }
  }

  private TestInfo getTestInfo(final String conf, final String fullTestSpec, final long testSuiteId) {
    TestInfo testInfo = jdbcTemplate.queryForObject("SELECT ID FROM HYDRA_TEST WHERE FULL_TEST_SPEC=? AND HYDRA_TESTSUITE_ID=?",
        new Object[] { fullTestSpec, testSuiteId },
        new RowMapper<TestInfo>() {
          @Override
          public TestInfo mapRow(ResultSet rs, int arg1) throws SQLException {
            return new TestInfo(rs.getLong(1), conf, fullTestSpec, testSuiteId);
          }
        });
    return testInfo;
  }

  @Override
  public List<HydraRun> getHydraRuns(final HydraRunSearch hrs) throws DataAccessException {


    return jdbcTemplate.execute(new ConnectionCallback<List<HydraRun>>() {
      @Override
      public List<HydraRun> doInConnection(Connection conn) throws SQLException, DataAccessException {
        PreparedStatement ps = conn.prepareStatement(hrs.getQuery());
        ResultSet rs = ps.executeQuery();
        List<HydraRun> runs = new ArrayList<HydraRun>();
        while (rs.next()) {
          runs.add(getHydraRunFromResultSet(rs));
        }
        return runs;
      }


    });
  }

  /**
   * Look for the Hydra Run for Hydra Run Search.
   * If it did not find any record, it will send DataAccessException
   * It will return only single record.
   */
  @Override
  public HydraRun getHydraRun(final HydraRunSearch hrs) throws DataAccessException {
    return jdbcTemplate.execute(new ConnectionCallback<HydraRun>() {

      @Override
      public HydraRun doInConnection(Connection conn) throws SQLException, DataAccessException {
        PreparedStatement ps = conn.prepareStatement(hrs.getQuery());
        ResultSet rs = ps.executeQuery();
        if (rs.next()) {
          return getHydraRunFromResultSet(rs);
        }
        log.error("Did not find any hydra Run for Search :" + hrs);
        return null;
      }
    });
  }

  @Override
  public HydraRun getOrCreateHydraRun(String userName,
                                      String productVersion,
                                      String buildId,
                                      String svnRepository,
                                      String svnRevision,
                                      String javaVersion,
                                      String javaVendor,
                                      String javaHome,
                                      java.util.Date date,
                                      int regressionType,
                                      String comments,
                                      String buildLocation) throws DataAccessException {

    // I am purposefully not including java_home in this test since we run
    // several bts with the 64bit VM within the context of a single regression
    // (diskRecovery64, scale64, and scale64wan.bt

    // regression server integration. regression server passes runId
    // which can be used here to directly locate the run
    HydraRun hydraRun = null;
    HydraRunSearch hrs = new HydraRunSearch();
    hrs = buildHRS(userName, productVersion, buildId, svnRepository, svnRevision, javaVersion, javaVendor);

    StringBuilder sb = new StringBuilder("Trying to find Hydra Run For in case runId is not found or null...");
    //  sb.append("User Name : " + userName).append("\n");
    sb.append("Product Version : " + productVersion).append("\n");
    sb.append("Build Id  : " + buildId).append("\n");
    sb.append("SVN Repository : " + svnRepository).append("\n");
    sb.append("SVN Revision : " + svnRevision).append("\n");
    sb.append("Java Version : " + javaVersion).append("\n");
    sb.append("Java Vendor : " + javaVendor).append("\n");

    log.info(sb.toString());
    String runId = System.getProperty(RegressionSummary.HYDRADB_RUNID);

    if (runId != null && !"-1".equals(runId)) {
      int run = Integer.parseInt(runId);
      HydraRunSearch newHrs = new HydraRunSearch();
      newHrs.setRunId(run);
      hydraRun = findHydraRun(newHrs);
      if(hydraRun != null) {
        return hydraRun;
      }
    }
    hydraRun = findHydraRun(hrs);
    if(hydraRun != null) {
      return hydraRun;
    }

    // insert new record and query (based on newly generated run_id)
    hydraRun = createHydraRun(userName,
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
        buildLocation,
        hrs);
    if(hydraRun == null) {
      log.error("Error occurred while Creating Hydra Run for " + hrs);
      return null;
    }
    System.setProperty(RegressionSummary.HYDRADB_RUNID, "" + hydraRun.getId());
    return hydraRun;

  }

  private HydraRun findHydraRun(HydraRunSearch newHrs) {
    HydraRun hydraRun = null;
    try {
      log.debug("Trying to find Hydra Run for " + newHrs);
      hydraRun = getHydraRun(newHrs);
    } catch (DataAccessException e) {
      log.error("Error occurred while getting Hydra Run for " + newHrs);
    }
    return hydraRun;
  }

  private HydraRunSearch buildHRS(String userName, String productVersion, String buildId,
                                  String svnRepository, String svnRevision, String javaVersion,
                                  String javaVendor) {
    HydraRunSearch hrs = new HydraRunSearch();
    //  hrs.setUserName(userName);
    hrs.setProductVersion(productVersion);
    hrs.setBuildId(buildId);
    hrs.setSvnRepository(svnRepository);
    hrs.setSvnRevision(svnRevision);
    hrs.setJavaVersion(javaVersion);
    hrs.setJavaVendor(javaVendor);
    return hrs;
  }

  private HydraRun createHydraRun(String userName, String productVersion,
                                  String buildId, String svnRepository, String svnRevision,
                                  String javaVersion, String javaVendor, String javaHome,
                                  java.util.Date date, int regressionType, String comments, String buildLocation, HydraRunSearch hrs) {

    jdbcTemplate.update(
        "INSERT INTO HYDRA_RUN(ID, USER_NAME, PRODUCT_VERSION, BUILD_ID, SVN_REPOSITORY, SVN_REVISION, JAVA_VERSION, " +
            "JAVA_VENDOR, JAVA_HOME, DATE, REGRESSION_TYPE, COMMENTS, BUILD_LOCATION) VALUES (NEXTVAL('hydra_run_id_seq'), ?,?,?,?,?,?,?,?,?,?,?,?)",
        new Object[] { userName, productVersion, buildId, svnRepository,
            svnRevision, javaVersion, javaVendor, javaHome, new Timestamp(date.getTime()), regressionType, comments, buildLocation });
    HydraRun hydraRun = getHydraRun(hrs);
    return hydraRun;
  }

  @Override
  public void updateBugNumber(long detailId, String bugNumber) throws DataAccessException {
    int updateCount = jdbcTemplate.update("UPDATE HYDRA_TEST_DETAIL SET BUG_NUMBER=? WHERE ID=?", new Object[] { bugNumber, detailId });
    log.info("Update count for bugNumber : " + bugNumber + " is " + updateCount);
    boolean tags_table_present = Boolean.getBoolean("TAGS_TABLE_PRESENT");
    if ((bugNumber != null) && tags_table_present) {
      String tags = TestResultStoreManager.getInstance().getTagsForBug(bugNumber);
      jdbcTemplate.update(
          "UPDATE HYDRA_TEST_DETAIL SET TAGS=? WHERE ID=?",
          new Object[] { tags, detailId });
    }
  }

  @Override
  public void updateStatus(long detailId, TestDetailState state) throws DataAccessException {
    jdbcTemplate.update("UPDATE HYDRA_TEST_DETAIL SET STATUS=? WHERE ID=?", state.toString(), detailId);
  }

  @Override
  public void updateComment(long detailId, String comment) throws DataAccessException {
    jdbcTemplate.update("UPDATE HYDRA_TEST_DETAIL SET COMMENT=? WHERE ID=?", comment, detailId);
  }

  @Override
  public void updateCommentsInHydraRun(long runId, String oldComment, String comment) {
    if(comment == null) {
      return;
    }
    jdbcTemplate.update("UPDATE HYDRA_RUN SET COMMENTS=? WHERE ID=?", oldComment.length() > 0
        ? oldComment + "\n" + comment
        : comment, runId);
  }

  @Override
  public void updateBtComment(long testSuiteDetailId, String comment) throws DataAccessException {
    jdbcTemplate.update("UPDATE HYDRA_TESTSUITE_DETAIL SET COMMENT=? WHERE ID=?", comment, testSuiteDetailId);
  }

  @Override
  public Host getOrCreateHost(String name, String osType, String osInfo) throws DataAccessException {
    try {
      Host host = getHost(name);
      return host;
    } catch (EmptyResultDataAccessException e) {
      log.info("No Host found for name : " + name);
      jdbcTemplate.update("INSERT INTO HOST(ID, NAME, OS_TYPE, OS_INFO) VALUES(NEXTVAL('host_id_seq'), ?,?,?)", new Object[] { name, osType, osInfo });
      return getHost(name);
    }
  }

  @Override
  public Host getHost(int hostId) throws DataAccessException {
    return jdbcTemplate.queryForObject(SELECT_HOST + " WHERE ID = ?", new Object[] { hostId }, geHostRowMapper());
  }

  @Override
  public Host getHost(String hostName) {
    return jdbcTemplate.queryForObject(SELECT_HOST + " WHERE NAME=?", new Object[] { hostName }, geHostRowMapper());
  }

  private RowMapper<Host> geHostRowMapper() {
    return new RowMapper<Host>() {
      @Override
      public Host mapRow(ResultSet rs, int arg1) throws SQLException {
        return new Host(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4));
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

  @Override
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
  public Set<TestSuiteInfo> getAllTestSuiteInfos() throws DataAccessException {

    String sql = "SELECT HYDRA_TESTSUITE_ID, NAME, COUNT, MAX FROM HYDRA_TESTSUITE, "
        + "(SELECT HYDRA_TESTSUITE_ID, COUNT(*) FROM HYDRA_TEST GROUP BY HYDRA_TESTSUITE_ID ORDER BY COUNT) AS FOO, "
        + "(SELECT HYDRA_TESTSUITE_ID AS HTID, MAX(ELAPSED_TIME) FROM HYDRA_TESTSUITE_DETAIL GROUP BY HYDRA_TESTSUITE_ID) AS POO "
        + " WHERE HYDRA_TESTSUITE_ID = ID AND ID = HTID ORDER BY COUNT";
    return jdbcTemplate.query(sql,
        new ResultSetExtractor<Set<TestSuiteInfo>>() {
          @Override
          public Set<TestSuiteInfo> extractData(ResultSet rs) throws SQLException {
            Set<TestSuiteInfo> infos = new TreeSet<TestSuiteInfo>();
            while (rs.next()) {
              infos.add(new TestSuiteInfo(rs.getLong(1),
                  rs.getString(2),
                  rs.getInt(3),
                  "",
                  rs.getString(4),
                  ""));
            }
            return infos;
          }
        });
  }

  @Override
  public TestSuiteDetail getOrCreateTestSuiteDetail(int runId,
                                                    int hostId,
                                                    long testSuiteId,
                                                    Date date,
                                                    String elapsedTime,
                                                    String diskStr,
                                                    String localConf,
                                                    String btComment,
                                                    String artifactLogLocation) throws DataAccessException {
    try {
      TestSuiteDetail tsd = getTestSuiteDetail(date, elapsedTime, diskStr, localConf, testSuiteId, runId, hostId, btComment, artifactLogLocation);
      log.debug("Test Suite detail already exist. Need not to create new for testSuiteId : " + testSuiteId);
      return tsd;
    } catch (DataAccessException e) {
      log.info("Did not find any test suite details...");
    }
    log.debug("Test Suite detail does not exist. Creating new testSuiteId for  " + testSuiteId);
    jdbcTemplate.update(
        "INSERT INTO HYDRA_TESTSUITE_DETAIL(ID, DATE, ELAPSED_TIME, DISK_USAGE, LOCAL_CONF, HYDRA_TESTSUITE_ID, HYDRA_RUN_ID, HOST_ID, COMMENT, ARTIFACT_LOCATION) " +
            "VALUES(NEXTVAL('hydra_testsuite_detail_id_seq'), ?,?,?,?,?,?,?,?, ?)",
        new Object[] { new Timestamp(date.getTime()), elapsedTime, diskStr, localConf, testSuiteId, runId, hostId, btComment, artifactLogLocation });
    return getTestSuiteDetail(date, elapsedTime, diskStr, localConf, testSuiteId, runId, hostId, btComment, artifactLogLocation);
  }

  private TestSuiteDetail getTestSuiteDetail(java.util.Date date,
                                             String elapsedTime, String diskStr,
                                             String localConf,
                                             long testSuiteId, int runId,
                                             int hostId, String btComment,
                                             String artifactLogLocation) {
    TestSuiteDetail tsd = jdbcTemplate.queryForObject(
        "SELECT ID FROM HYDRA_TESTSUITE_DETAIL WHERE HYDRA_TESTSUITE_ID=? AND HYDRA_RUN_ID=? AND HOST_ID=? AND DATE=?",
        new Object[] { testSuiteId, runId, hostId, date },
        new RowMapper<TestSuiteDetail>() {
          @Override
          public TestSuiteDetail mapRow(ResultSet rs, int arg1) throws SQLException {
            return new TestSuiteDetail(rs.getLong(1),
                date,
                elapsedTime,
                diskStr,
                localConf,
                testSuiteId,
                runId,
                hostId,
                btComment,
                artifactLogLocation);
          }
        });
    return tsd;

  }

  @Override
  public List<TestSuiteDetail> getLastNTestSuiteDetails(long suiteId, int max) throws DataAccessException {
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
  public List<TestSuiteDetail> getTestSuiteDetailsForRun(int runId) throws DataAccessException {
    final Map<Long, TestSuiteDetail> testDetailToRunMap = new HashMap<Long, TestSuiteDetail>();
    final Map<Long, Set<TestDetail>> testDetailListToRunMap = new HashMap<Long, Set<TestDetail>>();
    final List<TestSuiteDetail> tsds = new ArrayList<TestSuiteDetail>();
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

  @Override
  public TestSuiteDetail getTestSuiteDetails(final long detailsId) throws DataAccessException {
    return jdbcTemplate.queryForObject(SELECT_HYDRA_TESTSUITE_DETAIL + " WHERE ID=?", new Object[] { detailsId },
        new RowMapper<TestSuiteDetail>() {
          @Override
          public TestSuiteDetail mapRow(ResultSet rs, int arg1) throws SQLException {
            return getTestSuiteDetailFromResultSet(rs);
          }
        });
  }

  @Override
  public String updateTestDetailsForBugWithTag(String bug, int regressionId, final String tag, String value) throws DataAccessException {

    Map<Long, String> idTags = jdbcTemplate.query(BUG_BY_BUG_NUMBER_SQL, new Object[] { bug, regressionId }, new ResultSetExtractor<Map<Long, String>>() {
      @Override
      public Map<Long, String> extractData(ResultSet rsi) throws SQLException {
        Map<Long, String> idTags = new HashMap<Long, String>();
        while (rsi.next()) {
          String tags = rsi.getString(3);
          long id = rsi.getLong(1);
          idTags.put(id, tags);
        }
        return idTags;
      }
    });

    int updatedCount = 0;
    for(Map.Entry<Long, String> idTag : idTags.entrySet()) {
      String tags = idTag.getValue();
      if(tags.contains(tag)) {
        tags = removeTagFromTags(tags, tag);
      }
      tags = tags + "|" + tag + "=" + value;
      jdbcTemplate.update("UPDATE HYDRA_TEST_DETAIL SET TAGS = '" + tags + "' WHERE ID = " + idTag.getKey());
      updatedCount++;
    }
    if (updatedCount > 0) {
      String message = "Updated " + updatedCount + " test records for bug number " + bug + " with tag '" + tag + "=" + value + "'";
      log.info(message);
      return message;
    } else {
      String message = "WARN : no test detail found with bug = " + bug;
      log.info(message);
      return message;
    }
//    if (ids.size() > 0) {
//      StringBuilder sb = new StringBuilder("UPDATE HYDRA_TEST_DETAIL SET TAGS = TAGS || '|").append(tag).append("=").append(value).append("' where id in (");
//      sb.append(populateInClause(ids));
//      sb.append(") AND TAGS IS");
//
//      String query1 = sb.toString() + " NOT NULL";
//      String query2 = sb.toString() + " NULL";
//
//      log.debug("Exeuting update query where trying to update bug with tag : " + sb.toString());
//      jdbcTemplate.update(query1);
//      jdbcTemplate.update(query2);
//      return ("Updated " + ids.size() + " test records with bug=" + bug + " tag=" + tag + "=" + value);
//    } else {
//      return "WARN : no test detail found with bug = " + bug + " and not tagged to tag=" + tag;
//    }

  }

  private String removeTagFromTags(String tags, String tag) {
    Map<String, String> map = new HashMap<String, String>();
    log.debug("Current Tags is " + tags + " and trying to remove tag : " + tag);
    StringTokenizer st = new StringTokenizer(tags, "|");
    while(st.hasMoreTokens()) {
      String token = st.nextToken();
      if(token.contains("=")) {
        String[] tokens = token.split("=");
        if(tokens.length > 1) {
          map.put(tokens[0], tokens[1]);
        }
      }
    }
    map.remove(tag);
    StringBuilder builder = new StringBuilder();
    for(Map.Entry<String, String> entry : map.entrySet()) {
      builder.append(entry.getKey()).append("=").append(entry.getValue());
      builder.append("|");
    }

    if(builder.length() > 1) {
      builder.deleteCharAt(builder.length() - 1);
    }
    return builder.toString();

  }

  private ResultSetExtractor<List<TestDetail>> getTestDetailResultExtractor() {
    return new ResultSetExtractor<List<TestDetail>>() {
      @Override
      public List<TestDetail> extractData(ResultSet rs) throws SQLException {
        List<TestDetail> details = new ArrayList<TestDetail>();
        while (rs.next()) {
          details.add(getTestDetailFromResultSet(rs));
        }
        return details;
      }
    };
  }

  @Override
  public Map<String, Long> getTestTimes(Map<String, Integer> idMap, int runId) {
    log.info("Qeurying test duration history for run=" + runId);
    StringBuilder sb = new StringBuilder(
        "SELECT HTSD.HYDRA_RUN_ID,HTSD.HYDRA_TESTSUITE_ID, HTSD.ELAPSED_TIME, HTS.NAME "
            + "FROM HYDRA_TESTSUITE_DETAIL HTSD, HYDRA_TESTSUITE HTS WHERE HTSD.HYDRA_TESTSUITE_ID IN (");
    Collection<Integer> ids = idMap.values();
    for (Integer id : ids) {
      sb.append(id);
      sb.append(',');
    }
    sb.deleteCharAt(sb.length() - 1);
    sb.append(") AND HTSD.HYDRA_RUN_ID=").append(runId).append(" AND HTS.ID = HTSD.HYDRA_TESTSUITE_ID");
    return jdbcTemplate.query(sb.toString(),
        new ResultSetExtractor<Map<String, Long>>() {
          @Override
          public Map<String, Long> extractData(ResultSet rs) throws SQLException {
            Map<String, Long> testTimeMap = new HashMap<String, Long>();
            while (rs.next()) {
              String time = rs.getString(3);
              long timel = UtilityHelper.parseTime(time);
              String testName = rs.getString(4);
              testTimeMap.put(testName, timel);
              log.debug("TestTimes For bt <" + testName + "> : " + timel);
            }
            return testTimeMap;

          }

        });
  }

  @Override
  public Object[] getLatestRunIds() {

    return jdbcTemplate.query("SELECT * FROM HYDRA_RUN ORDER BY DATE DESC LIMIT 5",
        new ResultSetExtractor<Object[]>() {
          @Override
          public Object[] extractData(ResultSet rs) throws SQLException {
            List<String> stringList = new ArrayList<String>();
            List<Long> idList = new ArrayList<Long>();
            while (rs.next()) {
              idList.add(rs.getLong(1));
              String str = rs.getString(2) + " " + rs.getString(5) + " "
                  + rs.getString(6);
              stringList.add(str);
            }
            return new Object[] { stringList, idList };
          }
        });
  }


  private TestInfo getTestInfoFromResultSet(ResultSet rs) throws SQLException {
    return new TestInfo(rs.getLong(1),
        rs.getString(2),
        rs.getString(3),
        rs.getLong(4));
  }

  private HydraRun getHydraRunFromResultSet(ResultSet rs) throws SQLException {
    return new HydraRun(rs.getInt(1),
        rs.getString(2),
        rs.getString(3),
        rs.getString(4),
        rs.getString(5),
        rs.getString(6),
        rs.getString(7),
        rs.getString(8),
        rs.getString(9),
        rs.getTimestamp(10),
        rs.getInt(11),
        rs.getString(12),
        rs.getString(13));
  }

  private TestSuiteDetail getTestSuiteDetailFromResultSet(ResultSet rs) throws SQLException {
    TestSuiteDetail td = null;

    try {
      td = new TestSuiteDetail(rs.getLong(1),
          rs.getTimestamp(2),
          rs.getString(3),
          rs.getString(4),
          rs.getString(5),
          rs.getLong(6),
          rs.getInt(7),
          rs.getInt(8),
          rs.getString(9),
          rs.getString(10));
    }
    catch(Exception e) {
      log.error("********************" + rs.getObject(2), e);
    }
    return td;
  }

  private TestDetail getTestDetailFromResultSet(ResultSet rs) throws SQLException {
    TestDetail td = new TestDetail(rs.getLong(1),
        rs.getString(2),
        rs.getString(3),
        rs.getString(4),
        rs.getString(5),
        rs.getString(6),
        rs.getLong(7),
        rs.getLong(8),
        rs.getInt(9),
        rs.getString(10),
        rs.getString(12),
        rs.getString(11)
    );
    return td;
  }



  private static boolean printed = false;

  public Map<String, Integer> getTestFailures(final Map<String, Integer> idMap) {

    final StringBuilder query = new StringBuilder(
        "SELECT COUNT(HTD.STATUS), HR.ID, HTSD.HYDRA_TESTSUITE_ID FROM HYDRA_TEST_DETAIL HTD , HYDRA_TESTSUITE_DETAIL HTSD , HYDRA_RUN HR");
    query.append(" WHERE HR.ID=HTD.HYDRA_RUN_ID");
    query.append(" AND HTD.STATUS IN ('F','H')");
    query.append(" AND HTD.HYDRA_TESTSUITE_DETAIL_ID=HTSD.ID");
    query.append(" AND HTSD.HYDRA_TESTSUITE_ID IN( ");
    for (Integer id : idMap.values()) {
      query.append(id).append(",");
    }
    query.deleteCharAt(query.length()-1);
    query.append(") AND HTSD.HYDRA_RUN_ID=HR.ID");
    query.append(" GROUP BY HR.ID, HTSD.HYDRA_TESTSUITE_ID");

    if(log.isDebugEnabled()) {
      log.debug("Failure Query : " + query);
    }
    Map<String, Integer> out = new HashMap<>();
    try {
      out = jdbcTemplate.execute(new ConnectionCallback<Map<String, Integer>>() {
        @Override
        public Map<String, Integer> doInConnection(Connection connection) throws SQLException, DataAccessException {
          Map<Integer, Map<String, Integer>> valueMap = new HashMap<Integer, Map<String, Integer>>();
          Map<String, Integer> failureMap = new HashMap<>();
          PreparedStatement ps = null;
          ResultSet rs = null;

          try {
            ps = connection.prepareStatement(query.toString());
            rs = ps.executeQuery();
            while (rs.next()) {
              Map<String, Integer> value = new HashMap<String, Integer>();
              value.put("RunId", rs.getInt(2));
              value.put("Count", rs.getInt(1));
              Integer hsId = rs.getInt(3);
              Map<String, Integer> hsIdMap = valueMap.get(hsId);
              if(hsIdMap == null) {
                valueMap.put(hsId, value);
                hsIdMap = valueMap.get(hsId);
              }
              Integer runId = hsIdMap.get("RunId");
              if(runId.compareTo(value.get("RunId")) < 0) {
                valueMap.put(rs.getInt(3), value);
              }
            }

            for (Map.Entry<Integer, Map<String, Integer>> entry : valueMap.entrySet()) {
              String name = getKeyForValue(idMap, entry.getKey());
              if (name != null) {
                Integer count = entry.getValue().get("Count");
                failureMap.put(name, count);
                log.debug("TestFailures For bt : <" + name + "> : F:" + count);
              } else {
                log.debug("TestFailures not found For id : <" + entry.getKey() + ">");
              }
            }
            return failureMap;
          } catch (SQLException e) {
            throw e;
          } finally {
            closeRequired(ps, rs);
          }
        }
      });
    } catch (Exception e) {
      log.error("Error : ", e);
    }

    if(log.isDebugEnabled()) {
      log.debug("Found failure map : " + out);
    }
    return out;
  }

  private static String getKeyForValue(Map<String, Integer> idMap, Integer key) {
    for(Map.Entry<String, Integer> entry : idMap.entrySet()) {
      if(entry.getValue().intValue() == key.intValue()) {
        return entry.getKey();
      }
    }
    return null;
  }

  public  Map<String, Integer> getTestIds(final String[] tests)  {
    final StringBuilder sb = new StringBuilder(
        "select * from  hydra_testsuite where name in ( ");
    for (int i = 0; i < tests.length; i++) {
      String s = tests[i];
      sb.append("'").append(s).append("'");
      if (i < tests.length - 1)
        sb.append(",");
    }
    sb.append(")");
    log.info("getTestsuitesFromNames Query : <" + sb.toString() + ">");
    final Map<String, Integer> idMap = new HashMap<String, Integer>();
    try {
      jdbcTemplate.execute(new ConnectionCallback<String>() {
        @Override
        public String doInConnection(Connection connection) throws SQLException, DataAccessException {
          PreparedStatement ps = null;
          ResultSet rs = null;

          try {
            ps = connection.prepareStatement(sb.toString());
            rs = ps.executeQuery();

            while (rs.next()) {
              String name = rs.getString(2);
              int id = rs.getInt(1);
              idMap.put(name, id);
            }
            int missing = 0;
            for (String s : tests) {
              if (!idMap.containsKey(s)) {
                idMap.put(s, -1);
                missing++;
              }
            }
            log.info("Missing Tests : " + missing);
          } catch (SQLException e) {
            throw e;
          } finally {
            closeRequired(ps, rs);
          }
          return "";
        }
      });
    } catch (Exception e) {
      log.error("Error occurred while trying to get test Ids from database",
          e);
    }
    return idMap;
  }

  private static void closeRequired(PreparedStatement ps, ResultSet rs) {
    if (rs != null) {
      try {
        rs.close();
      } catch (SQLException e) {
      }
    }
    if (ps != null) {
      try {
        ps.close();
      } catch (SQLException e) {
      }
    }
  }
  // filter runId any created
  public int getLastRunIdForBranch(final String svnRepo, int hydraDBRunId)
      throws SQLException {
    final String query = "SELECT * FROM HYDRA_RUN WHERE SVN_REPOSITORY=? AND ID!="
        + hydraDBRunId + " ORDER BY DATE DESC LIMIT 1";

    final List<Integer> result = new ArrayList<Integer>();
    try {
      jdbcTemplate.execute(new ConnectionCallback<String>() {
        @Override
        public String doInConnection(Connection connection) throws SQLException, DataAccessException {
          PreparedStatement ps = null;
          ResultSet rs = null;

          try {
            ps = connection.prepareStatement(query);
            rs = ps.executeQuery();
            ps.setString(1, svnRepo);
            if (rs.next()) {
              int id = rs.getInt(1);
              rs.close();
              result.add(id);
            } else {
              ps = connection.prepareStatement(query);
              rs = ps.executeQuery();
              ps.setString(1, "gemfire/trunk");
              rs.next();
              int id = rs.getInt(1);
              result.add(id);
            }
          } catch (SQLException e) {
            throw e;
          } finally {
            closeRequired(ps, rs);
          }
          return "";
        }
      });
    } catch (Exception e) {
      log.error("Error occurred while trying to get last run if for branch from database",
          e);
    }
    if (result.size() > 0) {
      return result.get(0);
    }
    return 0;
  }

  public Object[] getLatestRunIds(String user, String password, String url) {

    return jdbcTemplate.execute(new ConnectionCallback<Object[]>() {
      @Override
      public Object[] doInConnection(Connection con) throws SQLException, DataAccessException {
        String sql = "select * from hydra_run order by date desc limit 20";
        Statement stmt = con.createStatement();
        ResultSet rs = stmt.executeQuery(sql);
        List<String> stringList = new ArrayList<String>();
        List<Integer> idList = new ArrayList<Integer>();
        while (rs.next()) {
          idList.add(rs.getInt(1));
          String str = rs.getString(2) + " " + rs.getString(5) + " " + rs.getString(6);
          stringList.add(str);
        }
        rs.close();
        stmt.close();
        con.close();
        return new Object[] { stringList, idList };
      }
    });

  }

  public Map<String, Long> getTestTimes(Map<String, Integer> idMap) {
    final Map<String, Long> testTimeMap = new HashMap<String, Long>();
    final StringBuilder sb = new StringBuilder(
        "SELECT HTSD.HYDRA_RUN_ID,HTSD.HYDRA_TESTSUITE_ID, HTSD.ELAPSED_TIME, HTS.NAME "
            + "FROM HYDRA_TESTSUITE_DETAIL HTSD, HYDRA_TESTSUITE HTS WHERE HTSD.HYDRA_TESTSUITE_ID IN (");
    Collection<Integer> ids = idMap.values();
    for (Integer id : ids) {
      sb.append(id).append(',');
    }
    sb.deleteCharAt(sb.length()-1);
    sb.append(") AND HTSD.HYDRA_RUN_ID=").append("(SELECT MAX(HYDRA_RUN_ID) FROM HYDRA_TESTSUITE_DETAIL WHERE hydra_testsuite_id = HTS.ID)").append(" AND HTS.ID = HTSD.HYDRA_TESTSUITE_ID");
    log.debug("Query to execute : " +sb.toString());
    try {
      jdbcTemplate.execute(new ConnectionCallback<String>() {
        @Override
        public String doInConnection(Connection connection) throws SQLException, DataAccessException {

          PreparedStatement ps = connection.prepareStatement(sb.toString());
          ResultSet rs = ps.executeQuery();
          while (rs.next()) {
            String time = rs.getString(3);
            long timel = parseTime(time);
            String testName = rs.getString(4);
            testTimeMap.put(testName, timel);
            log.debug("TestTimes For bt <" + testName + "> : " + timel);
          }
          return "";
        }
      });
    } catch(Exception e) {
      log.error("Error occurre while getting test times for bt for idMap : " + idMap, e);
    }
    return testTimeMap;
  }

  private static long parseTime(String time) {
    String split[] = time.split(":");
    String shour = split[0];
    String sminute = split[1];
    int hour = Integer.parseInt(shour);
    int minute = Integer.parseInt(sminute);
    return minute + hour*60;
  }

  @Override
  public String getTestTimes(final String test) {

    final String query = "SELECT HYDRA_TESTSUITE_ID AS HTID, MAX(ELAPSED_TIME) FROM HYDRA_TESTSUITE_DETAIL HTD, HYDRA_TESTSUITE HT " +
        "WHERE HTD.hydra_testsuite_id=HT.ID AND HT.NAME = ? GROUP BY HTID";
    final List<String> result = new ArrayList<String>();
    try {
      jdbcTemplate.execute(new ConnectionCallback<String>() {
        @Override
        public String doInConnection(Connection connection) throws SQLException, DataAccessException {
          PreparedStatement ps = null;
          ResultSet rs = null;

          try {
            ps = connection.prepareStatement(query);
            ps.setString(1, test);
            rs = ps.executeQuery();
            if (rs.next()) {
              String time = rs.getString(2);
              result.add(time);
            }
          } catch (SQLException e) {
            throw e;
          } finally {
            closeRequired(ps, rs);
          }
          return "";
        }
      });
    } catch (Exception e) {
      if(!printed ) {
        log.error("Error occurred while trying to get test time from database for test : " + test,  e);
      }
      printed = true;
      return "NA";
    }
    if(result.isEmpty()) {
      return "NA";
    }
    return result.get(0);
  }

  private static Pattern pattern = Pattern.compile("(\\d{2}):(\\d{2}):(\\d{2}).(\\d{3})");

  public static long dateParseRegExp(String period) {
    Matcher matcher = pattern.matcher(period);
    if (matcher.matches()) {
      return Long.parseLong(matcher.group(1)) * 3600000L
          + Long.parseLong(matcher.group(2)) * 60000
          + Long.parseLong(matcher.group(3)) * 1000
          + Long.parseLong(matcher.group(4));
    } else {
      throw new IllegalArgumentException("Invalid format " + period);
    }
  }

  public int getTestFailure(final String test) {
    final List<Integer> count = new ArrayList<Integer>();
    final StringBuilder query = new StringBuilder("SELECT FAILCOUNT+HANGCOUNT AS COUNT, HYDRA_RUN_ID FROM HYDRA_TESTSUITE_DETAIL HTSD,  " +
        "HYDRA_TESTSUITE HT WHERE HT.ID = HTSD.HYDRA_TESTSUITE_ID " +
        "AND HT.NAME = ? ORDER BY HYDRA_RUN_ID DESC LIMIT 2");

    if(log.isDebugEnabled()) {
      log.debug("Failure Query : " + query);
    }

    try {
      jdbcTemplate.execute(new ConnectionCallback<String>() {
        @Override
        public String doInConnection(Connection connection) throws SQLException, DataAccessException {
          PreparedStatement ps = null;
          ResultSet rs = null;

          try {
            ps = connection.prepareStatement(query.toString());
            ps.setString(1, test);
            rs = ps.executeQuery();
            while (rs.next()) {
              count.add(rs.getInt(1));
            }
          } catch (SQLException e) {
            log.error("Error occurred while trying to get test failure from database for test : " + test, e);
          }
          return "";
        }
      });
    } catch (Exception e) {
      log.error("Error occurred while trying to get test failure from database for test : " + test, e);
      count.add(0);
    }

    if(log.isDebugEnabled()) {
      log.debug("Found count : " + count);
    }
    return count.get(0);


  }

  public Map<Long, String> getLastRunPlatform(final String test, final long runId) {
    final String sql = "SELECT HYDRA_RUN_ID, OS_TYPE FROM HYDRA_TESTSUITE_DETAIL HTSD, HYDRA_TESTSUITE HT, HOST HOST " +
        "WHERE HT.ID = HTSD.HYDRA_TESTSUITE_ID " +
        "AND HOST.ID = HTSD.HOST_ID " +
        "AND HYDRA_RUN_ID >= ? AND HT.NAME = ?";
    final Map<Long, String> platformMap = new HashMap<Long, String>();
    try {
      jdbcTemplate.execute(new ConnectionCallback<String>() {
        @Override
        public String doInConnection(Connection connection) throws SQLException, DataAccessException {

          PreparedStatement ps = connection.prepareStatement(sql);
          ps.setLong(1, runId);
          ps.setString(2, test);
          ResultSet rs = ps.executeQuery();
          while (rs.next()) {
            long runId = rs.getLong(1);
            String platform = rs.getString(2);
            platformMap.put(runId, platform.toLowerCase());
          }

          return "";
        }
      });
    } catch(Exception e) {
      log.error("Error occurre while getting platform mapping with runId : " + platformMap, e);
    }
    return platformMap;
  }


}
