package vn.com.nsmv.javabean;

/**
 */
public class ExportingSearchCondition
{
	private String szNo;
	private String jyuoka;
	private String sojoNo;
	private String sagyoDate;
	private String sukkaYoteibi;
	private String shukkaDate;
	private String sokoCd;

	public ExportingSearchCondition()
	{
		super();
	}
	public ExportingSearchCondition(
		String szNo,
		String jyuoka,
		String sojoNo,
		String sagyoDate,
		String sukkaYoteibi,
		String shukkaDate)
	{
		super();
		this.szNo = szNo;
		this.jyuoka = jyuoka;
		this.sojoNo = sojoNo;
		this.sagyoDate = sagyoDate;
		this.sukkaYoteibi = sukkaYoteibi;
		this.shukkaDate = shukkaDate;
	}
	public String getSzNo()
	{
		return this.szNo;
	}
	public void setSzNo(String szNo)
	{
		this.szNo = szNo;
	}
	public String getJyuoka()
	{
		return this.jyuoka;
	}
	public void setJyuoka(String jyuoka)
	{
		this.jyuoka = jyuoka;
	}
	public String getSojoNo()
	{
		return this.sojoNo;
	}
	public void setSojoNo(String sojoNo)
	{
		this.sojoNo = sojoNo;
	}
	public String getSagyoDate()
	{
		return this.sagyoDate;
	}
	public void setSagyoDate(String sagyoDate)
	{
		this.sagyoDate = sagyoDate;
	}
	public String getSukkaYoteibi()
	{
		return this.sukkaYoteibi;
	}
	public void setSukkaYoteibi(String sukkaYoteibi)
	{
		this.sukkaYoteibi = sukkaYoteibi;
	}
	public String getShukkaDate()
	{
		return this.shukkaDate;
	}
	public void setShukkaDate(String shukkaDate)
	{
		this.shukkaDate = shukkaDate;
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
