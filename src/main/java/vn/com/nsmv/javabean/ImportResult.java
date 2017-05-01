package vn.com.nsmv.javabean;

/**
 */
public final class ImportResult
{
	private final String szNo;
	private final int status;

	public ImportResult(String szNo, int status)
	{
		this.szNo = szNo;
		this.status = status;
	}
	public String getSzNo()
	{
		return this.szNo;
	}
	public int getStatus()
	{
		return this.status;
	}

}
