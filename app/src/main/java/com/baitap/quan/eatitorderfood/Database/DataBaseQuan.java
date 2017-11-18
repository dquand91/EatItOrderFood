package com.baitap.quan.eatitorderfood.Database;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;

import com.baitap.quan.eatitorderfood.Model.Order;
import com.readystatesoftware.sqliteasset.SQLiteAssetHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by User on 11/17/2017.
 */

public class DataBaseQuan extends SQLiteAssetHelper {
    // Tên file Data Base ta đã import vào project
    private static final String DB_NAME = "EatItDataBase.db";
    private static final int DB_VER = 1;
    public DataBaseQuan(Context context) {
        super(context, DB_NAME,null, DB_VER);
    }

    // Để lấy dữ liệu từ DataBase ra
    public List<Order> getCarts()   {
        // Tạo 1 đối tượng DataBase
        SQLiteDatabase db = getReadableDatabase();
        // Khơi tạo 1 đối tượng để duyệt dataBase.
        SQLiteQueryBuilder qb = new SQLiteQueryBuilder();

        // Để chỉ ra các cột dữ liệu cần lấy
        // Tương ứng với các cột trong Bảng của data base.
        String[] sqlSelect = {"ProductName","ProductId","Quantity","Price","Discount"};
        // Tên bảng trong Data Base.
        String sqlTable = "OrderDetail";

        // để chỉ định bảng cần duyệt
        qb.setTables(sqlTable);
        // Tạo con trỏ để duyệt từng dòng dữ liệu
        Cursor c = qb.query(db,sqlSelect,null,null,null,null,null);

        // Mảng đối tượng cần lấy ra
        final List<Order> result = new ArrayList<>();
        // Di chuyển con trỏ về dòng đầu tiên
        if (c.moveToFirst())    {
            // Trong khi con trỏ chưa ở điểm cuối cùng thì add đối tượng ở dòng đọc được vào mảng.
            do {
                result.add(new Order(c.getString(c.getColumnIndex("ProductID")),
                        c.getString(c.getColumnIndex("ProductName")),
                        c.getString(c.getColumnIndex("Quantity")),
                        c.getString(c.getColumnIndex("Price")),
                        c.getString(c.getColumnIndex("Discount"))));
            } while (c.moveToNext());
        } return result;
    }

    // Để thêm 1 dòng vào data base
    public void addToCart(Order order) {
        final SQLiteDatabase db = getReadableDatabase();
        // Ở đây là nguyên 1 câu truy vấn SQL
        // Với %s sẽ được truyền tương ứng với order.getProductId()
        // Bao nhiu cái %s thì phải truyền đủ bao nhiêu giá trị vào
        String query = String.format("INSERT INTO OrderDetail(ProductId,ProductName,Quantity,Price,Discount)" +
                        " VALUES('%s', '%s','%s','%s','%s')",
                order.getProductId(),
                order.getProductName(),
                order.getQuantity(),
                order.getPrice(),
                order.getDiscount());

        // Chạy câu truy vấn ở trên
        db.execSQL(query);
    }

    public void cleanCart() {
        // Ở đây là 1 câu truy vấn để xóa database
        SQLiteDatabase db = getReadableDatabase();
        String query = String.format("DELETE FROM OrderDetail");

        db.execSQL(query);
    }
}