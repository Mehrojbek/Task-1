package uz.pdp.apptaskone.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;
import uz.pdp.apptaskone.entity.Address;
import uz.pdp.apptaskone.entity.Hotel;
import uz.pdp.apptaskone.payload.HotelDto;
import uz.pdp.apptaskone.repository.AddressRepository;
import uz.pdp.apptaskone.repository.HotelRepository;

import java.util.*;
import java.util.Optional;

@RestController
@RequestMapping("/hotel")
public class HotelController {
    @Autowired
    HotelRepository hotelRepository;
    @Autowired
    AddressRepository addressRepository;

    @GetMapping
    public Page<Hotel> getHotelPages(@RequestParam int page){
        Pageable pageable= PageRequest.of(page,10);
        return hotelRepository.findAll(pageable);
    }

    @PostMapping
    public String addHotel(@RequestBody HotelDto hotelDto){
        boolean all = addressRepository.existsAllByDistrictNameAndHomeNumberAndStreetName(hotelDto.getDistrictName(),
                hotelDto.getHomeNumber(), hotelDto.getStreetName());
        if (all)
            return "This address allready exist";
        Address address=new Address();

        address.setHomeNumber(hotelDto.getHomeNumber());
        address.setStreetName(hotelDto.getStreetName());
        address.setDistrictName(hotelDto.getDistrictName());

        Address savedAddress = addressRepository.save(address);

        Hotel hotel=new Hotel();
        hotel.setName(hotelDto.getName());
        hotel.setAddress(savedAddress);
        hotelRepository.save(hotel);
        return "hotel saved";
    }

    @DeleteMapping("/{id}")
    public String deleteHotel(@PathVariable Integer id){
        try {
            hotelRepository.deleteById(id);
            return "hotel deleted";
        }catch (Exception e){
            return "exception in deleting";
        }
    }

    @PutMapping("/{id}")
    public String editHotel(@PathVariable Integer id, @RequestBody HotelDto hotelDto){
        Optional<Hotel> optionalHotel = hotelRepository.findById(id);
        if (optionalHotel.isPresent()) {
            Hotel editingHotel = optionalHotel.get();

            Address address = editingHotel.getAddress();

            address.setHomeNumber(hotelDto.getHomeNumber());
            address.setHomeNumber(hotelDto.getHomeNumber());
            address.setHomeNumber(hotelDto.getHomeNumber());
            Address savedAddress = addressRepository.save(address);

            editingHotel.setName(hotelDto.getName());
            editingHotel.setAddress(savedAddress);
            hotelRepository.save(editingHotel);
            return "Hotel edited";
        }
        return "Hotel not found";
    }
}
