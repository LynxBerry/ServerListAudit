package com.lynxberry.serverlistaudit.library;



import javax.json.*;
import java.sql.Timestamp;
import java.util.*;
import java.time.LocalDateTime;
import java.time.ZoneId;


/**
 * Created by stevenshao on 27/10/2016.
 */
public abstract class Record  {
    //Common Properties
    /*
    private String recordID; //RecordID
    private LocalDateTime createdDate; //CreatedDate
    private LocalDateTime notValidDate; // NotValidDate Date when this record becomes Not Valid.
    private String replacedWithRecordID;//ReplacedWithRecordID
    private String isValid;//IsValid y or n
    */

    private Hashtable<String, Object> commonProperties;
    //End of Common Properties


    private Schema schema; // for schema of inner properties.
    static private Schema commonSchema = new Schema(
            new SchemaItem("RecordID","java.lang.String",o->UUID.randomUUID().toString(),o->o),
            new SchemaItem("CreatedDate","java.time.LocalDateTime",o->LocalDateTime.now(ZoneId.of("GMT")),Record::typeMap),
            new SchemaItem("NotValidDate","java.time.LocalDateTime",o->LocalDateTime.of(1900,1,1,0,0,0),Record::typeMap),
            new SchemaItem("ReplacedWithRecordID","java.lang.String",o->"0000",o->o),
            new SchemaItem("IsValid","java.lang.String",o->'y',o->o)
            //add later
    );

    static public Schema getCommonSchema(){
        return commonSchema;
    }

    private boolean isInited = false;

    public abstract Object getInnerPropertyByName(String propertyName);

    public Object getPropertyByName(String propertyName)

    public final String getRecordID() {
        return (String) commonProperties.get("RecordID");
    }

    // The date when the record is created;
    public final LocalDateTime getCreatedDate() {
        return (LocalDateTime) commonProperties.get("CreatedDate");

    }

    // The date when the record becomes not valid;
    public final LocalDateTime getNotValidDate() {
        return (LocalDateTime) commonProperties.get("NotValidDate");

    }



    private void checkEachSchema(String key, ArrayList<Property> properties) throws Exception {
        if (! properties.stream().anyMatch(p->p.getPropertyName().equals(key))) {
            throw new Exception("Data for Column: " + key + " is required.");
        }
    }
    private void checkSchema(Schema schema, ArrayList<Property> properties) throws Exception { //return Rest Of Properties

        for (SchemaItem sc:schema.getListSchema()) {
            checkEachSchema(sc.getKeyName(),properties);
        }

    }

    Record() {
        commonProperties = new Hashtable<>();
        //Initialize common properties;
        commonSchema.getListSchema().stream().forEach(sc->{commonProperties.put(sc.getKeyName(),sc.getInit().checkAndInit(null));});

    }

    public void setSchema(Schema schema){
        this.schema = schema;
    }


    private void loadInnerProperties(ArrayList<Property> properties) {
        for (Property property: properties) {
            if(schema.getListSchema().stream().anyMatch(sc->sc.getKeyName().equals(property.getPropertyName()))){
                addProperty(new Property<>(property.getPropertyName(),typeMap(property.getPropertyValue())));
            }

        }

    }

    //Create record from scratch;
    public void createRecord(ArrayList<Property> properties) throws Exception {
        //enforce SchemaCheck
        checkSchema(schema, properties);

        loadInnerProperties(properties);

        // The record is ready to use
        isInited = true;

    }


    //protected abstract void loadProperties(ArrayList<com.lynxberry.serverlistaudit.library.Property> properties);

    protected abstract void addProperty(Property property);

    static private Object typeMap(Object obj){
        if (obj.getClass().getCanonicalName().equals("java.sql.Timestamp")){
            return ((Timestamp)obj).toLocalDateTime();
        };
        return obj;
    }


    // Read com.lynxberry.serverlistaudit.library.Record from existing properties
    public void readRecord(ArrayList<Property> properties) throws Exception {
        //enforce schema check
        checkSchema(commonSchema, properties);
        checkSchema(schema,properties);

        for (Property property: properties) {

            //Load common properties first
            if (commonProperties.containsKey(property.getPropertyName())){
                commonProperties.replace(property.getPropertyName(),commonSchema.getListSchema().stream().filter(sc->sc.getKeyName().equals(property.getPropertyName())).findFirst().get().getCheck().checkAndInit(property.getPropertyValue()));
            }

            /* replaced by above code
            switch (property.getPropertyName()) {
                case "RecordID":
                    recordID = (String) typeMap(property.getPropertyValue());
                    break;
                case "ReplacedWithRecordID":
                    replacedWithRecordID = (String) typeMap(property.getPropertyValue());
                    break;
                case "CreatedDate":
                    createdDate = (LocalDateTime) typeMap(property.getPropertyValue());
                    break;
                case "NotValidDate":
                    notValidDate = (LocalDateTime) typeMap(property.getPropertyValue());
                    break;
                case "IsValid":
                    isValid = (String) typeMap(property.getPropertyValue());
                default:
                    if(schema.getListSchema().stream().anyMatch(sc->sc.getKeyName().equals(property.getPropertyName()))){
                        addProperty(new Property<>(property.getPropertyName(),typeMap(property.getPropertyValue())));
                    }

                    break;

            }

            */


        }

        //load inner properties
        loadInnerProperties(properties);

        isInited = true;

    }

    public final String toJsonString(){
        JsonObjectBuilder jsonBuilder = Json.createObjectBuilder();

        //For common properties
        Enumeration<String> e = commonProperties.keys();
        while(e.hasMoreElements()){
            String key = e.nextElement();
            jsonBuilder.add(key, commonProperties.get(key).toString());
        }

        //For inner properties
        schema.getListSchema().forEach(sc->jsonBuilder.add(sc.getKeyName(), getInnerPropertyByName(sc.getKeyName()).toString()));

        return jsonBuilder.build().toString();
    }


    @Override
    public final String toString() {
        return toJsonString();

    }

    public abstract ArrayList<Property> getInnerProperties();

}
