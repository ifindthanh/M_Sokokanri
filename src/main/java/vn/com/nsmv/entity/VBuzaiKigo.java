package vn.com.nsmv.entity;

import javax.persistence.*;

/**
 */

@Entity
@Table(name = "v_buzai_kigo")
public class VBuzaiKigo implements java.io.Serializable
{

	private String buzaiKigo;

	public VBuzaiKigo()
	{
	}

	public VBuzaiKigo(String buzaiKigo)
	{
		this.buzaiKigo = buzaiKigo;
	}

	@Id

	@Column(name = "BUZAI_KIGO", length = 12)
	public String getBuzaiKigo()
	{
		return this.buzaiKigo;
	}

	public void setBuzaiKigo(String buzaiKigo)
	{
		this.buzaiKigo = buzaiKigo;
	}

}
