package vn.com.nsmv.entity;

import java.util.Date;

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

import vn.com.nsmv.common.SokokanriException;
import vn.com.nsmv.common.Utils;

@Entity
@Table(name = "items")
public class Item implements java.io.Serializable {
	private Long id;
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
	private Boolean cannotBuy;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID")
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
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
	
	public void validate() throws SokokanriException{
		if (Utils.isEmpty(this.name) && Utils.isEmpty(this.link) 
				&& this.quantity == null && this.cost == null) {
			return;
		}
		
		if (Utils.isEmpty(this.name)) {
			throw new SokokanriException("Tên mặt hàng không được để trống");
		}
		
		if (Utils.isEmpty(this.link)) {
			throw new SokokanriException("Đường dẫn đến mặt hàng không được để trống");
		}
		
		if (this.quantity == null) {
			throw new SokokanriException("Số lượng không được để trống");
		}
		
		if (this.quantity < 1) {
			throw new SokokanriException("Số lượng phải lớn hơn 0");
		}
		
		if (this.cost == null) {
			throw new SokokanriException("Đơn giá không được để trống");
		}
		
		if (this.cost <= 0) {
			throw new SokokanriException("Đơn giá phải lớn hơn 0");
		}
	}
}
