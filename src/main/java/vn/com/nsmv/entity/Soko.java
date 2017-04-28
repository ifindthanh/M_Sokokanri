package vn.com.nsmv.entity;

import javax.persistence.*;

/**
 */
@Entity
@Table(name = "soko")
public class Soko
{
	private String sokoCd;
	private String sokoName;

	public Soko()
	{

	}

	public Soko(String sokoCd, String sokoName)
	{
		this.sokoCd = sokoCd;
		this.sokoName = sokoName;
	}

	@Id
	@Column(name = "SOKO_CD", unique = true, nullable = false, length = 4)
	public String getSokoCd()
	{
		return this.sokoCd;
	}
	public void setSokoCd(String sokoCd)
	{
		this.sokoCd = sokoCd;
	}

	@Column(name = "SOKO_NAME", nullable = false, length = 45)
	public String getSokoName()
	{
		return this.sokoName;
	}
	public void setSokoName(String sokoName)
	{
		this.sokoName = sokoName;
	}

}
