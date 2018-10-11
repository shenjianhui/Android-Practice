package es.source.code.model;


public class Order {
    private String orderName;
    private String orderPrice;
    private String orderNumber;
    private String orderRemark;
    private String orderChoose;

    public Order(String orderName, String orderPrice, String orderNumber, String orderRemark) {

        this.orderName = orderName;
        this.orderPrice = orderPrice;
        this.orderNumber = orderNumber;
        this.orderRemark = orderRemark;
    }

    public String getOrderName() {
        return orderName;
    }

    public void setOrderName(String orderName) {
        this.orderName = orderName;
    }

    public String getOrderPrice() {
        return orderPrice;
    }

    public void setOrderPrice(String orderPrice) {
        this.orderPrice = orderPrice;
    }

    public String getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(String orderNumber) {
        this.orderNumber = orderNumber;
    }

    public String getOrderRemark() {
        return orderRemark;
    }

    public void setOrderRemark(String orderRemark) {
        this.orderRemark = orderRemark;
    }

    public String getOrderChoose() {
        return orderChoose;
    }

    public void setOrderChoose(String orderChoose) {
        this.orderChoose = orderChoose;
    }
}
