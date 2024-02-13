package ua.gaponov.datacollectionterminal.product;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Product {

    private String code;
    private String name;
    private String barcode;
    private String stock;
    private String price;

    public Product(String barcode) {
        this.barcode = barcode;
    }
}