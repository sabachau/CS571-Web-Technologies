<!doctype html>
<html>
    <head>
        
        <title>Forecast Search</title>
        <style type="text/css">
            .middle{
                margin: auto;
                width: 30%;
                border:3px solid #8AC007;
                padding: 20px;
            }
            .calign{
            text-align: center;
                
            }
                img {
        display: block;
    margin-left: auto;
    margin-right: auto
        }
            table.data {
               margin: auto;
                width: 30%;
                font-size: 16px;
                padding: 20px;
            }
            #wcon{
                display: inline;
            }
          
        </style>
  
    </head>
    <body>
         <h1 class="calign"><b>Forecast Search</b></h1>
        <form class="middle" name="myform" method="post" action=<?php echo $_SERVER['PHP_SELF'];?>>
            
                <table border="0">
                
                <tr>
                    <td>Street Address:*</td>
                    <td><input type="text" name="txtStreet" value="<?php if(isset($_POST['txtStreet'])): $a=$_POST["txtStreet"];echo $a; endif;?>"></td>
                </tr>
                <tr>
                    <td>City:*</td>
                    <td><input type="text" name="txtCity" value="<?php if(isset($_POST['txtCity'])): $a=$_POST["txtCity"];echo $a; endif;?>"></td>
                </tr>
                <tr>
                    <td>State:*</td>
                    <td>
                        <?php $options = array(
                          "" => "Select your state...",
                          "AL"=>"Alabama",
                          "AK"=>"Alaska",
                          "AZ"=>"Arizona",
                          "AR"=>"Arkansas",
                          "CA"=>"California",
                          "CO"=>"Colorado",
                          "CT"=>"Connecticut", 
                          "DE"=>"Delaware", 
                          "DC"=>"District Of Columbia",
                          "FL"=>"Florida", 
                          "GA"=>"Georgia" ,
                          "HI"=>"Hawai" ,
                          "ID"=>"Idaho",
                          "IL"=>"Illinois",
                          "IN"=>"Indiana",
                          "IA"=>"Iowa",
                          "KS"=>"Kansas",
                          "KY"=>"Kentucky",
                          "LA"=>"Louisiana",
                          "ME"=>"Maine" ,
                          "MD"=>"Maryland", 
                          "MA"=>"Massachusetts", 
                          "MI"=>"Michigan" ,
                          "MN"=>"Minnesota" ,
                          "MS"=>"Mississippi", 
                          "MO"=>"Missouri" ,
                          "MT"=>"Montana" ,
                          "NE"=>"Nebraska" ,
                          "NV"=>"Nevada" ,
                          "NH"=>"New Hampshire", 
                          "NJ"=>"New Jersey", 
                          "NM"=>"New Mexico" ,
                          "NY"=>"New York" ,
                          "NC"=>"North Carolina",
                          "ND"=>"North Dakota" ,
                          "OH"=>"Ohio" ,
                          "OK"=>"Oklahoma", 
                          "OR"=>"Oregon" ,
                          "PA"=>"Pennsylvania", 
                          "RI"=>"Rhode Island" ,
                          "SC"=>"South Carolina",
                          "SD"=>"South Dakota" ,
                          "TN"=>"Tennessee" ,
                          "TX"=>"Texas" ,
                          "UT"=>"Utah" ,
                          "VT"=>"Vermont", 
                          "VA"=>"Virginia", 
                          "WA"=>"Washington", 
                          "WV"=>"West Virginia", 
                          "WI"=>"Wisconsin" ,
                          "WY"=>"Wyoming"  ,
                          ); 
$state = isset($_POST['state'])?$_POST['state']:"";
?>

<select name="state" id="state" class="styled">
<?php foreach ($options as $value=>$text){ ?>
   <option <?php if($value == $state){echo 'selected="selected"'; }?> value="<?php echo $value; ?>"><?php echo $text; ?></option>
<?php } ?>
</select>

                    </td>
                </tr>    
                <tr>    
                    <td>Degree:*</td>
                    <td><input type="radio" name="radDeg" value="fahrenheit" checked <?php if(isset($_POST['radDeg'])&&$_POST['radDeg']=="fahrenheit") {echo "checked";}?>>Fahrenheit&nbsp;
                    <input type="radio" name="radDeg" value="celsius" <?php if(isset($_POST['radDeg'])&&$_POST['radDeg']=="celsius") {echo "checked"; }?>>Celsius</td>
                  
                </tr>
                <tr>
                    <td></td>
                    <td><input type="submit" name="submit" value="Search" onclick="checkEmpty()">
