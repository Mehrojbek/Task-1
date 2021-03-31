package uz.pdp.apptaskone.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.pdp.apptaskone.entity.Address;

public interface AddressRepository extends JpaRepository<Address,Integer> {
    boolean existsAllByDistrictNameAndHomeNumberAndStreetName(String districtName, String homeNumber, String streetName);
}
