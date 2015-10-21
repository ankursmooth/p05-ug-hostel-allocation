<?php
session_start();

$response = array();

require 'password.php';

require 'connect.php';

if(isset($_POST ['register'])){
    $register=!empty($_POST['register']) ? trim($_POST['register']) : null;
    echo $register;
    if($register=="2"){
            $sid = !empty($_POST['sid']) ? trim($_POST['sid']) : null;
            $name = !empty($_POST['name']) ? trim($_POST['name']) : null;
            $email = !empty($_POST['email']) ? trim($_POST['email']) : null;
            $dob = !empty($_POST['dob']) ? trim($_POST['dob']) : null;
            
            $contact = !empty($_POST['contact']) ? trim($_POST['contact']) : null;
            $password = !empty($_POST['password']) ? trim($_POST['password']) : null;
            
            $sex = !empty($_POST['sex']) ? trim($_POST['sex']) : null;
            
            $sql = "SELECT COUNT(sid) AS num FROM student WHERE sid = :sid";
            $stmt = $pdo->prepare($sql);
            
            $stmt->bindValue(':sid', $sid);
            
            $stmt->execute();
            
            $row = $stmt->fetch(PDO::FETCH_ASSOC);
            
            if($row['num'] > 0){
                $response["success"] = 0;
                $response["message"] = "Oops! An error occurred.";
                
                echo json_encode($response);
            }
            else
            {
            $passwordHash = password_hash($password, PASSWORD_BCRYPT, array("cost" => 12));
            
            $sql = "INSERT INTO student (name, sid, password, contact, sex , email , dob) VALUES (:name, :sid, :password, :contact,:sex , :email, :dob)";
            $stmt = $pdo->prepare($sql);
            
            $stmt->bindValue(':name', $name);
            $stmt->bindValue(':sid', $sid);
            $stmt->bindValue(':password', $passwordHash);
            $stmt->bindValue(':contact', $contact);
            $stmt->bindValue(':sex', $sex);
            $stmt->bindValue(':email', $email);
            $stmt->bindValue(':dob', $dob);
            
            $result = $stmt->execute();
            
            if($result){
            
            $response["success"] = 1;
                $response["message"] = "User successfully Registered.";

                $response["sid"] = $sid;
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
    else if($register=="3"){
            $vid = !empty($_POST['vid']) ? trim($_POST['vid']) : null;
            $name = !empty($_POST['name']) ? trim($_POST['name']) : null;
            $email = !empty($_POST['email']) ? trim($_POST['email']) : null;
            $department = !empty($_POST['department']) ? trim($_POST['department']) : null;
            
            $contact = !empty($_POST['contact']) ? trim($_POST['contact']) : null;
            $password = !empty($_POST['password']) ? trim($_POST['password']) : null;
            
            
            
            $sql = "SELECT COUNT(vid) AS num FROM viewers WHERE vid = :vid";
            $stmt = $pdo->prepare($sql);
            
            $stmt->bindValue(':vid', $vid);
            
            $stmt->execute();
            
            $row = $stmt->fetch(PDO::FETCH_ASSOC);
            
            if($row['num'] > 0){
                $response["success"] = 0;
                $response["message"] = "Oops! An error occurred.";
                
                echo json_encode($response);
            }
            else
            {
            $passwordHash = password_hash($password, PASSWORD_BCRYPT, array("cost" => 12));
            
            $sql = "INSERT INTO viewers (name, vid, password, contact, email , department) VALUES (:name, :vid, :password, :contact, :email, :department)";
            $stmt = $pdo->prepare($sql);
            
            $stmt->bindValue(':name', $name);
            $stmt->bindValue(':vid', $vid);
            $stmt->bindValue(':password', $passwordHash);
            $stmt->bindValue(':contact', $contact);
            
            $stmt->bindValue(':email', $email);
            $stmt->bindValue(':department', $department);
            
            $result = $stmt->execute();
            
            if($result){
            
            $response["success"] = 1;
                $response["message"] = "Viewer successfully Registered.";

                $response["vid"] = $vid;
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
    else if($register=="1"){
        $wid = !empty($_POST['wid']) ? trim($_POST['wid']) : null;
            $name = !empty($_POST['name']) ? trim($_POST['name']) : null;
            $email = !empty($_POST['email']) ? trim($_POST['email']) : null;
            $address = !empty($_POST['address']) ? trim($_POST['address']) : null;
            
            $contact = !empty($_POST['contact']) ? trim($_POST['contact']) : null;
            $password = !empty($_POST['password']) ? trim($_POST['password']) : null;
            
            $position = !empty($_POST['position']) ? trim($_POST['position']) : null;
            
            $sql = "SELECT COUNT(wid) AS num FROM warden WHERE wid = :wid";
            $stmt = $pdo->prepare($sql);
            
            $stmt->bindValue(':wid', $wid);
            
            $stmt->execute();
            
            $row = $stmt->fetch(PDO::FETCH_ASSOC);
            
            if($row['num'] > 0){
                $response["success"] = 0;
                $response["message"] = "Oops! An error occurred.";
                
                echo json_encode($response);
            }
            else
            {
            $passwordHash = password_hash($password, PASSWORD_BCRYPT, array("cost" => 12));
            
            $sql = "INSERT INTO warden (name, wid, password, contact, position , email , address) VALUES (:name, :wid, :password, :contact,:position , :email, :address)";
            $stmt = $pdo->prepare($sql);
            
            $stmt->bindValue(':name', $name);
            $stmt->bindValue(':wid', $wid);
            $stmt->bindValue(':password', $passwordHash);
            $stmt->bindValue(':contact', $contact);
            $stmt->bindValue(':position', $position);
            $stmt->bindValue(':email', $email);
            $stmt->bindValue(':address', $address);
            
            $result = $stmt->execute();
            
            if($result){
            
            $response["success"] = 1;
                $response["message"] = "User successfully Registered.";

                $response["wid"] = $wid;
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
}
else
{
    $response["success"] = 0;
    $response["message"] = "Unknown Error 2";

    echo json_encode($response);
}

?>