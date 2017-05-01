package vn.com.nsmv.entity;

// default package
// Generated Jan 4, 2017 5:46:14 PM by Hibernate Tools 5.2.0.CR1
import java.util.*;

import javax.persistence.*;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.hibernate.annotations.*;

@Entity
@Table(name = "shukka_body")
public class ShukkaBody implements java.io.Serializable
{
	private static final long serialVersionUID = 1L;
	private Long id;
	private String sokoCd;
	private String sagyoDate;
	private String sojoNo;
	private String dataKbn;
	private String bkn09;
	private String buzaiKigo;
	private String zaisitu;
	private String dansunMongon;
	private Integer nagasa;
	private Integer zaikoInzu;
	private Integer renban;
	private String nagasaTani;
	private String kensyo;
	private String priBkn09;
	private String priBuzaiKigo;
	private String priDansunMongon;
	private String kakoMeisyo;
	private String yobi;
	private String szNo;
	private Date nykoDate;
	private Date shukkaDate;
	private Integer status;
	private Integer deleteFlg;
	private Date createDate;
	private Date updateDate;

	public ShukkaBody()
	{
	}

	public ShukkaBody(
		Long id,
		String sagyoDate,
		String sojoNo,
		String dataKbn,
		String bkn09,
		String buzaiKigo,
		String zaisitu,
		String dansunMongon,
		Integer nagasa,
		Integer zaikoInzu,
		String nagasaTani,
		String kensyo,
		String priBkn09,
		String priBuzaiKigo,
		String priDansunMongon,
		String kakoMeisyo,
		String yobi,
		String szNo,
		Date nykoDate,
		Date shukkaDate,
		Integer status,
		Integer deleteFlg,
		Date createDate,
		Date updateDate)
	{
		this.id = id;
		this.sagyoDate = sagyoDate;
		this.sojoNo = sojoNo;
		this.dataKbn = dataKbn;
		this.bkn09 = bkn09;
		this.buzaiKigo = buzaiKigo;
		this.zaisitu = zaisitu;
		this.dansunMongon = dansunMongon;
		this.nagasa = nagasa;
		this.zaikoInzu = zaikoInzu;
		this.nagasaTani = nagasaTani;
		this.kensyo = kensyo;
		this.priBkn09 = priBkn09;
		this.priBuzaiKigo = priBuzaiKigo;
		this.priDansunMongon = priDansunMongon;
		this.kakoMeisyo = kakoMeisyo;
		this.yobi = yobi;
		this.szNo = szNo;
		this.nykoDate = nykoDate;
		this.shukkaDate = shukkaDate;
		this.status = status;
		this.deleteFlg = deleteFlg;
		this.createDate = createDate;
		this.updateDate = updateDate;
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

	@Column(name = "SOKO_CD", length = 4, nullable = false)
	public String getSokoCd()
	{
		return this.sokoCd;
	}

	public void setSokoCd(String sokoCd)
	{
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

	@Column(name = "DATA_KBN", length = 2)
	public String getDataKbn()
	{
		return this.dataKbn;
	}

	public void setDataKbn(String dataKbn)
	{
		this.dataKbn = dataKbn;
	}

	@Column(name = "BKN09", length = 10)
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

	@Column(name = "ZAISITU", length = 12)
	public String getZaisitu()
	{
		return this.zaisitu;
	}

	public void setZaisitu(String zaisitu)
	{
		this.zaisitu = zaisitu;
	}

	@Column(name = "DANSUN_MONGON", length = 20)
	public String getDansunMongon()
	{
		return this.dansunMongon;
	}

	public void setDansunMongon(String dansunMongon)
	{
		this.dansunMongon = dansunMongon;
	}

	@Column(name = "NAGASA")
	public Integer getNagasa()
	{
		return this.nagasa;
	}

	public void setNagasa(Integer nagasa)
	{
		this.nagasa = nagasa;
	}

	@Column(name = "ZAIKO_INZU")
	public Integer getZaikoInzu()
	{
		return this.zaikoInzu;
	}

	public void setZaikoInzu(Integer zaikoInzu)
	{
		this.zaikoInzu = zaikoInzu;
	}

	@Column(name = "RENBAN")
	public Integer getRenban()
	{
		return this.renban;
	}

	public void setRenban(Integer renban)
	{
		this.renban = renban;
	}

	@Column(name = "NAGASA_TANI", length = 1)
	public String getNagasaTani()
	{
		return this.nagasaTani;
	}

	public void setNagasaTani(String nagasaTani)
	{
		this.nagasaTani = nagasaTani;
	}

	@Column(name = "KENSYO", length = 1)
	public String getKensyo()
	{
		return this.kensyo;
	}

	public void setKensyo(String kensyo)
	{
		this.kensyo = kensyo;
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

	@Column(name = "KAKO_MEISYO", length = 4)
	public String getKakoMeisyo()
	{
		return this.kakoMeisyo;
	}

	public void setKakoMeisyo(String kakoMeisyo)
	{
		this.kakoMeisyo = kakoMeisyo;
	}

	@Column(name = "YOBI", length = 25)
	public String getYobi()
	{
		return this.yobi;
	}

	public void setYobi(String yobi)
	{
		this.yobi = yobi;
	}

	@Column(name = "SZ_NO", length = 15)
	public String getSzNo()
	{
		return this.szNo;
	}

	public void setSzNo(String szNo)
	{
		this.szNo = szNo;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "NYKO_DATE", length = 19)
	public Date getNykoDate()
	{
		return this.nykoDate;
	}

	public void setNykoDate(Date nykoDate)
	{
		this.nykoDate = nykoDate;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "SHUKKA_DATE", length = 19)
	public Date getShukkaDate()
	{
		return this.shukkaDate;
	}

	public void setShukkaDate(Date shukkaDate)
	{
		this.shukkaDate = shukkaDate;
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

}
