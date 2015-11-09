<?php
session_start();

$response = array();


// **************** getwardendetails.php ************8
// post

// getwardendetails=anything

// response has the details of all wardens
// {
//     "warden": [
//         {
//             "name": "Amit neogi",
//             "email": "amit.neofi@gmail.com",
//             "position": "HEAD",
//             "contact": "9891011211"
//         }
//     ],
//     "success": 1,
//     "message": " warden details",
//     "noofwarden": 1
// }


require 'connect.php';

if(isset($_POST ['getwardendetails'])){

    $sql = "SELECT name,email,position,contact from warden";
    $stmt = $pdo->prepare($sql);
    
    $stmt->execute();
    $cnt=0;
    $notizes= array();
    $notizes["warden"]= array();
    while($row = $stmt->fetch(PDO::FETCH_ASSOC)){
        $cnt++;

        $warden=array();
        $warden["name"]=$row["name"];
        $warden["email"]=$row["email"];
        $warden["position"]=$row["position"];
        $warden["contact"]=$row["contact"];
        array_push($notizes["warden"], $warden);
    }
    if($cnt==0){
        $response["success"] = 1;
        $response["message"] = "No warden details";
        $response["noofwarden"]= 0;
        echo json_encode($response);
    }
    else{
        $notizes["success"] = 1;
        $notizes["message"] = " warden details";
        $notizes["noofwarden"]= $cnt;
        echo json_encode($notizes);
    }
    
}  
else
{
    $response["success"] = 0;
    $response["message"] = "Unknown Error 2";

    echo json_encode($response);
}

?>