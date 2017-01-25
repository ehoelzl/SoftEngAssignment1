<?php
require_once 'model/RegistrationManager.php';
require_once 'model/Registration.php';
require_once 'model/Participant.php';
require_once 'model/Event.php';
require_once 'persistence/PersistenceEventRegistration.php';
require_once 'controller/InputValidator.php';
class Controller {
	// private RegistrationManger $rm;
	public function __construct() {
	}
	public function register($participant, $event) {
		$p = InputValidator::validate_input ( $participant );
		$e = InputValidator::validate_input ( $event );
		$pm = new PersistenceEventRegistration ( 'data.txt' );
		$rm = $pm->loadDataFromStore ();
		$reg = new Registration ( $p, $e );
		$rm->addRegistration ( $reg );
		$pm->writeDataToStore ( $rm );
	}
	public function createParticipant($name) {
		$n = InputValidator::validate_input ( $name );
		$par = new Participant ( $n );
		$pm = new PersistenceEventRegistration ( 'data.txt' );
		$rm = $pm->loadDataFromStore ();
		$rm->addParticipant ( $par );
		$pm->writeDataToStore ( $rm );
	}
	public function createEvent($eventname, $EventDate, $StartTime, $EndTime) {
		$en = InputValidator::validate_input ( $eventname );
		$ed = InputValidator::validate_input ( $EventDate );
		$st = InputValidator::validate_input ( $StartTime );
		$et = InputValidator::validate_input ( $EndTime );
		
		$par = new Event ( $en, $ed, $st, $et );
		$pm = new PersistenceEventRegistration ( 'data.txt' );
		$rm = $pm->loadDataFromStore ();
		$rm->addEvent ( $par );
		$pm->writeDataToStore ( $rm );
	}
}