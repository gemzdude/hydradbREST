package io.pivotal.gemfire.toolsmiths.hdbr.data.gen;

import org.springframework.data.domain.Persistable;
import org.apache.commons.lang3.builder.ToStringBuilder;
import java.util.Date;

/**
 * This class is generated by Spring Data Jdbc code generator.
 *
 * @author Spring Data Jdbc Code Generator
 */
public class HydraRun implements Persistable<Integer>
{

	private static final long serialVersionUID = 1L;

	private Integer id;

	private String userName;

	private String productVersion;

	private String buildId;

	private String svnRepository;

	private String svnRevision;

	private String javaVersion;

	private String javaVendor;

	private String javaHome;

	private Date date = new Date ();

	private Boolean fullRegression;

	private Integer regressionType;

	private String comments;

	private String buildLocation;

	private transient boolean persisted;


	public HydraRun ()
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

	public void setProductVersion (String productVersion)
	{
		this.productVersion = productVersion;
	}

	public String getProductVersion ()
	{
		return this.productVersion;
	}

	public void setBuildId (String buildId)
	{
		this.buildId = buildId;
	}

	public String getBuildId ()
	{
		return this.buildId;
	}

	public void setSvnRepository (String svnRepository)
	{
		this.svnRepository = svnRepository;
	}

	public String getSvnRepository ()
	{
		return this.svnRepository;
	}

	public void setSvnRevision (String svnRevision)
	{
		this.svnRevision = svnRevision;
	}

	public String getSvnRevision ()
	{
		return this.svnRevision;
	}

	public void setJavaVersion (String javaVersion)
	{
		this.javaVersion = javaVersion;
	}

	public String getJavaVersion ()
	{
		return this.javaVersion;
	}

	public void setJavaVendor (String javaVendor)
	{
		this.javaVendor = javaVendor;
	}

	public String getJavaVendor ()
	{
		return this.javaVendor;
	}

	public void setJavaHome (String javaHome)
	{
		this.javaHome = javaHome;
	}

	public String getJavaHome ()
	{
		return this.javaHome;
	}

	public void setDate (Date date)
	{
		this.date = date;
	}

	public Date getDate ()
	{
		return this.date;
	}

	public void setFullRegression (Boolean fullRegression)
	{
		this.fullRegression = fullRegression;
	}

	public Boolean getFullRegression ()
	{
		return this.fullRegression;
	}

	public void setRegressionType (Integer regressionType)
	{
		this.regressionType = regressionType;
	}

	public Integer getRegressionType ()
	{
		return this.regressionType;
	}

	public void setComments (String comments)
	{
		this.comments = comments;
	}

	public String getComments ()
	{
		return this.comments;
	}

	public void setBuildLocation (String buildLocation)
	{
		this.buildLocation = buildLocation;
	}

	public String getBuildLocation ()
	{
		return this.buildLocation;
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