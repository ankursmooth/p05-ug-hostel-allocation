package com.example.ankurshukla.hostel.Controller;

/**
 * Created by Ankur Shukla on 10/20/2015.
 */
public class AppConfig {

   // public static String linkvar   = "192.168.133.1/sepmdb";

    public static String linkvar = "hostel.16mb.com";

    // Server create notification url
    public static String URL_CREATE_NOTIFY = "http://"+linkvar+"/createnotification.php";

    //Server get notifications url
    public static String URL_GET_NOTIFY="http://"+linkvar+"/getnotification.php";

    //Server Login URL
    public static String URL_LOGIN="http://"+linkvar+"/login.php";

    //Allocation create
    public static String URl_CREATEALLOPROCESS="http://"+linkvar+"/createalloprocess.php";

    //for resetting datbase
    public  static  String URL_RESET = "http://"+linkvar+"/resetDB.php";

    //Allocation create
    public static String URl_CHECKFORM="http://"+linkvar+"/checkform.php";

    //Check for whether connection to database has been made or not
    public static String URL_CHECKCONNECT="http://"+linkvar+"/checkconnect.php";

    //for saved form
    public static String URL_SAVEDFORM = "http://"+linkvar+"/createsavedform.php";

    //for submitting form for the first time
    public static String URL_SUBMITFORM = "http://"+linkvar+"/createsubmittedform.php";

    //for getting saved form if present
    public static String URL_GETSAVEDFORM = "http://"+linkvar+"/getsavedform.php";

    //for getting saved form if present
    public static String URL_GETSUBMITTEDFORM = "http://"+linkvar+"/getsubmittedform.php";

    //for getting search result
    public static  String URL_SEARCH="http://"+linkvar+"/search.php";

    //for checking conflicts
    public static String URL_CONFLICTS="http://"+linkvar+"/checkconflicts.php";

    //for doing allocation
    public static String URL_DOALLOCATION="http://"+linkvar+"/doallocation.php";

    //for creating special request
    public static String URL_CREATESR="http://"+linkvar+"/createsr.php";

    //for getting Special request
    public static String URL_GETSR="http://"+linkvar+"/getsr.php";

    //for gettingist of empty rooms
    public  static  String URL_EMPTYROOMS = "http://"+linkvar+"/getemptyrooms.php";

    //for changing room
    public  static  String URL_CHANGEROOM =  "http://"+linkvar+"/changeroom.php";

    //for responding to sr
    public static String URL_RESPONDSR =  "http://"+linkvar+"/respondtosr.php";

}
