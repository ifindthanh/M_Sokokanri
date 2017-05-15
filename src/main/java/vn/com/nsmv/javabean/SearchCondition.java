package vn.com.nsmv.javabean;

import java.util.Map;

import vn.com.nsmv.common.Utils;

/**
 */
public final class SearchCondition
{
	private String brand;
	private Integer status;
	private String userId;
	
	public String getBrand() {
		return brand;
	}

	public void setBrand(String brand) {
		this.brand = brand;
	}
	
	
	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public StringBuilder getSearching(SearchCondition searchCondition, Map<String,Object> params)
	{
		StringBuilder searching = new StringBuilder();
		if (!Utils.isEmpty(this.brand))
		{
			searching.append(" and brand like %:brand%");
			params.put("brand", this.brand);
		}
		
		if (this.userId != null) {
			searching.append(" and user_id like %:userId%");
			params.put("userId", this.userId);
		}
		
		if (this.status != null) {
			searching.append(" and status like %:status%");
			params.put("status", this.status);
		}
		
		return searching;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}
	
	

}
