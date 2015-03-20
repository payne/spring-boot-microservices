package com.frankmoley.data.product;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.webmvc.RepositoryRestController;

/**
 * @author Frank Moley
 */
@RepositoryRestController
public interface ProductRepository extends  MongoRepository<Product, String>{

    Product findByCatalogId(@Param(value = "catalogId") String catalogId);
}
