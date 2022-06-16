package onb.com.scf.commonms.pincodemaster.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import onb.com.scf.commonms.pincodemaster.entity.PinCodeMaster;

@Repository
public interface PincodeMasterRepository extends JpaRepository<PinCodeMaster, Long> {
	@Query(value="select * from pin_code_master where pincode=:pinCode",nativeQuery = true)
	List<PinCodeMaster> findByPincode(String pinCode);
}
