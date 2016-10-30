package com.lynxberry.serverlistaudit.library;

import java.util.ArrayList;
/**
 * Created by stevenshao on 25/10/2016.
 */
public class StoreFile extends Store {
    private String filePath;
    private ArrayList<ServerRecord> serverRecords;
    static private Schema schema = new Schema();
    public StoreFile(String path) throws Exception {
        filePath = path;
        loadData();
    }

    private void loadData() throws Exception {
        /*
        FileReader file = new FileReader(filePath);
        JsonReader jsReader = Json.createReader(file);
        JsonStructure jsStruct = jsReader.read();
        serverRecords = new ArrayList<>();
        
        JsonArray jsArray = ((JsonArray) jsStruct);
        for (JsonValue value:jsArray) {
            JsonObject jsObj = (JsonObject) value;
            ArrayList<com.lynxberry.serverlistaudit.library.Property> properties = new ArrayList<>();
            for (String key: jsObj.keySet()) {

                properties.add(new com.lynxberry.serverlistaudit.library.Property<>(key,jsObj.get(key)));
                
            }
            serverRecords.add(new com.lynxberry.serverlistaudit.library.ServerRecord(schema, properties));
            
        }
*/

    }


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
