package haris.com.domain;

abstract public class Discount {

    private static Boolean discount=false;

    public static Boolean getDiscount() {
        return discount;
    }

    public static void setDiscount(Boolean discount) {
        Discount.discount = discount;
    }
}
