package com.example.inventory.auth.repositories;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.example.inventory.auth.entities.AuthUser;

@Repository
public interface AuthUserRepository extends CrudRepository<AuthUser, Integer>{
	
	public Optional<AuthUser> findByName(String name);
	
	public Optional<AuthUser> findByEmail(String email);
	
	public Boolean existsByName(String name);
	
	public Boolean existsByEmail(String email);

}
