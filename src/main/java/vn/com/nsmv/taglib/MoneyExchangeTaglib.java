package vn.com.nsmv.taglib;

import java.io.IOException;

import javax.servlet.jsp.JspWriter;

import vn.com.nsmv.bean.LiveMoneyExchange;

public class MoneyExchangeTaglib extends AbstractTaglib{

	@Override
	protected void printUI(JspWriter writer) throws IOException {
		writer.write(LiveMoneyExchange.VALUE);
	}

}
