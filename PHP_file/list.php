<?php
	$con = mysqli_connect("localhost", "chlwogh0829", "ZOQTMXHS123", "chlwogh0829");
	mysqli_query($con, 'SET NAMES utf8);
	$response = array();

	while($row = mysqli_fecth_array($result)){
		array_push($response, array("titlename" => $row[0], "noticedate" => $row[1]));
	}

	echo json_encode(array("response"=>$response));
	mysqli_close($con);
?>
	