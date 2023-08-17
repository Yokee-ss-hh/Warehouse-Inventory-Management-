package com.example.inventory.auth.repositories;

import org.springframework.data.repository.CrudRepository;

import com.example.inventory.auth.entities.AuthToken;

public interface AuthTokenRepository extends CrudRepository<AuthToken, Integer>{
	
	
}
