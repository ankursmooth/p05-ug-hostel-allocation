<?php
session_start();

$response = array();

//require 'message.php';

require 'connect.php';

if(isset($_POST ['checkconnect'])){
    //$createnotification=!empty($_POST['register']) ? trim($_POST['register']) : null;
    //echo $register;
    //if($register=="2"){
    
    
    $sql = "SELECT COUNT(*) AS num FROM warden";
    $stmt = $pdo->prepare($sql);
    
    
    
    $stmt->execute();
    
    $row = $stmt->fetch(PDO::FETCH_ASSOC);
    
    
    
    
    
    if($row['num']>0){
    
            $response["success"] = 1;
            $response["message"] = "connection working";
            $response["registeredwardens"] = $row['num'];
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