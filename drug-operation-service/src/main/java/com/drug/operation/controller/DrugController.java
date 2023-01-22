package com.drug.operation.controller;

import java.util.List;

import javax.validation.ConstraintViolationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.drug.operation.exception.DrugCollectionException;
import com.drug.operation.model.AuthenticationRequests;
import com.drug.operation.model.AuthenticationResponse;
import com.drug.operation.model.Drug;
import com.drug.operation.repository.DrugRepository;
import com.drug.operation.service.DrugService;
import com.drug.operation.service.MyUserDetailsService;
import com.drug.operation.util.JwtUtil;

@RestController
@CrossOrigin(origins = {"http://localhost:4200/"})
public class DrugController {

	@Autowired
	private DrugRepository drugRepository;
	
	@Autowired
	private DrugService drugService;
	
	@Autowired
	private AuthenticationManager authenticationManager;
	
	@Autowired
	private MyUserDetailsService userDetailsService;
	
	@Autowired
	private JwtUtil jwtUtil;
	
	// Demo API
	@GetMapping("/hello-world")
	public String getHelloWorld() {
		return "Hello World from drug Operation";
	}
	
	//JWT authentication 
	@PostMapping("/authenticate")
	public ResponseEntity<?> createAuthenticationToken(@RequestBody AuthenticationRequests authenticationRequests) throws Exception{
		
		try {
			authenticationManager.authenticate(
					   new UsernamePasswordAuthenticationToken(authenticationRequests.getUsername(),
							   authenticationRequests.getPassword())
					);
		} catch (BadCredentialsException e) {
			throw new Exception("Incorrect username or password",e);
		}
		
		final UserDetails userDetails = userDetailsService
				.loadUserByUsername(authenticationRequests.getUsername());
		
		final String jwt = jwtUtil.generateToken(userDetails);
		
		return ResponseEntity.ok(new AuthenticationResponse(jwt));
	}
	
	// inserting drug/medicine in DB
	@PostMapping("/drug")
	public ResponseEntity<?> addDrug(@RequestBody Drug drug) {
		
		try {
			drugService.addDrug(drug);
			return new ResponseEntity<Drug>(drug,HttpStatus.OK);
		} catch (ConstraintViolationException e) {
			return new ResponseEntity<>(e.getMessage(),HttpStatus.UNPROCESSABLE_ENTITY);
		}catch (DrugCollectionException e) {
			return new ResponseEntity<>(e.getMessage(),HttpStatus.CONFLICT);
		}
		
	}
	
	// Retrieve all drugs
	@GetMapping("/drug")
	public ResponseEntity<?> getAllDrugs() {
		List<Drug> drugList = drugService.getDrugs();
		
		if(drugList.size() > 0) {
			return new ResponseEntity<List<Drug>>(drugList, HttpStatus.OK);
		}else {
			return new ResponseEntity<>("No Drug Available", HttpStatus.NOT_FOUND);
		}
		 
	}
	
	// delete drug by drugID
	@DeleteMapping("/drug/{did}")
	public void deleteReview(@PathVariable("did") String did) {
		drugRepository.deleteById(did);
	}
	
	// Update drug info by drugID
	@PutMapping("/drug/{did}")
	public ResponseEntity<?> updateDrugInfo(@PathVariable("did") String did, @RequestBody Drug drugNew) {
		return drugService.updateDrugInfo(did, drugNew);
	}
	
	//get drug by drugID
	@GetMapping("/drug/id/{did}")
	public ResponseEntity<?> getDrugById(@PathVariable("did") String did) {
	    return drugService.getDrugById(did);
	}
	
	//get drug by drug Name
	@GetMapping("/drug/name/{drugName}")
	public ResponseEntity<?> getDrugByName(@PathVariable("drugName") String drugName) {
	    return drugService.getDrugByName(drugName);
	}
}
