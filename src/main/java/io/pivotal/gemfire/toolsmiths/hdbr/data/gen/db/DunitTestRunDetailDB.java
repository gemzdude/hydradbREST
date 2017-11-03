package io.pivotal.gemfire.toolsmiths.hdbr.data.gen.db;

import io.pivotal.gemfire.toolsmiths.hdbr.data.gen.DunitTestRunDetail;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.LinkedHashMap;
import java.util.Map;

//import com.nurkiewicz.jdbcrepository.RowUnmapper;

/**
 * This class is generated by Spring Data Jdbc code generator.
 *
 * @author Spring Data Jdbc Code Generator
 */
public class DunitTestRunDetailDB
{

	private static String TABLE_NAME = "DUNIT_TEST_RUN_DETAIL";

	private static String TABLE_ALIAS = "dtrd";

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
		DUNIT_RUN_ID("dunit_run_id"),
		PASS("pass"),
		MESSAGE("message"),
		FAILCOUNT("failcount"),
		TOOKMS("tookms"),
		TIME("time"),
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

	public DunitTestRunDetailDB ()
	{

	}

	public static final RowMapper<DunitTestRunDetail> ROW_MAPPER = new DunitTestRunDetailRowMapper ();
	public static final class  DunitTestRunDetailRowMapper implements RowMapper<DunitTestRunDetail>
	{
		public DunitTestRunDetail mapRow(ResultSet rs, int rowNum) throws SQLException 
		{
			DunitTestRunDetail obj = new DunitTestRunDetail();
			obj.setId(rs.getInt(COLUMNS.ID.getColumnName()));
			obj.setDunitRunId(rs.getInt(COLUMNS.DUNIT_RUN_ID.getColumnName()));
			obj.setPass(rs.getBoolean(COLUMNS.PASS.getColumnName()));
			obj.setMessage(rs.getString(COLUMNS.MESSAGE.getColumnName()));
			obj.setFailcount(rs.getInt(COLUMNS.FAILCOUNT.getColumnName()));
			obj.setTookms(rs.getLong(COLUMNS.TOOKMS.getColumnName()));
			obj.setTime(rs.getTimestamp(COLUMNS.TIME.getColumnName()));
			return obj;
		}
	}

	public static final RowUnmapper<DunitTestRunDetail> ROW_UNMAPPER = new DunitTestRunDetailRowUnmapper ();
	public static final class DunitTestRunDetailRowUnmapper implements RowUnmapper<DunitTestRunDetail>
	{
		public Map<String, Object> mapColumns(DunitTestRunDetail dunittestrundetail)
		{
			Map<String, Object> mapping = new LinkedHashMap<String, Object>();
			mapping.put(COLUMNS.ID.getColumnName(), dunittestrundetail.getId());
			mapping.put(COLUMNS.DUNIT_RUN_ID.getColumnName(), dunittestrundetail.getDunitRunId());
			mapping.put(COLUMNS.PASS.getColumnName(), dunittestrundetail.getPass());
			mapping.put(COLUMNS.MESSAGE.getColumnName(), dunittestrundetail.getMessage());
			mapping.put(COLUMNS.FAILCOUNT.getColumnName(), dunittestrundetail.getFailcount());
			mapping.put(COLUMNS.TOOKMS.getColumnName(), dunittestrundetail.getTookms());
			if (dunittestrundetail.getTime() != null)
				mapping.put(COLUMNS.TIME.getColumnName(), new Timestamp (dunittestrundetail.getTime().getTime()));
			return mapping;
		}
	}

	public static final RowMapper<DunitTestRunDetail> ALIAS_ROW_MAPPER = new DunitTestRunDetailAliasRowMapper ();
	public static final class  DunitTestRunDetailAliasRowMapper implements RowMapper<DunitTestRunDetail>
	{
		private boolean loadAllFKeys = false;
		public void setLoadAllFKeys (boolean loadAllFKeys)
		{
			this.loadAllFKeys = loadAllFKeys;
		}

		private boolean loadDunitRun = false;
		public void setLoadDunitRun (boolean loadDunitRun)
		{
			this.loadDunitRun = loadDunitRun;
		}

		public DunitTestRunDetail mapRow(ResultSet rs, int rowNum) throws SQLException 
		{
			DunitTestRunDetail obj = new DunitTestRunDetail();
			obj.setId(rs.getInt(COLUMNS.ID.getColumnAliasName()));
			obj.setDunitRunId(rs.getInt(COLUMNS.DUNIT_RUN_ID.getColumnAliasName()));
			obj.setPass(rs.getBoolean(COLUMNS.PASS.getColumnAliasName()));
			obj.setMessage(rs.getString(COLUMNS.MESSAGE.getColumnAliasName()));
			obj.setFailcount(rs.getInt(COLUMNS.FAILCOUNT.getColumnAliasName()));
			obj.setTookms(rs.getLong(COLUMNS.TOOKMS.getColumnAliasName()));
			obj.setTime(rs.getTimestamp(COLUMNS.TIME.getColumnAliasName()));
			if (this.loadAllFKeys || this.loadDunitRun)
				obj.setDunitRun(DunitRunDB.ALIAS_ROW_MAPPER.mapRow(rs, rowNum));
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