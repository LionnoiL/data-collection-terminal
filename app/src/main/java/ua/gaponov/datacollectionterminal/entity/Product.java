package ua.gaponov.datacollectionterminal.entity;

public class Product {

    private String name;
    private String code;
    private String barcode;
    private String stock;
    private String price;

    public Product(String barcode) {
        this.barcode = barcode;
    }

    public Product(String name, String code, String barcode, String stock, String price) {
        this.name = name;
        this.code = code;
        this.barcode = barcode;
        this.stock = stock;
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getBarcode() {
        return barcode;
    }

    public void setBarcode(String barcode) {
        this.barcode = barcode;
    }

    public String getStock() {
        return stock;
    }

    public void setStock(String stock) {
        this.stock = stock;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }
}