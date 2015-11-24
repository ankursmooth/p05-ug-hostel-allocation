<?php
session_start();

$response = array();
// to reset Databe to state where all rooms are unallocated and no form is submitted
//post resetDB=anything
//pass=12345
require 'connect.php';

if(isset($_POST ['resetDB'])&&isset($_POST ['pass'])){
 
  
  
  $resetDB=!empty($_POST['resetDB']) ? trim($_POST['resetDB']) : null;
  $pass=!empty($_POST['pass']) ? trim($_POST['pass']) : null;
  
  $sql = "DELETE FROM allocation WHERE 1";
  $stmtin = $pdo->prepare($sql);
  
  $stmtin->execute();
  $sql = "UPDATE rooms set rcondition=:rcondition WHERE 1";
  $stmtin = $pdo->prepare($sql);
  $stmtin->bindValue(':rcondition', "unallocated");
  $stmtin->execute();
  $sql = "DELETE FROM allocationprocess WHERE 1";
  $stmtin = $pdo->prepare($sql);
 
  $stmtin->execute();
  $sql = "DELETE FROM notifications WHERE 1";
  $stmtin = $pdo->prepare($sql);
 
  $stmtin->execute();
  $sql = "DELETE FROM preferences WHERE 1";
  $stmtin = $pdo->prepare($sql);
 
  $stmtin->execute();
  $sql = "DELETE FROM wingform WHERE 1";
  $stmtin = $pdo->prepare($sql);
 
  $stmtin->execute();
  $sql = "DELETE FROM wingformdetails WHERE 1";
  $stmtin = $pdo->prepare($sql);
 
  $stmtin->execute();

  $stmtin->execute();
  $sql = "DELETE FROM savedpreferences WHERE 1";
  $stmtin = $pdo->prepare($sql);
 
  $stmtin->execute();
  $sql = "DELETE FROM savedwingform WHERE 1";
  $stmtin = $pdo->prepare($sql);
 
  $stmtin->execute();
  $sql = "DELETE FROM savedwingformdetails WHERE 1";
  $stmtin = $pdo->prepare($sql);
 
  $stmtin->execute();
  
  $sql = "DELETE FROM srequest WHERE 1";
  $stmtin = $pdo->prepare($sql);
 
  $stmtin->execute();

  $response["success"] = 1;
  $response["message"] = "reset ";

  echo json_encode($response);
}  
else
{
  $response["success"] = 0;
  $response["message"] = "Unknown Error 2";

  echo json_encode($response);
}

?>
