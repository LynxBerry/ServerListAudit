package com.lynxberry.serverlistaudit.library;

import java.util.*;

/**
 * Created by stevenshao on 25/10/2016.
 */
public class Schema {
    private ArrayList<SchemaItem> listSchema;
    public Schema(){
        //hard coded for now
        listSchema = new ArrayList<>();
        listSchema.add(new SchemaItem("ServerName","java.lang.String",o->o,o->o));
        listSchema.add(new SchemaItem("ServiceName","java.lang.String",o->o,o->o));
        listSchema.add(new SchemaItem("SequenceName","java.lang.String",o->o,o->o));
        listSchema.add(new SchemaItem("ServerListName","java.lang.String",o->o,o->o));
    }
    public Schema(SchemaItem... schemaItems){
        //listSchema = schemaItems;
        listSchema = new ArrayList<>();
        Arrays.stream(schemaItems).forEach(si->listSchema.add(si));
    }
    ArrayList<SchemaItem> getListSchema(){
        return new ArrayList<>(listSchema);
    }

}
