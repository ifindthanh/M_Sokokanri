package vn.com.nsmv.entity;

import java.util.*;

import javax.persistence.*;

@Entity
@Table(name = "pri_shain_bas")
public class PriShainBas implements java.io.Serializable
{
	private static final long serialVersionUID = 1L;
	private Long id;
	private String sokoCd;
	private Date kijunDt;
	private String stfNo;
	private String duNameKnj;
	private String duNameKna;
	private String duSex;
	private Date duBirthDt;
	private String duAlias;
	private String duMailaddress;
	private Date makeDataDt;
	private Date makeModule;
	private Date upDataDt;
	private Date upModuleDt;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID")
	public Long getId()
	{
		return id;
	}
	public void setId(Long id)
	{
		this.id = id;
	}
	@Column(name = "KIJUN_DT", nullable = false)
	public Date getKijunDt()
	{
		return kijunDt;
	}
	public void setKijunDt(Date kijunDt)
	{
		this.kijunDt = kijunDt;
	}
	@Column(name = "STF_NO", length = 10, nullable = false)
	public String getStfNo()
	{
		return stfNo;
	}
	public void setStfNo(String stfNo)
	{
		this.stfNo = stfNo;
	}
	@Column(name = "DU_NAME_KNJ", length = 20, nullable = true)
	public String getDuNameKnj()
	{
		return duNameKnj;
	}
	public void setDuNameKnj(String duNameKnj)
	{
		this.duNameKnj = duNameKnj;
	}
	@Column(name = "DU_NAME_KNA", length = 20, nullable = true)
	public String getDuNameKna()
	{
		return duNameKna;
	}
	public void setDuNameKna(String duNameKna)
	{
		this.duNameKna = duNameKna;
	}
	@Column(name = "DU_SEX", length = 1, nullable = true)
	public String getDuSex()
	{
		return duSex;
	}
	public void setDuSex(String duSex)
	{
		this.duSex = duSex;
	}
	@Column(name = "DU_BIRTH_DT", nullable = true)
	public Date getDuBirthDt()
	{
		return duBirthDt;
	}
	public void setDuBirthDt(Date duBirthDt)
	{
		this.duBirthDt = duBirthDt;
	}
	@Column(name = "DU_ALIAS", length = 255, nullable = true)
	public String getDuAlias()
	{
		return duAlias;
	}
	public void setDuAlias(String duAlias)
	{
		this.duAlias = duAlias;
	}
	@Column(name = "DU_MAILADDRESS", length = 255, nullable = true)
	public String getDuMailaddress()
	{
		return duMailaddress;
	}
	public void setDuMailaddress(String duMailaddress)
	{
		this.duMailaddress = duMailaddress;
	}
	@Column(name = "MAKE_DATA_DT", nullable = true)
	public Date getMakeDataDt()
	{
		return makeDataDt;
	}
	public void setMakeDataDt(Date makeDataDt)
	{
		this.makeDataDt = makeDataDt;
	}
	@Column(name = "MAKE_MODULE", nullable = true)
	public Date getMakeModule()
	{
		return makeModule;
	}
	public void setMakeModule(Date makeModule)
	{
		this.makeModule = makeModule;
	}
	@Column(name = "UP_DATA_DT", nullable = true)
	public Date getUpDataDt()
	{
		return upDataDt;
	}
	public void setUpDataDt(Date upDataDt)
	{
		this.upDataDt = upDataDt;
	}
	@Column(name = "UP_MODULE_DT", nullable = true)
	public Date getUpModuleDt()
	{
		return upModuleDt;
	}
	public void setUpModuleDt(Date upModuleDt)
	{
		this.upModuleDt = upModuleDt;
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

	//	public PriShainBas(Date kijunDt, String stfNo, String duNameKnj, String duNameKna, String duSex, Date duBirthDt,
	//			String duAlias, String duMailaddress, Date makeDataDt, Date makeModule, Date upDataDt, Date upModuleDt) {
	//		this.kijunDt = kijunDt;
	//		this.stfNo = stfNo;
	//
	//	}

}
