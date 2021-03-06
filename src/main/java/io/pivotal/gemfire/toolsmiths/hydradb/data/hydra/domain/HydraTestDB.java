package io.pivotal.gemfire.toolsmiths.hydradb.data.hydra.domain;

import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;
import java.sql.ResultSet;
import java.util.LinkedHashMap;
import java.util.Map;
import io.pivotal.gemfire.toolsmiths.hydradb.data.RowUnmapper;
import io.pivotal.gemfire.toolsmiths.hydradb.data.hydra.HydraTest;

/**
 * This class is generated by Spring Data Jdbc code generator.
 *
 * @author Spring Data Jdbc Code Generator
 */
public class HydraTestDB
{

	private static String TABLE_NAME = "HYDRA_TEST";

	private static String TABLE_ALIAS = "ht";

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
		ID("id"),
		CONF("conf"),
		FULL_TEST_SPEC("full_test_spec"),
		HYDRA_TESTSUITE_ID("hydra_testsuite_id"),
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

	public HydraTestDB ()
	{

	}

	public static final RowMapper<HydraTest> ROW_MAPPER = new HydraTestRowMapper ();
	public static final class  HydraTestRowMapper implements RowMapper<HydraTest>
	{
		public HydraTest mapRow(ResultSet rs, int rowNum) throws SQLException 
		{
			HydraTest obj = new HydraTest();
			obj.setId(rs.getInt(COLUMNS.ID.getColumnName()));
			obj.setConf(rs.getString(COLUMNS.CONF.getColumnName()));
			obj.setFullTestSpec(rs.getString(COLUMNS.FULL_TEST_SPEC.getColumnName()));
			obj.setHydraTestsuiteId(rs.getInt(COLUMNS.HYDRA_TESTSUITE_ID.getColumnName()));
			return obj;
		}
	}

	public static final RowUnmapper<HydraTest> ROW_UNMAPPER = new HydraTestRowUnmapper ();
	public static final class HydraTestRowUnmapper implements RowUnmapper<HydraTest>
	{
		public Map<String, Object> mapColumns(HydraTest hydratest)
		{
			Map<String, Object> mapping = new LinkedHashMap<String, Object>();
			mapping.put(COLUMNS.ID.getColumnName(), hydratest.getId());
			mapping.put(COLUMNS.CONF.getColumnName(), hydratest.getConf());
			mapping.put(COLUMNS.FULL_TEST_SPEC.getColumnName(), hydratest.getFullTestSpec());
			mapping.put(COLUMNS.HYDRA_TESTSUITE_ID.getColumnName(), hydratest.getHydraTestsuiteId());
			return mapping;
		}
	}

	public static final RowMapper<HydraTest> ALIAS_ROW_MAPPER = new HydraTestAliasRowMapper ();
	public static final class  HydraTestAliasRowMapper implements RowMapper<HydraTest>
	{
		private boolean loadAllFKeys = false;
		public void setLoadAllFKeys (boolean loadAllFKeys)
		{
			this.loadAllFKeys = loadAllFKeys;
		}

		private boolean loadHydraTestsuite = false;
		public void setLoadHydraTestsuite (boolean loadHydraTestsuite)
		{
			this.loadHydraTestsuite = loadHydraTestsuite;
		}

		public HydraTest mapRow(ResultSet rs, int rowNum) throws SQLException 
		{
			HydraTest obj = new HydraTest();
			obj.setId(rs.getInt(COLUMNS.ID.getColumnAliasName()));
			obj.setConf(rs.getString(COLUMNS.CONF.getColumnAliasName()));
			obj.setFullTestSpec(rs.getString(COLUMNS.FULL_TEST_SPEC.getColumnAliasName()));
			obj.setHydraTestsuiteId(rs.getInt(COLUMNS.HYDRA_TESTSUITE_ID.getColumnAliasName()));
			if (this.loadAllFKeys || this.loadHydraTestsuite)
				obj.setHydraTestsuite(HydraTestsuiteDB.ALIAS_ROW_MAPPER.mapRow(rs, rowNum));
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