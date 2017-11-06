package io.pivotal.gemfire.toolsmiths.hdbr.data.gen.db;

import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;
import java.sql.ResultSet;
import java.util.LinkedHashMap;
import java.util.Map;
import io.pivotal.gemfire.toolsmiths.hdbr.data.RowUnmapper;
import io.pivotal.gemfire.toolsmiths.hdbr.data.gen.BASEENV;

/**
 * This class is generated by Spring Data Jdbc code generator.
 *
 * @author Spring Data Jdbc Code Generator
 */
public class BASEENVDB
{

	private static String TABLE_NAME = "BASE_ENV";

	private static String TABLE_ALIAS = "be";

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
		USER_NAME("user_name"),
		NAME("name"),
		CHECKOUTPATH("checkoutpath"),
		OUTPUTPATH("outputpath"),
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

	public BASEENVDB ()
	{

	}

	public static final RowMapper<BASEENV> ROW_MAPPER = new BASEENVRowMapper ();
	public static final class  BASEENVRowMapper implements RowMapper<BASEENV>
	{
		public BASEENV mapRow(ResultSet rs, int rowNum) throws SQLException 
		{
			BASEENV obj = new BASEENV();
			obj.setId(rs.getInt(COLUMNS.ID.getColumnName()));
			obj.setUserName(rs.getString(COLUMNS.USER_NAME.getColumnName()));
			obj.setName(rs.getString(COLUMNS.NAME.getColumnName()));
			obj.setCheckoutpath(rs.getString(COLUMNS.CHECKOUTPATH.getColumnName()));
			obj.setOutputpath(rs.getString(COLUMNS.OUTPUTPATH.getColumnName()));
			return obj;
		}
	}

	public static final RowUnmapper<BASEENV> ROW_UNMAPPER = new BASEENVRowUnmapper ();
	public static final class BASEENVRowUnmapper implements RowUnmapper<BASEENV>
	{
		public Map<String, Object> mapColumns(BASEENV baseenv)
		{
			Map<String, Object> mapping = new LinkedHashMap<String, Object>();
			mapping.put(COLUMNS.ID.getColumnName(), baseenv.getId());
			mapping.put(COLUMNS.USER_NAME.getColumnName(), baseenv.getUserName());
			mapping.put(COLUMNS.NAME.getColumnName(), baseenv.getName());
			mapping.put(COLUMNS.CHECKOUTPATH.getColumnName(), baseenv.getCheckoutpath());
			mapping.put(COLUMNS.OUTPUTPATH.getColumnName(), baseenv.getOutputpath());
			return mapping;
		}
	}

	public static final RowMapper<BASEENV> ALIAS_ROW_MAPPER = new BASEENVAliasRowMapper ();
	public static final class  BASEENVAliasRowMapper implements RowMapper<BASEENV>
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

		public BASEENV mapRow(ResultSet rs, int rowNum) throws SQLException 
		{
			BASEENV obj = new BASEENV();
			obj.setId(rs.getInt(COLUMNS.ID.getColumnAliasName()));
			obj.setUserName(rs.getString(COLUMNS.USER_NAME.getColumnAliasName()));
			obj.setName(rs.getString(COLUMNS.NAME.getColumnAliasName()));
			obj.setCheckoutpath(rs.getString(COLUMNS.CHECKOUTPATH.getColumnAliasName()));
			obj.setOutputpath(rs.getString(COLUMNS.OUTPUTPATH.getColumnAliasName()));
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