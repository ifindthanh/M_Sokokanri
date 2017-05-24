package vn.com.nsmv.javabean;

import java.util.Map;

import vn.com.nsmv.common.Utils;

/**
 */
public final class SearchCondition
{
	private String brand;
	private Integer status;
	private Long userId;
	private String customerName; 
	
	
	public SearchCondition() {
		
	}
	
	public SearchCondition(Integer status) {
		this.status = status;
	}
	
	public String getBrand() {
		return brand;
	}

	public void setBrand(String brand) {
		this.brand = brand;
	}
	
	
	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId2) {
		this.userId = userId2;
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
			searching.append(" and user_id = :userId");
			params.put("userId", this.userId);
		}
		
		if (this.status != null) {
			if (this.status == 0) {
				searching.append(" and (status = :status or status = -1)");
			} else if (this.status == 1) {
				searching.append(" and (status = :status or status = -2)");
			} else {
				searching.append(" and status = :status");
			}
			params.put("status", this.status);
		}
		
		if (Utils.isEmpty(this.customerName)) {
			// TODO join to user table
		}
		
		return searching;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}
	
	

}
