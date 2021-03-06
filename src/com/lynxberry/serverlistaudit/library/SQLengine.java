package com.lynxberry.serverlistaudit.library;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by stevenshao on 26/10/2016.
 */
public class SQLengine {
    private String sqlServerName;
    private Schema schema;
    String username;
    String password;
    String tableName;
    String dbName;

    public void setConfig(Schema schema, String sqlServerName, String dbName, String tableName){
        //for now hard coded
        this.sqlServerName = sqlServerName;
        this.schema = schema;
        this.dbName = dbName;
        this.tableName = tableName;
    }

    public void setUsernamePwd(String username, String password){
        this.username = username;
        this.password = password;
    }

    //Not used right now.
    public ArrayList<String> getDBColumns() throws Exception{
        Connection connect = connect();
        DatabaseMetaData metaData = connect.getMetaData();
        ResultSet setCols = metaData.getColumns(null,null,"serverlist","");

        ArrayList<String> cols = new ArrayList<>();
        while(setCols.next()){
            cols.add(setCols.getString("COLUMN_NAME"));
        }
        connect.close();
        return cols;

    }
    private Connection connect() throws ClassNotFoundException, SQLException {
        Class.forName("com.mysql.jdbc.Driver");
        return DriverManager.getConnection("jdbc:mysql://" + sqlServerName + "/" + dbName + "?user=" + username + "&password=" + password);


    }

    public Record getRecord(String recordID) throws Exception {
        Connection connect = connect();

        PreparedStatement preparedStatement = connect.prepareStatement("select * from serverlist where RecordID = ?;");
        preparedStatement.setString(1,recordID);

        ResultSet resultSet = preparedStatement.executeQuery();
        resultSet.first();



        Record record = loadRecordFromRS(resultSet);

        connect.close();

        return record;


    }

    public ArrayList<Record> getAllRecords() throws Exception {
        Connection connect = connect();

        PreparedStatement preparedStatement = connect.prepareStatement("select * from serverlist where IsValid = 'y';");


        ResultSet resultSet = preparedStatement.executeQuery();


        ArrayList<Record> records = new ArrayList<>();

        while(resultSet.next()){
            records.add(loadRecordFromRS(resultSet));
        }

        //Record record = loadRecordFromRS(resultSet);

        connect.close();

        return records;

    }

    public void insertRecord(Record record) throws Exception {
        Connection connect = connect();
        //Disabling autocommit to start transaction.
        connect.setAutoCommit(false);
        List<String> conditions;
        List<String> columns;
        List<String> values;
        conditions = schema.getListSchema().stream().filter(SchemaItem::isKey).map(sc->sc.getKeyName()+"="+"'"+ record.getInnerPropertyByName(sc.getKeyName())+"'").collect(Collectors.toList());
        columns = Record.getCommonSchema().getListSchema().stream().map(sc->sc.getKeyName()).collect(Collectors.toList());
        columns.addAll(schema.getListSchema().stream().map(sc->sc.getKeyName()).collect(Collectors.toList()));
        values = columns.stream().map(col->"'" +  record.getPropertyByName(col).toString() + "'").collect(Collectors.toList());
        String sqlQuery = "select count(*) from " + tableName + " "
                + "where " + String.join(" and ",conditions) + " for update;";


        String sqlInsert = String.format("insert into %s (%s) values(%s);",tableName,String.join(",", columns), String.join(",", values));

        System.out.println(sqlQuery);
        System.out.println(sqlInsert);

        PreparedStatement ps = connect.prepareStatement(sqlQuery);
        ResultSet rs = ps.executeQuery();
        int count = 1;
        while (rs.next()) {
            count = rs.getInt(1);
        }

        if (count == 0) {
            PreparedStatement insertPS = connect.prepareStatement(sqlInsert);
            insertPS.executeUpdate();

        }

        connect.commit();
        connect.close();

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
        List<String> conditions = properties.stream().map(p->p.getPropertyName() + " like " + "'%" + p.getPropertyValue() + "%'").collect(Collectors.toList());
        conditions.add("IsValid = 'y'");//only fetch valid record;
        String sqlQuery =  "select *"
                          + String.format("from %s where ",tableName)
                          + String.join(" and ", conditions)
                          + ";";

        PreparedStatement ps = connect.prepareStatement(sqlQuery);
        ResultSet rs = ps.executeQuery();
        while(rs.next()){
            Record record = loadRecordFromRS(rs);
            records.add(record);


        }
        connect.close();

        return records;
    }

    private Record loadRecordFromRS(ResultSet rs) throws Exception {
        ArrayList<Property> filledProperties = new ArrayList<>();

        for (SchemaItem sc:Record.getCommonSchema().getListSchema()
                ) {
            filledProperties.add(new Property<>(sc.getKeyName(), rs.getString(sc.getKeyName()) ));
        }

        for (SchemaItem sc:schema.getListSchema()
             ) {
            filledProperties.add(new Property<>(sc.getKeyName(), rs.getString(sc.getKeyName()) ));
        }

        return RecordBuilderFactory.createRecorderBuilder("").readRecord().setSchema(schema).setProperties(filledProperties).build();
    }
}
