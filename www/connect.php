<?php

 
define('MYSQL_USER', 'u510020271_us');
 
define('MYSQL_PASSWORD', '9783148239');
 
define('MYSQL_HOST', 'mysql.hostinger.in');

define('MYSQL_DATABASE', 'u510020271_sepm');
$pdoOptions = array(
    PDO::ATTR_ERRMODE => PDO::ERRMODE_EXCEPTION,
    PDO::ATTR_EMULATE_PREPARES => false
);

$pdo = new PDO(
    "mysql:host=" . MYSQL_HOST . ";dbname=" . MYSQL_DATABASE, 
    MYSQL_USER, 
    MYSQL_PASSWORD, 
    $pdoOptions 
);
?>
