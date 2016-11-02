/**
 * Created by stevenshao on 27/10/2016.
 */
import static spark.Spark.*;
import com.lynxberry.serverlistaudit.library.*;

import java.util.ArrayList;

public class WebServer {
    public static void main(String[] args) {
        get("/hello", (req,res) -> {
            res.body("fox");
            return res;
            //return "Hello World";

        });

        SQLengine sqlEngine = new SQLengine();
        sqlEngine.setConfig(new Schema(),"localhost","assetdb","serverlist");
        sqlEngine.setUsernamePwd("steven","zima#9996zima#hong");

        //System.out.println("xxxx===");
        //System.out.println(sqlEngine.getRecord("xxxx"));

        //Sample code for how to throw exception
        get("/throwexception",(req,res) -> {
            res.body("abc");
            throw new Exception("Testing Exception");
        });

        //query
        get("/RecordID/:recordID",(req,res) ->{

            res.type("application/json");
            return sqlEngine.getRecord("xxxx").toJsonString();
        });

        //queryRecordByKeys
        get("/Record", (req,res) ->{
           //Get json object from request body
            //convert json string json object
            //convert json object to list of properties
            ArrayList<Property> properties = null;
            ArrayList<Record> records = sqlEngine.queryRecordbyKeys(properties);
            //convert records to json object
            //convert json object to string;
            return "json object string";
        });



        //update
        put("/RecordID/:recordID",(req,res) -> {
            //Get json object from request body
            req.body();
            //Convert json object to Record
            //use sqlEngine to replace that Record;
            return "";


        });

        //Create A new one
        post("/RecordID/:recordID",(req, res) ->{
            //Get json object from request body
            //Convert json object to Record
            //use sqlEngine to insert that Record
            return "";
        });

        exception(Exception.class, (exception,req,res) -> {
            res.body("fool panda");
            System.out.println("excep 1");
        });
        exception(Exception.class, (exception,req,res) -> {
            res.body(res.body() + "fool panda2");
            System.out.println("excep 2");
        });

    }



    public static Object someMethod() throws Exception {
        throw new Exception("xxxx");
    }

}
