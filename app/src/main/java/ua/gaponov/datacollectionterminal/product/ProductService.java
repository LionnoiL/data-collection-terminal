package ua.gaponov.datacollectionterminal.product;

import static ua.gaponov.datacollectionterminal.utils.Helpers.db;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ProductService {

    public static void deleteAll(){
        db.execSQL("delete from products", new String[]{});
    }
}
