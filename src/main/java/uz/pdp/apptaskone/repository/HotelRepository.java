package uz.pdp.apptaskone.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.pdp.apptaskone.entity.Hotel;

public interface HotelRepository extends JpaRepository<Hotel,Integer> {
}
