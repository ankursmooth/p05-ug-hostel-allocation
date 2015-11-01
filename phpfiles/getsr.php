<?php
session_start();

$response = array();
/*
getsr.php
post of two types
first type when student is logged in
getsr=anything 
usertype=student 
uid=y13uc032
response
{
  "requez": [
    {
      "rqid": "1",
      "sid": "y13uc032",
      "reqmessage": "me and my roomie ankur shukla y13uc033 are fed up of water leakage problem. please change our room",
      "responsebyid": "neogi",
      "reqresponse": "ok. changed your room",
      "rdate": "2015-10-23"
    }
  ],
  "success": 1,
  "message": "requests for this user",
  "noofrequests": 1
}

second type when warden is logged in
post 
getsr=anything
usertype=warden 

response
{
    "requez": [
        {
            "rqid": "1",
            "sid": "y13uc032",
            "reqmessage": "me and my roomie ankur shukla y13uc033 are fed up of water leakage problem. please change our room",
            "responsebyid": "neogi",
            "reqresponse": "ok. changed your room",
            "rdate": "2015-10-23"
        }
    ],
    "success": 1,
    "message": "requests for this user",
    "noofrequests": 1
}

s*/

require 'connect.php';

if(isset($_POST ['getsr'])){
   
    
    
  $getsr=!empty($_POST['getsr']) ? trim($_POST['getsr']) : null;
  
  $usertype=!empty($_POST['usertype']) ? trim($_POST['usertype']) : null;
  
  if($usertype=="student"){
    $uid = !empty($_POST['uid']) ? trim($_POST['uid']) : null;
    $sql = "SELECT COUNT(rqid) AS num FROM srequest where sid = :uid";
    $stmt = $pdo->prepare($sql);
    $stmt->bindValue(':uid', $uid);
    
    $stmt->execute();
    
    $row = $stmt->fetch(PDO::FETCH_ASSOC);
    if($getsr == "number" || $row['num']==0){
      if($row){
              $response["success"] = 1;
              $response["message"] = "no of requests for this user";
              $response["noofrequests"] = $row['num'];
              
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

      $sql = "SELECT * FROM srequest where sid = :uid";
      $stmt = $pdo->prepare($sql);
      $stmt->bindValue(':uid', $uid);
      
      $stmt->execute();
      $requezes= array();
      $requezes["requez"]= array();

      while($notic = $stmt->fetch(PDO::FETCH_ASSOC)){
        $requez=array();
        $requez["rqid"]=$notic["rqid"];
        $requez["sid"]=$notic["sid"];
        $requez["reqmessage"]=$notic["reqmessage"];
        $requez["responsebyid"]=$notic["responsebyid"];
        $requez["reqresponse"]=$notic["reqresponse"];
        $requez["rdate"]=$notic["rdate"];
        array_push($requezes["requez"], $requez);
      }    
      
      
      if($requezes){
        $requezes["success"] = 1;
        $requezes["message"] = "requests for this user";
        $requezes["noofrequests"] = $row['num'];
        
        echo json_encode($requezes);
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
    $sql = "SELECT COUNT(rqid) AS num FROM srequest ";
    $stmt = $pdo->prepare($sql);
    
    
    $stmt->execute();
    
    $row = $stmt->fetch(PDO::FETCH_ASSOC);
    if($getsr == "number" || $row['num']==0){
      if($row){
              $response["success"] = 1;
              $response["message"] = "no of requests for this user";
              $response["noofrequests"] = $row['num'];
              
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

      $sql = "SELECT * FROM srequest";
      $stmt = $pdo->prepare($sql);
      
      
      $stmt->execute();
      $requezes= array();
      $requezes["requez"]= array();

      while($notic = $stmt->fetch(PDO::FETCH_ASSOC)){
        $requez=array();
        $requez["rqid"]=$notic["rqid"];
        $requez["sid"]=$notic["sid"];
        $requez["reqmessage"]=$notic["reqmessage"];
        $requez["responsebyid"]=$notic["responsebyid"];
        $requez["reqresponse"]=$notic["reqresponse"];
        $requez["rdate"]=$notic["rdate"];
        array_push($requezes["requez"], $requez);
      }    
      
      
      if($requezes){
        $requezes["success"] = 1;
        $requezes["message"] = "requests for this user";
        $requezes["noofrequests"] = $row['num'];
        
        echo json_encode($requezes);
      }
      else
      {
        $response["success"] = 0;
        $response["message"] = "Unknown Error";

        echo json_encode($response);
      }
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