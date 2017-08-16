package vn.com.nsmv.javabean;

import org.springframework.context.i18n.LocaleContextHolder;
import vn.com.nsmv.i18n.SokokanriMessage;

public enum TransactionTypeEnum {
    RECHARGE("RECHARGE", SokokanriMessage.getMessageInforLabelTransactionTypeRecharge(LocaleContextHolder.getLocale()))
    , PAY("PAY", SokokanriMessage.getMessageInforLabelTransactionTypePay(LocaleContextHolder.getLocale()))
    , REFUND("REFUND", SokokanriMessage.getMessageInforLabelTransactionTypeRefund(LocaleContextHolder.getLocale()));
    private String code;
    private String name;
    private TransactionTypeEnum(String code, String name) {
        this.code = code;
        this.name = name;
    }
    
    public String getCode() {
        return code;
    }
    
    public void setCode(String code) {
        this.code = code;
    }
    
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
}
