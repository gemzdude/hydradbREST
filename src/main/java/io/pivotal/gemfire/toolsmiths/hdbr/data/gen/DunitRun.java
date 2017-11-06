package io.pivotal.gemfire.toolsmiths.hdbr.data.gen;

import org.springframework.data.domain.Persistable;
import org.apache.commons.lang3.builder.ToStringBuilder;
import java.util.Date;

/**
 * This class is generated by Spring Data Jdbc code generator.
 *
 * @author Spring Data Jdbc Code Generator
 */
public class DunitRun implements Persistable<Integer>
{

	private static final long serialVersionUID = 1L;

	private Integer id;

	private String userName;

	private String path;

	private Integer sites;

	private Integer runtimeEnv;

	private Integer baseEnv;

	private String revision;

	private String branch;

	private String osName;

	private String osVersion;

	private String javaVersion;

	private String javaVmVersion;

	private String javaVmVendor;

	private Date time = new Date ();

	private transient boolean persisted;


	public DunitRun ()
	{

	}

	public Integer getId ()
	{
		return this.id;
	}

	public boolean isNew ()
	{
		return this.id == null;
	}

	public void setId(Integer id)
	{
		this.id = id;
	}

	public void setUserName (String userName)
	{
		this.userName = userName;
	}

	public String getUserName ()
	{
		return this.userName;
	}

	public void setPath (String path)
	{
		this.path = path;
	}

	public String getPath ()
	{
		return this.path;
	}

	public void setSites (Integer sites)
	{
		this.sites = sites;
	}

	public Integer getSites ()
	{
		return this.sites;
	}

	public void setRuntimeEnv (Integer runtimeEnv)
	{
		this.runtimeEnv = runtimeEnv;
	}

	public Integer getRuntimeEnv ()
	{
		return this.runtimeEnv;
	}

	public void setBaseEnv (Integer baseEnv)
	{
		this.baseEnv = baseEnv;
	}

	public Integer getBaseEnv ()
	{
		return this.baseEnv;
	}

	public void setRevision (String revision)
	{
		this.revision = revision;
	}

	public String getRevision ()
	{
		return this.revision;
	}

	public void setBranch (String branch)
	{
		this.branch = branch;
	}

	public String getBranch ()
	{
		return this.branch;
	}

	public void setOsName (String osName)
	{
		this.osName = osName;
	}

	public String getOsName ()
	{
		return this.osName;
	}

	public void setOsVersion (String osVersion)
	{
		this.osVersion = osVersion;
	}

	public String getOsVersion ()
	{
		return this.osVersion;
	}

	public void setJavaVersion (String javaVersion)
	{
		this.javaVersion = javaVersion;
	}

	public String getJavaVersion ()
	{
		return this.javaVersion;
	}

	public void setJavaVmVersion (String javaVmVersion)
	{
		this.javaVmVersion = javaVmVersion;
	}

	public String getJavaVmVersion ()
	{
		return this.javaVmVersion;
	}

	public void setJavaVmVendor (String javaVmVendor)
	{
		this.javaVmVendor = javaVmVendor;
	}

	public String getJavaVmVendor ()
	{
		return this.javaVmVendor;
	}

	public void setTime (Date time)
	{
		this.time = time;
	}

	public Date getTime ()
	{
		return this.time;
	}

	public void setPersisted (Boolean persisted)
	{
		this.persisted = persisted;
	}

	public Boolean getPersisted ()
	{
		return this.persisted;
	}

	@Override
	public String toString () 
	{
		return ToStringBuilder.reflectionToString (this); 
	}

	/* START Do not remove/edit this line. CodeGenerator will preserve any code between start and end tags.*/

	/* END Do not remove/edit this line. CodeGenerator will preserve any code between start and end tags.*/

}