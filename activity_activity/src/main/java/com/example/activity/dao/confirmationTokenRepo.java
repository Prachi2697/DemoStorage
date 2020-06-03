package com.example.activity.dao;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.example.activity.model.confirmationTokenModel;

@Repository
public interface confirmationTokenRepo extends CrudRepository<confirmationTokenModel, String> {
	confirmationTokenModel findByConfirmationToken(String confirmationToken);
}
