package vn.com.nsmv.taglib;

import java.io.IOException;

import javax.servlet.jsp.JspWriter;

import vn.com.nsmv.bean.SettingMoneyRate;
import vn.com.nsmv.common.Constants;
import vn.com.nsmv.common.Utils;

public class MoneyExchangeTaglib extends AbstractTaglib{

	@Override
	protected void printUI(JspWriter writer) throws IOException {
	    if (Utils.hasRole(Constants.ROLE_A)) {
	        writer.append("<a id='moneyRate' href='/moneyExchange/update'>");
            writer.write(String.valueOf(SettingMoneyRate.VALUE));
            writer.append("</a>");
	    } else {
	        writer.append("<span id='moneyRate'>");
	        writer.write(String.valueOf(SettingMoneyRate.VALUE));
	        writer.append("</span>");
	    }
		
	}

}
