package com.cargotracker.booking.interfaces.rest;

import java.util.List;

import com.cargotracker.booking.application.internal.commandservices.CargoBookingCommandService;
import com.cargotracker.booking.application.internal.queryservices.CargoBookingQueryService;
import com.cargotracker.booking.domain.model.aggregates.BookingId;
import com.cargotracker.booking.domain.model.aggregates.Cargo;
import com.cargotracker.booking.interfaces.rest.dto.BookCargoResource;
import com.cargotracker.booking.interfaces.rest.transform.BookCargoCommandDTOAssembler;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller    // This means that this class is a Controller
@RequestMapping("/cargobooking")
public class CargoBookingController {


    private CargoBookingCommandService cargoBookingCommandService; // Application Service Dependency
    private CargoBookingQueryService cargoBookingQueryService;

    /**
     * Provide the dependencies
     * @param cargoBookingCommandService
     */
    public CargoBookingController(CargoBookingCommandService cargoBookingCommandService, CargoBookingQueryService cargoBookingQueryService){
        this.cargoBookingCommandService = cargoBookingCommandService;
        this.cargoBookingQueryService = cargoBookingQueryService;
    }

    /**
     * POST method to book a cargo
     * @param bookCargoResource
     */

    @PostMapping
    @ResponseBody
    public BookingId bookCargo(@RequestBody  BookCargoResource bookCargoResource){
        BookingId bookingId  = cargoBookingCommandService.bookCargo(BookCargoCommandDTOAssembler.toCommandFromDTO(bookCargoResource));
        return bookingId;
    }

    /**
     * GET method to retrieve a Cargo
     * @param bookingId
     * @return
     */
    @GetMapping("/findCargo")
    @ResponseBody
    public Cargo findByBookingId(@RequestParam("bookingId") String bookingId){
        return cargoBookingQueryService.find(bookingId);
    }

    /**
     * GET method to retrieve all Cargo
     * @param
     * @return
     */
    @GetMapping("/getallCargo")
    @ResponseBody
    public List<Cargo> findByBookingId(){
        return cargoBookingQueryService.findAll();
    }    
}