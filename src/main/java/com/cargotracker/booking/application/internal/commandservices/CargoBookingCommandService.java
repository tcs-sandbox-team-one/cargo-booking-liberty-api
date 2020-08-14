package com.cargotracker.booking.application.internal.commandservices;

import com.cargotracker.booking.application.internal.outboundservices.acl.ExternalCargoRoutingService;

import com.cargotracker.booking.domain.model.aggregates.BookingId;
import com.cargotracker.booking.domain.model.aggregates.Cargo;

import com.cargotracker.booking.domain.model.commands.BookCargoCommand;
import com.cargotracker.booking.domain.model.commands.RouteCargoCommand;
import com.cargotracker.booking.domain.model.commands.UpdateCargoCommand;
import com.cargotracker.booking.domain.model.valueobjects.CargoItinerary;
import com.cargotracker.booking.domain.model.valueobjects.LastCargoHandledEvent;
import com.cargotracker.booking.infrastructure.repositories.CargoRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.UUID;

/**
 * Application Service class for the Cargo Booking Commands
 */

@Service
public class CargoBookingCommandService {
    private CargoRepository cargoRepository;
    
    @Autowired
    private ExternalCargoRoutingService externalCargoRoutingService;

    public CargoBookingCommandService(CargoRepository cargoRepository){
        this.cargoRepository = cargoRepository;
    }

    /**
     * Service Command method to book a new Cargo
     * @return BookingId of the Cargo
     */
    public BookingId bookCargo(BookCargoCommand bookCargoCommand){
        String random = UUID.randomUUID().toString().toUpperCase();
       
        bookCargoCommand.setBookingId(random.substring(0, random.indexOf("-")));
        Cargo cargo = new Cargo(bookCargoCommand);
        
        cargoRepository.save(cargo);
        return new BookingId(random.substring(0, random.indexOf("-")));
    }

    /**
     * Service Command method to assign a route to a Cargo
     * @param routeCargoCommand
     */
    public void assignRouteToCargo(RouteCargoCommand routeCargoCommand){
    	Cargo cargo = cargoRepository.findByBookingId(new BookingId(routeCargoCommand.getCargoBookingId()));

        CargoItinerary cargoItinerary = externalCargoRoutingService
                .fetchRouteForSpecification(cargo.getRouteSpecification());

        cargo.assignToRoute(cargoItinerary);       
        cargoRepository.save(cargo);
    }
    
    public void updateCargoDetails(UpdateCargoCommand updateCargoCommand) {
    	Cargo cargo = cargoRepository.findByBookingId(new BookingId(updateCargoCommand.getBookingId()));

    	LastCargoHandledEvent lastCargoHandledEvent = new LastCargoHandledEvent (1, 
    			updateCargoCommand.getHandlingType(), 
    			updateCargoCommand.getVoyageNumber(),
    			updateCargoCommand.getHandlingLocation()
    			);
    	
    	cargo.deriveDeliveryProgress(lastCargoHandledEvent);
        cargoRepository.save(cargo);
    }
}