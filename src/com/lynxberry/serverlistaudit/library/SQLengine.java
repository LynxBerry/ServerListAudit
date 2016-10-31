package com.lynxberry.serverlistaudit.library;
import java.sql.*;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.stream.Collectors;

/**
 * Created by stevenshao on 26/10/2016.
 */
public class SQLengine {
    String sqlServerName;
    Integer portNumber;
    Schema schema;
    String username;
    String password;
    String tableName;

    public void setConfig(Schema schema){
        //for now hard coded
        sqlServerName = "localhost";
        portNumber = 123;
        this.schema = schema;
        tableName = "serverlist";
    }
    private Connection connect() throws ClassNotFoundException, SQLException {
        Class.forName("com.mysql.jdbc.Driver");
        Connection connect = DriverManager.getConnection("jdbc:mysql://localhost/AssetDB?user=root&password=mail0806");
        return connect;


    }

    public Record getRecord(String recordID) throws Exception {
        Connection connect = connect();
        DatabaseMetaData metaData = connect().getMetaData();
        ResultSet setCols = metaData.getColumns(null,null,"serverlist","");
        ArrayList<String> cols = new ArrayList<>();
        while(setCols.next()){
            cols.add(setCols.getString("COLUMN_NAME"));
        }

        PreparedStatement preparedStatement = connect.prepareStatement("select * from serverlist where RecordID = ?;");
        preparedStatement.setString(1,recordID);

        ResultSet resultSet = null;

        resultSet = preparedStatement.executeQuery();
        resultSet.first();

        //statement.executeUpdate("insert into serverlist values(\"xxxxqqqq\",\"dm2mpbiutloct034\",\"BST\",\"FEpatching\",\"CP-1\")");


        ArrayList<Property> p = new ArrayList<>();

        //ResultSet finalResultSet = resultSet;
        for (String col :
                cols) {
            p.add(new Property<>(col, resultSet.getObject(col)));
        }


        connect.close();

        return RecordBuilderFactory.createRecorderBuilder("").readRecord().setSchema(schema).setProperties(p).build();


    }

    public void insertRecord(Record record){

    }

    public void updateRecord(String recordID, Record withRecord){

        //first check whether the target record is marked by someone
        // use sql statement to mark replaceRecordID and valid bit to withRecordID if the replaceRecordID is 0000 and isValid=y. This action is atomic
        // query to ensure replaceRecordID has been marked by using select
        //then insert the withRecord

    }

    public void deleteRecord(String recordID){

        //mark this Record is not valid if it is valid. (no need) Report error if it is already not valid.

    }

    public ArrayList<Record> queryRecordbyKeys (ArrayList<Property> properties) throws Exception {
        Connection connect = connect();
        ArrayList<Record> records = new ArrayList<>();
        String sqlQuery =  "select *"
                          + String.format("from %s where ",tableName)
                          + String.join(" and ", properties.stream().map(p->p.getPropertyName() + " like " + "'%" + p.getPropertyValue().toString() + "%'").collect(Collectors.toList()))
                          + ";";

        PreparedStatement ps = connect.prepareStatement(sqlQuery);
        ResultSet rs = ps.executeQuery();
        while(rs.next()){
            ArrayList<Property> filledProperties = new ArrayList<>();

            for (SchemaItem sc:Record.getCommonSchema().getListSchema()
                    ) {
                filledProperties.add(new Property<>(sc.getKeyName(), rs.getString(sc.getKeyName()) ));
            }

            for (SchemaItem sc:schema.getListSchema()
                 ) {
                filledProperties.add(new Property<>(sc.getKeyName(), rs.getString(sc.getKeyName()) ));
            }

            records.add(RecordBuilderFactory.createRecorderBuilder("").readRecord().setSchema(schema).setProperties(filledProperties).build());


        }

        return records;
    }
}
