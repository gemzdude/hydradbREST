package io.pivotal.gemfire.toolsmiths.hdbr.data.gen.db;

import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;
import java.sql.ResultSet;
import java.util.LinkedHashMap;
import java.util.Map;
import com.nurkiewicz.jdbcrepository.RowUnmapper;
import io.pivotal.gemfire.toolsmiths.hdbr.data.gen.RuntimeEnv;
import io.pivotal.gemfire.toolsmiths.hdbr.data.gen.RUNTIMEENV;

/**
 * This class is generated by Spring Data Jdbc code generator.
 *
 * @author Spring Data Jdbc Code Generator
 */
public class RuntimeEnvDB
{

	private static String TABLE_NAME = "RUNTIME_ENV";

	private static String TABLE_ALIAS = "re";

	public static String getTableName()
	{
		return TABLE_NAME;
	}

	public static String getTableAlias()
	{
		return TABLE_NAME + " as " + TABLE_ALIAS;
	}

	public static String getAlias()
	{
		return TABLE_ALIAS;
	}

	public static String selectAllColumns(boolean ... useAlias)
	{
		return (useAlias[0] ? TABLE_ALIAS : TABLE_NAME) + ".*";
	}

	public enum COLUMNS
	{
		NAME("name"),
		USER_NAME("user_name"),
		BUILD_PROPERTIES("build_properties"),
		TARGETS("targets"),
		BUILDPATH("buildpath"),
		RESULTPATH("resultpath"),
		;

		private String columnName;

		private COLUMNS (String columnName)
		{
			this.columnName = columnName;
		}

		public void setColumnName (String columnName)
		{
			this.columnName = columnName;
		}

		public String getColumnName ()
		{
			return this.columnName;
		}

		public String getColumnAlias ()
		{
			return TABLE_ALIAS + "." + this.columnName;
		}

		public String getColumnAliasAsName ()
		{
			return TABLE_ALIAS  + "." + this.columnName + " as " + TABLE_ALIAS + "_" + this.columnName;
		}

		public String getColumnAliasName ()
		{
			return TABLE_ALIAS + "_" + this.columnName;
		}

	}

	public RuntimeEnvDB ()
	{

	}

	public static final RowMapper<RuntimeEnv> ROW_MAPPER = new RuntimeEnvRowMapper ();
	public static final class  RuntimeEnvRowMapper implements RowMapper<RuntimeEnv>
	{
		public RuntimeEnv mapRow(ResultSet rs, int rowNum) throws SQLException 
		{
			RuntimeEnv obj = new RuntimeEnv();
			obj.setName(rs.getString(COLUMNS.NAME.getColumnName()));
			obj.setUserName(rs.getString(COLUMNS.USER_NAME.getColumnName()));
			obj.setBuildProperties(rs.getString(COLUMNS.BUILD_PROPERTIES.getColumnName()));
			obj.setTargets(rs.getString(COLUMNS.TARGETS.getColumnName()));
			obj.setBuildpath(rs.getString(COLUMNS.BUILDPATH.getColumnName()));
			obj.setResultpath(rs.getString(COLUMNS.RESULTPATH.getColumnName()));
			return obj;
		}
	}

	public static final RowUnmapper<RuntimeEnv> ROW_UNMAPPER = new RuntimeEnvRowUnmapper ();
	public static final class RuntimeEnvRowUnmapper implements RowUnmapper<RuntimeEnv>
	{
		public Map<String, Object> mapColumns(RuntimeEnv runtimeenv)
		{
			Map<String, Object> mapping = new LinkedHashMap<String, Object>();
			mapping.put(COLUMNS.NAME.getColumnName(), runtimeenv.getName());
			mapping.put(COLUMNS.USER_NAME.getColumnName(), runtimeenv.getUserName());
			mapping.put(COLUMNS.BUILD_PROPERTIES.getColumnName(), runtimeenv.getBuildProperties());
			mapping.put(COLUMNS.TARGETS.getColumnName(), runtimeenv.getTargets());
			mapping.put(COLUMNS.BUILDPATH.getColumnName(), runtimeenv.getBuildpath());
			mapping.put(COLUMNS.RESULTPATH.getColumnName(), runtimeenv.getResultpath());
			return mapping;
		}
	}

	public static final RowMapper<RuntimeEnv> ALIAS_ROW_MAPPER = new RuntimeEnvAliasRowMapper ();
	public static final class  RuntimeEnvAliasRowMapper implements RowMapper<RuntimeEnv>
	{
		private boolean loadAllFKeys = false;
		public void setLoadAllFKeys (boolean loadAllFKeys)
		{
			this.loadAllFKeys = loadAllFKeys;
		}

		private boolean loadUserName1 = false;
		public void setLoadUserName1 (boolean loadUserName1)
		{
			this.loadUserName1 = loadUserName1;
		}

		public RuntimeEnv mapRow(ResultSet rs, int rowNum) throws SQLException 
		{
			RuntimeEnv obj = new RuntimeEnv();
			obj.setName(rs.getString(COLUMNS.NAME.getColumnAliasName()));
			obj.setUserName(rs.getString(COLUMNS.USER_NAME.getColumnAliasName()));
			obj.setBuildProperties(rs.getString(COLUMNS.BUILD_PROPERTIES.getColumnAliasName()));
			obj.setTargets(rs.getString(COLUMNS.TARGETS.getColumnAliasName()));
			obj.setBuildpath(rs.getString(COLUMNS.BUILDPATH.getColumnAliasName()));
			obj.setResultpath(rs.getString(COLUMNS.RESULTPATH.getColumnAliasName()));
			if (this.loadAllFKeys || this.loadUserName1)
				obj.setUserName1(USERDB.ALIAS_ROW_MAPPER.mapRow(rs, rowNum));
			return obj;
		}
	}

	public static StringBuffer getAllColumnAliases ()
	{
		StringBuffer strBuf = new StringBuffer ();
		int i = COLUMNS.values ().length;
		for (COLUMNS c : COLUMNS.values ())
		{
			strBuf.append (c.getColumnAliasAsName ());
			if (--i > 0)
				strBuf.append (", ");
		}
		return strBuf;
	}

	/* START Do not remove/edit this line. CodeGenerator will preserve any code between start and end tags.*/

	/* END Do not remove/edit this line. CodeGenerator will preserve any code between start and end tags.*/

}