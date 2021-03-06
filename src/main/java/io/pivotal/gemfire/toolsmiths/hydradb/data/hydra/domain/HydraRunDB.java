package io.pivotal.gemfire.toolsmiths.hydradb.data.hydra.domain;

import io.pivotal.gemfire.toolsmiths.hydradb.data.RowUnmapper;
import io.pivotal.gemfire.toolsmiths.hydradb.data.hydra.HydraRun;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * This class is generated by Spring Data Jdbc code generator.
 *
 * @author Spring Data Jdbc Code Generator
 */
public class HydraRunDB
{

	private static String TABLE_NAME = "HYDRA_RUN";

	private static String TABLE_ALIAS = "hr";

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
		PRODUCT_VERSION("product_version"),
		BUILD_ID("build_id"),
		SVN_REPOSITORY("svn_repository"),
		SVN_REVISION("svn_revision"),
		JAVA_VERSION("java_version"),
		JAVA_VENDOR("java_vendor"),
		JAVA_HOME("java_home"),
		DATE("date"),
		FULL_REGRESSION("full_regression"),
		REGRESSION_TYPE("regression_type"),
		COMMENTS("comments"),
		BUILD_LOCATION("build_location"),
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

	public HydraRunDB ()
	{

	}

	public static final RowMapper<HydraRun> ROW_MAPPER = new HydraRunRowMapper ();
	public static final class  HydraRunRowMapper implements RowMapper<HydraRun>
	{
		public HydraRun mapRow(ResultSet rs, int rowNum) throws SQLException 
		{
			HydraRun obj = new HydraRun();
			obj.setId(rs.getInt(COLUMNS.ID.getColumnName()));
			obj.setUserName(rs.getString(COLUMNS.USER_NAME.getColumnName()));
			obj.setProductVersion(rs.getString(COLUMNS.PRODUCT_VERSION.getColumnName()));
			obj.setBuildId(rs.getString(COLUMNS.BUILD_ID.getColumnName()));
			obj.setSvnRepository(rs.getString(COLUMNS.SVN_REPOSITORY.getColumnName()));
			obj.setSvnRevision(rs.getString(COLUMNS.SVN_REVISION.getColumnName()));
			obj.setJavaVersion(rs.getString(COLUMNS.JAVA_VERSION.getColumnName()));
			obj.setJavaVendor(rs.getString(COLUMNS.JAVA_VENDOR.getColumnName()));
			obj.setJavaHome(rs.getString(COLUMNS.JAVA_HOME.getColumnName()));
			obj.setDate(rs.getTimestamp(COLUMNS.DATE.getColumnName()));
			obj.setFullRegression(rs.getBoolean(COLUMNS.FULL_REGRESSION.getColumnName()));
			obj.setRegressionType(rs.getInt(COLUMNS.REGRESSION_TYPE.getColumnName()));
			obj.setComments(rs.getString(COLUMNS.COMMENTS.getColumnName()));
			obj.setBuildLocation(rs.getString(COLUMNS.BUILD_LOCATION.getColumnName()));
			return obj;
		}
	}

	public static final RowUnmapper<HydraRun> ROW_UNMAPPER = new HydraRunRowUnmapper ();
	public static final class HydraRunRowUnmapper implements RowUnmapper<HydraRun>
	{
		public Map<String, Object> mapColumns(HydraRun hydrarun)
		{
			Map<String, Object> mapping = new LinkedHashMap<String, Object>();
			mapping.put(COLUMNS.ID.getColumnName(), hydrarun.getId());
			mapping.put(COLUMNS.USER_NAME.getColumnName(), hydrarun.getUserName());
			mapping.put(COLUMNS.PRODUCT_VERSION.getColumnName(), hydrarun.getProductVersion());
			mapping.put(COLUMNS.BUILD_ID.getColumnName(), hydrarun.getBuildId());
			mapping.put(COLUMNS.SVN_REPOSITORY.getColumnName(), hydrarun.getSvnRepository());
			mapping.put(COLUMNS.SVN_REVISION.getColumnName(), hydrarun.getSvnRevision());
			mapping.put(COLUMNS.JAVA_VERSION.getColumnName(), hydrarun.getJavaVersion());
			mapping.put(COLUMNS.JAVA_VENDOR.getColumnName(), hydrarun.getJavaVendor());
			mapping.put(COLUMNS.JAVA_HOME.getColumnName(), hydrarun.getJavaHome());
			if (hydrarun.getDate() != null)
				mapping.put(COLUMNS.DATE.getColumnName(), new Timestamp (hydrarun.getDate().getTime()));
			mapping.put(COLUMNS.FULL_REGRESSION.getColumnName(), hydrarun.getFullRegression());
			mapping.put(COLUMNS.REGRESSION_TYPE.getColumnName(), hydrarun.getRegressionType());
			mapping.put(COLUMNS.COMMENTS.getColumnName(), hydrarun.getComments());
			mapping.put(COLUMNS.BUILD_LOCATION.getColumnName(), hydrarun.getBuildLocation());
			return mapping;
		}
	}

	public static final RowMapper<HydraRun> ALIAS_ROW_MAPPER = new HydraRunAliasRowMapper ();
	public static final class  HydraRunAliasRowMapper implements RowMapper<HydraRun>
	{
		public HydraRun mapRow(ResultSet rs, int rowNum) throws SQLException 
		{
			HydraRun obj = new HydraRun();
			obj.setId(rs.getInt(COLUMNS.ID.getColumnAliasName()));
			obj.setUserName(rs.getString(COLUMNS.USER_NAME.getColumnAliasName()));
			obj.setProductVersion(rs.getString(COLUMNS.PRODUCT_VERSION.getColumnAliasName()));
			obj.setBuildId(rs.getString(COLUMNS.BUILD_ID.getColumnAliasName()));
			obj.setSvnRepository(rs.getString(COLUMNS.SVN_REPOSITORY.getColumnAliasName()));
			obj.setSvnRevision(rs.getString(COLUMNS.SVN_REVISION.getColumnAliasName()));
			obj.setJavaVersion(rs.getString(COLUMNS.JAVA_VERSION.getColumnAliasName()));
			obj.setJavaVendor(rs.getString(COLUMNS.JAVA_VENDOR.getColumnAliasName()));
			obj.setJavaHome(rs.getString(COLUMNS.JAVA_HOME.getColumnAliasName()));
			obj.setDate(rs.getTimestamp(COLUMNS.DATE.getColumnAliasName()));
			obj.setFullRegression(rs.getBoolean(COLUMNS.FULL_REGRESSION.getColumnAliasName()));
			obj.setRegressionType(rs.getInt(COLUMNS.REGRESSION_TYPE.getColumnAliasName()));
			obj.setComments(rs.getString(COLUMNS.COMMENTS.getColumnAliasName()));
			obj.setBuildLocation(rs.getString(COLUMNS.BUILD_LOCATION.getColumnAliasName()));
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
	public static StringBuffer getAllColumnNames ()
	{
		StringBuffer strBuf = new StringBuffer ();
		int i = COLUMNS.values ().length;
		for (COLUMNS c : COLUMNS.values ())
		{
			strBuf.append (c.getColumnName ());
			if (--i > 0)
				strBuf.append (", ");
		}
		return strBuf;
	}


	/* END Do not remove/edit this line. CodeGenerator will preserve any code between start and end tags.*/

}