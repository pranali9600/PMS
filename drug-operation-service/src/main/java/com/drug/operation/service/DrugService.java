package com.drug.operation.service;

import java.util.List;
import java.util.Optional;

import javax.validation.ConstraintViolationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.drug.operation.exception.DrugCollectionException;
import com.drug.operation.model.Drug;
import com.drug.operation.repository.DrugRepository;

@Service
public class DrugService {

	@Autowired
	private DrugRepository drugRepository;
	
	// get all drugs
	public List<Drug> getDrugs() {
		List<Drug> drugList = drugRepository.findAll();
		return drugList;
	}
	
	// add drugs
	public void addDrug(Drug drug) throws ConstraintViolationException,DrugCollectionException {
		Optional<Drug> drugOptional = drugRepository.findByName(drug.getName());
		
		if(drugOptional.isPresent()) {
			throw new DrugCollectionException(DrugCollectionException.drugAlreadyExists());
		}else {
			drugRepository.save(drug);
		}
	}
	
	//delete drug 
	public void deleteDrug(Drug drug) {
		drugRepository.delete(drug);
	}
	
	
	//get drug by drugID
	public ResponseEntity<?> getDrugById(String id) {
		Optional<Drug> drugOptional = drugRepository.findById(id);
		
		if(drugOptional.isPresent()) {
			return new ResponseEntity<>(drugOptional, HttpStatus.OK);
		}else {
			return new ResponseEntity<>("Drug not found with id "+id, HttpStatus.NOT_FOUND);
		}
	}
	
	//get drug by name
	public ResponseEntity<?> getDrugByName(String drugName) {
		Optional<Drug> drugOptional = drugRepository.findByName(drugName);
		
		if(drugOptional.isPresent()) {
			return new ResponseEntity<>(drugOptional, HttpStatus.OK);
		}else {
			return new ResponseEntity<>("Drug not found with Name "+drugName, HttpStatus.NOT_FOUND);
		}
	}
	
	// update drug
	public ResponseEntity<?> updateDrugInfo(String did, Drug drugNew) {
		
		// get drug info from DB by drugID
		Optional<Drug> drugDB1 = drugRepository.findById(did);
		
		if(drugDB1.isPresent()) {
			Drug drugDB = drugDB1.get();
			// check weather user entered null value or not
			if(drugNew.getName() != null)
				drugDB.setName(drugNew.getName());
			
			if(drugNew.getDescription() != null)
				drugDB.setDescription(drugNew.getDescription());
			
			if(drugNew.getMfgDate() != null)
				drugDB.setMfgDate(drugNew.getMfgDate());
			
			if(drugNew.getExpireDate() != null)
				drugDB.setExpireDate(drugNew.getExpireDate());
			
			if(drugNew.getCompany() != null)
				drugDB.setCompany(drugNew.getCompany());
			
			if(drugNew.getCost() != 0)
				drugDB.setCost(drugNew.getCost());
			
			if(drugNew.getQuantity() != 0)
				drugDB.setQuantity(drugNew.getQuantity());
			
			// save & return updated changes in DB	 
			 return new ResponseEntity<Drug>(drugRepository.save(drugDB), HttpStatus.OK);
		}else {
			return new ResponseEntity<>("No Drug Available with id "+did, HttpStatus.NOT_FOUND);
		}
		
		
	}
	
}
