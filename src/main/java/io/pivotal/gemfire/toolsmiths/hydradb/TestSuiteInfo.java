package io.pivotal.gemfire.toolsmiths.hydradb;

public class TestSuiteInfo implements Comparable<TestSuiteInfo> {

  private final long id;
  private final String name;
  private int noOfTests;
  private String description;
  private String averageTime;
  private String category;

  public TestSuiteInfo(long id) {
    this(id, null, 0, null, null, null);
  }

  public TestSuiteInfo(long id, String name, int noOfTests, String description, String averageTime, String category) {
    this.id = id;
    this.name = name;
    this.noOfTests = noOfTests;
    this.description = description;
    this.averageTime = averageTime;
    this.category = category;
  }

  public TestSuiteInfo(long id, String name) {
    this.id = id;
    this.name = name;
  }

  public long getId() {
    return id;
  }

  public String getName() {
    return name;
  }

  public String getVeryShortName() {
    return name;
  }

  public int getNoOfTests() {
    return noOfTests;
  }

  public String getDescription() {
    return description;
  }

  public String getAverageTime() {
    return averageTime;
  }

  public String getCategory() {
    return category;
  }

  public String toString() {
    return new StringBuilder("TestsuiteInfo@" + System.identityHashCode(this)
        + ":").append("id:" + id).append(" name:" + name).toString();
  }

  public int compareTo(TestSuiteInfo o) {
    return getName().compareTo(o.getName());
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((name == null) ? 0 : name.hashCode());
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
    TestSuiteInfo other = (TestSuiteInfo) obj;
    if (name == null) {
      if (other.name != null)
        return false;
    } else if (!name.equals(other.name))
      return false;
    return true;
  }


}

