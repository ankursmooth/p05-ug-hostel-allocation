<?php
session_start();

$response = array();

// search
// **************** search.php ************8
// post

// search=name/sid/contact
// query=ankur/y13uc032/9910188803


// example response
// when search = name
// and query = ka

// response structure will be same for search usid sid or contact

// {
//     "res": [
//         {
//             "hostelid": "gh",
//             "roomid": "a101",
//             "sid1": "y13uc007",
//             "name1": "ayushi",
//             "sid2": "y13uc036",
//             "name2": "anshika"
//         },
//         {
//             "hostelid": "gh",
//             "roomid": "a103",
//             "sid1": "y13uc039",
//             "name1": "anushka",
//             "sid2": "y13uc092",
//             "name2": "divya"
//         },
//         {
//             "hostelid": "bh1",
//             "roomid": "a102",
//             "sid1": "y13uc093",
//             "name1": "vikash",
//             "sid2": "y13uc098",
//             "name2": "chugani"
//         },
//         {
//             "hostelid": "gh",
//             "roomid": "a102",
//             "sid1": "y13uc160",
//             "name1": "manjari",
//             "sid2": "y13uc170",
//             "name2": "monika"
//         }
//     ],
//     "success": 1,
//     "message": "search results for query",
//     "noofresults": 4
// }


// when not found
// search=name
// query=kasss
// response
// {
//   "success": 1,
//   "message": "Not found kasss indatabase",
//   "noofresults": 0
// }


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