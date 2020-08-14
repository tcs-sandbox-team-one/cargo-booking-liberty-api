package com.cargotracker.booking.domain.model.valueobjects;


import com.cargotracker.booking.domain.model.entities.Location;

import javax.persistence.Embedded;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.AttributeOverride;
import javax.persistence.Temporal;
import javax.persistence.Column;
import javax.persistence.TemporalType;
import javax.persistence.Entity;
import javax.persistence.Id;

import javax.validation.constraints.NotNull;
import java.util.Date;

@Entity
public class Leg {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Embedded
    @AttributeOverride(name = "voyageNumber", column = @Column(name = "voyage_number"))    
    private Voyage voyage;
    
    @Embedded
    @AttributeOverride(name = "unLocCode", column = @Column(name = "load_location_id"))
    private Location loadLocation;
    
    @Embedded
    @AttributeOverride(name = "unLocCode", column = @Column(name = "unload_location_id"))
    private Location unloadLocation;
    
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "load_time")
    @NotNull
    private Date loadTime;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "unload_time")
    @NotNull
    private Date unloadTime;
    
    public Leg () {}
    public Leg(String voyageNumber, String loadLocation, String unloadLocation, Date loadTime, Date unloadTime){
        this.voyage = new Voyage(voyageNumber);
        this.loadLocation = new Location(loadLocation);
        this.unloadLocation = new Location(unloadLocation);
        this.loadTime = loadTime;
        this.unloadTime = unloadTime;
    }
    
	public Voyage getVoyage() {
		return voyage;
	}
	
	public void setVoyage(Voyage voyage) {
		this.voyage = voyage;
	}
	
	public Location getLoadLocation() {
		return loadLocation;
	}
	
	public void setLoadLocation(Location loadLocation) {
		this.loadLocation = loadLocation;
	}
	
	public Location getUnloadLocation() {
		return unloadLocation;
	}
	
	public void setUnloadLocation(Location unloadLocation) {
		this.unloadLocation = unloadLocation;
	}
	
	public Date getLoadTime() {
		return loadTime;
	}
	
	public void setLoadTime(Date loadTime) {
		this.loadTime = loadTime;
	}
	
	public Date getUnloadTime() {
		return unloadTime;
	}
	
	public void setUnloadTime(Date unloadTime) {
		this.unloadTime = unloadTime;
	}
	
	@Override
	public String toString() {
		return "Leg [id=" + id + ", voyage=" + voyage + ", loadLocation=" + loadLocation + ", unloadLocation="
				+ unloadLocation + ", loadTime=" + loadTime + ", unloadTime=" + unloadTime + "]";
	}
}