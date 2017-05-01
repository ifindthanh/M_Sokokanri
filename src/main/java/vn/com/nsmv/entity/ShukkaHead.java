package vn.com.nsmv.entity;

// Generated Jan 4, 2017 5:46:14 PM by Hibernate Tools 5.2.0.CR1
import java.util.*;

import javax.persistence.*;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.hibernate.annotations.*;

@Entity
@Table(name = "shukka_head")
public class ShukkaHead implements java.io.Serializable
{

	private ShukkaHeadId id;
	private String dataKbn;
	private Date sukkaYoteibi;
	private String jyuoka;
	private String yobi;
	private Integer status;
	private Integer deleteFlg;
	private Date createDate;
	private Date updateDate;
	private Integer exportedFlg;

	public ShukkaHead()
	{
	}

	public ShukkaHead(ShukkaHeadId id)
	{
		this.id = id;
	}

	public ShukkaHead(
		ShukkaHeadId id,
		String dataKbn,
		Date sukkaYoteibi,
		String jyuoka,
		String yobi,
		Integer status,
		Integer deleteFlg,
		Date createDate,
		Date updateDate)
	{
		this.id = id;
		this.dataKbn = dataKbn;
		this.sukkaYoteibi = sukkaYoteibi;
		this.jyuoka = jyuoka;
		this.yobi = yobi;
		this.status = status;
		this.deleteFlg = deleteFlg;
		this.createDate = createDate;
		this.updateDate = updateDate;
	}

	@EmbeddedId

	@AttributeOverrides({
			@AttributeOverride(name = "sagyoDate", column = @Column(name = "SAGYO_DATE", nullable = false, length = 8)),
			@AttributeOverride(name = "sojoNo", column = @Column(name = "SOJO_NO", nullable = false, length = 9)) })
	public ShukkaHeadId getId()
	{
		return this.id;
	}

	public void setId(ShukkaHeadId id)
	{
		this.id = id;
	}

	@Column(name = "DATA_KBN", length = 2)
	public String getDataKbn()
	{
		return this.dataKbn;
	}

	public void setDataKbn(String dataKbn)
	{
		this.dataKbn = dataKbn;
	}

	@Column(name = "SUKKA_YOTEIBI", length = 10)
	public Date getSukkaYoteibi()
	{
		return this.sukkaYoteibi;
	}

	public void setSukkaYoteibi(Date sukkaYoteibi)
	{
		this.sukkaYoteibi = sukkaYoteibi;
	}

	@Column(name = "JYUOKA", length = 12)
	public String getJyuoka()
	{
		return this.jyuoka;
	}

	public void setJyuoka(String jyuoka)
	{
		this.jyuoka = jyuoka;
	}

	@Column(name = "YOBI", length = 12)
	public String getYobi()
	{
		return this.yobi;
	}

	public void setYobi(String yobi)
	{
		this.yobi = yobi;
	}

	@Generated(GenerationTime.INSERT)
	@Column(name = "STATUS")
	public Integer getStatus()
	{
		return this.status;
	}

	public void setStatus(Integer status)
	{
		this.status = status;
	}

	@Generated(GenerationTime.INSERT)
	@Column(name = "DELETE_FLG")
	public Integer getDeleteFlg()
	{
		return this.deleteFlg;
	}

	public void setDeleteFlg(Integer deleteFlg)
	{
		this.deleteFlg = deleteFlg;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "CREATE_DATE", length = 19)
	public Date getCreateDate()
	{
		return this.createDate;
	}

	public void setCreateDate(Date createDate)
	{
		this.createDate = createDate;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "UPDATE_DATE", length = 19)
	public Date getUpdateDate()
	{
		return this.updateDate;
	}

	public void setUpdateDate(Date updateDate)
	{
		this.updateDate = updateDate;
	}

	@Column(name = "EXPORTED_FLG")
	public Integer getExportedFlg()
	{
		return this.exportedFlg;
	}

	public void setExportedFlg(Integer exportedFlg)
	{
		this.exportedFlg = exportedFlg;
	}

	@Override
	public int hashCode()
	{
		final int prime = 31;
		int result = 1;
		result = prime * result + ((this.dataKbn == null) ? 0 : this.dataKbn.hashCode());
		result = prime * result + ((this.id == null) ? 0 : this.id.hashCode());
		result = prime * result + ((this.jyuoka == null) ? 0 : this.jyuoka.hashCode());
		result = prime * result + ((this.status == null) ? 0 : this.status.hashCode());
		result = prime * result + ((this.sukkaYoteibi == null) ? 0 : this.sukkaYoteibi.hashCode());
		result = prime * result + ((this.yobi == null) ? 0 : this.yobi.hashCode());
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
		ShukkaHead other = (ShukkaHead) obj;
		if (this.dataKbn == null)
		{
			if (other.dataKbn != null)
				return false;
		}
		else if (!this.dataKbn.equals(other.dataKbn))
			return false;
		if (this.id == null)
		{
			if (other.id != null)
				return false;
		}
		else if (!this.id.equals(other.id))
			return false;
		if (this.jyuoka == null)
		{
			if (other.jyuoka != null)
				return false;
		}
		else if (!this.jyuoka.equals(other.jyuoka))
			return false;
		if (this.status == null)
		{
			if (other.status != null)
				return false;
		}
		else if (!this.status.equals(other.status))
			return false;
		if (this.sukkaYoteibi == null)
		{
			if (other.sukkaYoteibi != null)
				return false;
		}
		else if (!this.sukkaYoteibi.equals(other.sukkaYoteibi))
			return false;
		if (this.yobi == null)
		{
			if (other.yobi != null)
				return false;
		}
		else if (!this.yobi.equals(other.yobi))
			return false;
		return true;
	}

}