<!--                        <input type="reset" name="btnClear" value="Clear" onclick="clearResultArea()">-->
                        <button onclick="clearResultArea()">Clear</button>
                    </td>
                </tr>
            </table>
       <br>
         
         <i> * - Mandatory fields</i> <br/>
            <div class="calign" ><a href="http://forecast.io">Powered by Forecast.io</a></div>
        </form>
        
          <script type="text/javascript">
              function clearResultArea()
              {
                  console.log("nowhere");
                  document.getElementsByName("txtStreet")[0].value="";
                  document.getElementsByName("txtCity")[0].value="";
                  document.getElementsByName("state")[0].selectedIndex=0;
                  document.getElementsByName("radDeg")[0].checked=true;
                  console.log("here");
                  document.getElementById("tabarea").innerHTML="";
              }
            function checkEmpty()
            {
                console.log("inside checkEmpty() function");
                var selectObj=this.myform.state;
                var streetadd_filled, city_filled,state_filled,deg_filled;
                streetadd_filled= city_filled=state_filled=deg_filled=false;
                if((document.myform.txtStreet.value).trim()!="") streetadd_filled=true;
                if((document.myform.txtCity.value).trim()!="") city_filled=true;
                if(document.myform.state.options[selectObj.selectedIndex].value!="") state_filled=true;
                if(document.myform.radDeg.value!="") deg_filled=true;
                
                if((!streetadd_filled)||(!city_filled)||(!state_filled)||(!deg_filled))
            
                {   var alertstring="The following fields are missing:\n";
                    if(!streetadd_filled) alertstring=alertstring+"street address\n";
                    if(!city_filled) alertstring=alertstring+"city\n";
                    if(!state_filled) alertstring=alertstring+"state\n";
                    if(!deg_filled) alertstring=alertstring+"degree\n";
                    alert(alertstring);
                 document.myform.reset();
                    return false;
                }
            }
            
        </script>
        
        <br/><br/>
<div id="tabarea">  
  <form id="predictions">
       <?php
        if(isset($_POST["submit"])):
            
                        $txtStreet=trim($_POST['txtStreet']);
                        $state=trim($_POST['state']);
                        $txtCity=trim($_POST['txtCity']);
                        
                if($txtStreet!=''&&$state!=''&&$txtCity!=''):  
                         $units=$_POST['radDeg'];
                       if($units=='fahrenheit'){
                           $units='us';}
                        else{
                            $units='si';
                       }
                        $g_url='https://maps.googleapis.com/maps/api/geocode/xml?address=';
                        $g_url=$g_url.urlencode($txtStreet.','.$txtCity.','.$state); 
                        $g_url=$g_url.urlencode('&key=Q***2ewRx_LHIHN');
                        $response = simplexml_load_file($g_url) or die("Error: Cannot create object");
                        $status=$response->status;
 
      if($status!='OK'){
          echo "status: {$status} <br/>";
          echo "No coordinates returned. Either address is incomplete or invalid. <br/>";
          echo "Also possible that Query Limit Exceeded!<br/>Wait 2 seconds and then try again. Otherwise try again tomorrow as you might have reached limit for quota of the day.<br/> For more info visit:
          https://developers.google.com/maps/documentation/business/articles/usage_limits#limitexceeded";
          
      }
        if($status=='OK'):
                        $lat= $response->result->geometry->location->lat;
                       
                        $lng=$response->result->geometry->location->lng;
                 //JSON       
                        $fore_url='https://api.forecast.io/forecast/';
                        $apikey='###########################';
                        $fore_url="{$fore_url}{$apikey}/{$lat},{$lng}?units={$units}&exclude=flags";
 $jsoncode=file_get_contents($fore_url);
 $decoded=json_decode($jsoncode,true);   
$output=''; 
             ?>
