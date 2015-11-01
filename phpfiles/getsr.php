<?php
session_start();

$response = array();
/*
getsr.php
post
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
        $requez["reqmessage"]=$notic["message"];
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
        $requez["reqmessage"]=$notic["message"];
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