<?php
session_start();

$response = array();

//require 'hostelid.php';

require 'connect.php';

if(isset($_POST ['doallocation'])){
    $wid = !empty($_POST['wid']) ? trim($_POST['wid']) : null;
    

    $sql = "SELECT wfid, noofstudent FROM wingform Order by noofstudent desc";
    $stmt = $pdo->prepare($sql);

    $stmt->execute();
    while($row = $stmt->fetch(PDO::FETCH_ASSOC)){
        // for every wing form
        $wfid= $row['wfid'];
        $noofstudent= $row['noofstudent'];
        $noofrooms= $noofstudent/2;
        // echo $noofstudent;
        // echo $wfid;
        // echo $noofrooms;

        $sql = "SELECT * FROM preferences where wfid = :wfid";
        $stmt = $pdo->prepare($sql);
        $stmt->bindValue(':wfid', $wfid);
        
        $stmt->execute();
        
        $notizes["pref"]= array();
        //$i=0;
        $pref=array();
        $prevroominwing=0;
        while($notic = $stmt->fetch(PDO::FETCH_ASSOC)){
            // check each preference
            $pref["wfid"]=$notic["wfid"];
            $pref["pfid"]=$notic["pfid"];
            $pref["floorno"]=$notic["floorno"];
            $pref["hostelid"]=$notic["hostelid"];
            if($pref["hostelid"]=="gh"){
                $gh=true;
            }
            else
                $gh=false;
            // $pref["ntype"]=$notic["ntype"];
            // $pref["ndate"]=$notic["ndate"];
            $sql = "SELECT hostelid, floorno, count(*) as num FROM rooms where hostelid = :hostelid and floorno= :floorno and rcondition = "unallocated"";
            $stmt = $pdo->prepare($sql);

            $stmt->bindValue(':hostelid', $pref["hostelid"]);
            $stmt->bindValue(':floorno', $pref["floorno"]);
            $stmt->execute();
            $checkroom = $stmt->fetch(PDO::FETCH_ASSOC);
            if($checkroom['num']>=$noofrooms){
                // do allocation
                $sql = "SELECT * FROM wingformdetails where wfid = :wfid order by roominwing asc";
                $stmt = $pdo->prepare($sql);
                $stmt->bindValue(':wfid', $wfid);
                
                $stmt->execute();
                $notizes= array();
                $notizes["entry"]= array();
                $entry=array();
                $prevroominwing=1;
                // $pref["ndate"]=$notic["ndate"];
                $sql = "SELECT hostelid, floorno, roomid as num FROM rooms where hostelid = :hostelid and floorno= :floorno and rcondition = "unallocated"";
                $stmt = $pdo->prepare($sql);

                $stmt->bindValue(':hostelid', $pref["hostelid"]);
                $stmt->bindValue(':floorno', $pref["floorno"]);
                $stmt->execute();
                $roomss=array();
                $hostelss=array();
                while($userooms = $stmt->fetch(PDO::FETCH_ASSOC)){
                    $roomss[$i]=$userooms['roomid'];
                    $hostelss[$i]=$userooms['hostelid'];
                }
                while($notic = $stmt->fetch(PDO::FETCH_ASSOC)){
                    
                    $entry["wfid"]=$notic["wfid"];
                    $entry["sid"]=$notic["sid"];
                    $entry["roominwing"]=$notic["roominwing"];

                    if($prevroominwing==$entry["roominwing"]){
                        $sql = "INSERT INTO allocaton (hostelid, sid, roomid) VALUES (:hostelid, :sid, :roomid)";
                        $stmt = $pdo->prepare($sql);
                        $stmt->bindValue(':hostelid', $pref["hostelid"]);
                        $stmt->bindValue(':sid', $pref["sid"]);
                        $stmt->execute();
                    }
                    $prevroominwing=$entry["roominwing"];

                    //$entry["sname"]=$notic["sname"];
                    // $entry["ntype"]=$notic["ntype"];
                    // $entry["ndate"]=$notic["ndate"];
                    //array_push($notizes["entry"], $entry);
                }    
        

                break;
            }

        }
        if($prevroominwing==0){
            // no preference satisfied
            if($gh==true){
                $sql = "SELECT * FROM wingformdetails where wfid = :wfid order by roominwing asc";
                $stmt = $pdo->prepare($sql);
                $stmt->bindValue(':wfid', $wfid);
                
                $stmt->execute();
                $notizes= array();
                $notizes["entry"]= array();
                $entry=array();
                $prevroominwing=1;
                while($notic = $stmt->fetch(PDO::FETCH_ASSOC)){
                    
                    $entry["wfid"]=$notic["wfid"];
                    $entry["sid"]=$notic["sid"];
                    $entry["roominwing"]=$notic["roominwing"];

                    if($prevroominwing==$entry["roominwing"]){
                        $sql = "INSERT INTO allocaton (hostelid, sid, roomid) VALUES (:hostelid, :sid, :roomid)";
                        $stmt = $pdo->prepare($sql);
                        $stmt->bindValue(':hostelid', $pref["hostelid"]);
                        $stmt->bindValue(':sid', $pref["sid"]);
                        $stmt->execute();
                    }
                    $prevroominwing=$entry["roominwing"];

                    //$entry["sname"]=$notic["sname"];
                    // $entry["ntype"]=$notic["ntype"];
                    // $entry["ndate"]=$notic["ndate"];
                    //array_push($notizes["entry"], $entry);
                }    
            }
            else{

            }
        }

    }
        
    // not in submitted form
    $sql = "SELECT sid, sex order FROM student WHERE sid NOT IN (SELECT sid FROM wingformdetails) order by sex desc ";
    $stmt = $pdo->prepare($sql);
    $stmt->execute();
    $innoform = $stmt->fetch(PDO::FETCH_ASSOC);
    
    }
    /*
    $i=1;
    $sid = array();

    foreach($_POST['sid'] as $key => $value) {
        
        $sid[$i]=$value;
        //echo $key, ' => ', $value, '<br />';
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
            
            $sql = "SELECT count(*) as num FROM wingform WHERE sid = :uid";
            $stmt = $pdo->prepare($sql);
            $stmt->bindValue(':uid', $uid);
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
        $sql = "INSERT INTO wingform (wfid, sid) VALUES (:wfid, :uid)";
        $stmt = $pdo->prepare($sql);
        
        $stmt->bindValue(':wfid', $wfid);
        $stmt->bindValue(':uid', $uid);
        
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
*/
 }  
else
{
    $response["success"] = 0;
    $response["message"] = "Unknown Error 2";

    echo json_encode($response);
}

?>