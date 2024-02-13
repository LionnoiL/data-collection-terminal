package ua.gaponov.datacollectionterminal.utils;

import ua.gaponov.datacollectionterminal.product.Product;

public interface OnGetBarcode {

    void getBarcode(Product product);
    void beforeGetBarcode();
    void afterGetBarcode();
}
