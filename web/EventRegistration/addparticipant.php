<?php
require_once 'controller/Controller.php';
session_start ();
$c = new Controller ();
try {
	if (empty ( $_POST ['participant_name'] )) {
		$_SESSION ["errorParticipantName"] = "Particicant name cannot be empty";
	} else {
		$c->createParticipant ( $_POST ['participant_name'] );
		$_SESSION ["errorParticipantName"] = "";
	}
} catch ( Exception $e ) {
	$_SESSION ["errorParticipantName"] = $e->getMessage ();
}
?>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="refresh" content="0; url=/EventRegistration/" />
</head>
</html>