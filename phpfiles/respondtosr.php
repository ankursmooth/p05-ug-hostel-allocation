<?php
session_start();

$response = array();

//require 'reqresponse.php';

require 'connect.php';

if(isset($_POST ['respondtosr'])){
  
    $wid = !empty($_POST['wid']) ? trim($_POST['wid']) : null;
    $reqresponse = !empty($_POST['reqresponse']) ? trim($_POST['reqresponse']) : null;
    $rqid = !empty($_POST['rqid']) ? trim($_POST['rqid']) : null;
    
    
    $sql = "SELECT COUNT(*) AS num FROM srequest where rqid= :rqid";
    $stmt = $pdo->prepare($sql);
    $stmt->bindValue(':rqid', $rqid);
    
    $stmt->execute();
    
    $row = $stmt->fetch(PDO::FETCH_ASSOC);
    
    
    if($row['num']==1)
    {
        $sql = "UPDATE srequest SET responsebyid= :responsebyid, reqresponse=:reqresponse where rqid=:rqid";
        $stmt = $pdo->prepare($sql);
        
        
        $stmt->bindValue(':responsebyid', $wid);
        $stmt->bindValue(':reqresponse', $reqresponse);
        $stmt->bindValue(':rqid', $rqid);
        
        $result = $stmt->execute();
        
        if($result){
        
                $response["success"] = 1;
                $response["message"] = "request responded";
                $response["rqid"] = $rqid;
                
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

        $response["success"] = 0;
        $response["message"] = "no entry with this rqid";
        $response["rqid"] = $rqid;
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