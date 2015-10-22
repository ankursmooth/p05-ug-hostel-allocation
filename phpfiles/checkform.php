<?php
session_start();

$response = array();

//require 'message.php';

require 'connect.php';

if(isset($_POST ['checkform'])){
    //$createnotification=!empty($_POST['register']) ? trim($_POST['register']) : null;
    //echo $register;
    //if($register=="2"){
    $uid = !empty($_POST['uid']) ? trim($_POST['uid']) : null;
    
    
    
    $sql = "SELECT COUNT(*) AS num FROM wingform where sid= :uid";
    $stmt = $pdo->prepare($sql);
    $stmt->bindValue(':uid', $uid);
    
    
    $stmt->execute();
    
    $row = $stmt->fetch(PDO::FETCH_ASSOC);
    if($row['num']>0){
            $response["success"] = 1;
            $response["message"] = "submittedFormPresent";
            echo json_encode($response);
    
    }
    else{
        $sql = "SELECT COUNT(*) AS num FROM savedwingform where sid = :uid";
        $stmt = $pdo->prepare($sql);
        $stmt->bindValue(':uid', $uid);
        
        
        $stmt->execute();
        
        $roww = $stmt->fetch(PDO::FETCH_ASSOC);
        if($roww['num']>0){
            $response["success"] = 1;
            $response["message"] = "savedFormPresent";
            echo json_encode($response);
        }
        else if($roww['num']==0){
            $response["success"] = 1;
            $response["message"] = "noFormPresent"; 
            echo json_encode($response);  
        }
        else
        {
            $response["success"] = 0;
            $response["message"] = "Unknown Error";

            echo json_encode($response);
        }
    }
    

 }  
else
{
    $response["success"] = 0;
    $response["message"] = "Unknown Error 2";

    echo json_encode($response);
}

?>