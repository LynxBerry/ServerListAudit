package com.lynxberry.serverlistaudit.library;

import java.util.*;

/**
 * Created by stevenshao on 25/10/2016.
 */
public final class ServerRecord extends Record { // com.lynxberry.serverlistaudit.library.ServerRecord cannot be modified.
    private ArrayList<Property> properties;
    private Hashtable<String, Object> hashProperties;


    public ServerRecord() {

        hashProperties = new Hashtable<String, Object>();

    }



    @Override
    protected void addProperty(Property property) {
        //System.out.println("abc----");
        //System.out.println(property);
        //System.out.println("endabc---");
        hashProperties.put(property.getPropertyName(),property.getPropertyValue());
    }


    @Override
    public Object getPropertyByName(String propertyName) {
        //System.out.println("----");
        //System.out.println(propertyName);
        //System.out.println(hashProperties.get(propertyName));
        //System.out.println("end---");
        return hashProperties.get(propertyName);
    }


    @Override
    public ArrayList<Property> getAllProperties() {
        ArrayList<Property> p = new ArrayList<>();
        Enumeration<String> e = hashProperties.keys();
        while(e.hasMoreElements()){
            String key = e.nextElement();
            p.add(new Property(key,hashProperties.get(key)));

        }
        return p;
    }
}
