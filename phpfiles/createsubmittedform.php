<?php
session_start();

$response = array();

// check any saved or submitted form by that user
// **************** createsavedform.php *************
// post
// NOTE: no of student shoud always be even

// createsavedform=anything                    uid=y13uc032                  
//   sid[1]=y13uc001                           roominwing[1]=1 
//         sid[2]=y13uc011                     roominwing[2]=1                  
//   pfid[1]=1                    hostelid[1]=bh1                    floorno[1]=first         
//            pfid[2]=2                 
//    hostelid[2]=bh1                    floorno[2]=second  noofstudent=2

// {
//   "success": 1,
//   "message": "wing form may have been submitted",
//   "wfid": 1
// }

// post another example

// createsubmittedform=anything         uid=y13uc019         sid[1]=y13uc019         sid[2]=y13uc074         roominwing[1]=1         roominwing[2]=1         pfid[1]=1         hostelid[1]=bh1         floorno[1]=ground         pfid[2]=2         hostelid[2]=bh1         floorno[2]=second         sid[3]=y13uc093         sid[4]=y13uc098         roominwing[3]=2         roominwing[4]=2         noofstudent=4
// response
// {
//   "success": 1,
//   "message": "wing form may have been submitted",
//   "wfid": 1
// }


require 'connect.php';

if(isset($_POST ['createsubmittedform']) && isset($_POST ['sid']) && isset($_POST ['roominwing']) && isset($_POST ['pfid']) && isset($_POST ['hostelid']) && isset($_POST ['floorno'])){
    $uid = !empty($_POST['uid']) ? trim($_POST['uid']) : null;
    $noofstudent = !empty($_POST['noofstudent']) ? trim($_POST['noofstudent']) : null;
    
    $i=1;
    $sid = array();

    foreach($_POST['sid'] as $key => $value) {
        
        $sid[$i]=$value;
        
        $i = $i +1;
    }
    // $i=1;
    // $sname = array();
    // foreach($_POST['sname'] as $key => $value) {
        
    //     $sname[$i]=$value;
    //     //echo $key, ' => ', $value, '<br />';
    //     $i = $i +1;
    // }
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
    
    $sql = "SELECT wfid FROM wingform WHERE sid = :uid";
    $stmt = $pdo->prepare($sql);
    $stmt->bindValue(':uid', $uid);
    $result= $stmt->execute();
    
    $wfidresult = $stmt->fetch(PDO::FETCH_ASSOC);
    $wfid=0;
    $alreadysubmit=0;
    if($wfidresult){
        
        $wfid= $wfidresult['wfid'];
        // $sql = "DELETE FROM savedwingform WHERE sid = :uid";
        // $stmt = $pdo->prepare($sql);
        // $stmt->bindValue(':uid', $uid);
        // $stmt->execute();
        // $sql = "DELETE FROM savedwingformdetails WHERE sid = :uid";
        // $stmt = $pdo->prepare($sql);
        // $stmt->bindValue(':uid', $uid);
        // $stmt->execute();
        // $sql = "DELETE FROM savedpreferences WHERE sid = :uid";
        // $stmt = $pdo->prepare($sql);
        // $stmt->bindValue(':uid', $uid);
        // $stmt->execute();
        $alreadysubmit=1;

    }
    else{
            
            $sql = "SELECT count(*) as num FROM wingform";
            $stmt = $pdo->prepare($sql);
            
            $stmt->execute();
            $wfidresult = $stmt->fetch(PDO::FETCH_ASSOC);
            $wfid= $wfidresult['num'] + 1;
            //echo $wfid;

    }   
    if($alreadysubmit==1){
        $response["success"] = 1;
        $response["message"] = "already submitted";
        $response["wfid"] = $wfid;
        echo json_encode($response);
    }
    else{
       // echo $wfid;
        $sql = "INSERT INTO wingform (wfid, sid, noofstudent) VALUES (:wfid, :uid, :noofstudent)";
        $stmt = $pdo->prepare($sql);
        
        $stmt->bindValue(':wfid', $wfid);
        $stmt->bindValue(':uid', $uid);
        $stmt->bindValue(':noofstudent', $noofstudent);
        $result1 = $stmt->execute();

        for ($j=1; $j < $i; $j++) { 
            $sql = "INSERT INTO wingformdetails (wfid, sid, roominwing ) VALUES (:wfid, :sid, :roominwing )";
            $stmt = $pdo->prepare($sql);
            
            $stmt->bindValue(':wfid', $wfid);
            $stmt->bindValue(':sid', $sid[$j]);
            $stmt->bindValue(':roominwing', $roominwing[$j]);
            //$stmt->bindValue(':sname', $sname[$j]);
            $result2 = $stmt->execute();    
        
        }
        for ($j=1; $j < $it ; $j++) { 
            # code...
            $sql = "INSERT INTO preferences (wfid, pfid, hostelid, floorno) VALUES (:wfid, :pfid, :hostelid, :floorno)";
            $stmt = $pdo->prepare($sql);
            
            $stmt->bindValue(':wfid', $wfid);
            $stmt->bindValue(':hostelid', $hostelid[$j]);
            $stmt->bindValue(':pfid', $pfid[$j]);
            
            $stmt->bindValue(':floorno', $floorno[$j]);
            $result2 = $stmt->execute();
        }
        
        
        
        if($result2){
        
                $response["success"] = 1;
                $response["message"] = "wing form may have been submitted";
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

 }  
else
{
    $response["success"] = 0;
    $response["message"] = "Unknown Error 2";

    echo json_encode($response);
}

?>