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
				// $response["uid"] = $user["sid"];
				// $response["name"] = $user["name"];
				// $response["email"] = $user["email"];
				// $response["contact"] = $user["contact"];
				// $response["dob"] = $user["dob"];
				// $response["sex"] = $user["sex"];
				$user["password"]= 0;
				$user["success"] = 1;
				$user["message"] = "User successfully Logged In.";
				echo json_encode($user);
	            
	        } else{
	             $response["success"] = 0;
				 $response["message"] = "Oops! Incorrect Password!";
					
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
				$user["password"] = 0;
				$user["success"] = 1;
				$user["message"] = "User successfully Logged In.";
				
				echo json_encode($user);
	            
	        } else{
	             $response["success"] = 0;
				 $response["message"] = "Oops! Incorrect Password!";
					
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
				$user["password"] = 0;
				$user["success"] = 1;
				$user["message"] = "User successfully Logged In.";
				
				echo json_encode($user);
				
				
	            
	        } else{
	             $response["success"] = 0;
				 $response["message"] = "Oops! Incorrect Password!";
					
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
 