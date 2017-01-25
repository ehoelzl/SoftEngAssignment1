<?php
require_once __DIR__ . '\..\persistence\PersistenceEventRegistration.php';
require_once __DIR__ . '\..\model\RegistrationManager.php';
require_once __DIR__ . '\..\model\Participant.php';
class PersistenEventResistrationTest extends PHPUnit_Framework_TestCase {
	protected $pm;
	protected function setUp() {
		$this->pm = new PersistenceEventRegistration ();
	}
	protected function tearDown() {
	}
	public function testPersistence() {
		// create test data
		$rm = new RegistrationManager (); // ::getInstance ();
		$participant = new Participant ( "Frank" );
		$rm->addParticipant ( $participant );
		// Write all data
		$this->pm->writeDataToStore ( $rm );
		// clear data from memory
		$rm->delete ();
		$this->assertEquals ( 0, count ( $rm->getparticipants () ) );
		// load back in
		$rm = $this->pm->loadDataFromStore ();
		// check what we got
		$this->assertEquals ( 1, count ( $rm->getparticipants () ) );
		$myParticipant = $rm->getParticipant_index ( 0 );
		$this->assertEquals ( "Frank", $myParticipant->getName () );
	}
}