package vn.com.nsmv.javabean;

import java.util.*;

import vn.com.nsmv.entity.*;

/**
 */
public class Shukka
{
	private Long bodyId;
	private String sagyoDate;
	private String sojoNo;
	private String dataKbn;
	private String bkn09;
	private String buzaiKigo;
	private String zaisitu;
	private String dansunMongon;
	private Integer nagasa;
	private Integer zaikoInzu;
	private String nagasaTani;
	private String kensyo;
	private String priBkn09;
	private String priBuzaiKigo;
	private String priDansunMongon;
	private String kakoMeisyo;
	private String szNo;
	private Date nykoDate;
	private Integer status;
	private Integer deleteFlg;
	private Date createDate;
	private Date updateDate;
	private ShukkaHeadId headId;
	private Date sukkaYoteibi;
	private String jyuoka;
	private String yobi;
	public Shukka()
	{
	}
	public Shukka(ShukkaHead head, ShukkaBody body)
	{
		this.bodyId = body.getId();
		this.sagyoDate = body.getSagyoDate();
		this.sojoNo = body.getSojoNo();
		this.dataKbn = body.getDataKbn();
		this.bkn09 = body.getBkn09();
		this.buzaiKigo = body.getBuzaiKigo();
		this.zaisitu = body.getZaisitu();
		this.dansunMongon = body.getDansunMongon();
		this.nagasa = body.getNagasa();
		this.zaikoInzu = body.getZaikoInzu();
		this.nagasaTani = body.getNagasaTani();
		this.kensyo = body.getKensyo();
		this.priBkn09 = body.getPriBkn09();
		this.priBuzaiKigo = body.getPriBuzaiKigo();
		this.priDansunMongon = body.getPriDansunMongon();
		this.kakoMeisyo = body.getKakoMeisyo();
		this.szNo = body.getSzNo();
		this.nykoDate = body.getNykoDate();
		this.status = body.getStatus();
		this.deleteFlg = body.getDeleteFlg();
		this.createDate = body.getCreateDate();
		this.updateDate = body.getUpdateDate();
		this.headId = head.getId();
		this.sukkaYoteibi = head.getSukkaYoteibi();
		this.jyuoka = head.getJyuoka();
		this.yobi = body.getYobi();
	}

	public Long getBodyId()
	{
		return this.bodyId;
	}
	public void setBodyId(Long bodyId)
	{
		this.bodyId = bodyId;
	}
	public String getSagyoDate()
	{
		return this.sagyoDate;
	}
	public void setSagyoDate(String sagyoDate)
	{
		this.sagyoDate = sagyoDate;
	}
	public String getSojoNo()
	{
		return this.sojoNo;
	}
	public void setSojoNo(String sojoNo)
	{
		this.sojoNo = sojoNo;
	}
	public String getDataKbn()
	{
		return this.dataKbn;
	}
	public void setDataKbn(String dataKbn)
	{
		this.dataKbn = dataKbn;
	}
	public String getBkn09()
	{
		return this.bkn09;
	}
	public void setBkn09(String bkn09)
	{
		this.bkn09 = bkn09;
	}
	public String getBuzaiKigo()
	{
		return this.buzaiKigo;
	}
	public void setBuzaiKigo(String buzaiKigo)
	{
		this.buzaiKigo = buzaiKigo;
	}
	public String getZaisitu()
	{
		return this.zaisitu;
	}
	public void setZaisitu(String zaisitu)
	{
		this.zaisitu = zaisitu;
	}
	public String getDansunMongon()
	{
		return this.dansunMongon;
	}
	public void setDansunMongon(String dansunMongon)
	{
		this.dansunMongon = dansunMongon;
	}
	public Integer getNagasa()
	{
		return this.nagasa;
	}
	public void setNagasa(Integer nagasa)
	{
		this.nagasa = nagasa;
	}
	public Integer getZaikoInzu()
	{
		return this.zaikoInzu;
	}
	public void setZaikoInzu(Integer zaikoInzu)
	{
		this.zaikoInzu = zaikoInzu;
	}
	public String getNagasaTani()
	{
		return this.nagasaTani;
	}
	public void setNagasaTani(String nagasaTani)
	{
		this.nagasaTani = nagasaTani;
	}
	public String getKensyo()
	{
		return this.kensyo;
	}
	public void setKensyo(String kensyo)
	{
		this.kensyo = kensyo;
	}
	public String getPriBkn09()
	{
		return this.priBkn09;
	}
	public void setPriBkn09(String priBkn09)
	{
		this.priBkn09 = priBkn09;
	}
	public String getPriBuzaiKigo()
	{
		return this.priBuzaiKigo;
	}
	public void setPriBuzaiKigo(String priBuzaiKigo)
	{
		this.priBuzaiKigo = priBuzaiKigo;
	}
	public String getPriDansunMongon()
	{
		return this.priDansunMongon;
	}
	public void setPriDansunMongon(String priDansunMongon)
	{
		this.priDansunMongon = priDansunMongon;
	}
	public String getKakoMeisyo()
	{
		return this.kakoMeisyo;
	}
	public void setKakoMeisyo(String kakoMeisyo)
	{
		this.kakoMeisyo = kakoMeisyo;
	}
	public String getSzNo()
	{
		return this.szNo;
	}
	public void setSzNo(String szNo)
	{
		this.szNo = szNo;
	}
	public Date getNykoDate()
	{
		return this.nykoDate;
	}
	public void setNykoDate(Date nykoDate)
	{
		this.nykoDate = nykoDate;
	}
	public Integer getStatus()
	{
		return this.status;
	}
	public void setStatus(Integer status)
	{
		this.status = status;
	}
	public Integer getDeleteFlg()
	{
		return this.deleteFlg;
	}
	public void setDeleteFlg(Integer deleteFlg)
	{
		this.deleteFlg = deleteFlg;
	}
	public Date getCreateDate()
	{
		return this.createDate;
	}
	public void setCreateDate(Date createDate)
	{
		this.createDate = createDate;
	}
	public Date getUpdateDate()
	{
		return this.updateDate;
	}
	public void setUpdateDate(Date updateDate)
	{
		this.updateDate = updateDate;
	}
	public ShukkaHeadId getHeadId()
	{
		return this.headId;
	}
	public void setHeadId(ShukkaHeadId headId)
	{
		this.headId = headId;
	}
	public Date getSukkaYoteibi()
	{
		return this.sukkaYoteibi;
	}
	public void setSukkaYoteibi(Date sukkaYoteibi)
	{
		this.sukkaYoteibi = sukkaYoteibi;
	}
	public String getJyuoka()
	{
		return this.jyuoka;
	}
	public void setJyuoka(String jyuoka)
	{
		this.jyuoka = jyuoka;
	}
	public String getYobi()
	{
		return this.yobi;
	}
	public void setYobi(String yobi)
	{
		this.yobi = yobi;
	}

}
