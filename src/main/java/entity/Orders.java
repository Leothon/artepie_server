package entity;

public class Orders {
    private String order_id;
    private String order_number;
    private String order_user_id;
    private String order_date;
    private String order_class_id;
    private String order_pay_type;
    private String order_class_price;
    private String order_discount;
    private String order_end_price;
    private String order_status;

    private String payInfo;

    public String getPayInfo() {
        return payInfo;
    }

    public void setPayInfo(String payInfo) {
        this.payInfo = payInfo;
    }

    private Merchandise merchandise;

    public Merchandise getMerchandise() {
        return merchandise;
    }

    public void setMerchandise(Merchandise merchandise) {
        this.merchandise = merchandise;
    }

    public String getOrder_class_id() {
        return order_class_id;
    }

    public void setOrder_class_id(String order_class_id) {
        this.order_class_id = order_class_id;
    }

    public void setOrder_class_price(String order_class_price) {
        this.order_class_price = order_class_price;
    }

    public void setOrder_date(String order_date) {
        this.order_date = order_date;
    }

    public void setOrder_discount(String order_discount) {
        this.order_discount = order_discount;
    }

    public void setOrder_end_price(String order_end_price) {
        this.order_end_price = order_end_price;
    }

    public void setOrder_id(String order_id) {
        this.order_id = order_id;
    }

    public void setOrder_number(String order_number) {
        this.order_number = order_number;
    }

    public void setOrder_pay_type(String order_pay_type) {
        this.order_pay_type = order_pay_type;
    }

    public void setOrder_status(String order_status) {
        this.order_status = order_status;
    }

    public void setOrder_user_id(String order_user_id) {
        this.order_user_id = order_user_id;
    }

    public String getOrder_class_price() {
        return order_class_price;
    }

    public String getOrder_date() {
        return order_date;
    }

    public String getOrder_discount() {
        return order_discount;
    }

    public String getOrder_end_price() {
        return order_end_price;
    }

    public String getOrder_id() {
        return order_id;
    }

    public String getOrder_number() {
        return order_number;
    }

    public String getOrder_pay_type() {
        return order_pay_type;
    }

    public String getOrder_status() {
        return order_status;
    }

    public String getOrder_user_id() {
        return order_user_id;
    }

}
