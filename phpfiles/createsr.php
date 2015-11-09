<?php
session_start();

$response = array();


// **************** createsr.php ************8
// post

// createsr=anything  
// sid=y13uc032        reqmessage=me and my roomie ankur shukla y13uc033 are fed up of water leakage problem. please change our room        rdate=2015-10-23
// response 
// {
//     "success": 1,
//     "message": "request submitted",
//     "rqid": "1"
// }


require 'connect.php';

if(isset($_POST ['createsr'])){
  
    $sid = !empty($_POST['sid']) ? trim($_POST['sid']) : null;
    $reqmessage = !empty($_POST['reqmessage']) ? trim($_POST['reqmessage']) : null;
    $rdate = !empty($_POST['rdate']) ? trim($_POST['rdate']) : null;
    
    
    
    $sql = "SELECT COUNT(*) AS num FROM srequest";
    $stmt = $pdo->prepare($sql);
    
    
    $stmt->execute();
    
    $row = $stmt->fetch(PDO::FETCH_ASSOC);
    
    
    
    
    $sql = "INSERT INTO srequest (rqid, sid, responsebyid, reqresponse, reqmessage, rdate) VALUES (:rqid, :sid, :responsebyid, :reqresponse, :reqmessage, :rdate)";
    $stmt = $pdo->prepare($sql);
    
    $stmt->bindValue(':rqid', (string)((int)$row['num'] + 1));
    $stmt->bindValue(':sid', $sid);
    $stmt->bindValue(':responsebyid', "not yet responded");
    $stmt->bindValue(':reqresponse', "not yet responded");
    $stmt->bindValue(':reqmessage', $reqmessage);
    $stmt->bindValue(':rdate', $rdate);
    
    $result = $stmt->execute();
    
    if($result){
    
            $response["success"] = 1;
            $response["message"] = "request submitted";
            $response["rqid"] = (string)((int)$row['num'] + 1);
            
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