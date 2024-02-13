package ua.gaponov.datacollectionterminal.utils;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class SQLOpenHelper extends SQLiteOpenHelper {

    public SQLOpenHelper(Context context, int DB_VERSION) {
        /*
         * В конструкторе родительского класса есть четыре параметра
         * Первый параметр - это контекст: используется для открытия или создания базы данных.
         * Второе имя базы данных, которая будет создана или изменена
         * В системе Linux имя суффикса файла является просто идентификатором, оно не имеет практического эффекта, что означает, что вы можете использовать базу данных как обычно, без добавления имени суффикса.
         * Третий - это фабрика курсоров, здесь мы используем ноль, то есть просто используем значение по умолчанию
         * Четвертый номер версии (начиная с 1): используется для создания или обновления базы данных соответственно для методов onCreate и onUpgrade.
         */


        super(context, "terminal.db", null, DB_VERSION);
    }

    public void onCreate(SQLiteDatabase db) {

        db.execSQL(" CREATE TABLE products (" +
                "code TEXT NOT NULL, " +
                "name TEXT, " +
                "barcode TEXT, " +
                "stock TEXT, " +
                "price TEXT, " +
                "PRIMARY KEY (code))"
        );

        db.execSQL("CREATE TABLE inventory ( " +
                "id integer primary key autoincrement, " +
                "date VARCHAR(50), " +
                "comment VARCHAR(100))"
        );

        db.execSQL("CREATE TABLE inventory_items ("+
                "    id           INTEGER       PRIMARY KEY AUTOINCREMENT,"+
                "    inventory_id INTEGER       REFERENCES inventory (id),"+
                "    product_name VARCHAR (200),"+
                "    product_code VARCHAR (50)  NOT NULL,"+
                "    price        VARCHAR (50),"+
                "    qty          DOUBLE"+
                ");");
    }

    public void deleteBD(SQLiteDatabase db) {
        try {
            Helpers.db.beginTransaction();

            onCreate(db);
            Helpers.db.setTransactionSuccessful();
        } finally {
            Helpers.db.endTransaction();
        }
    }

    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}