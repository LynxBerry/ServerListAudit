package com.lynxberry.serverlistaudit.test;

import java.util.ArrayList;
import com.lynxberry.serverlistaudit.library.*;
import java.util.Date;
import java.time.*;
/**
 * Created by stevenshao on 25/10/2016.
 */
public class TestMain {
    public static void main(String args[]) throws Exception {
        //Test code
        Property<String> prptServerName = new Property<>("ServerName", "co1mpbiutloct01");
        Property prptServiceBelonging = new Property<String>("ServiceName", "BST");
        Property prptNumber = new Property<String>("SequenceName", "FE_Patch");
        Property prptServerListName = new Property<String>("ServerListName", "CP-1");
        System.out.println(prptServerName);
        System.out.println(prptServiceBelonging);
        System.out.println(prptNumber);


        ArrayList<Property> properties = new ArrayList<>();
        properties.add(prptServerName);
        properties.add(prptServiceBelonging);
        properties.add(prptNumber);
        properties.add(prptServerListName);

        RecordFactory recordFactory = new RecordFactory(new Schema());
        Record record = recordFactory.createRecord(properties);
        System.out.println(record);

        System.out.println("New Test");
        Record record2 = RecordBuilderFactory.createRecorderBuilder("").setSchema(new Schema()).setProperties(properties).build();
        System.out.println(record2);

        SQLengine sqlEngine = new SQLengine();
        sqlEngine.setConfig(new Schema());

        System.out.println("xxxx===");
        System.out.println(sqlEngine.getRecord("xxxx"));
        ArrayList<Property> props = new ArrayList<>();
        props.add(new Property<>("RecordID","xxxx"));
        System.out.println(sqlEngine.queryRecordbyKeys(props));
        //System.out.println("yyy" + (new Date()).getClass().getCanonicalName());
        //System.out.println(("abc").getClass().getName());

    }
}
