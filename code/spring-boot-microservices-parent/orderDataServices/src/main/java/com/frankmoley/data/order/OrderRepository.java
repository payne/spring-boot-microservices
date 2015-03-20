package com.frankmoley.data.order;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.webmvc.RepositoryRestController;

import java.util.List;

/**
 * @author Frank Moley
 */
@RepositoryRestController
public interface OrderRepository extends MongoRepository<Order, String>{

    List<Order> findByCustomerId(@Param(value="customerId") String customerId);
}
