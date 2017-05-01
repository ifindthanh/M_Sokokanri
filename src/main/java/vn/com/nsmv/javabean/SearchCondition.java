package vn.com.nsmv.javabean;

import java.text.*;
import java.util.*;

import vn.com.nsmv.common.*;

/**
 */
public final class SearchCondition
{
	private String sojoNo;
	private String szNo;
	private String buzaiKigo;
	private String zaisitu;
	private String kakoMongon;
	private String dansunMongon;
	private String nagasa;
	private String dateStart;
	private String dateEnd;
	private boolean includeExportedItems = true; // by default, this option is not used in searching and displaying, it is used in exporting only. We do not set value of this property in other cases.
	private String sokoCd;

	public String getSojoNo()
	{
		return sojoNo;
	}
	public void setSojoNo(String sojoNo)
	{
		this.sojoNo = sojoNo;
	}
	public String getSzNo()
	{
		return szNo;
	}
	public void setSzNo(String szNo)
	{
		this.szNo = szNo;
	}
	public String getBuzaiKigo()
	{
		return buzaiKigo;
	}
	public void setBuzaiKigo(String buzaiKigo)
	{
		this.buzaiKigo = buzaiKigo;
	}
	public String getZaisitu()
	{
		return zaisitu;
	}
	public void setZaisitu(String zaisitu)
	{
		this.zaisitu = zaisitu;
	}
	public String getKakoMongon()
	{
		return kakoMongon;
	}
	public void setKakoMongon(String kakoMongon)
	{
		this.kakoMongon = kakoMongon;
	}
	public String getDansunMongon()
	{
		return dansunMongon;
	}
	public void setDansunMongon(String dansunMongon)
	{
		this.dansunMongon = dansunMongon;
	}
	public String getNagasa()
	{
		return nagasa;
	}
	public void setNagasa(String nagasa)
	{
		this.nagasa = nagasa;
	}
	public String getDateStart()
	{
		return dateStart;
	}
	public void setDateStart(String dateStart)
	{
		this.dateStart = dateStart;
	}
	public String getDateEnd()
	{
		return dateEnd;
	}
	public void setDateEnd(String dateEnd)
	{
		this.dateEnd = dateEnd;
	}
	public Date getDateStartDate()
	{
		SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd");
		if (!Utils.isEmpty(dateStart))
		{
			try
			{
				Date dtStart = format.parse(dateStart);
				return dtStart;
			}
			catch (ParseException e)
			{
				return null;
			}
		}
		return null;
	}
	public Date getDateEndDate()
	{
		SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd");
		if (!Utils.isEmpty(dateEnd))
		{
			try
			{
				Date dtEnd = format.parse(dateEnd);
				return dtEnd;
			}
			catch (ParseException e)
			{
				return null;
			}
		}
		return null;
	}
	public boolean isIncludeExportedItems()
	{
		return this.includeExportedItems;
	}
	public void setIncludeExportedItems(boolean includeExportedItems)
	{
		this.includeExportedItems = includeExportedItems;
	}
	public String getSokoCd()
	{
		return this.sokoCd;
	}
	public void setSokoCd(String sokoCd)
	{
		this.sokoCd = sokoCd;
	}

}
