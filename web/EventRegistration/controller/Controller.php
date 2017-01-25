<?php
require_once 'model/RegistrationManager.php';
require_once 'model/Registration.php';
require_once 'model/Participant.php';
require_once 'model/Event.php';
require_once 'persistence/PersistenceEventRegistration.php';
class Controller {
	//private RegistrationManger $rm;
	public function __construct(){
		
	}
	public function register($participant, $event) {
		echo ("haha");
		$pm = new PersistenceEventRegistration ('data.txt');
		$rm = $pm->loadDataFromStore ();
		$reg=new Registration($aParticipant, $aEvent);
		$rm->addRegistration($reg);
		$pm->writeDataToStore($rm);
		echo "%%".$reg->getEvent()."sss";
	}
	public function createParticipant($name){
		echo("lalala");
		$par=new Participant($name);
		$pm = new PersistenceEventRegistration ('data.txt');
		$rm = $pm->loadDataFromStore ();
		$rm->addParticipant($par);
		$pm->writeDataToStore($rm);
	}
	public function createEvent($eventname,$EventDate, $StartTime, $EndTime){
		echo("lalala");
		$par=new Event($eventname,$EventDate, $StartTime, $EndTime);
		$pm = new PersistenceEventRegistration ('data.txt');
		$rm = $pm->loadDataFromStore ();
		$rm->addEvent($par);
		$pm->writeDataToStore($rm);
	}
}