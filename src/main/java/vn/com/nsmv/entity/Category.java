package vn.com.nsmv.entity;

import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import vn.com.nsmv.common.SokokanriException;
import vn.com.nsmv.common.Utils;


@Entity
@Table(name = "category")
public class Category implements java.io.Serializable {
	private Long id;
	private User user;
	private Integer status;
	private Date createdDate;
	private Date updateDate;
	private List<Item> items;
	private String fullName;
	private String email;
	private String phone;
	private String address;
	private String city;
	private String note;
	private Long userId;
	private String formattedId;
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
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID")
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	
	@ManyToOne(fetch = FetchType.EAGER )
	@JoinColumn(name = "USER_ID", nullable = false)
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	
	@Column(name = "STATUS")
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	
	@Column(name = "CREATED_DATE")
	public Date getCreatedDate() {
		return createdDate;
	}
	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}
	
	@OneToMany(fetch = FetchType.EAGER, mappedBy = "category")
	public List<Item> getItems() {
		return this.items;
	}

	public void setItems(List<Item> items) {
		this.items = items;
	}
	
	@Column(name = "FULL_NAME")
	public String getFullName() {
		return fullName;
	}
	public void setFullName(String fullName) {
		this.fullName = fullName;
	}
	
	@Column(name = "EMAIL")
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	
	@Column(name = "PHONE")
	public String getPhone() {
		return phone;
	}
	
	public void setPhone(String phone) {
		this.phone = phone;
	}

	@Column(name = "ADDRESS")
	public String getAddress() {
		return address;
	}
	
	public void setAddress(String address) {
		this.address = address;
	}
	
	@Column(name = "CITY")
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	
	@Column(name = "NOTE")
	public String getNote() {
		return note;
	}
	public void setNote(String note) {
		this.note = note;
	}
	public void removeItem(Item item) {
		this.items.remove(item);
	}
	
	@Transient
	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
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
	
	public void setFormattedId(String formattedId) {
		this.formattedId = formattedId;
	}
	
	@Transient
	public String getFormattedId(Long anId) {
		return Utils.getFormattedId(anId, 7);
	}
	
	@Transient
	public String getOrderPrice() {
		if (this.items == null) {
			return "";
		}
		double result = 0;
		for (Item item : this.items) {
			if (item.getTotal() == null) {
				continue;
			}
			result += item.getTotal();
		}
		if (result == 0) {
			return "";
		}
		return String.format("%.4f", result);
	}
	
	@Transient
	public String getOrderRealPrice() {
		if (this.items == null) {
			return "";
		}
		double result = 0;
		for (Item item : this.items) {
			if (item.getRealPrice() == null) {
				continue;
			}
			result += item.getRealPrice();
		}
		if (result == 0) {
			return "";
		}
		return String.format("%.4f", result);
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
	
	public void validate() throws SokokanriException{
		
		if (Utils.isEmpty(this.fullName)) {
			throw new SokokanriException("Họ và tên không được để trống");
		}
		
		if (Utils.isEmpty(this.email)) {
			throw new SokokanriException("Địa chỉ email không được để trống");
		}
		
		if (Utils.isEmpty(this.phone)) {
			throw new SokokanriException("Số điện thoại không được để trống");
		}
		
		if (Utils.isEmpty(this.address)) {
			throw new SokokanriException("Địa chỉ không được để trống");
		}
	}
	
}
