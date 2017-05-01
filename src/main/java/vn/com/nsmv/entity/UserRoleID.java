package vn.com.nsmv.entity;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class UserRoleID implements java.io.Serializable {
	private Long userID;
	private String roleID;

	public UserRoleID()
	{
	}

	public UserRoleID(Long userID, String roleID)
	{
		this.userID = userID;
		this.roleID = roleID;
	}

	@Column(name = "USER_ID", length = 45, nullable = false)
	public Long getUserID()
	{
		return this.userID;
	}

	public void setUserID(Long userID)
	{
		this.userID = userID;
	}

	@Column(name = "ROLE_ID", length = 1, nullable = false)
	public String getRoleID() {
		return roleID;
	}

	public void setRoleID(String roleID) {
		this.roleID = roleID;
	}

}
