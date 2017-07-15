package vn.com.nsmv.entity;

import java.util.Date;
import java.util.Locale;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.springframework.context.i18n.LocaleContextHolder;

import vn.com.nsmv.common.Constants;
import vn.com.nsmv.common.SokokanriException;
import vn.com.nsmv.common.Utils;
import vn.com.nsmv.i18n.SokokanriMessage;

@Entity
@Table(name = "items")
public class Item implements java.io.Serializable {
	private Long id;
	private Integer status;
	private String name;
	private String brand;
	private String description; //color and size
	private String link;
	private Double cost;
	private Integer quantity;
	private Double total;
	private Double realPrice;
	private Category category;
	private Date createdDate;
	private Date updateDate;
	private String transferId;
    private Bill bill;
    private Long approver;
    private Date approvedDate;
    private Long buyer;
    private Date boughtDate;
    private Long transported;
    private Date transportedDate;
    private Long transporterVn;
    private Date transportedVnDate;
    private Long checker;
    private Date checkedDate;
    private Long informer;
    private Date informedDate;
    private String note;
    private User user;
    private Long userId;
    private String approvalNote;
    private String buyNote;
    private Double realCost;
    private Double realQuantity;
    private Double computeCost;
    private Double computePrice;
    private String buyingCode;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID")
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	
	@Column(name = "STATUS")
    public Integer getStatus() {
        return status;
    }
    public void setStatus(Integer status) {
        this.status = status;
    }
	
	@Column(name = "NAME")
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	@Column(name = "BRAND")
	public String getBrand() {
		return brand;
	}
	public void setBrand(String brand) {
		this.brand = brand;
	}
	
	@Column(name = "DESCRIPTION")
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	
	@Column(name = "LINK")
	public String getLink() {
		return link;
	}
	public void setLink(String link) {
		this.link = link;
	}
	
	@Column(name = "COST")
	public Double getCost() {
		return cost;
	}
	public void setCost(Double cost) {
		this.cost = cost;
	}
	
