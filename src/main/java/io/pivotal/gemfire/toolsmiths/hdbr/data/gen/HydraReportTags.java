package io.pivotal.gemfire.toolsmiths.hdbr.data.gen;

import org.springframework.data.domain.Persistable;
import org.apache.commons.lang.builder.ToStringBuilder;

/**
 * This class is generated by Spring Data Jdbc code generator.
 *
 * @author Spring Data Jdbc Code Generator
 */
public class HydraReportTags implements Persistable<Integer>
{

	private static final long serialVersionUID = 1L;

	private Integer id;

	private String tagName;

	private String tagValue;

	private Boolean active;

	private String displayText;

	private Integer priority;

	private transient boolean persisted;


	public HydraReportTags ()
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

	public void setTagName (String tagName)
	{
		this.tagName = tagName;
	}

	public String getTagName ()
	{
		return this.tagName;
	}

	public void setTagValue (String tagValue)
	{
		this.tagValue = tagValue;
	}

	public String getTagValue ()
	{
		return this.tagValue;
	}

	public void setActive (Boolean active)
	{
		this.active = active;
	}

	public Boolean getActive ()
	{
		return this.active;
	}

	public void setDisplayText (String displayText)
	{
		this.displayText = displayText;
	}

	public String getDisplayText ()
	{
		return this.displayText;
	}

	public void setPriority (Integer priority)
	{
		this.priority = priority;
	}

	public Integer getPriority ()
	{
		return this.priority;
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