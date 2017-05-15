package com.jobportal.databases;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Environment;
import android.util.Log;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.channels.FileChannel;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static String DATABASE_NAME = "Ritzy.sqlite";
    private static final int DATABASE_VERSION = 1;
    private static DatabaseHelper sInstance;
    private SQLiteDatabase sqLiteDatabase;
    private String tag = DatabaseHelper.class.getName();
    private Context mContext;


    /**
     * Constructor should be private to prevent direct instantiation.
     * make call to static method "getInstance()" instead.
     */
    private DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.mContext = context;
    }

    public static synchronized DatabaseHelper getInstance(Context context) {

        if (sInstance == null) {
            sInstance = new DatabaseHelper(context.getApplicationContext());
        }
        return sInstance;
    }

    /**
     * Copy database each time
     *
     * @throws IOException
     */
    public void createDatabase() {
        try {
            // check if the sqldbMaximizerSync exists
            boolean dbExist = checkDatabase();
            if (!dbExist) {
                sqLiteDatabase = this.getReadableDatabase();
                // This is VERY IMP
                sqLiteDatabase.close();
                Log.d("createDatabase()", "db does not exist");
                copyDatabse();
            } else {

                Log.d("createDatabase()", "db already exist");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Copies database from internal storage to assest folder
     * bytestream.
     */
    private void copyDatabse() {
        try {
            String strDB_path = File.separator + "data" + File.separator + "data" + File.separator + mContext.getPackageName() + File.separator + "databases"
                    + File.separator;
            // Open your local db as the input stream
            InputStream myInput = mContext.getAssets().open(DATABASE_NAME);
            // Path to the just created empty db
            String outFileName = strDB_path + DATABASE_NAME;
            // Open the empty db as the output stream

            OutputStream myOutput = new FileOutputStream(outFileName);
            // transfer bytes from the inputfile to the outputfile
            byte[] buffer = new byte[1024 * 2];
            int length;
            while ((length = myInput.read(buffer)) > 0) {
                try {
                    myOutput.write(buffer, 0, length);
                } catch (Exception ignored) {
                }
            }
            myOutput.flush();
            myOutput.close();
            myInput.close();
        } catch (Exception e) {

            e.printStackTrace();

        }
    }

    /**
     * Check if the sqldbMaximizerSync already exist to avoid re-copying the file each time you open the application.
     *
     * @return true if it exists, false if it doesn't
     */
    private boolean checkDatabase() {
        File dbFile = null;
        String strDB_path = File.separator + "data" + File.separator + "data" + File.separator + mContext.getPackageName() + File.separator + "databases"
                + File.separator;
        try {
            String strmyPath = strDB_path + DATABASE_NAME;
            dbFile = new File(strmyPath);

        } catch (Exception e) {
            e.printStackTrace();

        }
        return dbFile != null ? dbFile.exists() : false;
    }

    /****
     * Clear singleton database instance
     */
    public static void clearInstance() {
        if (sInstance != null) {
            sInstance = null;
        }
    }

    private static boolean doesDatabaseExist(Context context) {
        File dbFile = context.getApplicationContext().getDatabasePath(DATABASE_NAME);
        return dbFile.exists();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        sqLiteDatabase = db;
        // new DatabaseScript(sInstance).executeScript();
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    /**
     * Drop existing table(if present with same name) and create new table.
     *
     * @param tableCreator
     * @throws Exception
     */
    public void createTable(TableCreator tableCreator) throws Exception {
        openDatabaseForWrite();
        if (tableCreator.getTableName() != null && !tableCreator.getTableName().isEmpty()) {
            sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + tableCreator.getTableName());

            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("CREATE TABLE ").append(tableCreator.getTableName());
            stringBuilder.append("(");
            if (tableCreator.getColumnCreatorArrayList() != null && tableCreator.getColumnCreatorArrayList().size() > 0) {
                //traverse through is each column
                for (ColumnCreator columnCreator : tableCreator.getColumnCreatorArrayList()) {
                    if (columnCreator.getColumnName() != null && !columnCreator.getColumnName().isEmpty()) {

                        stringBuilder.append(columnCreator.getColumnName());
                        stringBuilder.append(" ");

                        if (columnCreator.getColumnDataType() == ColumnDataType.INTEGER) {
                            stringBuilder.append("INTEGER");
                        } else if (columnCreator.getColumnDataType() == ColumnDataType.REAL) {
                            stringBuilder.append("REAL");
                        } else if (columnCreator.getColumnDataType() == ColumnDataType.TEXT) {
                            stringBuilder.append("TEXT");
                        } else {
                            throw new DatabaseHelperException("DataType for column \'"
                                    + columnCreator.getColumnName() + "\' is not specified");
                        }
                        stringBuilder.append(" ");

                        if (columnCreator.isPrimaryKey()) {
                            stringBuilder.append("PRIMARY KEY");
                        }
                        stringBuilder.append(" ");

                        if (columnCreator.isNotNull()) {
                            stringBuilder.append("NOT NULL");
                        }

                        stringBuilder.append(" ");
                        stringBuilder.append(",");
                    } else {
                        throw new DatabaseHelperException(
                                "Column name cannot be NULL or Empty.Please use valid column name");
                    }

                }

            } else {
                throw new DatabaseHelperException("Cannot create empty table \'" + tableCreator.getTableName()
                        + "\', you should specify atleast one column");

            }

            stringBuilder.append(")");
            String queryString = stringBuilder.toString();
            queryString = queryString.replaceAll(" ,", ",");
            queryString = queryString.replaceAll(",\\)", ")");
            Log.d(tag, "" + queryString);

            sqLiteDatabase.execSQL(queryString);

        } else {
            throw new DatabaseHelperException("Table name cannot be NULL or Empty.Please use valid table name");
        }

    }

    public void replaceAll(StringBuilder builder, String from, String to) {
        int index = builder.indexOf(from);
        while (index != -1) {
            builder.replace(index, index + from.length(), to);
            index += to.length(); // Move to the end of the replacement
            index = builder.indexOf(from, index);
        }
    }

    /**
     * get Readable instance of sqLiteDatabase
     *
     * @throws SQLException
     */
    private synchronized void openDatabaseForRead() {
        // Open the sqLiteDatabase
        try {
            if (sqLiteDatabase == null || !sqLiteDatabase.isOpen()) {
                sqLiteDatabase = this.getReadableDatabase();
            } else {
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * get Writable instance of sqLiteDatabase
     *
     * @throws SQLException
     */
    private synchronized void openDatabaseForWrite() {
        // Open the sqLiteDatabase
        try {
            if (sqLiteDatabase == null || !sqLiteDatabase.isOpen()) {
                sqLiteDatabase = this.getWritableDatabase();
            } else {
                // if the current mode is read only;open db in write mode
                if (sqLiteDatabase.isReadOnly()) {
                    sqLiteDatabase = this.getWritableDatabase();
                }
            }
        } catch (Exception e) {

            e.printStackTrace();
        }
    }

    private synchronized void closeDatabase() {
        super.close();
        try {
            if (sqLiteDatabase != null) {
                sqLiteDatabase.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Insert single record in table.
     *
     * @param TableName table name in which record to be inserted
     * @param values    column values
     * @return true if record is inserted
     */
    private boolean InsertRecord(String TableName, ContentValues values) {
        long lid;

        try {
            openDatabaseForWrite();
            lid = sqLiteDatabase.insert(TableName, null, values);

        } catch (Exception e) {
            lid = -1;
            e.printStackTrace();
        }

        return lid == -1 ? false : true;

    }


    /**
     * update single record in table
     *
     * @param TableName   table name in which record to be inserted
     * @param values      column values
     * @param WhereClause where clause for primary key
     * @return true if record is updated
     */
    public boolean UpdateRecord(String TableName, ContentValues values, String WhereClause, String[] args) {
        long lid = -1;
        try {
            openDatabaseForWrite();
            lid = sqLiteDatabase.update(TableName, values, WhereClause, args);

        } catch (Exception e) {
            lid = -1;
            e.printStackTrace();
        } finally {
            closeDatabase();
        }
        return lid == -1 ? false : true;
    }

    public boolean deleteRecord(String TableName, String strWhereClause) {
        int affectedRows = 0;
        try {
            openDatabaseForWrite();
            affectedRows = sqLiteDatabase.delete(TableName, strWhereClause, null);

        } catch (Exception e) {
            e.printStackTrace();

        } finally {
            closeDatabase();
        }
        return affectedRows == 0 ? false : true;
    }


    /**
     * update single record in table
     *
     * @param Query Query to execute
     */
    public void UpdateRecordByQuery(String Query, String[] args) {
        Cursor cursor;
        try {
            openDatabaseForWrite();
            cursor = sqLiteDatabase.rawQuery(Query, args);
            cursor.moveToFirst();
            cursor.close();
        } catch (Exception e) {
            e.printStackTrace();

        } finally {
            closeDatabase();
        }
    }


    /**
     * check if this record already exist
     *
     * @return true if record exists
     */
    public boolean checkIfAlreadyExist(String strTableName, String columnName, String columnValue) {
        String Query = "Select * FROM " + strTableName + " WHERE " + columnName + " = '" + columnValue + "'";
        Cursor cursor = getAllrecords(Query);
        if (cursor != null) {
            if (cursor.getCount() > 0) {
                cursor.close();
                return true;
            }
            cursor.close();
        }
        return false;
    }

 /*   *//**
     * check if this record already exist
     *
     * @return true if record exists
     *//*
    public boolean checkIfAlreadyExist(String strTableName, String columnFirst, String columnSecond, String strFirstValue, String strSecondValue) {
        String strDeviceId = CommonCode.strDeviceId;
        String strQuery = "SELECT * FROM " + strTableName + " WHERE " + columnFirst + "='" + CommonCode.encryptParam(strDeviceId, strFirstValue) +
                "' AND " + columnSecond + "='" + CommonCode.encryptParam(strDeviceId, strSecondValue) + "'";
        Cursor cursor = getAllrecords(strQuery);
        if (cursor != null) {
            if (cursor.getCount() > 0) {
                cursor.close();
                return true;
            }
            cursor.close();
        }
        return false;
    }
*/

    /**
     * Get all records by executing raw Query
     *
     * @param strQuery -sqlite query to execute
     * @return Cursor
     */
    public Cursor getAllrecords(String strQuery, String args[]) {
        Cursor cursor = null;

        try {
            openDatabaseForRead();
            cursor = sqLiteDatabase.rawQuery(strQuery, args);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return cursor;

    }

    /**
     * Get all records by executing raw Query
     *
     * @param strQuery -sqlite query to execute
     * @return Cursor
     */

    private Cursor getAllrecords(String strQuery) {
        Cursor cursor = null;

        try {
            openDatabaseForRead();
            cursor = sqLiteDatabase.rawQuery(strQuery, null);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return cursor;

    }

    /**
     * This method export database to SDcard
     *
     * @return true if exported
     */
    public boolean exportDBToSDcard(Context con) {
        String strDB_path = File.separator + "data" + File.separator + "data" + File.separator + con.getPackageName() + File.separator + "databases"
                + File.separator;
        FileChannel source;
        FileChannel destination;
        File currentDB = new File(strDB_path + DATABASE_NAME);
        File backupDB = new File(Environment.getExternalStorageDirectory() + File.separator + DATABASE_NAME);
        try {
            source = new FileInputStream(currentDB).getChannel();
            destination = new FileOutputStream(backupDB).getChannel();
            destination.transferFrom(source, 0, source.size());
            source.close();
            destination.close();
            return true;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    public void executeInsert(String query) {
        try {
            openDatabaseForWrite();
            Cursor cursor = sqLiteDatabase.rawQuery(query, null);
            if (cursor != null) {
                cursor.moveToFirst();
                cursor.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
/*

    public boolean insertOrUpdateCustOrder(CustOrderStatus objOrderStatus) {
        boolean isInserted;
        String strDeviceId = CommonCode.strDeviceId;
        ContentValues values = new ContentValues();
        objOrderStatus.setRemainingQty(objOrderStatus.getRemainingQty().replaceAll(",", ""));
        objOrderStatus.setShippedQty(objOrderStatus.getShippedQty().replaceAll(",", ""));
        objOrderStatus.setOrderedQty(objOrderStatus.getOrderedQty().replaceAll(",", ""));
        values.put(DBConstants.remainingQty, CommonCode.encryptParam(strDeviceId, objOrderStatus.getRemainingQty()));
        values.put(DBConstants.orderedQty, CommonCode.encryptParam(strDeviceId, objOrderStatus.getOrderedQty()));
        values.put(DBConstants.shippedQty, CommonCode.encryptParam(strDeviceId, objOrderStatus.getShippedQty()));
        values.put(DBConstants.custOrderMonthName, CommonCode.encryptParam(strDeviceId, objOrderStatus.getMonthName()));
        values.put(DBConstants.custOrderMonthNo, CommonCode.encryptParam(strDeviceId, objOrderStatus.getMonthNo()));
        values.put(DBConstants.custOrderYear, CommonCode.encryptParam(strDeviceId, objOrderStatus.getYear()));
//        values.put(DBConstants.custUserName, CommonCode.encryptParam(strDeviceId, AppConstants.sDemoNamePass));
        values.put(DBConstants.custUserName, CommonCode.encryptParam(strDeviceId, LoginActivity.objLoginWrapper.getUserName()));
        isInserted = InsertRecord(DBConstants.TableCustOrderStatus, values);

        return isInserted;
    }

    public ArrayList<CustOrderStatus> getAllCustOrderStatus(String year) {
        String strDeviceId = CommonCode.strDeviceId;
        ArrayList<CustOrderStatus> mArrLOrderStatus = null;
        try {
            String strQuery = "SELECT * FROM " + DBConstants.TableCustOrderStatus + " WHERE " + DBConstants.custOrderYear + "='" + CommonCode.encryptParam(strDeviceId, year)
                    + "' AND " + DBConstants.custUserName + " = '" + CommonCode.encryptParam(strDeviceId, LoginActivity.objLoginWrapper.getUserName()) + "'";
           */
/* String strQuery = "SELECT * FROM " + DBConstants.TableCustOrderStatus + " WHERE " + DBConstants.custOrderYear + " = '" + CommonCode.encryptParam(strDeviceId, year)
                    + "'";
*//*

            Log.i("strQuery", strQuery);
            Cursor cursor = sInstance.getAllrecords(strQuery);

            if (cursor != null) {
                if (cursor.getCount() > 0) {
                    mArrLOrderStatus = new ArrayList();
                    cursor.moveToFirst();
                    do {
                        CustOrderStatus objCustOrderStatus = new CustOrderStatus();
                        objCustOrderStatus.setYear(CommonCode.decryptParam(strDeviceId, cursor.getString(cursor.getColumnIndex(DBConstants.custOrderYear))));
                        objCustOrderStatus.setRemainingQty(CommonCode.decryptParam(strDeviceId, cursor.getString(cursor.getColumnIndex(DBConstants.remainingQty))));
                        objCustOrderStatus.setOrderedQty(CommonCode.decryptParam(strDeviceId, cursor.getString(cursor.getColumnIndex(DBConstants.orderedQty))));
                        objCustOrderStatus.setShippedQty(CommonCode.decryptParam(strDeviceId, cursor.getString(cursor.getColumnIndex(DBConstants.shippedQty))));
                        objCustOrderStatus.setMonthName(CommonCode.decryptParam(strDeviceId, cursor.getString(cursor.getColumnIndex(DBConstants.custOrderMonthName))));
                        objCustOrderStatus.setMonthNo(CommonCode.decryptParam(strDeviceId, cursor.getString(cursor.getColumnIndex(DBConstants.custOrderMonthNo))));
                        objCustOrderStatus.setUserName(CommonCode.decryptParam(strDeviceId, cursor.getString(cursor.getColumnIndex(DBConstants.custUserName))));
                        mArrLOrderStatus.add(objCustOrderStatus);
                    }
                    while (cursor.moveToNext());
                }

                cursor.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return mArrLOrderStatus;
    }
*/


}
