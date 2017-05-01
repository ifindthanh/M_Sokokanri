/*
 * Copyright Orchestra Networks 2000-2010. All rights reserved.
 */
package vn.com.nsmv.entity;

import javax.persistence.*;

/**
 */
@Embeddable
public class NyukoId implements java.io.Serializable
{

	private String szNo;
	private String sokoCd;

	public NyukoId()
	{
	}

	public NyukoId(String szNo, String sokoCd)
	{
		this.szNo = szNo;
		this.sokoCd = sokoCd;
	}

	@Column(name = "SZ_NO", length = 15, nullable = false)
	public String getSzNo()
	{
		return this.szNo;
	}

	public void setSzNo(String szNo)
	{
		this.szNo = szNo;
	}

	@Column(name = "SOKO_CD", length = 4, nullable = false)
	public String getSokoCd()
	{
		return this.sokoCd;
	}

	public void setSokoCd(String sokoCd)
	{
		this.sokoCd = sokoCd;
	}

}
