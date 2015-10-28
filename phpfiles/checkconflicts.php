<?php
session_start();

$response = array();


require 'connect.php';

if(isset($_POST ['checkconflicts']) && isset($_POST ['sid']) ){
    
    
    $i=1;
    $sid = array();

    foreach($_POST['sid'] as $key => $value) {
        
        $sid[$i]=$value;
        
        $i = $i +1;
    }
    $conflicts=0;
    
    $response["repeat"]= array();

    for ($j=1; $j < $i; $j++) { 
        $sql = "SELECT sid, wfid from wingformdetails where sid= :sid";
        $stmt = $pdo->prepare($sql);
        
        $stmt->bindValue(':sid', $sid[$j]);
        
        $stmt->execute();
        $result2=$stmt->fetch(PDO::FETCH_ASSOC);
        if($result2){
            $conflicts++;
            $repeat=array();
            $repeat["wfid"]=$result2["wfid"];
            $repeat["sid"]=$result2["sid"];
            array_push($response["repeat"], $repeat);
        }
       
    }
    $response["success"] = 1;
    $response["conflicts"] = $conflicts;
    $response["message"] = "check value of conflics";
   
}  
else
{
    $response["success"] = 0;
    $response["message"] = "Unknown Error 2";

    echo json_encode($response);
}

?>