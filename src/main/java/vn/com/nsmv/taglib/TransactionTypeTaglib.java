package vn.com.nsmv.taglib;

import java.io.IOException;

import javax.servlet.jsp.JspWriter;

import vn.com.nsmv.javabean.TransactionTypeEnum;


public class TransactionTypeTaglib extends AbstractTaglib {

    @Override
    protected void printUI(JspWriter writer) throws IOException {
        writer.append("<select class=\"form-control\" name=\"transactionType\" >");
        for (TransactionTypeEnum item : TransactionTypeEnum.values()) {
            writer.append("<option value=\""+item.getCode()+"\" >");
            writer.append(item.getName());
            writer.append("</option>");
        }
        writer.append("");
        writer.append("</select>");
    }

}
