package vn.com.nsmv.entity;

import java.util.*;

import javax.persistence.*;

@Entity
@Table(name = "log")
public class LogRequest implements java.io.Serializable
{
	private static final long serialVersionUID = 1L;
	private Long id;
	private String logInfo;
	private Date logDate;
	private int logType;
	private String sokoCd;

	public LogRequest(String logInfo, Date logDate, int logType)
	{
		this.logInfo = logInfo;
		this.logDate = logDate;
		this.logType = logType;
	}

	public LogRequest(String logInfo, Date logDate, int logType, String sokoCd)
	{
		this.logInfo = logInfo;
		this.logDate = logDate;
		this.logType = logType;
		this.sokoCd = sokoCd;
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

	@Column(name = "log_info")
	public String getLogInfo()
	{
		return this.logInfo;
	}
	public void setLogInfo(String logInfo)
	{
		this.logInfo = logInfo;
	}

	@Column(name = "log_date")
	public Date getLogDate()
	{
		return this.logDate;
	}
	public void setLogDate(Date logDate)
	{
		this.logDate = logDate;
	}

	@Column(name = "log_type")
	public int getLogType()
	{
		return this.logType;
	}
	public void setLogType(int logType)
	{
		this.logType = logType;
	}

	@Column(name = "SOKO_CD", length = 4)
	public String getSokoCd()
	{
		return this.sokoCd;
	}
	public void setSokoCd(String sokoCd)
	{
		this.sokoCd = sokoCd;
	}

}