	@Column(name = "QUANTITY")
	public Integer getQuantity() {
		return quantity;
	}
	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}
	
	@Column(name = "TOTAL")
	public Double getTotal() {
		return total;
	}
	public void setTotal(Double total) {
		this.total = total;
	}
	
	@Column(name = "REAL_PRICE")
	public Double getRealPrice() {
		return realPrice;
	}
	public void setRealPrice(Double realPrice) {
		this.realPrice = realPrice;
	}
	
	// in case of all property is not set => we ignore this record
	public boolean ignore() {
		return Utils.isEmpty(this.name) && Utils.isEmpty(this.brand) && Utils.isEmpty(this.brand) && Utils.isEmpty(this.description) 
				&& Utils.isEmpty(this.link) && this.cost == null && this.quantity == null;
	}
	
	@ManyToOne(fetch = FetchType.EAGER )
	@JoinColumn(name = "CATEGORYID", nullable = false)
	public Category getCategory() {
		return category;
	}
	public void setCategory(Category category) {
		this.category = category;
	}
	
	@Column(name = "CREATE_DATE")
	public Date getCreatedDate() {
		return createdDate;
	}
	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}
	
	@Column(name = "UPDATE_DATE")
	public Date getUpdateDate() {
		return updateDate;
	}
	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}
	
	@Column(name = "TRANSFER_ID")
    public String getTransferId() {
        return transferId;
    }
    public void setTransferId(String transferId) {
        this.transferId = transferId;
    }
    
    @ManyToOne(fetch = FetchType.EAGER )
    @JoinColumn(name = "BILL_ID", nullable = true)
    public Bill getBill() {
        return bill;
    }
    public void setBill(Bill bill) {
        this.bill = bill;
    }

    @Transient
    public String getFormattedId() {
        return Utils.getFormattedId(this.id, 7);
    }
    
    
    @Column(name = "APPROVER")
    public Long getApprover() {
        return approver;
    }
    public void setApprover(Long approver) {
        this.approver = approver;
    }
    
    @Column(name = "APPROVED_DATE")
    public Date getApprovedDate() {
        return approvedDate;
    }
    public void setApprovedDate(Date approvedDate) {
        this.approvedDate = approvedDate;
    }
    
    @Column(name = "BUYER")
    public Long getBuyer() {
        return buyer;
    }
    public void setBuyer(Long buyer) {
        this.buyer = buyer;
    }
    
    @Column(name = "BOUGHT_DATE")
    public Date getBoughtDate() {
        return boughtDate;
    }
    public void setBoughtDate(Date boughtDate) {
        this.boughtDate = boughtDate;
    }
    
    @Column(name = "TRANSPORTER")
    public Long getTransported() {
        return transported;
    }
    public void setTransported(Long transported) {
        this.transported = transported;
    }
    
    @Column(name = "TRANSPORTED_DATE")
    public Date getTransportedDate() {
        return transportedDate;
    }
    public void setTransportedDate(Date transportedDate) {
        this.transportedDate = transportedDate;
    }
    
    @Column(name = "TRANSPORTER_VN")
    public Long getTransporterVn() {
        return transporterVn;
    }
    public void setTransporterVn(Long transporterVn) {
        this.transporterVn = transporterVn;
    }
    
    @Column(name = "TRANSPORTED_VN_DATE")
    public Date getTransportedVnDate() {
        return transportedVnDate;
    }
    public void setTransportedVnDate(Date transportedVnDate) {
        this.transportedVnDate = transportedVnDate;
    }
    
    @Column(name = "CHECKER")
    public Long getChecker() {
        return checker;
    }
    public void setChecker(Long checker) {
        this.checker = checker;
    }
    
    @Column(name = "CHECKED_DATE")
    public Date getCheckedDate() {
        return checkedDate;
    }
    public void setCheckedDate(Date checkedDate) {
        this.checkedDate = checkedDate;
    }
    
    @Column(name = "INFORMER")
    public Long getInformer() {
        return informer;
    }
    public void setInformer(Long informer) {
        this.informer = informer;
    }
    
    @Column(name = "INFORMED_DATE")
    public Date getInformedDate() {
        return informedDate;
    }
    public void setInformedDate(Date informedDate) {
        this.informedDate = informedDate;
    }
	
    @Column(name = "NOTE")
    public String getNote() {
        return note;
    }
    public void setNote(String note) {
        this.note = note;
    }
    
    @Transient
    public Long getUserId() {
        return userId;
    }
    public void setUserId(Long userId) {
        this.userId = userId;
    }
    
    @ManyToOne(fetch = FetchType.EAGER )
    @JoinColumn(name = "USER_ID", nullable = false)
    public User getUser() {
        return user;
    }
    public void setUser(User user) {
        this.user = user;
    }
    
    
    @Column(name = "APPROVAL_NOTE")
    public String getApprovalNote() {
        return approvalNote;
    }
    
    public void setApprovalNote(String warnNote) {
        this.approvalNote = warnNote;
    }
    
    
    @Column(name = "BUY_NOTE")
    public String getBuyNote() {
        return buyNote;
    }
    
    public void setBuyNote(String buyNote) {
        this.buyNote = buyNote;
    }
    
    @Column(name = "REAL_COST")
    public Double getRealCost() {
        return realCost;
    }
    
    public void setRealCost(Double realCost) {
        this.realCost = realCost;
    }
    
    @Column(name = "REAL_QUANTITY")
    public Double getRealQuantity() {
        return realQuantity;
    }
    
    public void setRealQuantity(Double realQuantity) {
        this.realQuantity = realQuantity;
    }
    
    @Column(name = "COMPUTE_COST")
    public Double getComputeCost() {
        return computeCost;
    }
    
    public void setComputeCost(Double computeCost) {
        this.computeCost = computeCost;
    }
    
    @Column(name = "COMPUTE_PRICE")
    public Double getComputePrice() {
        return computePrice;
    }
    
    public void setComputePrice(Double computePrice) {
        this.computePrice = computePrice;
    }
    
    @Column(name = "BUYING_CODE")    
    public String getBuyingCode() {
        return buyingCode;
    }
    
    public void setBuyingCode(String buyingCode) {
        this.buyingCode = buyingCode;
    }
    
    
    public void validate() throws SokokanriException{
		if (Utils.isEmpty(this.name) && Utils.isEmpty(this.link) 
				&& this.quantity == null && this.cost == null) {
			return;
		}
		Locale locale = LocaleContextHolder.getLocale();
		if (Utils.isEmpty(this.name)) {
            throw new SokokanriException(SokokanriMessage.getMessageErrorNameCannotBeEmpty(locale));
		}
		
		if (Utils.isEmpty(this.link)) {
		    throw new SokokanriException(SokokanriMessage.getMessageErrorLinkCannotBeEmpty(locale));
		}
		
		if (this.quantity == null) {
		    throw new SokokanriException(SokokanriMessage.getMessageErrorQuantityCannotBeEmpty(locale));
		}
		
		if (this.quantity < 1) {
		    throw new SokokanriException(SokokanriMessage.getMessageErrorQuantityMustGtZero(locale));
		}
		
		if (this.cost == null) {
		    throw new SokokanriException(SokokanriMessage.getMessageErrorCostCannotBeEmpty(locale));
		}
		
		if (this.cost <= 0) {
		    throw new SokokanriException(SokokanriMessage.getMessageErrorCostMustGtZero(locale));
		}
	}
	
	@Transient
	public boolean isReadonly(){
	    if (Utils.hasRole(Constants.ROLE_A)) {
	        return false;
	    }
	    
	    if ((Utils.hasRole(Constants.ROLE_C) || Utils.hasRole(Constants.ROLE_U)) && (this.status == 0 || this.status == -1)) {
	        return false;
	    }
	    
	    if (Utils.hasRole(Constants.ROLE_B)  && this.status == 1) {
            return false;
        }
	    
	    return true;
	}
}
