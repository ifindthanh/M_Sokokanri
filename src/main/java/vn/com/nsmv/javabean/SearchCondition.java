package vn.com.nsmv.javabean;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import vn.com.nsmv.common.Utils;

/**
 */
public final class SearchCondition
{
	private static final SimpleDateFormat FORMAT_DATE = new SimpleDateFormat("yyyy/MM/dd");
	
	private List<String> brands = new ArrayList<String>();
	private List<String> buyingCodes = new ArrayList<String>();
	private List<String> transferIds = new ArrayList<String>();
	private List<Long> bills = new ArrayList<Long>();
	private Integer status = 999;
	private Long userId;
	private String customerName; 
	private String transferId;
	private Long billId;
	private String fromDate;
	private String toDate;
	private String fromApprovalDate;
	private String toApprovalDate;
	private String fromBoughtDate;
	private String toBoughtDate;
	private String fromTransferDate;
	private String toTransferDate;
	private String fromTransferToVNDate;
	private String toTransferToVNDate;
	private String fromCheckedVNDate;
	private String toCheckedVNDate;
	private String fromInformedDate;
	private String toInformedDate;
	
	
	public SearchCondition() {
		
	}
	
	public SearchCondition(Integer status) {
		this.status = status;
	}
	
	public List<String> getBrands() {
		return brands;
	}

	public void setBrands(List<String> brands) {
		this.brands = brands;
	}
	
    public List<String> getBuyingCodes() {
        return buyingCodes;
    }
    
    public void setBuyingCodes(List<String> buyingCodes) {
        this.buyingCodes = buyingCodes;
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
		if (this.brands != null && !this.brands.isEmpty())
		{
			searching.append(" and brand in :brands");
			params.put("brands", this.brands);
		}
		
		if (this.buyingCodes != null && !this.buyingCodes.isEmpty())
        {
            searching.append(" and buyingCode in :buyingCodes");
            params.put("buyingCodes", this.buyingCodes);
        }
		
		if (this.bills != null && !this.bills.isEmpty())
        {
            searching.append(" and billId in :bills");
            params.put("bills", this.bills);
        }
		
		if (this.userId != null) {
			searching.append(" and user_id = :userId");
			params.put("userId", this.userId);
		}
		
		if (this.status != null && this.status != 999) {
			if (this.status == 0) {
				searching.append(" and (status = :status or status = -1)");
			} else if (this.status == 1) {
				searching.append(" and (status = :status or status = -2)");
			} else {
				searching.append(" and status = :status");
			}
			params.put("status", this.status);
		} else {
			searching.append(" and status <> 8 and status <> -5");
		}
		
		if (Utils.isEmpty(this.customerName)) {
			// TODO join to user table
		}
		
		if (!Utils.isEmpty(this.transferId)) {
			searching.append(" and transferId like :transferId");
			params.put("transferId", "%" + this.transferId + "%");
		}
		
		if (this.billId != null) {
			searching.append(" and billId like :billId");
			params.put("billId", "%" + this.billId + "%");
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

	public String getTransferId() {
		return transferId;
	}

	public void setTransferId(String transferId) {
		this.transferId = transferId;
	}

	public Long getBillId() {
		return billId;
	}

	public void setBillId(Long billId) {
		this.billId = billId;
	}

	public String getFromDate() {
		return fromDate;
	}

	public void setFromDate(String fromDate) {
		this.fromDate = fromDate;
	}

	public String getToDate() {
		return toDate;
	}

	public void setToDate(String toDate) {
		this.toDate = toDate;
	}

	public String getFromApprovalDate() {
		return fromApprovalDate;
	}

	public void setFromApprovalDate(String fromApprovalDate) {
		this.fromApprovalDate = fromApprovalDate;
	}

	public String getToApprovalDate() {
		return toApprovalDate;
	}

	public void setToApprovalDate(String toApprovalDate) {
		this.toApprovalDate = toApprovalDate;
	}

	public String getFromBoughtDate() {
		return fromBoughtDate;
	}

	public void setFromBoughtDate(String fromBoughtDate) {
		this.fromBoughtDate = fromBoughtDate;
	}

	public String getToBoughtDate() {
		return toBoughtDate;
	}

	public void setToBoughtDate(String toBoughtDate) {
		this.toBoughtDate = toBoughtDate;
	}

	public String getFromTransferDate() {
		return fromTransferDate;
	}

	public void setFromTransferDate(String fromTransferDate) {
		this.fromTransferDate = fromTransferDate;
	}

	public String getToTransferDate() {
		return toTransferDate;
	}

	public void setToTransferDate(String toTransferDate) {
		this.toTransferDate = toTransferDate;
	}

	public String getFromTransferToVNDate() {
		return fromTransferToVNDate;
	}

	public void setFromTransferToVNDate(String fromTransferToVNDate) {
		this.fromTransferToVNDate = fromTransferToVNDate;
	}

	public String getToTransferToVNDate() {
		return toTransferToVNDate;
	}

	public void setToTransferToVNDate(String toTransferToVNDate) {
		this.toTransferToVNDate = toTransferToVNDate;
	}

	public String getFromCheckedVNDate() {
		return fromCheckedVNDate;
	}

	public void setFromCheckedVNDate(String fromCheckedVNDate) {
		this.fromCheckedVNDate = fromCheckedVNDate;
	}

	public String getToCheckedVNDate() {
		return toCheckedVNDate;
	}

	public void setToCheckedVNDate(String toCheckedVNDate) {
		this.toCheckedVNDate = toCheckedVNDate;
	}

	public String getFromInformedDate() {
		return fromInformedDate;
	}

	public void setFromInformedDate(String fromInformedDate) {
		this.fromInformedDate = fromInformedDate;
	}

	public String getToInformedDate() {
		return toInformedDate;
	}

	public void setToInformedDate(String toInformedDate) {
		this.toInformedDate = toInformedDate;
	}

    
    public List<String> getTransferIds() {
        return transferIds;
    }

    
    public void setTransferIds(List<String> transferIds) {
        this.transferIds = transferIds;
    }

    
    public List<Long> getBills() {
        return bills;
    }

    
    public void setBills(List<Long> bills) {
        this.bills = bills;
    }
	
    
}
