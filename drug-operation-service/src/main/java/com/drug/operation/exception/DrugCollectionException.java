package com.drug.operation.exception;

public class DrugCollectionException extends Exception {

	public DrugCollectionException(String message) {
		super(message);
	}
	
	public static String NotFoundException(long id) {
		return "Drug with "+id+ " not found";
	}
	
	public static String drugAlreadyExists() {
		return "Drug with given name already exists";
	}
}
