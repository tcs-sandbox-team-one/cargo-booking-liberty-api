package com.cargotracker.shareddomain.events.transform;

import com.cargotracker.booking.domain.model.commands.UpdateCargoCommand;
import com.cargotracker.shareddomain.events.CargoHandledEvent;
import com.cargotracker.shareddomain.events.CargoHandledEventData;

/**
 * Assembler class to convert the Cargo Handled Event to the Update Cargo Details Command Model
 */
public class HandlingActivityCommandEventAssembler {
    /**
     * Static method within the Assembler class
     * @param cargoHandledEvent
     * @return AssignTrackingNumberCommand Model
     */
    public static UpdateCargoCommand toCommandFromEvent(CargoHandledEvent cargoHandledEvent){
        CargoHandledEventData eventData = cargoHandledEvent.getCargoHandledEventData();
        return new UpdateCargoCommand(
                eventData.getBookingId(),
                eventData.getHandlingType(),
                eventData.getHandlingCompletionTime(),                
                eventData.getHandlingLocation(),
                eventData.getVoyageNumber());
    }
}