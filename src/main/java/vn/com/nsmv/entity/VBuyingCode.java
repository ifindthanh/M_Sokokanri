package vn.com.nsmv.entity;

import javax.persistence.*;

/**
 */

@Entity
@Table(name = "buying_code")
public class VBuyingCode implements java.io.Serializable
{

	private String buyingCode;
	private Integer status;
	private Long userId;

	public VBuyingCode()
	{
	}

	public VBuyingCode(String buyingCode)
	{
		this.buyingCode = buyingCode;
	}



    @Column(name = "STATUS", length = 45)
    public Integer getStatus() {
        return status;
    }

    
    @Id
    @Column(name = "BUYING_CODE", length = 45)
    public String getBuyingCode() {
        return buyingCode;
    }

    
    public void setBuyingCode(String buyingCode) {
        this.buyingCode = buyingCode;
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
