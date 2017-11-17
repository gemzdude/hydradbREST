package io.pivotal.gemfire.toolsmiths.hydradb;

import org.apache.log4j.Logger;

public class HydraRunSearch {

  public final static Logger log = Logger.getLogger(HydraRunSearch.class);

  private long runId = -1;
  private String userName = null;
  private String productVersion = null;
  private String buildId = null;
  private String svnRepository = null;
  private String svnRevision = null;
  private String javaVersion = null;
  private String javaVendor = null;
  private String javaHome = null;
  private String svnRevisionOp;

  private int regressionType;

  public HydraRunSearch setRunId(long i) {
    this.runId = i;
    return this;
  }

  public long getRunId() {
    return this.runId;
  }

  public String getQuery() {
    StringBuilder query = new StringBuilder(HydraDBService.HYDRA_RUN_QUERY);
    query.append(" WHERE ID=ID ");
    if (getRunId() > -1) {
      query.append(" AND ID=").append(getRunId());
    }

    if (getUserName() != null) {
      query.append(" AND USER_NAME='").append(getUserName()).append("'");
    }

    if (getProductVersion() != null) {
      query.append(" AND PRODUCT_VERSION='").append(getProductVersion())
          .append("'");
    }

    if (getBuildId() != null) {
      query.append(" AND BUILD_ID='").append(getBuildId()).append("'");
    }

    if (getSvnRepository() != null) {
      query.append(" AND SVN_REPOSITORY='").append(getSvnRepository())
          .append("'");
    }

    if (getSvnRevision() != null) {
      query.append(" AND SVN_REVISION='").append(getSvnRevision()).append("'");
    }

    if (getJavaVendor() != null) {
      query.append(" AND JAVA_VENDOR='").append(getJavaVendor()).append("'");
    }

    if (getJavaVersion() != null) {
      query.append(" AND JAVA_VERSION='").append(getJavaVersion()).append("'");
    }

    if (getJavaHome() != null) {
      query.append(" AND JAVA_HOME='").append(getJavaHome()).append("'");
    }
    if (getRegressionType() != 0) {
      query.append(" AND REGRESSION_TYPE=").append(getRegressionType()).append("");
    }

    log.info("Created this query to get HydraRun : " + query);
    return query.toString();
  }

  private int getRegressionType() {
    return regressionType;
  }

  public String getUserName() {
    return userName;
  }

  public HydraRunSearch setUserName(String userName) {
    this.userName = userName;
    return this;
  }

  public String getProductVersion() {
    return productVersion;
  }

  public HydraRunSearch setProductVersion(String productVersion) {
    this.productVersion = productVersion;
    return this;
  }

  public String getBuildId() {
    return buildId;
  }

  public HydraRunSearch setBuildId(String buildId) {
    this.buildId = buildId;
    return this;
  }

  public String getSvnRepository() {
    return svnRepository;
  }

  public HydraRunSearch setSvnRepository(String svnRepository) {
    this.svnRepository = svnRepository;
    return this;
  }

  public String getSvnRevision() {
    return svnRevision;
  }

  public HydraRunSearch setSvnRevision(String svnRevision) {
    this.svnRevision = svnRevision;
    return this;
  }

  public String getJavaVersion() {
    return javaVersion;
  }

  public HydraRunSearch setJavaVersion(String javaVersion) {
    this.javaVersion = javaVersion;
    return this;
  }

  public String getJavaVendor() {
    return javaVendor;
  }

  public HydraRunSearch setJavaVendor(String javaVendor) {
    this.javaVendor = javaVendor;
    return this;
  }

  public String getJavaHome() {
    return javaHome;
  }

  public HydraRunSearch setJavaHome(String javaHome) {
    this.javaHome = javaHome;
    return this;
  }

  @Override
  public String toString() {
    return "HydraRunSearch [runId=" + runId + ", Query : " + getQuery() + "]";
  }

  public HydraRunSearch setSvnRevisionOp(String svnRevisionOp) {
    this.svnRevisionOp = svnRevisionOp;
    return this;

  }

  public String getSvnRevisionOp() {
    return svnRevisionOp;
  }

  public HydraRunSearch setRegressionType(int regressionType) {
    this.regressionType = regressionType;
    return this;
  }



}