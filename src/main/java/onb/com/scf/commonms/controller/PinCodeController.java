package onb.com.scf.commonms.controller;

import java.io.FileNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import onb.com.scf.commonms.dto.PincodeResponse;
import onb.com.scf.commonms.service.PincodeService;


@RestController
@RequestMapping("scfu/api/")
@CrossOrigin
public class PinCodeController {
	@Autowired PincodeService pincodeService;

	@GetMapping("/getPincodeDataByApi/{pinCode}")
	public ResponseEntity<PincodeResponse> getAllLocationDetailsByPinCode(@PathVariable("pinCode") String pinCode) {
		return new ResponseEntity<>(pincodeService.getAllLocationDetailsByPinCode(pinCode), HttpStatus.OK);
	}
	@GetMapping("/getPincodeData/{pinCode}")
	public ResponseEntity<PincodeResponse> getLocationDetailsByPinCode(@PathVariable("pinCode") String pinCode) {
		return new ResponseEntity<>(pincodeService.getLocationDetailsByPinCode(pinCode), HttpStatus.OK);
	}
	@PostMapping("/savePinCodeDataFromFile/")
	public ResponseEntity<Integer> savePinCodeDataFromFile() throws FileNotFoundException {
		return new ResponseEntity<>(pincodeService.savePinCodeDataFromFile(), HttpStatus.OK);
	}
}
