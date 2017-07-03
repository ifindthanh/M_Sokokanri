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

import org.springframework.security.core.context.SecurityContextHolder;

import vn.com.nsmv.bean.CustomUser;

@Entity
@Table(name = "item_history")
public class ItemHistory {
    private Long historyId;
    private Long id;
    private String name;
    private String brand;
    private String description; //color and size
    private String link;
    private Double cost;
    private Integer quantity;
    private Double total;
    private User updateBy;
    private Date updateDate;
    
    public ItemHistory(Item item) {
        this.id = item.getId();
        this.name = item.getName();
        this.brand = item.getBrand();
        this.description = item.getDescription();
        this.link = item.getLink();
        this.cost = item.getCost();
        this.quantity = item.getQuantity();
        this.total = item.getTotal();
        CustomUser customUser = (CustomUser)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = new User();
        user.setId(customUser.getUserId());
        this.updateBy = user;
        this.updateDate = new Date();
    }
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "HISTORY_ID")
    public Long getHistoryId() {
        return historyId;
    }
    
    public void setHistoryId(Long historyId) {
        this.historyId = historyId;
    }
    
    @ManyToOne(fetch = FetchType.EAGER )
    @JoinColumn(name = "UPDATE_BY", nullable = false)
    public User getUpdateBy() {
        return updateBy;
    }
    
    public void setUpdateBy(User updateBy) {
        this.updateBy = updateBy;
    }
    
    @Column(name = "UPDATE_DATE")
    public Date getUpdateDate() {
        return updateDate;
    }
    
    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }
   
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
}
