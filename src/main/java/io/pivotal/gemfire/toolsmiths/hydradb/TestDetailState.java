package io.pivotal.gemfire.toolsmiths.hydradb;

public enum TestDetailState {

  F("Fail"),
  H("Hang"),
  T("Test Issue"),
  S("Suspect String"),
  I("Ignore"),
  P("Pass");


  private final String friendly;
  private TestDetailState(String friendly) {
    this.friendly = friendly;
  }

  public String getFriendly() {
    return friendly;
  }

  public boolean isNeedsBug() {
    switch(this) {
      case F:
      case H:
      case S:
      case T:
        return true;
      case I:
      case P:
        return false;
      default:
        return false;
    }
  }
}

