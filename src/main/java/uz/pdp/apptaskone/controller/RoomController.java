package uz.pdp.apptaskone.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;
import uz.pdp.apptaskone.entity.Hotel;
import uz.pdp.apptaskone.entity.Room;
import uz.pdp.apptaskone.payload.RoomDto;
import uz.pdp.apptaskone.repository.HotelRepository;
import uz.pdp.apptaskone.repository.RoomRepository;

import java.util.Optional;

@RestController
@RequestMapping("/room")
public class RoomController {
    @Autowired
    RoomRepository roomRepository;
    @Autowired
    HotelRepository hotelRepository;

    @GetMapping("/getByHotelId/{hotelId}")
    public Page<Room> getRoomsByHotelId(@RequestParam int page, @PathVariable Integer hotelId){
        Pageable pageable= PageRequest.of(page,10);
        Page<Room> allByHotelId = roomRepository.findAllByHotelId(hotelId, pageable);
        return allByHotelId;
    }

    @PostMapping
    public String addRoom(@RequestBody RoomDto roomDto){
        boolean existsByNumber = roomRepository.existsByNumber(roomDto.getNumber());
        if (existsByNumber)
            return "This room allready exist";
        Optional<Hotel> optionalHotel = hotelRepository.findById(roomDto.getHotelId());
        if (optionalHotel.isPresent()) {
            Hotel hotel = optionalHotel.get();

            Room room=new Room();
            room.setFloor(roomDto.getFloor());
            room.setNumber(roomDto.getNumber());
            room.setSize(roomDto.getSize());
            room.setHotel(hotel);
            roomRepository.save(room);
            return "room saved";
        }
        return "hotel not found";
    }

    @DeleteMapping("/{id}")
    public String deleteRoom(@PathVariable Integer id){
        try {
            roomRepository.deleteById(id);
            return "room deleted";
        }catch (Exception e){
            return "exception in deleting";
        }
    }

    @PutMapping("/{id}")
    public String editRoom(@RequestBody RoomDto roomDto,@PathVariable Integer id){
        Optional<Room> optionalRoom = roomRepository.findById(id);
        if (optionalRoom.isPresent()) {
            Room editingRoom = optionalRoom.get();
            editingRoom.setNumber(roomDto.getNumber());
            editingRoom.setSize(roomDto.getSize());
            editingRoom.setFloor(roomDto.getFloor());
            roomRepository.save(editingRoom);
            return "room edited";
        }
        return "room  not found";
    }
}
