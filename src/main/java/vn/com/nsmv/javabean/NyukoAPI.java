package vn.com.nsmv.javabean;

import java.util.*;

import vn.com.nsmv.entity.*;

/**
 */
public class NyukoAPI
{
	private String szNo;
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

	public NyukoAPI(Nyuko nyuko)
	{
		this.szNo = nyuko.getId().getSzNo();
		this.sojoNo = nyuko.getSojoNo();
		this.seq = nyuko.getSeq();
		this.bkn09 = nyuko.getBkn09();
		this.buzaiKigo = nyuko.getBuzaiKigo();
		this.zaisitu = nyuko.getZaisitu();
		this.dansunMongon = nyuko.getDansunMongon();
		this.nagasa = nyuko.getNagasa();
		this.zaikoInzu = nyuko.getZaikoInzu();
		this.nagasaTani = nyuko.getNagasaTani();
		this.kakoMongon = nyuko.getKakoMongon();
		this.priBkn09 = nyuko.getPriBkn09();
		this.priBuzaiKigo = nyuko.getPriBuzaiKigo();
		this.priDansunMongon = nyuko.getPriDansunMongon();
		this.yobi = nyuko.getYobi();
		this.sagyoDate = nyuko.getSagyoDate();
		this.nykoDate = nyuko.getNykoDate();
		this.status = nyuko.getStatus();
		this.deleteFlg = nyuko.getDeleteFlg();
		this.createDate = nyuko.getCreateDate();
		this.updateDate = nyuko.getUpdateDate();
		this.exportedFlg = nyuko.getExportedFlg();
	}
	public String getSzNo()
	{
		return this.szNo;
	}
	public void setSzNo(String szNo)
	{
		this.szNo = szNo;
	}
	public String getSojoNo()
	{
		return this.sojoNo;
	}
	public void setSojoNo(String sojoNo)
	{
		this.sojoNo = sojoNo;
	}
	public String getSeq()
	{
		return this.seq;
	}
	public void setSeq(String seq)
	{
		this.seq = seq;
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
	public int getNagasa()
	{
		return this.nagasa;
	}
	public void setNagasa(int nagasa)
	{
		this.nagasa = nagasa;
	}
	public int getZaikoInzu()
	{
		return this.zaikoInzu;
	}
	public void setZaikoInzu(int zaikoInzu)
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
	public String getKakoMongon()
	{
		return this.kakoMongon;
	}
	public void setKakoMongon(String kakoMongon)
	{
		this.kakoMongon = kakoMongon;
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
	public String getYobi()
	{
		return this.yobi;
	}
	public void setYobi(String yobi)
	{
		this.yobi = yobi;
	}
	public Date getSagyoDate()
	{
		return this.sagyoDate;
	}
	public void setSagyoDate(Date sagyoDate)
	{
		this.sagyoDate = sagyoDate;
	}
	public Date getNykoDate()
	{
		return this.nykoDate;
	}
	public void setNykoDate(Date nykoDate)
	{
		this.nykoDate = nykoDate;
	}
	public int getStatus()
	{
		return this.status;
	}
	public void setStatus(int status)
	{
		this.status = status;
	}
	public int getDeleteFlg()
	{
		return this.deleteFlg;
	}
	public void setDeleteFlg(int deleteFlg)
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
	public Integer getExportedFlg()
	{
		return this.exportedFlg;
	}
	public void setExportedFlg(Integer exportedFlg)
	{
		this.exportedFlg = exportedFlg;
	}

}
