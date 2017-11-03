package io.pivotal.gemfire.toolsmiths.hdbr.data.gen.db;

import java.util.Map;

public interface RowUnmapper<T> {
  Map<String, Object> mapColumns(T t);
}
