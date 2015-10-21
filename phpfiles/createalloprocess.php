<?php
session_start();

$response = array();

//require 'message.php';

require 'connect.php';

if(isset($_POST ['createalloprocess'])){
    //$createnotification=!empty($_POST['register']) ? trim($_POST['register']) : null;
    //echo $register;
    //if($register=="2"){
    $wid = !empty($_POST['wid']) ? trim($_POST['wid']) : null;
    $startdate = !empty($_POST['startdate']) ? trim($_POST['startdate']) : null;
    $enddate = !empty($_POST['enddate']) ? trim($_POST['enddate']) : null;
     
    
    
      
        
    $sql = "INSERT INTO allocationprocess (wid, enddate, startdate) VALUES (:wid, :enddate, :startdate)";
    $stmt = $pdo->prepare($sql);
    
    
    $stmt->bindValue(':wid', $wid);
    $stmt->bindValue(':enddate', $enddate);
    $stmt->bindValue(':startdate', $startdate);
        
    $result = $stmt->execute();
    
    if($result){
    
            $response["success"] = 1;
            $response["message"] = "saved allocation process. now create notification";
            $response["wid"] = $wid;
            echo json_encode($response);
    }
    else
    {
        $response["success"] = 0;
        $response["message"] = "Unknown Error";

        echo json_encode($response);
    }

 }  
else
{
    $response["success"] = 0;
    $response["message"] = "Unknown Error 2";

    echo json_encode($response);
}

?>