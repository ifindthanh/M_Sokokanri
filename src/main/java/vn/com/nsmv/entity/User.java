package vn.com.nsmv.entity;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

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
	private String email;
	private String gender;

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


	@Column(name = "PASSWORD", length = 50, nullable = true)
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
	
}
