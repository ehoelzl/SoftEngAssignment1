package ca.mcgill.ecse321.eventregistration.controller;

public class InvalidInputException extends Exception{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 2898964483053066261L;

	
    public InvalidInputException(String errorMessage) {
        super(errorMessage);
    }
}
