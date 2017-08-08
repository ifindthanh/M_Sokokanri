package vn.com.nsmv.javabean;


public enum TransactionTypeEnum {
    RECHARGE("RECHARGE","Nạp tiền"), PAY("PAY", "Thanh toán"), REFUND("REFUND", "Hoàn tiền");
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
