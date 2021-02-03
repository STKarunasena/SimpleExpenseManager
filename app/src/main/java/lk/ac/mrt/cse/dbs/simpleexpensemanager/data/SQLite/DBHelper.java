package lk.ac.mrt.cse.dbs.simpleexpensemanager.data.SQLite;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

    public class DBHelper extends SQLiteOpenHelper {
        private final static String DB_NAME = "180313L";
        private final static int DB_VERSION = 1;

        public DBHelper(Context context) {
            super(context, DB_NAME, null, DB_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase sqLiteDatabase) {

            //create statement for account table
            sqLiteDatabase.execSQL(
                    "CREATE TABLE IF NOT EXISTS " + DBConstants.ACCOUNTS_TABLE + "(" +
                            DBConstants.ACCOUNTS_ACCOUNT_NO + " TEXT PRIMARY KEY," +
                            DBConstants.ACCOUNTS_BANK_NAME + " TEXT NOT NULL," +
                            DBConstants.ACCOUNTS_HOLDER_NAME + " TEXT NOT NULL," +
                            DBConstants.ACCOUNTS_BALANCE + " REAL" +
                            ");"
            );

            //create statement for transaction table
            sqLiteDatabase.execSQL(
                    "CREATE TABLE IF NOT EXISTS " + DBConstants.TRANSACTIONS_TABLE + "(" +
                            DBConstants.TRANSACTIONS_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                            DBConstants.TRANSACTIONS_DATE + " TEXT NOT NULL," +
                            DBConstants.TRANSACTIONS_ACCOUNT_NO + " TEXT NOT NULL," +
                            DBConstants.TRANSACTIONS_TYPE + " TEXT NOT NULL," +
                            DBConstants.TRANSACTIONS_AMOUNT + " REAL NOT NULL," +
                            "FOREIGN KEY (" + DBConstants.TRANSACTIONS_ACCOUNT_NO + ") REFERENCES "
                            + DBConstants.ACCOUNTS_TABLE + "(" + DBConstants.ACCOUNTS_ACCOUNT_NO + ")," +
                            "CHECK ("+DBConstants.TRANSACTIONS_TYPE+"==\""+DBConstants.TYPE_EXPENSE+"\" OR "+DBConstants.TRANSACTIONS_TYPE+"==\""+DBConstants.TYPE_INCOME+"\")"+
                            ");");
        }

        @Override
        public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
            sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + DBConstants.TRANSACTIONS_TABLE);
            sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + DBConstants.ACCOUNTS_TABLE);
            onCreate(sqLiteDatabase);
        }
    }

