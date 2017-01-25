<?php
require_once 'controller/Controller.php';
session_start ();
$c = new Controller ();
try {
	if (empty ( $_POST ['event_name'] )) {
		$_SESSION ["errorEventName"] = "Event name cannot be empty";
	} elseif (strtotime ( $_POST ['starttime'] ) > strtotime ( $_POST ['endtime'] )) {
		$_SESSION ["errorStartTime"] = "Event end time cannot be before event start time";
	} else {
		$c->createEvent ( $_POST ['event_name'], $_POST ['event_date'], $_POST ['starttime'], $_POST ['endtime'] );
		$_SESSION ["errorEventName"] = "";
		$_SESSION ["errorStartTime"] = "";
	}
} catch ( Exception $e ) {
	$_SESSION ["errorEventName"] = $e->getMessage ();
}
?>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="refresh" content="0; url=/EventRegistration/" />
</head>
</html>