package io.pivotal.gemfire.toolsmiths.hydradb.data;

import java.util.Map;

public interface RowUnmapper<T> {
  Map<String, Object> mapColumns(T t);
}