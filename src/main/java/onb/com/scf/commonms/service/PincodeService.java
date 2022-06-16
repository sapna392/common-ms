package onb.com.scf.commonms.service;

import java.io.FileNotFoundException;

import onb.com.scf.commonms.dto.PincodeResponse;

public interface PincodeService {
	
	PincodeResponse getAllLocationDetailsByPinCode(String pincode);
	
	PincodeResponse getLocationDetailsByPinCode(String pincode);
	
	int savePinCodeDataFromFile() throws FileNotFoundException;

}
