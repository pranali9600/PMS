package com.drug.operation.repository;


import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import com.drug.operation.model.Drug;

public interface DrugRepository extends MongoRepository<Drug, String> {

	@Query("{'name': ?0}")
	Optional<Drug> findByName(String name);

}
