package onb.com.scf.commonms.serviceimpl;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;
import org.springframework.web.client.RestTemplate;

import lombok.extern.slf4j.Slf4j;
import onb.com.scf.commonms.dto.PincodeResponse;
import onb.com.scf.commonms.pincodemaster.entity.PinCodeMaster;
import onb.com.scf.commonms.pincodemaster.repository.PincodeMasterRepository;
import onb.com.scf.commonms.service.PincodeService;
import onb.com.scf.commonms.statusconstant.PinCodeStatusConstant;

@Service
@Slf4j
public class PincodeServiceImpl implements PincodeService {
	@Value("${common.getpincodeUrl}")
	String getpincodeUrl;

	@Autowired
	RestTemplate restTemplate;

	@Autowired
	PincodeMasterRepository pincodeMasterRepository;

	public PincodeResponse getAllLocationDetailsByPinCode(String pincode) {
		HttpEntity<String> pincodeDetailsObj = null;
		List<PinCodeMaster> pincodeDataList = null;
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
							JSONObject cityData = (JSONObject) cityColl.get(i);
							PinCodeMaster obj = new PinCodeMaster();
							obj.setDistrict(cityData.getString("District"));
							obj.setState(cityData.getString("State"));
							obj.setCountry(cityData.getString("Country"));
							obj.setPincode(cityData.getString("Pincode"));
							obj.setAreaName(cityData.getString("Name"));
							obj.setCityName(cityData.getString("Division"));
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
	public int savePinCodeDataFromFile() throws FileNotFoundException {
		List<PinCodeMaster> cityMasterList = new ArrayList<>();
		File file = ResourceUtils.getFile("classpath:pincodeDatas.txt");
		try (Scanner sc = new Scanner(file)) {
			while (sc.hasNextLine()) {
				PinCodeMaster cm = new PinCodeMaster();
				String line = sc.nextLine();
				String[] arr = line.split(",");
				cm.setOfficeName(arr[0]);
				cm.setPincode(arr[1]);
				cm.setOfficeType(arr[2]);
				cm.setDeliveryStatus(arr[3]);
				cm.setDivisionName(arr[4]);
				cm.setRegionName(arr[5]);
				cm.setCircleName(arr[6]);
				cm.setTalukaName(arr[7]);
				cm.setDistrict(arr[8]);
				cm.setState(arr[9]);
				cm.setRelatedSuboffice(arr[11]);
				cm.setRelatedheadoffice(arr[12]);
				cm.setCityName(arr[4]);
				cm.setCountry("INDIA");
				cityMasterList.add(cm);
			}
			pincodeMasterRepository.saveAll(cityMasterList);
		}
		return cityMasterList.size();
	}
	public PincodeResponse getLocationDetailsByPinCode(String pincode) {
		PincodeResponse pincodeResponse = new PincodeResponse();
		try {
			List<PinCodeMaster>	pincodeDataList = pincodeMasterRepository.findByPincode(pincode);
			if(pincodeDataList.isEmpty()) {
				pincodeResponse.setStatusCode(PinCodeStatusConstant.STATUS_FAILURE_CODE);
				pincodeResponse.setStatusMsg(PinCodeStatusConstant.STATUS_PIN_CODE_DETAILS_NOT_FOUND);
				pincodeResponse.setStatus(PinCodeStatusConstant.STATUS_FAILURE);
			}else {
				pincodeResponse.setStatusCode(PinCodeStatusConstant.STATUS_SUCCESS_CODE);
				pincodeResponse.setStatusMsg(
						PinCodeStatusConstant.STATUS_DESCRIPTION_PIN_CODE_DETAILS_RETRIVED_SUCESSFULLY);
				pincodeResponse.setStatus(PinCodeStatusConstant.STATUS_SUCCESS);
			}
			pincodeResponse.setData(pincodeDataList);
		}catch(Exception e) {
			log.error(PinCodeStatusConstant.EXCEPTION_OCCURRED + e.getMessage());
		}
		return pincodeResponse;
	}
}