<!--
<pre>
<?php print_r($decoded); ?>
</pre>
-->
<?php
$currently=$decoded['currently'];
    //echo $decoded['currently']['summary'];
 foreach($currently as $key=>$item){
   
    $output[$key]=$item;
    
 }
?>

    <div class="calign">
        <h2 id="wcon" ><?php echo $output['summary']; ?> <br/>
            <?php echo round($output['temperature']); ?>&deg;
            <?php   
            if($units=='us'){ 
                echo 'F'; }
            else{
                echo 'C'; 
            } ?>
        </h2> 
    </div>
        <?php
         if($output['icon']=="clear-day"):
            $icon="clear.png";
         elseif ($output['icon']=="clear-night"):
             $icon="clear_night.png";
         elseif ($output['icon']=="partly-cloudy-day"):
            $icon="cloud_day.png";
         elseif($output['icon']=="partly-cloudy-night"):
            $icon="cloud_night.png";
         else:
            $icon=$output['icon'].".png";
        endif; 
    ?>
<img src='<?php echo "http://cs-server.usc.edu:45678/hw/hw6/images/{$icon}"; ?>' alt='<?php echo $output['summary']; ?>' title='<?php echo $output['summary']; ?>'/>        
<table id="tb2" class="data" border="0px">
    <tr>
        <td>Precipitation: </td>
        <td><?php 
            $precipInt=$output['precipIntensity'];
            if($units=='us'){
            if($precipInt>=0 &&$precipInt<0.002):
                echo "None";
            elseif($precipInt>=0.002 &&$precipInt<0.017):
                echo "Very Light";
            elseif($precipInt>=0.017 &&$precipInt<0.1):
                echo "Light";
            elseif($precipInt>=0.1 &&$precipInt<0.4):
                echo "Moderate";
            elseif($precipInt>=0.4):
                echo "Heavy";
            endif;
            }
            else {
                if($precipInt>=0 &&$precipInt<0.0508):
                echo "None";
            elseif($precipInt>=0.0508 &&$precipInt<0.4318):
                echo "Very Light";
            elseif($precipInt>=0.4318 &&$precipInt<2.54):
                echo "Light";
            elseif($precipInt>=2.54 &&$precipInt<10.16):
                echo "Moderate";
            elseif($precipInt>=10.16):
                echo "Heavy";
            endif;
            }
            ?></td>
    </tr>
      <tr>
        <td>Chance of Rain: </td>
        <td><?php echo $output['precipProbability']*100; echo "%" ?></td>
    </tr>
        <tr>
        <td>Wind Speed:</td>
        <td><?php $wspeed= round($output['windSpeed']);
              echo $wspeed;
            if($units=='us'): 
                echo " mph";  
            else: 
                
                echo " mtps";  
            endif; 
            
            ?></td>
    </tr>
        <tr>
        <td>Dew Point: </td>
        <td><?php echo round($output['dewPoint']);?>&deg;
            <?php   
            if($units=='us'): 
                echo 'F'; 
            else: 
                echo 'C'; 
            endif; ?></td>
    </tr>
        <tr>
        <td>Humidity:</td>
        <td><?php echo $output['humidity']*100; echo "%";?></td>
    </tr>
        <tr>
        <td>Visibility</td>
        <td><?php echo round($output['visibility']); 
            if($units=='us'): 
                echo " mi";  
            else: 
                echo " km";  
            endif; 
            ?></td>
    </tr>
        <tr>
        <td>Sunrise: </td>
        <td><?php 
            $timezone=$decoded['timezone'];
            date_default_timezone_set($timezone);
            $timestamp=$decoded['daily']['data'][0]['sunriseTime']; 
            $today=date("h:i A",$timestamp);
            echo $today;
            ?></td>
    </tr>
        <tr>
        <td>Sunset: </td>
        <td><?php 
            
            $timestamp=$decoded['daily']['data'][0]['sunsetTime']; 
            $today=date("h:i A",$timestamp);
            echo $today;
            ?></td>
    </tr>
        
        </table>
<?php 
                endif;
             endif;
          endif;
 
?>
                    </form>

    </div>
        <noscript/>
    </body>
</html>
