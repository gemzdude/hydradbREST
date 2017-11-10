package io.pivotal.gemfire.toolsmiths.hdbr;

public class IdAndNameObject {
  int id;
  String name;
  int getId() { return this.id; }
  String getName() { return this.name; }
  IdAndNameObject(int id, String name) {
    this.id = id;
    this.name = name;
  }
}
