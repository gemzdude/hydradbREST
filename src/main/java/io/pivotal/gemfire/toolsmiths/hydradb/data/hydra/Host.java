package io.pivotal.gemfire.toolsmiths.hydradb.data.hydra;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.springframework.data.domain.Persistable;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * This class is generated by Spring Data Jdbc code generator.
 *
 * @author Spring Data Jdbc Code Generator
 */
@Entity
@Table( name = "HOST" )
public class Host implements Persistable<Integer>
{

	private static final long serialVersionUID = 1L;

	@Id
	private Integer id;

	private String name;

	private String osType;

	private String osInfo;

	private transient boolean persisted;


	public Host ()
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

	public void setName (String name)
	{
		this.name = name;
	}

	public String getName ()
	{
		return this.name;
	}

	public void setOsType (String osType)
	{
		this.osType = osType;
	}

	public String getOsType ()
	{
		return this.osType;
	}

	public void setOsInfo (String osInfo)
	{
		this.osInfo = osInfo;
	}

	public String getOsInfo ()
	{
		return this.osInfo;
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