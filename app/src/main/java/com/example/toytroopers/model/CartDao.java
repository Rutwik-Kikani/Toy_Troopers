package com.example.toytroopers.model;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class CartDao {
    private final SQLiteDatabase database;

    public CartDao(Context context) {
        DatabaseHelper dbHelper = new DatabaseHelper(context);
        database = dbHelper.getWritableDatabase();
    }

    public long addToCart(String productId, String productName, double productPrice, int quantity, String imageUrl) {
        ContentValues values = new ContentValues();
        values.put(DatabaseHelper.COLUMN_PRODUCT_ID, productId);
        values.put(DatabaseHelper.COLUMN_PRODUCT_NAME, productName);
        values.put(DatabaseHelper.COLUMN_PRODUCT_PRICE, productPrice);
        values.put(DatabaseHelper.COLUMN_QUANTITY, quantity);
        values.put(DatabaseHelper.COLUMN_IMAGE_URL, imageUrl); // Add this line
        return database.insert(DatabaseHelper.TABLE_CART, null, values);
    }

    public Cursor getCartItems() {
        return database.query(DatabaseHelper.TABLE_CART, null, null, null, null, null, null);
    }

    public int removeCartItem(String productId) {
        return database.delete(DatabaseHelper.TABLE_CART, DatabaseHelper.COLUMN_PRODUCT_ID + " = ?", new String[]{productId});
    }

    public void close() {
        database.close();
    }
}
