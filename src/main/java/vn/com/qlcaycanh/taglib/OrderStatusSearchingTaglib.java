package vn.com.qlcaycanh.taglib;

import java.io.IOException;
import java.util.Locale;

import javax.servlet.jsp.JspWriter;

import org.springframework.context.i18n.LocaleContextHolder;

import vn.com.qlcaycanh.i18n.IMessage;

public class OrderStatusSearchingTaglib extends AbstractTaglib {
    private int status;

    @Override
    protected void printUI(JspWriter writer) throws IOException {
        Locale locale = LocaleContextHolder.getLocale();
        writer.write("<select class=\"selectpicker form-control inputstl\" style=\"width:auto;\" name=\"status\">");
        writer.write("<option value= \"999\">"+ IMessage.getLabelAll(locale)+"</option>");
        
        writer.write("<option value = \"0\"");
        if (this.status == 0) {
            writer.write("selected");   
        }
        
        writer.write(" >" + IMessage.getLabelWaitingToApproval(locale) + "</option>");
        
        writer.write("<option value = \"1\"");
        if (this.status == 1) {
            writer.write("selected");   
        }
        writer.write(" >" + IMessage.getLabelApproved(locale) + "</option>");
        
        writer.write("<option value = \"2\"");
        if (this.status == 2) {
            writer.write("selected");   
        }
        writer.write(" >" + IMessage.getLabelBought(locale) + "</option>");
        
        writer.write("<option value = \"3\"");
        if (this.status == 3) {
            writer.write("selected");   
        }
        writer.write(" >" + IMessage.getLabelTransferred(locale) + "</option>");
        
        writer.write("<option value = \"4\"");
        if (this.status == 4) {
            writer.write("selected");   
        }
        writer.write(" >" + IMessage.getLabelTransferredToVn(locale) + "</option>");
        
        writer.write("<option value = \"5\"");
        if (this.status == 5) {
            writer.write("selected");   
        }
        writer.write(" >" + IMessage.getLabelStored(locale) + "</option>");

        writer.write("<option value = \"6\"");
        if (this.status == 6) {
            writer.write("selected");   
        }
        writer.write(" >" + IMessage.getLabelBillExported(locale) + "</option>");

        writer.write("<option value = \"7\"");
        if (this.status == 7) {
            writer.write("selected");   
        }
        writer.write(" >" + IMessage.getLabelIsShipping(locale) + "</option>");
        
        writer.write("</select>");
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

}
