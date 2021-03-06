package vn.com.nsmv.entity;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

/**
 * User generated by hbm2java
 */
@Entity
@Table(name = "user")
public class User implements java.io.Serializable
{
	private static final long serialVersionUID = 1L;
	private Long id;
	private String password;
	private String fullname;
	private String phone;
	private String email;
	private String gender;
	private String resetPwTimestamp;
	private String confirmPassword;
	private String oldPassword;
	private String address;
	private List<String> roles;
	private Integer accountBalance;

	public User()
	{
	}


	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID")
	public Long getId()
	{
		return this.id;
	}

	public void setId(Long id)
	{
		this.id = id;
	}


	@Column(name = "PASSWORD", length = 450, nullable = true)
	public String getPassword()
	{
		return this.password;
	}

	public void setPassword(String password)
	{
		this.password = password;
	}

	@Column(name = "FULL_NAME", length = 45, nullable = false)
	public String getFullname() {
		return fullname;
	}


	public void setFullname(String fullname) {
		this.fullname = fullname;
	}

	@Column(name = "EMAIL", length = 45, nullable = false)
	public String getEmail() {
		return email;
	}


	public void setEmail(String email) {
		this.email = email;
	}


	@Column(name = "GENDER", length = 30, nullable = true)
	public String getGender()
	{
		return this.gender;
	}

	public void setGender(String gender)
	{
		this.gender = gender;
	}

	@Column(name = "PHONE", length = 30, nullable = true)
	public String getPhone() {
		return phone;
	}


	public void setPhone(String phone) {
		this.phone = phone;
	}


	@Column(name = "RESET_PW_TIMESTAMP", length = 45, nullable = true)
    public String getResetPwTimestamp() {
        return resetPwTimestamp;
    }
    
    public void setResetPwTimestamp(String resetPwTimestamp) {
        this.resetPwTimestamp = resetPwTimestamp;
    }

    @Transient
    public String getConfirmPassword() {
        return confirmPassword;
    }
    
    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }

    @Transient
    public String getOldPassword() {
        return oldPassword;
    }
    
    public void setOldPassword(String oldPassword) {
        this.oldPassword = oldPassword;
    }


    @Column(name = "address", length = 450, nullable = true)
    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Transient
    public List<String> getRoles() {
        return roles;
    }
    
    public void setRoles(List<String> roles) {
        this.roles = roles;
    }

    @Column(name = "account_balance", length = 45, nullable = true)
    public Integer getAccountBalance() {
        return accountBalance;
    }

    public void setAccountBalance(Integer accountBalance) {
        this.accountBalance = accountBalance;
    }
  

}
