package ua.gaponov.datacollectionterminal.inventory;

import static ua.gaponov.datacollectionterminal.utils.Helpers.db;

import android.annotation.SuppressLint;
import android.database.Cursor;

import java.util.ArrayList;
import java.util.List;

public class InventoryService {

    public static List<Inventory> getInventories() {
        List<Inventory> docs = new ArrayList<Inventory>();

        String sql_text = "select * from inventory";
        Cursor cursor = db.rawQuery(sql_text, new String[]{});

        while(cursor.moveToNext()) {
            @SuppressLint("Range") long id = cursor.getLong(cursor.getColumnIndex("id"));
            @SuppressLint("Range") String date = cursor.getString(cursor.getColumnIndex("date"));
            @SuppressLint("Range") String comment = cursor.getString(cursor.getColumnIndex("comment"));

            Inventory inventory = new Inventory();
            inventory.setDocId(id);
            inventory.setDate(date);
            inventory.setComment(comment);
            inventory.setItems(getInventoryItems(inventory));
            docs.add(inventory);
        }
        cursor.close();

        return docs;
    }

    public static List<InventoryItem> getInventoryItems(Inventory inventory) {
        List<InventoryItem> items = new ArrayList<>();
        long docId = inventory.getDocId();

        Cursor cursor = db.rawQuery("select * from inventory_items where inventory_id = ?",
                new String[]{String.valueOf(docId)});

        while(cursor.moveToNext()) {
            @SuppressLint("Range") long id = cursor.getLong(cursor.getColumnIndex("id"));
            @SuppressLint("Range") String productName = cursor.getString(cursor.getColumnIndex("product_name"));
            @SuppressLint("Range") String code = cursor.getString(cursor.getColumnIndex("product_code"));
            @SuppressLint("Range") String price = cursor.getString(cursor.getColumnIndex("price"));
            @SuppressLint("Range") double qty = cursor.getDouble(cursor.getColumnIndex("qty"));

            InventoryItem inventoryItem = new InventoryItem();
            inventoryItem.setItemId(id);
            inventoryItem.setProductName(productName);
            inventoryItem.setCode(code);
            inventoryItem.setPrice(price);
            inventoryItem.setQuantity(qty);

            items.add(inventoryItem);
        }
        cursor.close();
        return items;

    }

    public static Inventory getById(Long id){
        String sql_text = "select * from inventory where id = ?";
        Cursor cursor = db.rawQuery(sql_text, new String[]{String.valueOf(id)});

        if (cursor.getCount()>0){
            cursor.moveToFirst();

            @SuppressLint("Range") String date = cursor.getString(cursor.getColumnIndex("date"));
            @SuppressLint("Range") String comment = cursor.getString(cursor.getColumnIndex("comment"));

            Inventory inventory = new Inventory();
            inventory.setDate(date);
            inventory.setComment(comment);
            inventory.setItems(getInventoryItems(inventory));

            cursor.close();

            return inventory;
        } else {
            Inventory inventory = new Inventory();
            cursor.close();
            return inventory;
        }
    }

    public static void save(Inventory inventory) {

        String docId = String.valueOf(inventory.getDocId());
        try {
            db.beginTransaction();

            Cursor cursor = db.rawQuery("select * from inventory where id = ?",
                    new String[]{docId}
            );
            if (cursor.getCount()>0){
                //update
                db.execSQL("delete from inventory_items where inventory_id = ?",
                        new String[]{docId});

                db.execSQL("update inventory set date = ?, comment = ? where id = ?",
                        new String[]{
                                inventory.getDate(),
                                inventory.getComment(),
                                docId
                        });

            } else {
                //insert
                db.execSQL("insert into inventory (date, comment) values (?, ?)",
                        new String[]{
                                inventory.getDate(),
                                inventory.getComment()
                        });
            }

            for (InventoryItem inventoryItem : inventory.getItems()) {
                db.execSQL("insert into inventory_items(inventory_id, product_name, product_code, price, qty)" +
                                " values (?, ?, ?, ?, ?) ",
                        new String[]{
                                docId,
                                inventoryItem.getProductName(),
                                inventoryItem.getCode(),
                                inventoryItem.getPrice(),
                                String.valueOf(inventoryItem.getQuantity())
                        });
            }

            db.setTransactionSuccessful();
        } finally {
            db.endTransaction();
        }


    }
}
