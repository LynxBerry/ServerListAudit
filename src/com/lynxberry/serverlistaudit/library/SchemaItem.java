package com.lynxberry.serverlistaudit.library;

/**
 * Created by stevenshao on 29/10/2016.
 */
public class SchemaItem {
    private String keyName;
    private String typeName;
    private SchemaCheckAndInit init; //init the data
    private SchemaCheckAndInit check; //check the data only and does some conversion.
    private boolean isKey = false;

    public SchemaItem(String keyName, String typeName, SchemaCheckAndInit init, SchemaCheckAndInit check){
        this.keyName = keyName;
        this.typeName = typeName;
        this.init = init;
        this.check = check;

    }

    public SchemaItem setKey(){
        isKey = true;
        return this;
    }

    public boolean isKey(){
        return isKey;
    }

    public String getKeyName(){
        return keyName;
    }

    public String getTypeName(){
        return typeName;
    }

    public SchemaCheckAndInit getInit(){
        return init;
    }
    public SchemaCheckAndInit getCheck(){
        return check;
    }
}
