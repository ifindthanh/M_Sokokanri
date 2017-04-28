package vn.com.nsmv.javabean;

import java.util.*;

import vn.com.nsmv.entity.*;

/**
 */
public class ShukkaBodyBean
{
	private Long id;
	private String sokoCd;
	private String sagyoDate;
	private String sojoNo;
	private String seq;
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
	private String yobi;
	private String szNo;
	private Date nykoDate;
	private Date shukkaDate;
	private Integer status;
	private Integer renban;
	private Integer inzuToExport;

	public ShukkaBodyBean(ShukkaBody body)
	{
		this.id = body.getId();
		this.sokoCd = body.getSokoCd();
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
		this.yobi = body.getYobi();
		this.szNo = body.getSzNo();
		this.nykoDate = body.getNykoDate();
		this.shukkaDate = body.getShukkaDate();
		this.status = body.getStatus();
		this.renban = body.getRenban();
	}

	public String getSokoCd()
	{
		return this.sokoCd;
	}

	public void setSokoCd(String sokoCd)
	{
		this.sokoCd = sokoCd;
	}

	public String getSeq()
	{
		return this.seq;
	}

	public void setSeq(String seq)
	{
		this.seq = seq;
	}

	public Long getId()
	{
		return this.id;
	}
	public void setId(Long id)
	{
		this.id = id;
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
	public String getYobi()
	{
		return this.yobi;
	}
	public void setYobi(String yobi)
	{
		this.yobi = yobi;
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
	public Date getShukkaDate()
	{
		return this.shukkaDate;
	}
	public void setShukkaDate(Date shukkaDate)
	{
		this.shukkaDate = shukkaDate;
	}
	public Integer getStatus()
	{
		return this.status;
	}
	public void setStatus(Integer status)
	{
		this.status = status;
	}

	public Integer getRenban()
	{
		return this.renban;
	}

	public void setRenban(Integer renban)
	{
		this.renban = renban;
	}

	public Integer getInzuToExport()
	{
		return this.inzuToExport;
	}

	public void setInzuToExport(Integer inzuToExport)
	{
		this.inzuToExport = inzuToExport;
	}

}
