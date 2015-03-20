package com.frankmoley.data.person;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.rest.webmvc.RepositoryRestController;

/**
 * @author Frank Moley
 */
@RepositoryRestController
public interface PersonRepository extends MongoRepository<Person, String> {
}
