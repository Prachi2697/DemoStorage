package com.example.activity.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.example.activity.model.activityModel;
import com.example.activity.model.storageModel;
import com.example.activity.payload.UploadFileResponse;
import com.example.activity.service.activityService;
import com.twilio.rest.api.v2010.account.Message;

@RestController
public class activityController {

	 @Autowired
	 private activityService service;
	  	 
     @PostMapping("/register")     
     public String Register(@RequestBody activityModel model)
     { 
    	 return service.addUsers(model);
     }
     
     @RequestMapping(value="/confirm-account", method= {RequestMethod.GET, RequestMethod.POST}) 
     public String confirmUserAccount(@RequestParam("token") String confirmationToken)
     {
    	return service.confirmEmail(confirmationToken);       
     }
     
     @PostMapping("/login")  
     public String login(@RequestParam("userEmail") String email , @RequestParam("userPassword") String Password)
     {        		 
         return service.checkLoginDetail(email, Password);  	
     }
     
     @PostMapping("/otpcheck")
	 public String otpCheck(@RequestParam("OTP") int otp)
	 {
		 return service.checkOtp(otp);
	 }

     @GetMapping("/users")
     public List<activityModel> Users()
     {
    	 return service.allUsers();
     }
     
     @PutMapping("/update") 
     public String update(@RequestBody activityModel update)
     {        		 
         return service.updateUser(update);
    	
     } 
     
     @DeleteMapping("/deleteall")  
     public String deleteAlluser()
     {
    	 return service.deleteAlluser();
     }
     
	@PostMapping("/uploadFile")
     public String uploadFile(@RequestPart("file") MultipartFile[] files) {
    	 int count=0; 	 
    	 for (MultipartFile file : files) 
    	 { 
    		 
    	 System.out.print("ENTER");	 
         storageModel dbFile = service.storeFile(file);
    
         String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath()
                 .path("/downloadFile/")
                 .path(dbFile.getId())
                 .toUriString();   
         count++;
    	 }
    	 
    	 return "Files"+count+"Added!";
//    	  list.add( new UploadFileResponse(dbFile.getFileName(), fileDownloadUri,
//                  file.getContentType(), file.getSize()));
//    	 return list;
     }
     
     @GetMapping("/showimg")
     public List<storageModel> ShowImage() {
        
        return service.showImg();
     }
       
     @DeleteMapping("/delete/{userId}")  
     public String deleteuser(@PathVariable("userId") int Id)
     {
    	 return service.deleteuser(Id);
     }
} 
	   

