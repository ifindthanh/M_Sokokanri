package vn.com.nsmv.taglib;

import java.io.IOException;

import javax.servlet.jsp.JspWriter;

import vn.com.nsmv.javabean.TransactionTypeEnum;


public class TransactionTypeTaglib extends AbstractTaglib {
    
    private String type;
    private boolean disable;
    
    @Override
    protected void printUI(JspWriter writer) throws IOException {
        writer.append("<select class=\"form-control\" name=\"transactionType\"");
        if (disable) {
            writer.append(" disabled ");
        }
        writer.append(">");
        for (TransactionTypeEnum item : TransactionTypeEnum.values()) {
            writer.append("<option value=\""+item.getCode()+"\"");
            if (item.getCode().equals(this.type)) {
                writer.append(" selected ");
            }
            writer.append(">");
            writer.append(item.getName());
            writer.append("</option>");
        }
        writer.append("");
        writer.append("</select>");
    }
    
    public String getType() {
        return type;
    }
    
    public void setType(String type) {
        this.type = type;
    }

    
    public boolean isDisable() {
        return disable;
    }

    
    public void setDisable(boolean disable) {
        this.disable = disable;
    }

    
}
