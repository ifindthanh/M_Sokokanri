package vn.com.nsmv.javabean;

import java.io.*;

/**
 */
public class ExportItem implements Serializable
{
	private String szNo;
	private String bodyId;

	public ExportItem()
	{
	}

	public ExportItem(String szNo, String bodyId)
	{
		this.szNo = szNo;
		this.bodyId = bodyId;
	}

	public String getSzNo()
	{
		return this.szNo;
	}
	public void setSzNo(String szNo)
	{
		this.szNo = szNo;
	}
	public String getBodyId()
	{
		return this.bodyId;
	}
	public void setBodyId(String bodyId)
	{
		this.bodyId = bodyId;
	}

}
