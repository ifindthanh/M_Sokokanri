package vn.com.nsmv.javabean;

import java.util.*;

/**
 */
public class ExportParameter
{
	private String sagyoDate;
	private String sojoNo;
	private List<ExportItem> items;

	public ExportParameter()
	{
	}

	public ExportParameter(String sagyoDate, String sojoNo, List<ExportItem> items)
	{
		this.sagyoDate = sagyoDate;
		this.sojoNo = sojoNo;
		this.items = items;
	}
	public String getSagyoDate()
	{
		return this.sagyoDate;
	}
	public void setSagyoDate(String sagyoDate)
	{
		this.sagyoDate = sagyoDate;
	}
	public String getSojoNo()
	{
		return this.sojoNo;
	}
	public void setSojoNo(String sojoNo)
	{
		this.sojoNo = sojoNo;
	}
	public List<ExportItem> getItems()
	{
		return this.items;
	}
	public void setItems(List<ExportItem> items)
	{
		this.items = items;
	}

}