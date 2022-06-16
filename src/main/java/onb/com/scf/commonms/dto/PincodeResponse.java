package onb.com.scf.commonms.dto;

import java.util.List;

import lombok.Data;
import onb.com.scf.commonms.pincodemaster.entity.PinCodeMaster;

@Data
public class PincodeResponse {
	private String statusCode;
	private String status;
	private String statusMsg;
	private List<PinCodeMaster> data;
}
