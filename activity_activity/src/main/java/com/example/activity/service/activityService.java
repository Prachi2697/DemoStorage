package com.example.activity.service;

import org.springframework.mail.SimpleMailMessage;
import java.io.IOException;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.example.activity.dao.activityRepo;
import com.example.activity.dao.confirmationTokenRepo;
import com.example.activity.dao.otpRepo;
import com.example.activity.dao.storageRepo;
import com.example.activity.exception.FileStorageException;
import com.example.activity.model.activityModel;
import com.example.activity.model.confirmationTokenModel;
import com.example.activity.model.otpModel;
import com.example.activity.model.storageModel;
import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;

@Service
public class activityService {
	
	@Autowired
	activityRepo repo;

	@Autowired
	storageRepo Srepo;

	@Autowired
	confirmationTokenRepo Erepo;
	
	@Autowired
	EmailSenderService emailSenderService;
	
	@Autowired
	otpRepo Orepo;
	
	public static final String ACCOUNT_SID = "AC5db846e206c32470ce0cf0198e32c324";
    public static final String AUTH_TOKEN = "a7a660ef027804c2c1935d657adac83b";
	
	public String addUsers(activityModel model) 
	{				
		 activityModel existingUser = repo.findByuserEmailIgnoreCase(model.getUserEmail());
	        if(existingUser != null)
	        {        
	            return "User Already Exist!";          
	        }
	        else
	        {
	            repo.save(model);

	            confirmationTokenModel confirmationToken = new confirmationTokenModel(model);
	            Erepo.save(confirmationToken);

	            SimpleMailMessage mailMessage = new SimpleMailMessage();
	            mailMessage.setTo(model.getUserEmail());
	            mailMessage.setSubject("Complete Registration!");
	            mailMessage.setText("To confirm your account, please click here : "
	            +"http://localhost:8082/confirm-account?token="+ confirmationToken.getConfirmationToken());

	            emailSenderService.sendEmail(mailMessage);
	            
	            return "Verification link sent to your registered email address.";
	        }       
	}
	
	public String confirmEmail(String confirmationToken)
	{
		 confirmationTokenModel token = Erepo.findByConfirmationToken(confirmationToken);

         if(token != null)
         {
             activityModel model = repo.findByuserEmailIgnoreCase(token.getUser().getUserEmail());
             model.setEnabled(true);
             repo.save(model);      
	         return "Registered Email Verified.";         
         }
         else
         {
        	 Erepo.delete(token);
	         return "Varification link broke try to register again.";
         }

	}
	
	public String checkLoginDetail(String email, String password)
	{
		if(repo.existsByuserEmailIgnoreCase(email) )	
		{
			activityModel user=repo.findByuserEmailIgnoreCase(email);
			String pass=user.getUserPassword();
			int flag=pass.compareTo(password);
			if(flag==0)
			{
				otpModel model= new otpModel(user);		
				Orepo.save(model);
				
				Twilio.init(ACCOUNT_SID, AUTH_TOKEN);
		        Message message = Message.creator(
		                new com.twilio.type.PhoneNumber("+91".concat(user.getUserNumber())),//The phone number you are sending text to
		                new com.twilio.type.PhoneNumber("+12069844306"),//The Twilio phone number
		                "Authentication OTP is"+ model.getOtp())
		           .create();

		         return "OTP sent!";
			}
			else
			{
		         return "Invalid Password!";				
			}
		}
		else 
		{
	         return "Invalid User!";
		}		
	}
	
	public String checkOtp(int otp)
	{
		if(Orepo.existsByotp(otp))
		{
			otpModel model=Orepo.findAllByotp(otp);
			Orepo.delete(model);
	        return "Correct OTP!";
		}
		else
		{
	        return "Invalid OTP!";
		}
    }
		
	public List<activityModel> allUsers()
	{
		return repo.findAll();
	}
	
	public String updateUser(activityModel updatedmodel )
	{		
			repo.save(updatedmodel);
			return "User Updated!";
	}
	
	public String deleteuser(int id)
	{
		activityModel model=repo.getOne(id);
	    repo.delete(model);
		return "user deleted!";
	}
	
	public String deleteAlluser()
	{	
	   repo.deleteAll();
		return "user deleted!";
	}
		
	public storageModel storeFile(MultipartFile file) {
        // Normalize file name
        String fileName = StringUtils.cleanPath(file.getOriginalFilename());

        try {
            // Check if the file's name contains invalid characters
            if(fileName.contains("..")) {
                throw new FileStorageException("Sorry! Filename contains invalid path sequence " + fileName);
            }

            storageModel dbFile = new storageModel(fileName, file.getContentType(),file.getBytes());

            return Srepo.save(dbFile);
        } catch (IOException ex) {
            throw new FileStorageException("Could not store file " + fileName + ". Please try again!", ex);
        }
    }

	public List<storageModel> showImg()
	{
	  return Srepo.findAll();
	}
}
