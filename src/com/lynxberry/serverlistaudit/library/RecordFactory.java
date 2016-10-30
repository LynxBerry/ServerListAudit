package com.lynxberry.serverlistaudit.library;

import java.util.ArrayList;

/**
 * Created by stevenshao on 27/10/2016.
 */
public class RecordFactory {
    private Schema schema;
    public RecordFactory(Schema schema){
        this.schema = schema;

    }

    public Record createRecord(ArrayList<Property> properties) throws Exception {
        Record record = new ServerRecord();
        record.setSchema(schema);
        record.createRecord(properties);
        return record;
    }

    public Record readRecord(ArrayList<Property> properties) throws Exception {
        Record record = new ServerRecord();
        record.setSchema(schema);
        record.readRecord(properties);
        return record;
    }
}
