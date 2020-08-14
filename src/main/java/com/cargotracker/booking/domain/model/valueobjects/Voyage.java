package com.cargotracker.booking.domain.model.valueobjects;

import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 *  Class representing the Cargo Voyage
 */
@Embeddable
public class Voyage {

    @Column(name = "id", insertable = false, updatable = false)
    private String voyageNumber;

    public Voyage(){}
    public Voyage(String voyageId){
        this.voyageNumber = voyageId;
    }

    public String getVoyageId(){return this.voyageNumber;}
    public void setVoyageId(String voyageId){this.voyageNumber = voyageId;}
}
