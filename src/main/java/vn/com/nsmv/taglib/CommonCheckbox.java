/*
 * Copyright Orchestra Networks 2000-2010. All rights reserved.
 */
package vn.com.nsmv.taglib;

import java.io.*;

import javax.servlet.jsp.*;

import vn.com.nsmv.common.*;

/**
 */
public final class CommonCheckbox extends AbstractTaglib
{
	private String id;
	private String value;
	private boolean checked;
	private String onchange;
	protected void printUI(JspWriter writer) throws IOException
	{

		writer.append("<input type=\"checkbox\"");
		if (!Utils.isEmpty(this.id))
		{
			writer.append(" id=\"" + this.id + "\" ");
		}
		if (!Utils.isEmpty(this.value))
		{
			writer.append(" value=\"" + this.value + "\" ");
		}
		if (!Utils.isEmpty(this.onchange))
		{
			writer.append(" onchange=\"" + this.onchange + "\" ");
		}
		if (this.checked)
		{
			writer.append(" checked ");
		}
		writer.append("/>");
	}
	public String getId()
	{
		return this.id;
	}
	public void setId(String id)
	{
		this.id = id;
	}
	public String getValue()
	{
		return this.value;
	}
	public void setValue(String value)
	{
		this.value = value;
	}
	public boolean isChecked()
	{
		return this.checked;
	}
	public void setChecked(boolean checked)
	{
		this.checked = checked;
	}
	public String getOnchange()
	{
		return this.onchange;
	}
	public void setOnchange(String onchange)
	{
		this.onchange = onchange;
	}

}
