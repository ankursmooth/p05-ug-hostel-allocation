<?php
session_start();

$response = array();
/*
getemptyrooms.php
post
getemptyrooms=anything

response 
{
    "room": [
        {
            "roomid": "a001",
            "hostelid": "bh2",
            "floorno": "ground",
            "rcondition": "unallocated"
        },
        {
            "roomid": "a001",
            "hostelid": "gh",
            "floorno": "ground",
            "rcondition": "unallocated"
        },
        {
            "roomid": "a002",
            "hostelid": "bh1",
            "floorno": "ground",
            "rcondition": "unallocated"
        },
        {
            "roomid": "a002",
            "hostelid": "bh2",
            "floorno": "ground",
            "rcondition": "unallocated"
        }
    ],
    "success": 1,
    "message": "no of empty rooms",
    "noofemptyrooms": 4
}*/

require 'connect.php';

if(isset($_POST ['getemptyrooms'])){
 
  
  
  $getemptyrooms=!empty($_POST['getemptyrooms']) ? trim($_POST['getemptyrooms']) : null;
  
  
  
  $sql = "SELECT COUNT(roomid) AS num FROM rooms where rcondition = :rcondition";
  $stmt = $pdo->prepare($sql);
  $stmt->bindValue(':rcondition', "unallocated");
  
  
  
  $stmt->execute();
  
  $row = $stmt->fetch(PDO::FETCH_ASSOC);
  if($getemptyrooms == "number" || $row['num']==0){
    if($row){
            $response["success"] = 1;
            $response["message"] = "no of empty rooms";
            $response["noofemptyrooms"] = $row['num'];
            
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

    $sql = "SELECT * FROM rooms where rcondition = :rcondition";
    $stmt = $pdo->prepare($sql);
    $stmt->bindValue(':rcondition', "unallocated");
    
    $stmt->execute();
    $notizes= array();
    $notizes["room"]= array();

    while($notic = $stmt->fetch(PDO::FETCH_ASSOC)){
      $room=array();
      $room["roomid"]=$notic["roomid"];
      $room["hostelid"]=$notic["hostelid"];
      $room["floorno"]=$notic["floorno"];
      $room["rcondition"]=$notic["rcondition"];
      array_push($notizes["room"], $room);
    }    
    
    
    if($notizes){
      $notizes["success"] = 1;
      $notizes["message"] = "no of empty rooms";
      $notizes["noofemptyrooms"] = $row['num'];
          
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