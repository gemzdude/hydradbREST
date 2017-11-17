package io.pivotal.gemfire.toolsmiths.hydradb;

import org.apache.log4j.Logger;

public class TestInfo {

  public final static Logger log = Logger.getLogger(TestInfo.class);
  private final long id;
  private final String conf;
  private final String fullTestSpec;
  private final long testSuiteId;

  public TestInfo(long id, String conf, String fullTestSpec, long testSuiteId) {
    this.id = id;
    this.conf = conf;
    this.fullTestSpec = fullTestSpec;
    this.testSuiteId = testSuiteId;
  }

  public long getId() {
    return id;
  }
  public String getConf() {
    return conf;
  }

  public char[] getConfArray() {
    return conf.toCharArray();
  }
  public String getFullTestSpec() {
    return fullTestSpec;
  }
  public long getTestSuiteId() {
    return testSuiteId;
  }

//  public TestSuiteInfo getSuiteInfo() {
//    try {
//      return HydraDBService.getTestSuiteInfo(getTestSuiteId());
//    } catch (TestResultStoreException e) {
//      log.error("Error occured while tryn to get suite info for " + getTestSuiteId());
//    }
//    return null;
//  }
//
//  public List<TestDetail> getLast50Runs() {
//    List<TestDetail> last50runs = new ArrayList<TestDetail>();
//    try {
//      last50runs = HydraDBService.getLastNTestDetailsForTestId(this, 50, null, null);
//    } catch (TestResultStoreException e) {
//      log.error("Error occurred while trying to get last 50 test Details for Test : " + this);
//    }
//    return last50runs;
//  }

  @Override
  public String toString() {
    return new StringBuilder("TestInfo@" + System.identityHashCode(this) + ":")
        .append("id:" + id).append(" conf:" + conf)
        // .append(" fullTestSpec:"+fullTestSpec)
        // .append(" testsuiteId:"+testsuiteId)
        .toString();
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((conf == null) ? 0 : conf.hashCode());
    result = prime * result
        + ((fullTestSpec == null) ? 0 : fullTestSpec.hashCode());
    return result;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj)
      return true;
    if (obj == null)
      return false;
    if (getClass() != obj.getClass())
      return false;
    TestInfo other = (TestInfo) obj;
    if (conf == null) {
      if (other.conf != null)
        return false;
    } else if (!conf.equals(other.conf))
      return false;
    if (fullTestSpec == null) {
      if (other.fullTestSpec != null)
        return false;
    } else if (!fullTestSpec.equals(other.fullTestSpec))
      return false;
    return true;
  }


}

