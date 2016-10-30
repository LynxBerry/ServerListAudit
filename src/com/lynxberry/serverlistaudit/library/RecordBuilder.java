package com.lynxberry.serverlistaudit.library; /**
 * Created by stevenshao on 28/10/2016.
 */

import java.util.ArrayList;
public abstract class RecordBuilder {
    private Schema schema;
    private ArrayList<Property> properties;
    private boolean isNew = true;

    public RecordBuilder createRecord(){
        isNew = true;
        return this;

    }

    public RecordBuilder readRecord(){
        isNew = false;
        return this;
    }

    public RecordBuilder setSchema(Schema schema){
        this.schema = schema;
        return this;
    }

    public RecordBuilder setProperties(ArrayList<Property> properties){
        this.properties = properties;
        return this;

    }

    public Record build() throws Exception {
        Record record = innerBuild();
        record.setSchema(schema);
        if (isNew){
            record.createRecord(properties);
        } else {
            record.readRecord(properties);
        }

        return record;

    }

    protected abstract Record innerBuild();

}
