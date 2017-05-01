package vn.com.nsmv.javabean;

import org.springframework.web.multipart.*;

public class UploadBean
{
	private MultipartFile uploadFile;

	public MultipartFile getUploadFile()
	{
		return this.uploadFile;
	}

	public void setUploadFile(MultipartFile uploadFile)
	{
		this.uploadFile = uploadFile;
	}

}
