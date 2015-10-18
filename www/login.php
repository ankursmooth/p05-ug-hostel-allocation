<?php

session_start();
$response = array();

require 'password.php';

require 'connect.php';


if(isset($_POST['login'])){
    $login=trim($_POST['login']);
    
    $uid = !empty($_POST['uid']) ? trim($_POST['uid']) : null;
    $passwordAttempt = !empty($_POST['password']) ? trim($_POST['password']) : null;
	if($login=="student"){
		$sql = "SELECT * FROM student WHERE sid = :uid";
	    $stmt = $pdo->prepare($sql);
	    
	    
	    $stmt->bindValue(':uid', $uid);
	    
	    
	    $stmt->execute();
	    
	    
	    $user = $stmt->fetch(PDO::FETCH_ASSOC);
	    
	    if($user === false){
	    
			$response["success"] = 0;
	        $response["message"] = "Oops! An error occurred.";
			echo json_encode($response);
	        
	    } else{
	        $validPassword = password_verify($passwordAttempt, $user['password']);
	        
			if($validPassword){
	            
	        	
	            $response["success"] = 1;
				$response["message"] = "User successfully Logged In.";
				$response["uid"] = $user["uid"];
				$response["name"] = $user["name"];
				$response["year"] = $user["year"];
				$response["contact"] = $user["contact"];
				$response["sid"] = $user["sid"];
				
				echo json_encode($response);
	            
	        } else{
	             $response["success"] = 0;
				 $response["message"] = "Oops! An error occurred.";
					
				 echo json_encode($response);
	        }
	    }
    }
    elseif ($login=="warden") {
    	$sql = "SELECT * FROM warden WHERE wid = :uid";
	    $stmt = $pdo->prepare($sql);
	    
	    
	    $stmt->bindValue(':uid', $uid);
	    
	    
	    $stmt->execute();
	    
	    
	    $user = $stmt->fetch(PDO::FETCH_ASSOC);
	    
	    if($user === false){
	    
			$response["success"] = 0;
	        $response["message"] = "Oops! An error occurred.";
			echo json_encode($response);
	        
	    } else{
	        $validPassword = password_verify($passwordAttempt, $user['password']);
	        
			if($validPassword){
	            
	        	
	            $response["success"] = 1;
				$response["message"] = "User successfully Logged In.";
				$response["uid"] = $user["uid"];
				$response["name"] = $user["name"];
				$response["year"] = $user["year"];
				$response["contact"] = $user["contact"];
				$response["sid"] = $user["sid"];
				
				echo json_encode($response);
	            
	        } else{
	             $response["success"] = 0;
				 $response["message"] = "Oops! An error occurred.";
					
				 echo json_encode($response);
	        }
	    }
    }
    elseif ($login=="med") {
    	$sql = "SELECT * FROM viewers WHERE vid = :uid";
	    $stmt = $pdo->prepare($sql);
	    
	    
	    $stmt->bindValue(':uid', $uid);
	    
	    
	    $stmt->execute();
	    
	    
	    $user = $stmt->fetch(PDO::FETCH_ASSOC);
	    
	    if($user === false){
	    
			$response["success"] = 0;
	        $response["message"] = "Oops! An error occurred.";
			echo json_encode($response);
	        
	    } else{
	        $validPassword = password_verify($passwordAttempt, $user['password']);
	        
			if($validPassword){
	            
	        	
	            $response["success"] = 1;
				$response["message"] = "User successfully Logged In.";
				$response["uid"] = $user["uid"];
				$response["name"] = $user["name"];
				$response["year"] = $user["year"];
				$response["contact"] = $user["contact"];
				$response["sid"] = $user["sid"];
				
				echo json_encode($response);
	            
	        } else{
	             $response["success"] = 0;
				 $response["message"] = "Oops! An error occurred.";
					
				 echo json_encode($response);
	        }
	    }
    }
    else{
    			$response["success"] = 0;
				$response["message"] = "Oops! An error occurred in getting login type.";
					
				echo json_encode($response);
    }
}
 