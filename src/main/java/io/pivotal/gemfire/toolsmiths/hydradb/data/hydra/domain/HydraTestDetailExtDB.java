package io.pivotal.gemfire.toolsmiths.hydradb.data.hydra.domain;

import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;
import java.sql.ResultSet;
import java.util.LinkedHashMap;
import java.util.Map;
import io.pivotal.gemfire.toolsmiths.hydradb.data.RowUnmapper;
import io.pivotal.gemfire.toolsmiths.hydradb.data.hydra.HydraTestDetailExt;

/**
 * This class is generated by Spring Data Jdbc code generator.
 *
 * @author Spring Data Jdbc Code Generator
 */
public class HydraTestDetailExtDB
{

	private static String TABLE_NAME = "HYDRA_TEST_DETAIL_EXT";

	private static String TABLE_ALIAS = "htde";

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
		LOG_LOCATION("log_location"),
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

	public HydraTestDetailExtDB ()
	{

	}

	public static final RowMapper<HydraTestDetailExt> ROW_MAPPER = new HydraTestDetailExtRowMapper ();
	public static final class  HydraTestDetailExtRowMapper implements RowMapper<HydraTestDetailExt>
	{
		public HydraTestDetailExt mapRow(ResultSet rs, int rowNum) throws SQLException 
		{
			HydraTestDetailExt obj = new HydraTestDetailExt();
			obj.setId(rs.getLong(COLUMNS.ID.getColumnName()));
			obj.setLogLocation(rs.getString(COLUMNS.LOG_LOCATION.getColumnName()));
			return obj;
		}
	}

	public static final RowUnmapper<HydraTestDetailExt> ROW_UNMAPPER = new HydraTestDetailExtRowUnmapper ();
	public static final class HydraTestDetailExtRowUnmapper implements RowUnmapper<HydraTestDetailExt>
	{
		public Map<String, Object> mapColumns(HydraTestDetailExt hydratestdetailext)
		{
			Map<String, Object> mapping = new LinkedHashMap<String, Object>();
			mapping.put(COLUMNS.ID.getColumnName(), hydratestdetailext.getId());
			mapping.put(COLUMNS.LOG_LOCATION.getColumnName(), hydratestdetailext.getLogLocation());
			return mapping;
		}
	}

	public static final RowMapper<HydraTestDetailExt> ALIAS_ROW_MAPPER = new HydraTestDetailExtAliasRowMapper ();
	public static final class  HydraTestDetailExtAliasRowMapper implements RowMapper<HydraTestDetailExt>
	{
		private boolean loadAllFKeys = false;
		public void setLoadAllFKeys (boolean loadAllFKeys)
		{
			this.loadAllFKeys = loadAllFKeys;
		}

		private boolean load = false;
		public void setLoad (boolean load)
		{
			this.load = load;
		}

		public HydraTestDetailExt mapRow(ResultSet rs, int rowNum) throws SQLException 
		{
			HydraTestDetailExt obj = new HydraTestDetailExt();
			obj.setId(rs.getLong(COLUMNS.ID.getColumnAliasName()));
			obj.setLogLocation(rs.getString(COLUMNS.LOG_LOCATION.getColumnAliasName()));
			if (this.loadAllFKeys || this.load)
				obj.set(HydraTestDetailDB.ALIAS_ROW_MAPPER.mapRow(rs, rowNum));
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