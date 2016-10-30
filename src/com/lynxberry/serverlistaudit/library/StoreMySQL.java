package com.lynxberry.serverlistaudit.library;

/**
 * Created by stevenshao on 25/10/2016.
 */
public class StoreMySQL extends Store {
    @Override
    public void insertRecord(ServerRecord serverRecord) {
        System.out.printf("%s is inserted.%n", serverRecord);
    }

    @Override
    public void deleteRecord(String RecordID) {
        System.out.printf("%s is deleted.%n",RecordID);
    }

    @Override
    public void updateRecord(String RecordID, ServerRecord serverRecord) {
        System.out.printf("%s is replaced with %s",RecordID, serverRecord);
    }

    @Override
    public ServerRecord getRecordByID(String recordID) {
        return null;
    }

    @Override
    public ServerRecord getRecordByKey(Property... keys) {
        return null;
    }
}
