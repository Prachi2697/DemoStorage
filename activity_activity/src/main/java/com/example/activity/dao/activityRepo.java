package com.example.activity.dao;


import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.example.activity.model.activityModel;

@Repository
public interface activityRepo extends JpaRepository<activityModel,Integer>{

	boolean existsByuserNumber(String number);

	activityModel findByuserNumber(String number);
	
	List<activityModel> findByuserName(String name);
	
	@Modifying
	@Transactional 
	@Query("update activityModel ear set ear.userPassword = ?1 where ear.userName = ?2")
	int update(String p, String name);

	activityModel findByuserEmailIgnoreCase(String userEmail);

	activityModel findByisEnabled(boolean b);

	boolean existsByuserEmail(String email);

	boolean existsByuserEmailIgnoreCase(String email);

	

	


}
