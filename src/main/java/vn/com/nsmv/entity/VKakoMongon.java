package vn.com.nsmv.entity;

import javax.persistence.*;

/**
 */

@Entity
@Table(name = "v_kako_mongon")
public class VKakoMongon implements java.io.Serializable
{

	private String kakoMongon;

	public VKakoMongon()
	{
	}

	public VKakoMongon(String kakoMongon)
	{
		this.kakoMongon = kakoMongon;
	}

	@Id

	@Column(name = "Kako_mongon", length = 30)
	public String getKakoMongon()
	{
		return this.kakoMongon;
	}

	public void setKakoMongon(String kakoMongon)
	{
		this.kakoMongon = kakoMongon;
	}

}
