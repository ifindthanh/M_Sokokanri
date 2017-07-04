package vn.com.nsmv.entity;

import javax.persistence.*;

/**
 */

@Entity
@Table(name = "TRANSFER_ID")
public class VTransferId implements java.io.Serializable
{

	private String transferId;
	private Integer status;
	private Long userId;

	public VTransferId()
	{
	}

	public VTransferId(String transferId)
	{
		this.transferId = transferId;
	}

	@Id
    @Column(name = "TRANSFER_ID", length = 45)
    public String getTransferId() {
        return transferId;
    }

    
    public void setTransferId(String transferId) {
        this.transferId = transferId;
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
