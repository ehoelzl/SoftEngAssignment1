<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Event Registraion</title>
<style>
.error {
	color: #FF0000;
}
</style>
</head>
<body>
		<?php
		require_once 'model/Participant.php';
		require_once 'model/Event.php';
		require_once 'model/Registration.php';
		require_once 'model/RegistrationManager.php';
		require_once 'persistence/PersistenceEventRegistration.php';
		session_start ();
		$pm = new PersistenceEventRegistration ( 'data.txt' );
		$rm = $pm->loadDataFromStore ();
		echo "<form action='register.php' method='post'>";
		echo "<p>Name? <select name='participantspinner'>";
		foreach ( $rm->getParticipants () as $participant ) {
			echo "<option>" . $participant->getName () . "</option>";
		}
		echo "</select><span class='error'>";
		if (isset ( $_SESSION ['errorRegisterParticipant'] ) && ! empty ( $_SESSION ['errorRegisterParticipant'] )) {
			echo "*" . $_SESSION ["errorRegisterParticipant"];
		}
		echo "</span></p>";
		
		echo "<p>Event? <select name='eventspinner'>";
		foreach ( $rm->getEvents () as $event ) {
			echo "<option>" . $event->getName () . "</option>";
		}
		
		echo "</span></p>";
		
		echo "<br>";
		echo "<p><input type='submit' value='Register' /></p>";
		echo "</form>";
		
		?>
<form action="addparticipant.php" method="post">
		<p>
			Name?<input type="text" name="participant_name" /> <span
				class="error">
			<?php
			if (isset ( $_SESSION ['errorParticipantName'] ) && ! empty ( $_SESSION ['errorParticipantName'] )) {
				echo "*" . $_SESSION ["errorParticipantName"];
			}
			?>
			</span>

		</p>
		<p>
			<input type="submit" value="Add Participant" />
		</p>

	</form>
	<form action="addevent.php" method="post">
		<p>
			Event Name?<input type="text" name="event_name" /> <span
				class="error">
			<?php
			if (isset ( $_SESSION ['errorEventName'] ) && ! empty ( $_SESSION ['errorEventName'] )) {
				echo "*" . $_SESSION ["errorEventName"];
			}
			?>
			</span>

		</p>
		<p>
			<input type="submit" value="Add Event" />
		</p>
		<p>
			Event Date? <input type="date" name="event_date"
				value="<?php echo date('Y-m-d'); ?>" />
		</p>
		<p>
			Start Time? <input type="time" name="starttime"
				value="<?php echo date('H:i'); ?>" />
		</p>
		<p>
			End Time? <input type="time" name="endtime"
				value="<?php echo date('H:i'); ?>" /> <span class="error">
			<?php
			if (isset ( $_SESSION ['errorStartTime'] ) && ! empty ( $_SESSION ['errorStartTime'] )) {
				echo "*" . $_SESSION ["errorStartTime"];
			}
			?>
			</span>
		</p>

	</form>
</body>
</html>