package com.example.ankurshukla.hostel.Controller;

/**
 * Created by Ankur Shukla on 10/20/2015.
 */
public class AppConfig {

    // Server create notification url
    public static String URL_CREATE_NOTIFY = "http://192.168.133.1/sepmdb/createnotification.php";

    //Server get notifications url
    public static String URL_GET_NOTIFY="http://192.168.133.1/sepmdb/getnotification.php";

    //Server Login URL
    public static String URL_LOGIN="http://192.168.133.1/sepmdb/login.php";

    //Allocation create
    public static String URl_CREATEALLOPROCESS="http://192.168.133.1/sepmdb/createalloprocess.php";

    //Allocation create
    public static String URl_CHECKFORM="http://192.168.133.1/sepmdb/checkform.php";

    //Check for whether connection to database has been made or not
    public static String URL_CHECKCONNECT="http://192.168.133.1/sepmdb/checkconnect.php";

    //for saved form
    public static String URL_SAVEDFORM = "http://192.168.133.1/sepmdb/createsavedform.php";

    //for getting saved form if present
    public static String URL_GETSAVEDFORM = "http://192.168.133.1/sepmdb/getsavedform.php";

}
