package vn.com.nsmv.entity;

import javax.persistence.*;

/**
 */

@Entity
@Table(name = "brand")
public class VBrand implements java.io.Serializable
{

	private String brand;
	private Integer status;
	private Long userId;

	public VBrand()
	{
	}

	public VBrand(String brand)
	{
		this.brand = brand;
	}


	@Id
	@Column(name = "BRAND", length = 45)
	public String getBrand() {
        return brand;
    }
    
    public void setBrand(String brand) {
        this.brand = brand;
    }

    @Column(name = "STATUS", length = 45)
    public Integer getStatus() {
        return status;
    }

    
    public void setStatus(Integer status) {
        this.status = status;
    }

    @Column(name = "USER_ID", length = 45)
    public Long getUserId() {
        return userId;
    }

    
    public void setUserId(Long userId) {
        this.userId = userId;
    }

    
}
