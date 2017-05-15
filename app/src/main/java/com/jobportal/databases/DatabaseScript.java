package com.jobportal.databases;

import java.util.ArrayList;

/**
 * Created by PriyankaJ on 09-03-2016.
 */
class DatabaseScript {
    private DatabaseHelper databaseHelper;

    public DatabaseScript(DatabaseHelper databaseHelper) {
        this.databaseHelper = databaseHelper;

    }

    public void executeScript() {
        //New tables for Qatar Steel.
        TableCreator tableCreator = new TableCreator();
        //tableCreator.setTableName(DBConstants.TableAdvanceShipmentNotice);
        ArrayList<ColumnCreator> columnCreatorArrayList = new ArrayList<>();
      /*  columnCreatorArrayList.add(getColumnCreator(DBConstants.orderQuantityUom, ColumnDataType.TEXT, null, false, false));
        columnCreatorArrayList.add(getColumnCreator(DBConstants.headerId, ColumnDataType.TEXT, null, false, false));
        columnCreatorArrayList.add(getColumnCreator(DBConstants.creationDate, ColumnDataType.TEXT, null, false, false));
        columnCreatorArrayList.add(getColumnCreator(DBConstants.shipNoticeUserName, ColumnDataType.TEXT, null, false, false));*/
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
    private ColumnCreator getColumnCreator(String name, ColumnDataType type, String value, boolean bPrimary, boolean bNotNull) {
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
