package com.example.activity.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
public class otpModel {
	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="otp_id")
	private int ID;
	private int otp;
	@Temporal(TemporalType.TIMESTAMP)
	private Date createdDate;

	@OneToOne(targetEntity = activityModel.class, fetch = FetchType.EAGER)
	@JoinColumn(nullable = false, name = "userId")
	private activityModel activitymodel;
	
	
	public activityModel getActivitymodel() {
		return activitymodel;
	}

	public otpModel() {
		super();
	}

	public void setActivitymodel(activityModel activitymodel) {
		this.activitymodel = activitymodel;
	}

	public otpModel(activityModel activitymodel) {
		super();
		int max=10000,min=90000;	
		int random_int = (int)(Math.random() * (max - min + 1) + min);
		this.otp = random_int;
		this.createdDate = new Date();
		this.activitymodel=activitymodel;
		}

	public int getID() {
		return ID;
	}

	public void setID(int iD) {
		ID = iD;
	}

	public int getOtp() {
		return otp;
	}

	public void setOtp(int otp) {
		this.otp = otp;
	}

	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	
}
