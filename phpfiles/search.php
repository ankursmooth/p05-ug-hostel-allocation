<?php
session_start();

$response = array();

//require 'message.php';

require 'connect.php';

if(isset($_POST ['search'])){
    
    $search=!empty($_POST['search']) ? trim($_POST['search']) : null;
    $query = !empty($_POST['query']) ? trim($_POST['query']) : null;
    $found =0;
     
        
    $sql = "SELECT COUNT(*) AS num FROM student where ".$search." Like '%".$query."%'";
    $stmt = $pdo->prepare($sql);
    
    $stmt->execute();
    
    $row = $stmt->fetch(PDO::FETCH_ASSOC);
    if($row['num']>0){
        $sql = "SELECT sid FROM student where ".$search." Like '%".$query."%'";
        $stmt = $pdo->prepare($sql);
        
        $stmt->execute();
        $notizes= array();
        $notizes["res"]= array();
        $found=1;

        while($notic = $stmt->fetch(PDO::FETCH_ASSOC)){
            $sql = "Select roomid, hostelid, A.sid as sid, name from (SELECT * FROM allocation where (roomid,hostelid) In (select roomid, hostelid from allocation where sid=:sid)) A , student B where A.sid=B.sid";
            $stmtt = $pdo->prepare($sql);
            $stmtt->bindValue(':sid', $notic['sid']);
            $stmtt->execute();
            $i=1;
            $res=array();
            while($notir = $stmtt->fetch(PDO::FETCH_ASSOC)){
                $res["hostelid"]=$notir["hostelid"];
                $res["roomid"]=$notir["roomid"];                    
                $res["sid".$i.""]=$notir["sid"];
                $res["name".$i.""]=$notir["name"];

                $i++;
            }
            array_push($notizes["res"], $res);
        }
        if($notizes){
            $notizes["success"] = 1;
            $notizes["message"] = "search results for query";
            $notizes["noofresults"] = $row['num'];
            
            echo json_encode($notizes);
        }
        else
        {
            $response["success"] = 0;
            $response["message"] = "Unknown Error";
            $response["noofresults"] = 0;
            echo json_encode($response);
        }       
    }
    if($found==0){
        $response["success"] = 1;
        $response["message"] = "Not found ".$query." indatabase";
        $response["noofresults"] = 0;
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