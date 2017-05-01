package vn.com.nsmv.javabean;

import java.util.*;

public class UserBean
{
	private Long id;
	private String userCd;
	private String password;
	private String confirmPassword;
	private String oldPassword;
	private String authority;
	private String firstName;
	private String lastName;
	private String gender;
	private String companyName;
	private String title;
	private String dept;
	private String address;
	private String phone;
	private String email;
	private String status;
	private String deleteFlg;
	private Date createDate;
	private Date updateDate;
	private List<String> messages;
	private String txtSearch;

	public String getTxtSearch()
	{
		return txtSearch;
	}

	public void setTxtSearch(String txtSearch)
	{
		this.txtSearch = txtSearch;
	}

	public Long getId()
	{
		return id;
	}

	public void setId(Long id)
	{
		this.id = id;
	}

	public String getUserCd()
	{
		return userCd;
	}

	public void setUserCd(String userCd)
	{
		this.userCd = userCd;
	}

	public String getPassword()
	{
		return password;
	}

	public void setPassword(String password)
	{
		this.password = password;
	}

	public String getAuthority()
	{
		return authority;
	}

	public void setAuthority(String authority)
	{
		this.authority = authority;
	}

	public String getFirstName()
	{
		return firstName;
	}

	public void setFirstName(String firstName)
	{
		this.firstName = firstName;
	}

	public String getLastName()
	{
		return lastName;
	}

	public void setLastName(String lastName)
	{
		this.lastName = lastName;
	}

	public String getGender()
	{
		return gender;
	}

	public void setGender(String gender)
	{
		this.gender = gender;
	}

	public String getCompanyName()
	{
		return companyName;
	}

	public void setCompanyName(String companyName)
	{
		this.companyName = companyName;
	}

	public String getTitle()
	{
		return title;
	}

	public void setTitle(String title)
	{
		this.title = title;
	}

	public String getDept()
	{
		return dept;
	}

	public void setDept(String dept)
	{
		this.dept = dept;
	}

	public String getAddress()
	{
		return address;
	}

	public void setAddress(String address)
	{
		this.address = address;
	}

	public String getPhone()
	{
		return phone;
	}

	public void setPhone(String phone)
	{
		this.phone = phone;
	}

	public String getEmail()
	{
		return email;
	}

	public void setEmail(String email)
	{
		this.email = email;
	}

	public String getStatus()
	{
		return status;
	}

	public void setStatus(String status)
	{
		this.status = status;
	}

	public String getDeleteFlg()
	{
		return deleteFlg;
	}

	public void setDeleteFlg(String deleteFlg)
	{
		this.deleteFlg = deleteFlg;
	}

	public Date getCreateDate()
	{
		return createDate;
	}

	public void setCreateDate(Date createDate)
	{
		this.createDate = createDate;
	}

	public Date getUpdateDate()
	{
		return updateDate;
	}

	public void setUpdateDate(Date updateDate)
	{
		this.updateDate = updateDate;
	}

	public String getOldPassword()
	{
		return oldPassword;
	}

	public void setOldPassword(String oldPassword)
	{
		this.oldPassword = oldPassword;
	}

	public String getConfirmPassword()
	{
		return confirmPassword;
	}

	public void setConfirmPassword(String cofirmPassword)
	{
		this.confirmPassword = cofirmPassword;
	}

	public List<String> getMessages()
	{
		return messages;
	}

	public void setMessages(List<String> messages)
	{
		this.messages = messages;
	}

}
