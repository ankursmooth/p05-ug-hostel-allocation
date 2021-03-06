<?php
session_start();

$response = array();

// getsavedform=anything                    uid=y13uc032


// the above uid is the uid of the student who logged in and may have filled the form
// response

// {
//   "entry": [
//     {
//       "wfid": "1",
//       "sid": "y13uc001",
//       "roominwing": "1",
//       "sname": "aadhar"
//     },
//     {
//       "wfid": "1",
//       "sid": "y13uc011",
//       "roominwing": "1",
//       "sname": "aditya"
//     }
//   ],
//   "pref": [
//     {
//       "wfid": "1",
//       "pfid": "1",
//       "floorno": "first",
//       "hostelid": "bh1"
//     },
//     {
//       "wfid": "1",
//       "pfid": "2",
//       "floorno": "second",
//       "hostelid": "bh1"
//     }
//   ],
//   "success": 1,
//   "message": "entries in this form ",
//   "noofstudent": "2",
//   "noofprefer": 2
// }

require 'connect.php';

if(isset($_POST ['getsavedform'])){
    
    
    $uid = !empty($_POST['uid']) ? trim($_POST['uid']) : null;
    
    $sql = "SELECT wfid, noofstudent, count(wfid) as num FROM savedwingform where sid = :uid";
    $stmt = $pdo->prepare($sql);
    $stmt->bindValue(':uid', $uid);
    
    
    
    $stmt->execute();
    
    $row = $stmt->fetch(PDO::FETCH_ASSOC);
    $wfid= $row['wfid'];
    if($row['num']==0){
        if($row){
                $response["success"] = 1;
                $response["message"] = "no savedwingform";
                //$response["noofnotifications"] = $row['num'];
                
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

        $sql = "SELECT * FROM savedwingformdetails where wfid = :wfid";
        $stmt = $pdo->prepare($sql);
        $stmt->bindValue(':wfid', $wfid);
        
        $stmt->execute();
        $notizes= array();
        $notizes["entry"]= array();

        while($notic = $stmt->fetch(PDO::FETCH_ASSOC)){
            $entry=array();
            $entry["wfid"]=$notic["wfid"];
            $entry["sid"]=$notic["sid"];
            $entry["roominwing"]=$notic["roominwing"];
            $entry["sname"]=$notic["sname"];
            // $entry["ntype"]=$notic["ntype"];
            // $entry["ndate"]=$notic["ndate"];
            array_push($notizes["entry"], $entry);
        }    
        
        $sql = "SELECT * FROM savedpreferences where wfid = :wfid";
        $stmt = $pdo->prepare($sql);
        $stmt->bindValue(':wfid', $wfid);
        
        $stmt->execute();
        
        $notizes["pref"]= array();
        $i=0;
        while($notic = $stmt->fetch(PDO::FETCH_ASSOC)){
            $pref=array();
            $pref["wfid"]=$notic["wfid"];
            $pref["pfid"]=$notic["pfid"];
            $pref["floorno"]=$notic["floorno"];
            $pref["hostelid"]=$notic["hostelid"];
            // $pref["ntype"]=$notic["ntype"];
            // $pref["ndate"]=$notic["ndate"];
            array_push($notizes["pref"], $pref);
            $i= $i +1;
        }
        if($notizes){
            $notizes["success"] = 1;
            $notizes["message"] = "entries in this form ";
            $notizes["noofstudent"] = $row['noofstudent'];
            $notizes["noofprefer"]= $i;
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