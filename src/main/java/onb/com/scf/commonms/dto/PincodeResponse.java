package onb.com.scf.commonms.dto;

import java.util.List;

import lombok.Data;

@Data
public class PincodeResponse {
	private String statusCode;
	private String status;
	private String statusMsg;
	private List<PinCodeDetails> data;
}
