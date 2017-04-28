package vn.com.nsmv.javabean;

import java.util.*;

import vn.com.nsmv.entity.*;

/**
 */
public class ShukkaHeadBean
{
	private ShukkaHeadId id;
	private String dataKbn;
	private Date sukkaYoteibi;
	private String jyuoka;
	private String yobi;
	private Integer status;
	private Integer exportedFlg;
	public ShukkaHeadBean(ShukkaHeadId id)
	{
		this.id = id;
	}
	public ShukkaHeadBean(ShukkaHead shukkaHead)
	{
		this.id = shukkaHead.getId();
		this.dataKbn = shukkaHead.getDataKbn();
		this.sukkaYoteibi = shukkaHead.getSukkaYoteibi();
		this.jyuoka = shukkaHead.getJyuoka();
		this.yobi = shukkaHead.getYobi();
		this.status = shukkaHead.getStatus();
		this.exportedFlg = shukkaHead.getExportedFlg();
	}
	public ShukkaHeadId getId()
	{
		return this.id;
	}
	public void setId(ShukkaHeadId id)
	{
		this.id = id;
	}
	public String getDataKbn()
	{
		return this.dataKbn;
	}
	public void setDataKbn(String dataKbn)
	{
		this.dataKbn = dataKbn;
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
	public Integer getStatus()
	{
		return this.status;
	}
	public void setStatus(Integer status)
	{
		this.status = status;
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
