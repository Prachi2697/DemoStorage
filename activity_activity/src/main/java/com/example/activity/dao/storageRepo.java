package com.example.activity.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.activity.model.storageModel;

@Repository
public interface storageRepo extends JpaRepository<storageModel,Integer> {

}
