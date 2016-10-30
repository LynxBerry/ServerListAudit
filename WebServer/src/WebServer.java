/**
 * Created by stevenshao on 27/10/2016.
 */
import static spark.Spark.*;
import com.lynxberry.serverlistaudit.library.*;

public class WebServer {
    public static void main(String[] args) {
        get("/hello", (req,res) -> {
            res.body("fox");
            return res;
            //return "Hello World";

        });

        SQLengine sqlEngine = new SQLengine();
        sqlEngine.setConfig(new Schema());

        //System.out.println("xxxx===");
        //System.out.println(sqlEngine.getRecord("xxxx"));

        get("/throwexception",(req,res) -> {
            res.body("abc");
            throw new Exception("Testing Exception");
        });

        get("/RecordID/:recordID",(req,res) ->{

            res.type("application/json");
            return sqlEngine.getRecord("xxxx").toJsonString();
        });

        put("/ReplaceID/:recordID",(req,res) -> {
            //Get json object from request body
            req.body();
            //Convert json object to Record
            //use sqlEngine to replace that Record;
            return "";


        });
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
