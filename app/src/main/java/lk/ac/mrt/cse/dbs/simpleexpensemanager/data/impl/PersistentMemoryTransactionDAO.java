package lk.ac.mrt.cse.dbs.simpleexpensemanager.data.impl;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.TransactionDAO;
import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.SQLite.DBConstants;
import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.SQLite.DBHelper;
import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.model.ExpenseType;
import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.model.Transaction;

public class PersistentMemoryTransactionDAO implements TransactionDAO {
    private DBHelper DBHelper;
    private DateFormat dateFormat;

    public PersistentMemoryTransactionDAO(DBHelper DBHelper) {
        this.DBHelper = DBHelper;
        this.dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
    }

    @Override
    public void logTransaction(Date date, String accountNo, ExpenseType expenseType, double amount) {
        SQLiteDatabase db= DBHelper.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(DBConstants.TRANSACTIONS_DATE, this.dateFormat.format(date));
        contentValues.put(DBConstants.TRANSACTIONS_ACCOUNT_NO, accountNo);
        contentValues.put(DBConstants.TRANSACTIONS_TYPE, expenseType.toString());
        contentValues.put(DBConstants.TRANSACTIONS_AMOUNT, amount);

        //insert new row to transaction table
        db.insert(DBConstants.TRANSACTIONS_TABLE, null, contentValues);

    }

    @Override
    public List<Transaction> getAllTransactionLogs() {
        SQLiteDatabase DB = DBHelper.getReadableDatabase();

        //query to get all transactions ordered by date newest at top
        Cursor cursor = DB.rawQuery(
                "SELECT * FROM " + DBConstants.TRANSACTIONS_TABLE + " ORDER BY " + DBConstants.TRANSACTIONS_DATE + " DESC ",
                null
        );

        ArrayList<Transaction> transactionsList = new ArrayList<>();

        //loop and add transactions to the list creating transaction objects
        for (cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()) {
            try {

                Transaction transaction = new Transaction(
                        this.dateFormat.parse(cursor.getString(1)),
                        cursor.getString(2),
                        ExpenseType.valueOf(cursor.getString(3).toUpperCase()),
                        cursor.getDouble(4)
                );

                transactionsList.add(transaction);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        cursor.close();
        return transactionsList;

    }

    @Override
    public List<Transaction> getPaginatedTransactionLogs(int limit) {

        SQLiteDatabase DB = DBHelper.getReadableDatabase();

        //select limited number of rows from transaction table
        Cursor cursor = DB.rawQuery(
                "SELECT * FROM " + DBConstants.TRANSACTIONS_TABLE + " ORDER BY " + DBConstants.TRANSACTIONS_DATE + " DESC " +
                        " LIMIT ?;"
                , new String[]{Integer.toString(limit)}
        );


        ArrayList<Transaction> transactionsList = new ArrayList<>();

        //loop and add transactions to the list creating transaction objects
        for (cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()) {
            try {

                Transaction transaction = new Transaction(
                        this.dateFormat.parse(cursor.getString(1)),
                        cursor.getString(2),
                        ExpenseType.valueOf(cursor.getString(3).toUpperCase()),
                        cursor.getDouble(4)
                );

                transactionsList.add(transaction);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        cursor.close();
        return transactionsList;

    }
}