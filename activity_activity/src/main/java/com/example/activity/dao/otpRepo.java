package com.example.activity.dao;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.example.activity.model.confirmationTokenModel;
import com.example.activity.model.otpModel;

@Repository
public interface otpRepo  extends CrudRepository<otpModel, Integer> {

	boolean existsByotp(int otp);

	otpModel findAllByotp(int otp);

}
