<?php
session_start();

$response = array();

//post doallocation and wid

require 'connect.php';
$spacess="     . ";
if(isset($_POST ['doallocation'])){
    $wid = !empty($_POST['wid']) ? trim($_POST['wid']) : null;
    

    $sql = "SELECT wfid, noofstudent FROM wingform Order by noofstudent desc";
    $stmtz = $pdo->prepare($sql);

    $stmtz->execute();
    while($row = $stmtz->fetch(PDO::FETCH_ASSOC)){
        // for every wing form
        $wfid= $row['wfid'];
        $noofstudent= $row['noofstudent'];
        $noofrooms= $noofstudent/2;
        // //echo $noofstudent;
        // //echo $wfid;
        // //echo $noofrooms;

        $sql = "SELECT * FROM preferences where wfid = :wfid";
        $stmtp = $pdo->prepare($sql);
        $stmtp->bindValue(':wfid', $wfid);
        
        $stmtp->execute();
        
        //$notizes["pref"]= array();
        //$i=0;
        $pref=array();
        $prevroominwing=0;
        while($notic2 = $stmtp->fetch(PDO::FETCH_ASSOC)){
            // check each preference
            $pref["wfid"]=$notic2["wfid"];
            $pref["pfid"]=$notic2["pfid"];
            $pref["floorno"]=$notic2["floorno"];
            $pref["hostelid"]=$notic2["hostelid"];
            if($pref["hostelid"]=="gh"){
                $gh=true;
            }
            else{
                $gh=false;
            }
            // $pref["ntype"]=$notic["ntype"];
            // $pref["ndate"]=$notic["ndate"];
            $sql = "SELECT hostelid, floorno, count(*) as num FROM rooms where hostelid = :hostelid and floorno= :floorno and rcondition = :rcondition";
            $stmt = $pdo->prepare($sql);
            $stmt->bindValue(':rcondition',"unallocated");
            $stmt->bindValue(':hostelid', $pref["hostelid"]);
            $stmt->bindValue(':floorno', $pref["floorno"]);
            $stmt->execute();
            $checkroom = $stmt->fetch(PDO::FETCH_ASSOC);
            if($checkroom['num']>=$noofrooms){
                // do allocation
                $sql = "SELECT hostelid, floorno, roomid FROM rooms where hostelid = :hostelid and floorno= :floorno and rcondition = :rcondition ";
                $stmt = $pdo->prepare($sql);
                $stmt->bindValue(':rcondition',"unallocated");
                $stmt->bindValue(':hostelid', $pref["hostelid"]);
                $stmt->bindValue(':floorno', $pref["floorno"]);
                $stmt->execute();
                $roomss=array();
                $hostelss=array();
                $i=1;
                while($userooms = $stmt->fetch(PDO::FETCH_ASSOC)){
                    $roomss[$i]=$userooms['roomid'];
                    $hostelss[$i]=$userooms['hostelid'];
                    $roomss[$i+1]=$userooms['roomid'];
                    $hostelss[$i+1]=$userooms['hostelid'];
                    $i= $i +2;
                }

                break;

            }

        }//preferences loop

        if($prevroominwing==0){
            // no preference satisfied
            if($gh==true){
                $sql = "SELECT hostelid, floorno, roomid FROM rooms where (hostelid = :hostelid and rcondition = :rcondition) order by floorno ASC ";
                $stmt = $pdo->prepare($sql);
                $stmt->bindValue(':rcondition',"unallocated");
                $stmt->bindValue(':hostelid', "gh");
                $stmt->execute();
                $roomss=array();
                $hostelss=array();
                $i=1;
                while($userooms = $stmt->fetch(PDO::FETCH_ASSOC)){
                    $roomss[$i]=$userooms['roomid'];
                    $hostelss[$i]=$userooms['hostelid'];
                    $roomss[$i+1]=$userooms['roomid'];
                    $hostelss[$i+1]=$userooms['hostelid'];
                    $i= $i +2;
                }
        
            }                
            else{
                $sql = "SELECT hostelid, floorno, roomid FROM rooms where ( (hostelid = :hid1 or hostelid = :hid2) and rcondition = :rcondition) order by hostelid ASC, floorno ASC ";
                $stmt = $pdo->prepare($sql);
                $stmt->bindValue(':rcondition',"unallocated");
                $stmt->bindValue(':hid2',"bh1");
                $stmt->bindValue(':hid1',"bh2");
                $stmt->execute();
                $roomss=array();
                $hostelss=array();
                $i=1;
                while($userooms = $stmt->fetch(PDO::FETCH_ASSOC)){
                    $roomss[$i]=$userooms['roomid'];
                    $hostelss[$i]=$userooms['hostelid'];
                    $roomss[$i+1]=$userooms['roomid'];
                    $hostelss[$i+1]=$userooms['hostelid'];
                    $i= $i +2;
                }

            }            
        }
        // till now saved roomss and hostelss... now actual allotting
        $sql = "SELECT * FROM wingformdetails where wfid = :wfid order by roominwing asc";
        $stmtT = $pdo->prepare($sql);
        $stmtT->bindValue(':wfid', $wfid);
        
        $stmtT->execute();
        $notizes= array();
        $notizes["entry"]= array();
        $entry=array();
        $prevroominwing=1;
        // $pref["ndate"]=$notic["ndate"];
        $i=1;
        while($notic = $stmtT->fetch(PDO::FETCH_ASSOC)){
            
            $entry["wfid"]=$notic["wfid"];
            $entry["sid"]=$notic["sid"];
            $entry["roominwing"]=$notic["roominwing"];

            //if($prevroominwing==$entry["roominwing"]){
            $sql = "INSERT INTO allocation (hostelid, sid, roomid) VALUES (:hostelid, :sid, :roomid)";
            $stmt = $pdo->prepare($sql);
            $stmt->bindValue(':hostelid', $hostelss[$i]);
            $stmt->bindValue(':sid', $entry["sid"]);
            $stmt->bindValue(':roomid', $roomss[$i]);
            $stmt->execute();
            //echo $entry["sid"];       //echo $hostelss[$i]; //echo $roomss[$i];
        //echo $spacess;
            $sql = "UPDATE rooms Set rcondition = :rcondition WHERE roomid= :roomid and hostelid= :hostelid";
            $stmt = $pdo->prepare($sql);
            $stmt->bindValue(':rcondition',"allocated");
            $stmt->bindValue(':hostelid', $hostelss[$i]);
           
            $stmt->bindValue(':roomid', $roomss[$i]);
            $stmt->execute();


            $i= $i +1;
        
        }
    }
        
    // ************** alocation for not in submitted form for bh ************
    //get all unallocated rooms in bh
    $sql = "SELECT hostelid, floorno, roomid FROM rooms where ( (hostelid = :hid1 or hostelid = :hid2) and rcondition = :rcondition) order by hostelid ASC, floorno ASC ";
    $stmt = $pdo->prepare($sql);
    $stmt->bindValue(':rcondition',"unallocated");
    $stmt->bindValue(':hid2',"bh1");
    $stmt->bindValue(':hid1',"bh2");
    $stmt->execute();
    $roomss=array();
    $hostelss=array();
    $i=1;
    while($userooms = $stmt->fetch(PDO::FETCH_ASSOC)){
        $roomss[$i]=$userooms['roomid'];
        $hostelss[$i]=$userooms['hostelid'];
        $roomss[$i+1]=$userooms['roomid'];
        $hostelss[$i+1]=$userooms['hostelid'];
        $i= $i +2;
    }
    $sql = "SELECT sid, sex FROM student WHERE sex = :sex AND sid NOT IN (SELECT sid FROM wingformdetails) ";
    $stmt = $pdo->prepare($sql);
    $sex="M";
    $stmt->bindValue(':sex', $sex);
    $stmt->execute();
    $i=1;
    //do allocation after gettings sid of boys
    while($innoform = $stmt->fetch(PDO::FETCH_ASSOC)){
            
        //echo $innoform["sid"];       //echo $hostelss[$i]; //echo $roomss[$i];
        //echo $spacess;

        //if($prevroominwing==$entry["roominwing"]){
        $sql = "INSERT INTO allocation (hostelid, sid, roomid) VALUES (:hostelid, :sid, :roomid)";
        $stmtin = $pdo->prepare($sql);
        $stmtin->bindValue(':hostelid', $hostelss[$i]);
        $stmtin->bindValue(':sid', $innoform["sid"]);
        $stmtin->bindValue(':roomid', $roomss[$i]);
        $stmtin->execute();
        $sql = "UPDATE rooms Set rcondition = :rcondition WHERE roomid= :roomid and hostelid= :hostelid";
        $stmtin = $pdo->prepare($sql);
        $stmtin->bindValue(':hostelid', $hostelss[$i]);
        $stmtin->bindValue(':rcondition',"allocated");
        $stmtin->bindValue(':roomid', $roomss[$i]);
        $stmtin->execute();


        $i= $i +1;
            
    }
    // ************** alocation for not in submitted form for gh ************
    //get all unallocated rooms in gh
    $sql = "SELECT hostelid, floorno, roomid FROM rooms where ( hostelid = :hid1 and rcondition = :rcondition) order by hostelid ASC, floorno ASC ";
    $stmt = $pdo->prepare($sql);
    $stmt->bindValue(':rcondition',"unallocated");
    $stmt->bindValue(':hid1',"gh");
    $stmt->execute();
    $roomsss=array();
    $hostelsss=array();
    $i=1;
    while($userooms = $stmt->fetch(PDO::FETCH_ASSOC)){
        $roomsss[$i]=$userooms['roomid'];
        $hostelsss[$i]=$userooms['hostelid'];
        $roomsss[$i+1]=$userooms['roomid'];
        $hostelsss[$i+1]=$userooms['hostelid'];
        $i= $i +2;
    }
    $sql = "SELECT sid, sex FROM student WHERE sex = :sex AND sid NOT IN (SELECT sid FROM wingformdetails) ";
    $stmtin = $pdo->prepare($sql);
    $sex="F";
    $stmtin->bindValue(':sex', $sex);
    $stmtin->execute();
    $i=1;
    //do allocation after gettings sid of girls
    while($innoform = $stmtin->fetch(PDO::FETCH_ASSOC)){
            
        
        //if($prevroominwing==$entry["roominwing"]){
        //echo $innoform["sid"];       //echo $hostelsss[$i]; //echo $roomsss[$i];
        //echo $spacess;
        $sql = "INSERT INTO allocation (hostelid, sid, roomid) VALUES (:hostelid, :sid, :roomid)";
        $stmt = $pdo->prepare($sql);
        $stmt->bindValue(':hostelid', $hostelsss[$i]);
        $stmt->bindValue(':sid', $innoform["sid"]);
        $stmt->bindValue(':roomid', $roomsss[$i]);
        $stmt->execute();
        $sql = "UPDATE rooms Set rcondition = :rcondition WHERE roomid= :roomid and hostelid= :hostelid";
        $stmt = $pdo->prepare($sql);
        $stmt->bindValue(':hostelid', $hostelss[$i]);
        $stmt->bindValue(':rcondition',"allocated");
        $stmt->bindValue(':roomid', $roomss[$i]);
        $stmt->execute();


        $i= $i +1;
            
    }
    $response["success"] = 1;
    $response["message"] = "Done!";
    echo json_encode($response);
}
else
{
    $response["success"] = 0;
    $response["message"] = "Unknown Error 2";

    echo json_encode($response);
}

?>