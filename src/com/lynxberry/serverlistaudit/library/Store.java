package com.lynxberry.serverlistaudit.library;

/**
 * Created by stevenshao on 25/10/2016.
 */
public abstract class Store { //com.lynxberry.serverlistaudit.library.Store of Data

    public abstract void deleteRecord(String RecordID);
    public abstract void insertRecord(ServerRecord serverRecord);
    public abstract void updateRecord(String RecordID, ServerRecord serverRecord);
    public abstract ServerRecord getRecordByID(String recordID);
    public abstract ServerRecord getRecordByKey(Property... keys); // only one record is allowed to be returned.

}
