package vn.com.nsmv.taglib;

import java.io.*;
import java.util.*;

import javax.servlet.jsp.*;

import vn.com.nsmv.entity.*;

/**
 */
public class ExportedHeaderCheckbox extends AbstractTaglib
{
	private Set<ShukkaHeadId> selectedHeader;
	private ShukkaHeadId shukkaHeadId;
	protected void printUI(JspWriter writer) throws IOException
	{
		boolean checkContains = this.selectedHeader.contains(shukkaHeadId);
		writer.write(
			"<input type=\"checkbox\" class=\"items\" sojoNo=\"" + shukkaHeadId.getSojoNo() + "\""
				+ " sagyoDate=\"" + shukkaHeadId.getSagyoDate()
				+ "\" onchange=\"selectItem(this)\"");
		if (checkContains)
		{
			writer.write(" checked ");
		}
		writer.write("/>");
	}
	public Set<ShukkaHeadId> getSelectedHeader()
	{
		return this.selectedHeader;
	}
	public void setSelectedHeader(Set<ShukkaHeadId> selectedHeader)
	{
		this.selectedHeader = selectedHeader;
	}
	public ShukkaHeadId getShukkaHeadId()
	{
		return this.shukkaHeadId;
	}
	public void setShukkaHeadId(ShukkaHeadId shukkaHeadId)
	{
		this.shukkaHeadId = shukkaHeadId;
	}

}
