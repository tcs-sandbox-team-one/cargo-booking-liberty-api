package com.cargotracker.booking.interfaces.events;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;

import com.cargotracker.booking.application.internal.commandservices.CargoBookingCommandService;
import com.cargotracker.booking.domain.model.commands.UpdateCargoCommand;
import com.cargotracker.booking.infrastructure.brokers.rabbitmq.CargoEventSource;
import com.cargotracker.shareddomain.events.CargoHandledEvent;
import com.cargotracker.shareddomain.events.transform.HandlingActivityCommandEventAssembler;

/**
 * Event Handler for the Cargo Handled Event that the Booking Bounded Context is interested in
 */

@EnableAutoConfiguration
@EnableBinding(CargoEventSource.class)
public class CargoHandledEventHandler {
  @Autowired
  private CargoBookingCommandService cargoBookingCommandService;
  
  @StreamListener(CargoEventSource.cargoHandlingChannel2)
  public void receiveEvent(CargoHandledEvent cargoHandledEvent) {  	
  	UpdateCargoCommand updatecargoCommand = HandlingActivityCommandEventAssembler.toCommandFromEvent (cargoHandledEvent);
  	cargoBookingCommandService.updateCargoDetails(updatecargoCommand);
  }
}