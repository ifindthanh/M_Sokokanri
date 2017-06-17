package vn.com.nsmv.javabean;

import org.springframework.web.multipart.*;

public class UploadBean
{
	private MultipartFile uploadFile;
	private String fullName;
	private String email;
	private String phone;
	private String address;
	private String city;
	private String note;

	public MultipartFile getUploadFile()
	{
		return this.uploadFile;
	}

	public void setUploadFile(MultipartFile uploadFile)
	{
		this.uploadFile = uploadFile;
	}

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

}
