package ua.gaponov.datacollectionterminal.utils;

import ua.gaponov.datacollectionterminal.entity.Product;

public interface OnGetBarcode {

    void getBarcode(Product product);
    void beforeGetBarcode();
    void afterGetBarcode();
}
