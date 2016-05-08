package me.makeachoice.movies.model.db.contract;

/**
 * Created by Usuario on 5/7/2016.
 */
public class MyContract {
    protected static final String TEXT_TYPE = " TEXT";
    protected static final String NUMERIC_TYPE = " NUM";
    protected static final String INTEGER_TYPE = " INT";
    protected static final String REAL_TYPE = " REAL";
    protected static final String NONE_TYPE = "";
    protected static final String COMMA_SEP = ",";

    protected static final String CREATE_TABLE = "CREATE TABLE IF NOT EXISTS ";
    protected static final String PRIMARY_KEY = " INTEGER PRIMARY KEY AUTOINCREMENT, ";
    protected static final String PAREN_OPEN = "(";
    protected static final String PAREN_CLOSE = ")";

    protected static final String DROP_TABLE = "DROP TABLE IF EXISTS ";
}
