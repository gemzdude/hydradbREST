package io.pivotal.gemfire.toolsmiths.hydradb;

public class IdAndNameObject {
  int id;
  String name;
  int getId() { return this.id; }
  String getName() { return this.name; }
  IdAndNameObject(int id, String name) {
    this.id = id;
    this.name = name;
  }

  public String toString() {
    return "ID: " + getId() + " NAME: " + getName();
  }
}
