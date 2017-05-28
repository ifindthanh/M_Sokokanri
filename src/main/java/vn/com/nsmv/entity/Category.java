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
	@Transient
	public String getFormattedId() {
		return Utils.getFormattedId(this.id, 7);
	}
	public void setFormattedId(String formattedId) {
		this.formattedId = formattedId;
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
	
	
}
