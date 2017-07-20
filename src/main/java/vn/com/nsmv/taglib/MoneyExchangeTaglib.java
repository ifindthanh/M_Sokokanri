package vn.com.nsmv.taglib;

import java.io.IOException;

import javax.servlet.jsp.JspWriter;

import vn.com.nsmv.bean.SettingMoneyRate;

public class MoneyExchangeTaglib extends AbstractTaglib{

	@Override
	protected void printUI(JspWriter writer) throws IOException {
	        writer.write(String.valueOf(SettingMoneyRate.VALUE));
	}

}
