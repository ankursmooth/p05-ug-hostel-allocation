<?php
session_start();

$response = array();

//require 'message.php';

require 'connect.php';

if(isset($_POST ['getnotification'])){
    //$createnotification=!empty($_POST['register']) ? trim($_POST['register']) : null;
    //echo $register;
    //if($register=="2"){
    $usergroup=!empty($_POST['usergroup']) ? trim($_POST['usergroup']) : null;
    
    $getnotification=!empty($_POST['getnotification']) ? trim($_POST['getnotification']) : null;
    
    
    $uid = !empty($_POST['uid']) ? trim($_POST['uid']) : null;
    
    $sql = "SELECT COUNT(nfid) AS num FROM notifications where (usergroup = :usergroup or  usergroup = :uid)";
    $stmt = $pdo->prepare($sql);
    $stmt->bindValue(':uid', $uid);
    $stmt->bindValue(':usergroup', $usergroup);
    
    
    $stmt->execute();
    
    $row = $stmt->fetch(PDO::FETCH_ASSOC);
    if($getnotification == "number" || $row['num']==0){
        if($row){
                $response["success"] = 1;
                $response["message"] = "no of notifications for this user";
                $response["noofnotifications"] = $row['num'];
                
                echo json_encode($response);
        }
        else
        {
            $response["success"] = 0;
            $response["message"] = "Unknown Error";

            echo json_encode($response);
        }
    }
    else{

        $sql = "SELECT * FROM notifications where (usergroup = :usergroup or  usergroup = :uid)";
        $stmt = $pdo->prepare($sql);
        $stmt->bindValue(':uid', $uid);
        $stmt->bindValue(':usergroup', $usergroup);
        
        $stmt->execute();
        $notizes= array();
        $notizes["notiz"]= array();

        while($notic = $stmt->fetch(PDO::FETCH_ASSOC)){
            $notiz=array();
            $notiz["nfid"]=$notic["nfid"];
            $notiz["usergroup"]=$notic["usergroup"];
            $notiz["nmessage"]=$notic["message"];
            $notiz["creatorid"]=$notic["creatorid"];
            $notiz["ntype"]=$notic["ntype"];
            $notiz["ndate"]=$notic["ndate"];
            array_push($notizes["notiz"], $notiz);
        }    
        
        
        if($notizes){
            $notizes["success"] = 1;
            $notizes["message"] = "notifications for this user";
            $notizes["noofnotifications"] = $row['num'];
            
            echo json_encode($notizes);
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