package onb.com.scf.commonms.service;

import onb.com.scf.commonms.dto.PincodeResponse;

public interface PincodeService {
	
	PincodeResponse getAllLocationDetailsByPinCode(String pincode);

}
