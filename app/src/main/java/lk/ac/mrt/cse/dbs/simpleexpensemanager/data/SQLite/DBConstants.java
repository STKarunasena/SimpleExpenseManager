package lk.ac.mrt.cse.dbs.simpleexpensemanager.data.SQLite;

public class DBConstants { //for table names, attributes

    //table names
    public final static String ACCOUNTS_TABLE = "accounts_table";
    public final static String TRANSACTIONS_TABLE = "transactions_table";

    //column names related to accounts table
    public final static String ACCOUNTS_ACCOUNT_NO = "account_no";
    public final static String ACCOUNTS_BANK_NAME = "bank_name";
    public final static String ACCOUNTS_HOLDER_NAME = "holder_name";
    public final static String ACCOUNTS_BALANCE = "balance";

    //column names related to transactions table
    public final static String TRANSACTIONS_ID = "id";
    public final static String TRANSACTIONS_ACCOUNT_NO = "account_no";
    public final static String TRANSACTIONS_TYPE = "type";
    public final static String TRANSACTIONS_DATE = "date";
    public final static String TRANSACTIONS_AMOUNT = "amount";

    //the two types of transactions
    public final static String TYPE_EXPENSE = "EXPENSE";
    public final static String TYPE_INCOME = "INCOME";
}