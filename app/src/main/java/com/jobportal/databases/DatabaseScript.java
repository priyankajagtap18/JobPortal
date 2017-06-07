package com.jobportal.databases;


import com.jobportal.constants.DBConstants;

import java.util.ArrayList;

/**
 * Created by PriyankaJ on 09-03-2016.
 */
public class DatabaseScript {
    DatabaseHelper databaseHelper;

    public DatabaseScript(DatabaseHelper databaseHelper) {
        this.databaseHelper = databaseHelper;

    }

    public void executeScript() {
        TableCreator tableCreator = new TableCreator();
        tableCreator.setTableName(DBConstants.TableCity);
        ArrayList<ColumnCreator> columnCreatorArrayList = new ArrayList<>();
        columnCreatorArrayList.add(getColumnCreator(DBConstants.cityname, ColumnDataType.TEXT, null, false, false));
        tableCreator.setColumnCreatorArrayList(columnCreatorArrayList);
        createTable(tableCreator);

        tableCreator = new TableCreator();
        tableCreator.setTableName(DBConstants.TableJobCategory);
        columnCreatorArrayList = new ArrayList<>();
        columnCreatorArrayList.add(getColumnCreator(DBConstants.jobname, ColumnDataType.TEXT, null, false, false));
        tableCreator.setColumnCreatorArrayList(columnCreatorArrayList);
        createTable(tableCreator);

    }

    /**
     * create column using parameters
     *
     * @param name     column name
     * @param type     column data type
     * @param value    default value of column
     * @param bPrimary is it primary key
     * @param bNotNull whether it null or not
     * @return
     */
    ColumnCreator getColumnCreator(String name, ColumnDataType type, String value, boolean bPrimary, boolean bNotNull) {
        ColumnCreator columnCreator = new ColumnCreator();
        columnCreator.setColumnName(name);
        columnCreator.setColumnDataType(type);
        columnCreator.setColumnDefaultValue(value);
        columnCreator.setPrimaryKey(bPrimary);
        columnCreator.setNotNull(bNotNull);
        return columnCreator;
    }

    private void createTable(TableCreator tableCreator) {
        try {
            databaseHelper.createTable(tableCreator);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
