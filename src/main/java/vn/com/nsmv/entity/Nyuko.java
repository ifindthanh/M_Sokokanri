package vn.com.nsmv.entity;

import java.util.*;

import javax.persistence.*;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.hibernate.annotations.*;

/**
 */

@Entity
@Table(name = "nyuko")
public class Nyuko implements java.io.Serializable
{
	private static final long serialVersionUID = 1L;
	private NyukoId id;
	private String sojoNo;
	private String seq;
	private String bkn09;
	private String buzaiKigo;
	private String zaisitu;
	private String dansunMongon;
	private int nagasa;
	private int zaikoInzu;
	private String nagasaTani;
	private String kakoMongon;
	private String priBkn09;
	private String priBuzaiKigo;
	private String priDansunMongon;
	private String yobi;
	private Date sagyoDate;
	private Date nykoDate;
	private int status;
	private int deleteFlg;
	private Date createDate;
	private Date updateDate;
	private Integer exportedFlg;

	@EmbeddedId

	@AttributeOverrides({
			@AttributeOverride(name = "szNo", column = @Column(name = "SZ_NO", nullable = false, length = 8)),
			@AttributeOverride(name = "sokoCd", column = @Column(name = "SOKO_CD", nullable = false, length = 9)) })
	public NyukoId getId()
	{
		return this.id;
	}

	public void setId(NyukoId id)
	{
		this.id = id;
	}

	@Column(name = "SOJO_NO", length = 9, nullable = false)
	public String getSojoNo()
	{
		return sojoNo;
	}

	public void setSojoNo(String sojoNo)
	{
		this.sojoNo = sojoNo;
	}
	@Column(name = "SEQ", length = 2, nullable = true)
	public String getSeq()
	{
		return seq;
	}

	public void setSeq(String seq)
	{
		this.seq = seq;
	}

	@Column(name = "BKN09", length = 10, nullable = true)
	public String getBkn09()
	{
		return this.bkn09;
	}

	public void setBkn09(String bkn09)
	{
		this.bkn09 = bkn09;
	}

	@Column(name = "BUZAI_KIGO", length = 12)
	public String getBuzaiKigo()
	{
		return this.buzaiKigo;
	}

	public void setBuzaiKigo(String buzaiKigo)
	{
		this.buzaiKigo = buzaiKigo;
	}
	@Column(name = "ZAISITU", length = 12, nullable = true)
	public String getZaisitu()
	{
		return zaisitu;
	}

	public void setZaisitu(String zaisitu)
	{
		this.zaisitu = zaisitu;
	}
	@Column(name = "DANSUN_MONGON", length = 20, nullable = true)
	public String getDansunMongon()
	{
		return dansunMongon;
	}

	public void setDansunMongon(String dansunMongon)
	{
		this.dansunMongon = dansunMongon;
	}
	@Column(name = "NAGASA", length = 5, nullable = true)
	public int getNagasa()
	{
		return nagasa;
	}

	public void setNagasa(int nagasa)
	{
		this.nagasa = nagasa;
	}
	@Column(name = "ZAIKO_INZU", length = 5, nullable = true)
	public int getZaikoInzu()
	{
		return zaikoInzu;
	}

	public void setZaikoInzu(int zaikoInzu)
	{
		this.zaikoInzu = zaikoInzu;
	}
	@Column(name = "NAGASA_TANI", length = 1, nullable = true)
	public String getNagasaTani()
	{
		return nagasaTani;
	}

	public void setNagasaTani(String nagasaTani)
	{
		this.nagasaTani = nagasaTani;
	}
	@Column(name = "KAKO_MONGON", length = 30, nullable = true)
	public String getKakoMongon()
	{
		return kakoMongon;
	}

	public void setKakoMongon(String kakoMongon)
	{
		this.kakoMongon = kakoMongon;
	}

	@Column(name = "PRI_BKN09", length = 10)
	public String getPriBkn09()
	{
		return this.priBkn09;
	}

	public void setPriBkn09(String priBkn09)
	{
		this.priBkn09 = priBkn09;
	}

	@Column(name = "PRI_BUZAI_KIGO", length = 12)
	public String getPriBuzaiKigo()
	{
		return this.priBuzaiKigo;
	}

	public void setPriBuzaiKigo(String priBuzaiKigo)
	{
		this.priBuzaiKigo = priBuzaiKigo;
	}

	@Column(name = "PRI_DANSUN_MONGON", length = 20)
	public String getPriDansunMongon()
	{
		return this.priDansunMongon;
	}

	public void setPriDansunMongon(String priDansunMongon)
	{
		this.priDansunMongon = priDansunMongon;
	}

	@Column(name = "YOBI", length = 65, nullable = true)
	public String getYobi()
	{
		return yobi;
	}

	public void setYobi(String yobi)
	{
		this.yobi = yobi;
	}
	@Column(name = "SAGYO_DATE", nullable = true)
	public Date getSagyoDate()
	{
		return sagyoDate;
	}

	public void setSagyoDate(Date sagyoDate)
	{
		this.sagyoDate = sagyoDate;
	}
	@Column(name = "NYKO_DATE", nullable = true)
	public Date getNykoDate()
	{
		return nykoDate;
	}

	public void setNykoDate(Date nykoDate)
	{
		this.nykoDate = nykoDate;
	}
	@Column(name = "STATUS", length = 1, nullable = true)
	public int getStatus()
	{
		return status;
	}

	public void setStatus(int status)
	{
		this.status = status;
	}
	@Column(name = "DELETE_FLG", length = 1, nullable = true)
	public int getDeleteFlg()
	{
		return deleteFlg;
	}

	public void setDeleteFlg(int deleteFlg)
	{
		this.deleteFlg = deleteFlg;
	}
	//	@Generated(GenerationTime.INSERT)
	@Column(name = "CREATE_DATE", nullable = true/*, insertable = true, updatable = false*/)
	public Date getCreateDate()
	{
		return createDate;
	}

	public void setCreateDate(Date createDate)
	{
		this.createDate = createDate;
	}

	//	@Generated(GenerationTime.ALWAYS)
	@Column(name = "UPDATE_DATE", nullable = true)
	public Date getUpdateDate()
	{
		return updateDate;
	}

	public void setUpdateDate(Date updateDate)
	{
		this.updateDate = updateDate;
	}

	@Generated(GenerationTime.INSERT)
	@Column(name = "EXPORTED_FLG", length = 1, nullable = true)
	public Integer getExportedFlg()
	{
		return this.exportedFlg;
	}

	public void setExportedFlg(Integer exportedFlg)
	{
		this.exportedFlg = exportedFlg;
	}

	public boolean existInList(List<Nyuko> nyukos)
	{
		for (Nyuko item : nyukos)
		{
			if (item.getId().getSzNo().equals(this.id.getSzNo()))
			{
				return true;
			}
		}
		return false;
	}
}
