package vn.com.nsmv.javabean;

/**
 */
public class SortCondition
{
	private String sortField;
	private String sortOrder;

	public SortCondition(String sortField, String sortOrder)
	{
		this.sortField = sortField;
		this.sortOrder = sortOrder;
	}

	public String getSortField()
	{
		return this.sortField;
	}
	public void setSortField(String sortField)
	{
		this.sortField = sortField;
	}
	public String getSortOrder()
	{
		return this.sortOrder;
	}
	public void setSortOrder(String sortOrder)
	{
		this.sortOrder = sortOrder;
	}

}
