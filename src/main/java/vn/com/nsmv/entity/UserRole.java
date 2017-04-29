package vn.com.nsmv.entity;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "user_role")
public class UserRole {

	private UserRoleID id;
	
	@EmbeddedId
	@AttributeOverrides({
		@AttributeOverride(name = "userID", column = @Column(name = "USER_ID", nullable = false, length = 45)),
		@AttributeOverride(name = "roleID", column = @Column(name = "ROLE_ID", nullable = false, length = 15)) })
	public UserRoleID getId()
	{
		return this.id;
	}
	
	public void setId(UserRoleID id)
	{
		this.id = id;
	}
	public UserRole() {
	}

}
