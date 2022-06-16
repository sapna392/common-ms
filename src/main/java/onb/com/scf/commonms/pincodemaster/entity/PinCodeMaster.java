package onb.com.scf.commonms.pincodemaster.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Entity
@Table(name = "PIN_CODE_MASTER")
@Data
public class PinCodeMaster {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long cityMasterId;
	@Column
	private String District;
	@Column
	private String State;
	@Column
	private String Pincode;
	@Column
	private String Country;
	@Column
	private String areaName;
	@Column
	private String cityName;
	@Column
	private String officeType;
	@Column
	private String deliveryStatus;
	@Column
	private String divisionName;
	@Column
	private String regionName;
	@Column
	private String circleName;
	@Column
	private String relatedSuboffice;
	@Column
	private String relatedheadoffice; 
	@Column
	private String talukaName;
	@Column
	private String officeName;
}
