package com.jobportal.databases;

import java.util.ArrayList;

class TableCreator
{
    private String TableName;
    private ArrayList<ColumnCreator> columnCreatorArrayList;

    public String getTableName()
    {
        return TableName;
    }

    public void setTableName(String tableName)
    {
        TableName = tableName;
    }

    public ArrayList<ColumnCreator> getColumnCreatorArrayList()
    {
        return columnCreatorArrayList;
    }

    public void setColumnCreatorArrayList(ArrayList<ColumnCreator> columnCreatorArrayList)
    {
        this.columnCreatorArrayList = columnCreatorArrayList;
    }

}
