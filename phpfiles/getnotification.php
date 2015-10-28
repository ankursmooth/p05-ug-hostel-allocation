<?php
session_start();

$response = array();
/*
getnotification.php
post
usergroup=student&uid=y13uc032&getnotification=number
response
{
  "success": 1,
  "message": "no of notifications for this user",
  "noofnotifications": 2
}

post
usergroup=student&uid=y13uc032&getnotification=everything
response will be this when no of notifications are 0
{
  "success": 1,
  "message": "no of notifications for this user",
  "noofnotifications": 0
}
response when no of notifications >0
{
  "notiz": [
    {
      "nfid": "1",
      "usergroup": "student",
      "nmessage": "allocation process from this to this",
      "creatorid": "neogi",
      "ntype": "allocation",
      "ndate": "2015-10-19"
    },
    {
      "nfid": "2",
      "usergroup": "y13uc032",
      "nmessage": "allocation process from this to this",
      "creatorid": "neogi",
      "ntype": "allocation",
      "ndate": "2015-10-19"
    }
  ],
  "success": 1,
  "message": "notifications for this user",
  "noofnotifications": 2
}
*/

require 'connect.php';

if(isset($_POST ['getnotification'])){
   
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