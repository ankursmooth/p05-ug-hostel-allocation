<?php
session_start();

$response = array();

//require 'message.php';

require 'connect.php';

if(isset($_POST ['createnotification'])){
    //$createnotification=!empty($_POST['register']) ? trim($_POST['register']) : null;
    //echo $register;
    //if($register=="2"){
    $usergroup = !empty($_POST['usergroup']) ? trim($_POST['usergroup']) : null;
    $ntype = !empty($_POST['ntype']) ? trim($_POST['ntype']) : null;
    $creatorid = !empty($_POST['creatorid']) ? trim($_POST['creatorid']) : null;
    $ndate = !empty($_POST['ndate']) ? trim($_POST['ndate']) : null;
    $message = !empty($_POST['message']) ? trim($_POST['message']) : null;
    
    
    
    $sql = "SELECT COUNT(*) AS num FROM notifications";
    $stmt = $pdo->prepare($sql);
    
    
    
    $stmt->execute();
    
    $row = $stmt->fetch(PDO::FETCH_ASSOC);
    
    
    
    
    $sql = "INSERT INTO notifications (nfid, usergroup, creatorid, ntype, message, ndate) VALUES (:nfid, :usergroup, :creatorid, :ntype, :message, :ndate)";
    $stmt = $pdo->prepare($sql);
    
    $stmt->bindValue(':nfid', (string)((int)$row['num'] + 1));
    $stmt->bindValue(':usergroup', $usergroup);
    $stmt->bindValue(':creatorid', $creatorid);
    $stmt->bindValue(':ntype', $ntype);
    $stmt->bindValue(':message', $message);
    $stmt->bindValue(':ndate', $ndate);
    
    $result = $stmt->execute();
    
    if($result){
    
            $response["success"] = 1;
            $response["message"] = "notification sent to other users";
            $response["nfid"] = (string)((int)$row['num'] + 1);
            $response["usergroup"] = $usergroup;
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