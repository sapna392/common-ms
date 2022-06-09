package onb.com.scf.commonms.serviceimpl;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import lombok.extern.slf4j.Slf4j;
import onb.com.scf.commonms.dto.PinCodeDetails;
import onb.com.scf.commonms.dto.PincodeResponse;
import onb.com.scf.commonms.service.PincodeService;
import onb.com.scf.commonms.statusconstant.PinCodeStatusConstant;

@Service
@Slf4j
public class PincodeServiceImpl implements PincodeService {
	@Value("${common.getpincodeUrl}")
	String getpincodeUrl;

	@Autowired
	RestTemplate restTemplate;

	public PincodeResponse getAllLocationDetailsByPinCode(String pincode) {
		HttpEntity<String> pincodeDetailsObj = null;
		List<PinCodeDetails> pincodeDataList = null;
		PincodeResponse pincodeResponse = new PincodeResponse();
		try {
			String url = getpincodeUrl + pincode;
			log.info("url:" + url);
			pincodeDetailsObj = restTemplate.getForEntity(url, String.class);
			pincodeDataList = new ArrayList<>();
			if (pincodeDetailsObj.getBody() != null) {
				log.info("Response" + pincodeDetailsObj.toString());
				JSONArray json = new JSONArray(pincodeDetailsObj.getBody());
				JSONObject object = (JSONObject) json.get(0);
				if (!json.isEmpty() && object.has("PostOffice")) {
					JSONArray cityColl = (JSONArray) object.get("PostOffice");
					if (!cityColl.isEmpty()) {
						for (int i = 0; i < cityColl.length(); i++) {
							JSONObject cityData = (JSONObject) cityColl.get(0);
							PinCodeDetails obj = new PinCodeDetails();
							obj.setDistrict(cityData.getString("District"));
							obj.setState(cityData.getString("State"));
							obj.setCountry(cityData.getString("Country"));
							obj.setPincode(cityData.getString("Pincode"));
							pincodeDataList.add(obj);
						}
					}
				} else {
					pincodeResponse.setStatusCode(PinCodeStatusConstant.STATUS_FAILURE_CODE);
					pincodeResponse.setStatusMsg(PinCodeStatusConstant.STATUS_PIN_CODE_DETAILS_NOT_FOUND);
					pincodeResponse.setStatus(PinCodeStatusConstant.STATUS_FAILURE);
					pincodeResponse.setData(pincodeDataList);
				}
				if (!pincodeDataList.isEmpty()) {
					pincodeResponse.setStatusCode(PinCodeStatusConstant.STATUS_SUCCESS_CODE);
					pincodeResponse.setStatusMsg(
							PinCodeStatusConstant.STATUS_DESCRIPTION_PIN_CODE_DETAILS_RETRIVED_SUCESSFULLY);
					pincodeResponse.setStatus(PinCodeStatusConstant.STATUS_SUCCESS);
					pincodeResponse.setData(pincodeDataList);
				}
			} else {
				pincodeResponse.setStatusCode(PinCodeStatusConstant.STATUS_FAILURE_CODE);
				pincodeResponse.setStatusMsg(PinCodeStatusConstant.STATUS_PIN_CODE_DETAILS_NOT_FOUND);
				pincodeResponse.setStatus(PinCodeStatusConstant.STATUS_FAILURE);
				pincodeResponse.setData(pincodeDataList);
			}
		} catch (Exception e) {
			
			log.error("Exception Ocurred:"+e.getMessage());
		}
		return pincodeResponse;
	}
}
