<?php 
header('Access-Control-Allow-Origin: *');
header('Access-Control-Allow-Methods: GET, POST');
header('Access-Control-Allow-Headers: Content-Type');

$txtStreet=trim($_GET['txtStreet']);
                       
$state=trim($_GET['state']);
                       
$txtCity=trim($_GET['txtCity']);
                        
                
if($txtStreet!=''&&$state!=''&&$txtCity!=''){  
                       
$units=$_GET['radDeg'];
                      
if($units=='fahrenheit'){
                          
    $units='us';}
                       
else{
                            
    $units='si';
                      
}
                        
$g_url='https://maps.googleapis.com/maps/api/geocode/xml?address=';
                       
$g_url=$g_url.urlencode($txtStreet.','.$txtCity.','.$state);

$g_url=$g_url.urlencode('&key=QW*****1aEc_MAlQE');
                       
                       
$response = simplexml_load_file($g_url) or die("Error: Cannot create object");
                        
$status=$response->status;
 
if($status!='OK'){
          
    echo "status: {$status} <br/>";
    
    echo "No coordinates returned. Either address is incomplete or invalid. <br/>";
    
    echo "Also possible that Query Limit Exceeded!<br/>Wait 2 seconds and then try again. 
          Otherwise try again tomorrow as you might have reached limit for quota of the day.
          <br/> For more info visit:
          https://developers.google.com/maps/documentation/business/articles/usage_limits#limitexceeded";
          
    echo "Coordinates: {$lat},{$lng}";
          
      }
        

  
if($status=='OK'){                
$lat= $response->result->geometry->location->lat;

$lng=$response->result->geometry->location->lng;

//JSON       

$fore_url='https://api.forecast.io/forecast/';

$apikey='###############################';

$fore_url="{$fore_url}{$apikey}/{$lat},{$lng}?units={$units}&exclude=flags";

$jsoncode=file_get_contents($fore_url);

echo $jsoncode;
}
}
 ?>
  

