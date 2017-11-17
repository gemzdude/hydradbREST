package io.pivotal.gemfire.toolsmiths.hydradb;

public class TestDetail implements Comparable<TestDetail> {

  private final long id;
  private final String elapsedTime;
  private final String diskUsage;
  private final String status;
  private String error;
  private String bugNumber;

  private final long testId;
  private final long testSuiteDetailId;
  private final int runId;
  private final int hashCode;
  private TestDetailState stateEnum;
  private final String comment;
  private final String tags;
  private final String logLocation;

  public boolean equals(Object o) {
    if (!(o instanceof TestDetail)) {
      return false;
    }
    if (((TestDetail) o).getId() == getId()) {
      return true;
    } else {
      return false;
    }
  }

  public int hashCode() {
    return hashCode;
  }

  public int compareTo(TestDetail o1) {
    int rc = 0;
    if (getTestId() < o1.getTestId()) {
      rc = -1;
    } else if (getTestId() > o1.getTestId()) {
      rc = 1;
    }
    return rc;
  }

  public TestDetail(long id, String elapsedTime, String diskUsage,
                    String status, String error, String bugNumber, long testId,
                    long testSuiteDetailId, int runId, String comment, String tags,
                    String logLocation) {
    this.id = id;
    this.elapsedTime = elapsedTime;
    this.diskUsage = diskUsage;
    this.status = status;
    this.error = error;
    this.bugNumber = bugNumber;
    this.testId = testId;
    this.testSuiteDetailId = testSuiteDetailId;
    this.runId = runId;

    this.hashCode = new Long(id).hashCode();
    try {
      this.stateEnum = TestDetailState.valueOf(status);
    } catch (IllegalArgumentException e) {
      this.stateEnum = TestDetailState.I;
    }
    this.comment = comment;
    this.logLocation = logLocation;
    this.tags = tags;
  }

  public TestDetail() {
    this.id = 0;
    this.elapsedTime = null;
    this.diskUsage = null;
    this.status = null;
    this.error = null;
    this.bugNumber = null;
    this.testId = 0;
    this.testSuiteDetailId = 0;
    this.runId = 0;

    this.hashCode = new Long(id).hashCode();

    this.stateEnum = TestDetailState.I;

    this.comment = null;
    this.logLocation = null;
    this.tags = null;
  }

  public String getComment() {
    return comment;
  }

  public String getLogLocation() {
    return logLocation;
  }

  public TestDetailState getStateEnum() {
    return stateEnum;
  }

  /** accessor methods */
  public long getId() {
    return id;
  }


  public String getElapsedTime() {
    if (elapsedTime.startsWith("00:")) {
      return elapsedTime.substring(3);
    }
    return elapsedTime;
  }

  public String getDiskUsage() {
    return diskUsage;
  }

  public String getStatus() {
    return status;
  }

  public String getError() {
    if (error == null || error.trim().length() == 0) {
      return null;
    }
    return error;
  }

  public String getErrorHtml() {
    if (error == null) {
      return null;
    }
    return error.replaceAll("\\n", "<br/>");
  }

  public String getBugNumber() {
    return bugNumber;
  }

  public long getTestId() {
    return testId;
  }

  public long getTestSuiteDetailId() {
    return testSuiteDetailId;
  }

  public int getRunId() {
    return runId;
  }

  public boolean isPass() {
    return (status.startsWith("P")) ? true : false;
  }

  public boolean isFail() {
    return (status.startsWith("F")) ? true : false;
  }

  public boolean isHang() {
    return (status.startsWith("H")) ? true : false;
  }

  /** setter */
  public void setBugNumber(String bugNumber) {
    this.bugNumber = bugNumber;
  }

//  public TestInfo getTestInfo() {
//    try {
//      return HydraDBService.getTestInfo(getTestId());
//    } catch (TestResultStoreException e) {
//      return null;
//    }
//  }
//
//  public TestSuiteDetail getTestSuiteDetail() {
//    try {
//      return HydraDBService.getHydraTestSuiteDetailById(
//          getTestSuiteDetailId());
//    } catch (TestResultStoreException e) {
//      return null;
//    }
//  }

  @Override
  public String toString() {
    return new StringBuilder("TestDetail@" + System.identityHashCode(this)
        + ":").append("id:" + id).append(" elapsedTime:" + elapsedTime)
        .append(" diskUsage:" + diskUsage).append(" status:" + status)
        .append(" error:" + error).append(" bugNumber:" + bugNumber)
        .append(" testId:" + testId)
        .append(" testSuiteDetailId:" + testSuiteDetailId)
        .append(" runId:" + runId)
        .append(" logLocation:" + logLocation).toString();
  }

  public String getTags() {
    return tags;
  }

  public void setError(String error) {
    this.error = error;

  }

}

