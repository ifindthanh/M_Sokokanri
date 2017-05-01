package vn.com.nsmv.entity;

// Generated Jan 4, 2017 5:46:14 PM by Hibernate Tools 5.2.0.CR1
import javax.persistence.*;

@Embeddable
public class ShukkaHeadId implements java.io.Serializable
{

	private String sagyoDate;
	private String sojoNo;
	private String sokoCd;

	public ShukkaHeadId()
	{
	}

	public ShukkaHeadId(String sagyoDate, String sojoNo, String sokoCd)
	{
		this.sagyoDate = sagyoDate;
		this.sojoNo = sojoNo;
		this.sokoCd = sokoCd;
	}

	@Column(name = "SAGYO_DATE", nullable = false, length = 8)
	public String getSagyoDate()
	{
		return this.sagyoDate;
	}

	public void setSagyoDate(String sagyoDate)
	{
		this.sagyoDate = sagyoDate;
	}

	@Column(name = "SOJO_NO", nullable = false, length = 9)
	public String getSojoNo()
	{
		return this.sojoNo;
	}

	public void setSojoNo(String sojoNo)
	{
		this.sojoNo = sojoNo;
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

	@Override
	public int hashCode()
	{
		final int prime = 31;
		int result = 1;
		result = prime * result + ((this.sagyoDate == null) ? 0 : this.sagyoDate.hashCode());
		result = prime * result + ((this.sojoNo == null) ? 0 : this.sojoNo.hashCode());
		result = prime * result + ((this.sokoCd == null) ? 0 : this.sokoCd.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj)
	{
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ShukkaHeadId other = (ShukkaHeadId) obj;
		if (this.sagyoDate == null)
		{
			if (other.sagyoDate != null)
				return false;
		}
		else if (!this.sagyoDate.equals(other.sagyoDate))
			return false;
		if (this.sojoNo == null)
		{
			if (other.sojoNo != null)
				return false;
		}
		else if (!this.sojoNo.equals(other.sojoNo))
			return false;
		if (this.sokoCd == null)
		{
			if (other.sokoCd != null)
				return false;
		}
		else if (!this.sokoCd.equals(other.sokoCd))
			return false;
		return true;
	}

}
