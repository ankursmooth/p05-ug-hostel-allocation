<?php
session_start();

$response = array();
/*
changeroom.php
post
changeroom=anything

*/
require 'connect.php';

if(isset($_POST ['changeroom'])){
 
  
  
  $changeroom=!empty($_POST['changeroom']) ? trim($_POST['changeroom']) : null;
  $sid1=!empty($_POST['sid1']) ? trim($_POST['sid1']) : null;
  $sid2=!empty($_POST['sid2']) ? trim($_POST['sid2']) : null;
  $roomid=!empty($_POST['roomid']) ? trim($_POST['roomid']) : null;
  $hostelid=!empty($_POST['hostelid']) ? trim($_POST['hostelid']) : null;
  
  $sql = "SELECT roomid, hostelid FROM allocation where sid= :sid1";
  $stmt = $pdo->prepare($sql);
  $stmt->bindValue(':sid1', $sid1);
    
  $stmt->execute();
  $row = $stmt->fetch(PDO::FETCH_ASSOC);

  $sql = "UPDATE rooms Set rcondition = :rcondition WHERE roomid= :roomid and hostelid= :hostelid";
  $stmtin = $pdo->prepare($sql);
  $stmtin->bindValue(':hostelid', $row["hostelid"]);
  $stmtin->bindValue(':rcondition',"unallocated");
  $stmtin->bindValue(':roomid', $row["roomid"]);
  $stmtin->execute();
  $sql = "DELETE FROM allocation WHERE sid =:sid1";
  $stmtin = $pdo->prepare($sql);
  $stmtin->bindValue(':sid1', $innoform["sid1"]);
  $stmtin->execute();
  $sql = "DELETE FROM allocation WHERE sid =:sid2";
  $stmtin = $pdo->prepare($sql);
  $stmtin->bindValue(':sid2', $innoform["sid2"]);
  $stmtin->execute();
  
  $sql = "INSERT INTO allocation (hostelid, sid, roomid) VALUES (:hostelid, :sid1, :roomid)";
  $stmtin = $pdo->prepare($sql);
  $stmtin->bindValue(':hostelid', $hostelid);
  $stmtin->bindValue(':sid1', $sid1);
  $stmtin->bindValue(':roomid', $roomid);
  $stmtin->execute();
  $sql = "INSERT INTO allocation (hostelid, sid, roomid) VALUES (:hostelid, :sid2, :roomid)";
  $stmtin = $pdo->prepare($sql);
  $stmtin->bindValue(':hostelid', $hostelid);
  $stmtin->bindValue(':sid2', $sid2);
  $stmtin->bindValue(':roomid', $roomid);
  $stmtin->execute();
  $sql = "UPDATE rooms Set rcondition = :rcondition WHERE roomid= :roomid and hostelid= :hostelid";
  $stmtin = $pdo->prepare($sql);
  
  $stmtin->bindValue(':rcondition',"allocated");
  $stmtin->bindValue(':hostelid', $hostelid);
  
  $stmtin->bindValue(':roomid', $roomid);
  $stmtin->execute();
}  
else
{
  $response["success"] = 0;
  $response["message"] = "Unknown Error 2";

  echo json_encode($response);
}

?>