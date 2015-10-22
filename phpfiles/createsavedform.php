<?php
session_start();

$response = array();

//require 'hostelid.php';

require 'connect.php';

if(isset($_POST ['createsavedform']) && isset($_POST ['sid']) && isset($_POST ['sname']) && isset($_POST ['roominwing']) && isset($_POST ['pfid']) && isset($_POST ['hostelid']) && isset($_POST ['floorno'])){
    $uid = !empty($_POST['uid']) ? trim($_POST['uid']) : null;
    
    $i=1;
    $sid = array();

    foreach($_POST['sid'] as $key => $value) {
        
        $sid[$i]=$value;
        //echo $key, ' => ', $value, '<br />';
        $i = $i +1;
    }
    $i=1;
    $sname = array();
    foreach($_POST['sname'] as $key => $value) {
        
        $sname[$i]=$value;
        //echo $key, ' => ', $value, '<br />';
        $i = $i +1;
    }
    $i=1;
    $roominwing = array();
    foreach($_POST['roominwing'] as $key => $value) {
        
        $roominwing[$i]=$value;
        //echo $key, ' => ', $value, '<br />';
        $i = $i +1;
    }
    $it=1;
    $pfid = array();
    foreach($_POST['pfid'] as $key => $value) {
        
        $pfid[$it]=$value;
        //echo $key, ' => ', $value, '<br />';
        $it = $it +1;
    }
    $it=1;
    $hostelid = array();
    foreach($_POST['hostelid'] as $key => $value) {
        
        $hostelid[$it]=$value;
        //echo $key, ' => ', $value, '<br />';
        $it = $it +1;
    }
    $it=1;
    $floorno = array();
    foreach($_POST['floorno'] as $key => $value) {
        
        $floorno[$it]=$value;
        //echo $key, ' => ', $value, '<br />';
        $it = $it +1;
    }
    
    $sql = "SELECT wfid FROM savedwingform WHERE sid = :uid";
    $stmt = $pdo->prepare($sql);
    $stmt->bindValue(':uid', $uid);
    $result= $stmt->execute();
    
    $wfidresult = $stmt->fetch(PDO::FETCH_ASSOC);

    if($wfidresult){
        
        $wfid= $wfidresult['wfid'];
        $sql = "DELETE FROM savedwingform WHERE sid = :uid";
        $stmt = $pdo->prepare($sql);
        $stmt->bindValue(':uid', $uid);
        $stmt->execute();
        $sql = "DELETE FROM savedwingformdetails WHERE sid = :uid";
        $stmt = $pdo->prepare($sql);
        $stmt->bindValue(':uid', $uid);
        $stmt->execute();
        $sql = "DELETE FROM savedpreferences WHERE sid = :uid";
        $stmt = $pdo->prepare($sql);
        $stmt->bindValue(':uid', $uid);
        $stmt->execute();

    }
    else{
            
            $sql = "SELECT count(*) as num FROM savedwingform WHERE sid = :uid";
            $stmt = $pdo->prepare($sql);
            $stmt->bindValue(':uid', $uid);
            $stmt->execute();
            $wfidresult = $stmt->fetch(PDO::FETCH_ASSOC);
            $wfid= $wfidresult['num'] + 1;
            //echo $wfid;

    }   
    $sql = "INSERT INTO savedwingform (wfid, sid) VALUES (:wfid, :uid)";
    $stmt = $pdo->prepare($sql);
    
    $stmt->bindValue(':wfid', $wfid);
    $stmt->bindValue(':uid', $uid);
    
    $result1 = $stmt->execute();

    for ($j=1; $j < $i; $j++) { 
        $sql = "INSERT INTO savedwingformdetails (wfid, sid, roominwing, sname) VALUES (:wfid, :sid, :roominwing, :sname)";
        $stmt = $pdo->prepare($sql);
        
        $stmt->bindValue(':wfid', $wfid);
        $stmt->bindValue(':sid', $sid[$j]);
        $stmt->bindValue(':roominwing', $roominwing[$j]);
        $stmt->bindValue(':sname', $sname[$j]);
        $result2 = $stmt->execute();    
    
    }
    for ($j=1; $j < $it ; $j++) { 
        # code...
        $sql = "INSERT INTO savedpreferences (wfid, pfid, hostelid, floorno) VALUES (:wfid, :pfid, :hostelid, :floorno)";
        $stmt = $pdo->prepare($sql);
        
        $stmt->bindValue(':wfid', $wfid);
        $stmt->bindValue(':hostelid', $hostelid[$j]);
        $stmt->bindValue(':pfid', $pfid[$j]);
        
        $stmt->bindValue(':floorno', $floorno[$j]);
        $result2 = $stmt->execute();
    }
    
    
    
    if($result2){
    
            $response["success"] = 1;
            $response["message"] = "wing form may have been saved";
            $response["wfid"] = $wfid;
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