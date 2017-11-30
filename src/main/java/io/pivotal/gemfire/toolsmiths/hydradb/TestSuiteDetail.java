package io.pivotal.gemfire.toolsmiths.hydradb;

import io.pivotal.gemfire.toolsmiths.hydradb.data.hydra.HydraRun;
import org.apache.log4j.Logger;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

public class TestSuiteDetail implements Comparable<TestSuiteDetail> {

  public final static Logger log = Logger.getLogger(TestSuiteDetail.class);
  private final long id;
  private final Date date;
  private final String elapsedTime;
  private final String diskUsage;
  private final String localConf;
  private final long testSuiteId;
  private final int runId;
  private HydraRun run;
  private final int hostId;
  private Set<TestDetail> testDetails = null;
  private String btName = null;
  private String product = "Gemfire";
  private String artifactLogLocation = "";

  private final String btComment;

  public boolean equals(Object o) {
    if (!(o instanceof TestSuiteDetail)) {
      return false;
    }
    if (((TestSuiteDetail) o).getId() == getId()) {
      return true;
    } else {
      return false;
    }
  }

  public String getTimeString() {
    SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yy HH:mm:ss");
    return sdf.format(getDate());
  }

  public int compareTo(TestSuiteDetail o1) {
    return getDate().compareTo(o1.getDate());
  }

  public TestSuiteDetail(long id) {
    this(id, null, null, null, null, 0, 0, 0, null, "");
  }

  public TestSuiteDetail(long id, Date date, String elapsedTime,
                         String diskUsage, String localConf, long testSuiteId, int runId, int hostId, String btComment, String artifactLogLocation) {
    this.id = id;
    this.date = date;
    this.elapsedTime = elapsedTime;
    this.diskUsage = diskUsage;
    this.localConf = localConf;
    this.testSuiteId = testSuiteId;
    this.runId = runId;
    this.hostId = hostId;
    this.artifactLogLocation = artifactLogLocation;
    try {
      TestSuiteInfo testSuiteInfo = TestResultStoreManager.getInstance().getTestSuiteInfo(getTestSuiteId());
      this.btName = testSuiteInfo.getName();
      //TODO: Viren, its a dirty way to figure out product name, actually
      // it should be in database and should populate when regression server
      // pushing entry to hydra db database. Regression server has that details
      // abour product.
      this.product = testSuiteInfo.getName().startsWith("sql") ? "SqlFire" : product;

    } catch (TestResultStoreException e) {
      log.error("Error occurred while getting btName and Product. This field will be null or default", e);
    }

    this.btComment = btComment;
  }

  /** accessor methods */
  public long getId() {
    return id;
  }

  public String getBtComment() {
    return btComment;
  }

  public Date getDate() {
    return date;
  }

  public String getElapsedTime() {
    return elapsedTime;
  }

  public String getDiskUsage() {
    return diskUsage;
  }

  public int getPassCount() {
    int pass = 0;
    for (TestDetail td : getTestDetails()) {
      if (td.isPass()) {
        pass++;
      }
    }
    return pass;
  }

  public int getFailCount() {
    int fail = 0;
    for (TestDetail td : getTestDetails()) {
      if (td.isFail()) {
        fail++;
      }
    }
    return fail;
  }

  public int getHangCount() {
    int hang = 0;
    for (TestDetail td : getTestDetails()) {
      if (td.isHang()) {
        hang++;
      }
    }
    return hang;
  }

  public int getOtherCount() {
    int other = 0;
    for (TestDetail td : getTestDetails()) {
      if (!td.isFail() && !td.isPass() && !td.isHang()) {
        other++;
      }
    }
    return other;
  }

  public String getLocalConf() {
    return localConf;
  }

  public String getLocalConfHtml() {
    return localConf.replaceAll("\\n", "<br/>");
  }

  public long getTestSuiteId() {
    return testSuiteId;
  }

  public int getRunId() {
    return runId;
  }

  public int getHostId() {
    return hostId;
  }

  public String getArtifactLogLocation() {
    return artifactLogLocation;
  }

  public TestSuiteInfo getTestSuiteInfo() {
    try {
      return TestResultStoreManager.getInstance().getTestSuiteInfo(getTestSuiteId());
    } catch (TestResultStoreException e) {
      log.error("Error occurred while trying to get test suite info for " + getTestSuiteId());
    }
    return null;
  }

  public HydraRun getRun() {
    if (run == null) {
      try {
        run = TestResultStoreManager.getInstance().getHydraRun(runId);
      } catch (TestResultStoreException se) {
        log.error("Error occurred while trying to get HydraRun for " + runId, se);
      }
    }
    return run;
  }

  public Host getHost() {
    return TestResultStoreManager.getInstance().getHost(getHostId());
  }

  public Set<TestDetail> getTestDetails() {
    if (testDetails == null) {
      log.debug("Trying to get Test Details for id : " + getId());
      List<TestDetail> testDetailsAsList = TestResultStoreManager.getInstance().getTestDetailsForSuiteDetail(getId());
      testDetails = new TreeSet<TestDetail>(new TestDetailStateAndIdComparator());
      testDetails.addAll(testDetailsAsList);
      log.debug("Got the Test Details for id : " + getId() + ", testDetails Size : " + testDetails.size());
    }
    return testDetails;
  }

  public void setTestDetails(Set<TestDetail> testDetails) {
    this.testDetails = testDetails;
  }

  public List<BugCountInfo> getBugCountInfos() {


    Set<TestDetail> tds = getTestDetails();
    Map<String, BugCountInfo> bugs = new HashMap<String, BugCountInfo>();
    for (TestDetail td : tds) {
      String bugNumber = null;
      if (td.getBugNumber() != null && !td.getBugNumber().equals("")) {
        bugNumber = td.getBugNumber();
      } else if (td.getStateEnum().isNeedsBug()) {
        bugNumber = TicketDetail.NOT_REPORTED;
      }
      if (bugNumber != null) {
        BugCountInfo bci = bugs.get(bugNumber);
        if (bci == null) {
          bci = new BugCountInfo(bugNumber, 1);
        } else {
          bci = new BugCountInfo(bugNumber, bci.getCount() + 1);
        }
        bugs.put(bugNumber, bci);
      }
    }
    List<BugCountInfo> bcis = new ArrayList<BugCountInfo>();
    bcis = new ArrayList<BugCountInfo>(bugs.values());
    Collections.sort(bcis);
    return bcis;
  }

  public String getBtName() {
    return btName;
  }

  @Override
  public String toString() {
    return new StringBuilder("TestsuiteDetail@" + System.identityHashCode(this)
        + ":").append("id:" + id).append(" date:" + date)
        .append(" elapsedTime:" + elapsedTime)
        .append(" diskUsage:" + diskUsage)
        //.append(" local.conf:" + localConf)
        .append(" testSuiteId:" + testSuiteId)
        .append(" runId:" + runId)
        .append(" hostId:" + hostId)
        .append(" product:" + product)
        .append(" btName:" + btName)
        .append(" artifactLogLocation:" + artifactLogLocation).toString();
  }

  public String getProduct() {
    return product;
  }
}
