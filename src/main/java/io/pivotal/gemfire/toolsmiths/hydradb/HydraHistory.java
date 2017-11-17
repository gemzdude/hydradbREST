package io.pivotal.gemfire.toolsmiths.hydradb;

import io.pivotal.gemfire.toolsmiths.hydradb.data.hydra.HydraRun;

import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

public class HydraHistory {

  private Map<Integer, HydraRun> hydraRunList;
  private Map<Long, String> testFullConfMapping = new HashMap<Long, String>();
  private Map<Long, String> testConfMapping = new HashMap<Long, String>();

  private String batteryTest;

  private TestDetail[][] testDetails;

  public void addTestDetail(int row, int col, TestDetail td) {
    testDetails[row][col] = td;

  }

  public Map<Long, String> getTestConfMapping() {
    return testConfMapping;
  }

  public Map<Long, String> getTestFullConfMapping() {
    return testFullConfMapping;
  }

  public void setBattryTest(String batteryTest) {
    this.batteryTest = batteryTest;
  }

  public String getBatteryTest() {
    return batteryTest;
  }

  public void setTestDetails(TestDetail[][] testDetails) {
    this.testDetails = testDetails;

  }

  public HydraRun getHydraRunForColumn(HydraDBService history, int column) {
    return getHydraRunForColumn(column);
  }

  public Map<Integer, HydraRun> getHydraRunList() {

    return hydraRunList;
  }

  public TestDetail[][] getHistory() {
    return testDetails;
  }

  public TestDetail getHistory(int k, int i) {
    return testDetails[k][i];
  }

  public void setHydraRunList(Map<Integer, HydraRun> populateHydraRuns) {
    Map<Integer, HydraRun> map = new TreeMap<Integer, HydraRun>(new Comparator<Integer>() {

      @Override
      public int compare(Integer o1, Integer o2) {
        return o2.compareTo(o1);
      }
    });

    map.putAll(populateHydraRuns);
    hydraRunList = map;

  }

  public HydraRun getHydraRunForColumn(int column) {
    int i = 0;
    TestDetail td = testDetails[i][column];
    if (td == null) {
      for (i = 1; i < testDetails.length; i++) {
        td = testDetails[i][column];
        if (td != null)
          break;
      }
    }
    if (td != null) {
      return hydraRunList.get(td.getRunId());
    }
    return null;
  }

  // this method goes through all test record for one particular test
  // so traverse horizontally
  public String getFullTestConfigurationForRow(int rowNum) {
    TestDetail td = getTestDetail(rowNum);
    if (td != null) {
      return testFullConfMapping.get(td.getTestId());
    }

    return "Unable to find FulltestConf for row=" + rowNum;
  }

  // this method goes through all test record for one particular test
  // so traverse horizontally
  public String getTestConfigurationForRow(int rowNum) {
    TestDetail td = getTestDetail(rowNum);
    if (td != null) {
      return testConfMapping.get(td.getTestId());
    }

    return "Unable to find testConf for row = " + rowNum;
  }

  private TestDetail getTestDetail(int rowNum) {
    int i = 0;
    TestDetail td = testDetails[rowNum][i];
    if (td == null) {
      for (i = 1; i < testDetails[rowNum].length; i++) {
        td = testDetails[rowNum][i];
        if (td != null)
          break;
      }
    }
    return td;
  }
}

