package com.cargotracker.booking.interfaces.rest;

import com.cargotracker.booking.application.internal.commandservices.CargoBookingCommandService;
import com.cargotracker.booking.interfaces.rest.dto.RouteCargoResource;
import com.cargotracker.booking.interfaces.rest.transform.RouteCargoCommandDTOAssembler;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/cargorouting")
public class CargoRoutingController {


    private CargoBookingCommandService cargoBookingCommandService; // Application Service Dependency

    /**
     * Provide the dependencies
     * @param cargoBookingCommandService
     */
    public CargoRoutingController(CargoBookingCommandService cargoBookingCommandService){
        this.cargoBookingCommandService = cargoBookingCommandService;
    }

    /**
     * POST method to route a cargo
     * @param routeCargoResource
     */
    @PostMapping
    @ResponseBody
    public String routeCargo(@RequestBody RouteCargoResource routeCargoResource){
        cargoBookingCommandService.assignRouteToCargo(
                RouteCargoCommandDTOAssembler.toCommandFromDTO(routeCargoResource));

        final String returnValue = "Cargo Routed";
        return returnValue;
    }
}