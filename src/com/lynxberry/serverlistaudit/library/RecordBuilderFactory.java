package com.lynxberry.serverlistaudit.library;

/**
 * Created by stevenshao on 28/10/2016.
 */
public class RecordBuilderFactory {
    public static RecordBuilder createRecorderBuilder(String config){
        return new ServerRecordBuilder();
    }

    //prevent instance creation
    private RecordBuilderFactory(){

    }
}
