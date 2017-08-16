package vn.com.nsmv.javabean;


public class NumberOfOrdersByStatusBean {
    private final Integer nbOfNotedOrder;

    public NumberOfOrdersByStatusBean(Integer nbOfNotedOrder) {
        this.nbOfNotedOrder = nbOfNotedOrder;
    }

    public Integer getNbOfNotedOrder() {
        return nbOfNotedOrder;
    }
    
}
