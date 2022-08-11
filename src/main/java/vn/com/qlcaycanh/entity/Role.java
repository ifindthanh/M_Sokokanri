package vn.com.qlcaycanh.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "role")
public class Role {
    private String roleId;
    private String roleName;
    
    public Role(){
        
    }
    
    public Role(String roleId){
        this.roleId = roleId;
    }
    
    
    
    public Role(String roleId, String roleName) {
        this.roleId = roleId;
        this.roleName = roleName;
    }

    @Id
    @Column(name = "id")
    public String getRoleId() {
        return roleId;
    }
    
    public void setRoleId(String roleId) {
        this.roleId = roleId;
    }
    
    @Column(name = "name")
    public String getRoleName() {
        return roleName;
    }
    
    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((roleId == null) ? 0 : roleId.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Role other = (Role) obj;
        if (roleId == null) {
            if (other.roleId != null)
                return false;
        } else if (!roleId.equals(other.roleId))
            return false;
        return true;
    }
    
    
}
