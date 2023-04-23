package com.example.model;

import javax.persistence.Table;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;


@Entity
@Table(name="LocationStats")
public class LocationStats 
{
	@Id
	@GeneratedValue(strategy =GenerationType.AUTO)
	private int cid;
	
	private String state;
	private String country;
	private int latestTotalDeaths;
	private int differFromPrevay;
	
	
	
	
	public int getCid() {
		return cid;
	}
	public void setCid(int cid) {
		this.cid = cid;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public String getCountry() {
		return country;
	}
	public void setCountry(String country) {
		this.country = country;
	}
	public int getLatestTotalDeaths() {
		return latestTotalDeaths;
	}
	public void setLatestTotalDeaths(int latestTotalDeaths) {
		this.latestTotalDeaths = latestTotalDeaths;
	}
	public int getDifferFromPrevay() {
		return differFromPrevay;
	}
	public void setDifferFromPrevay(int differFromPrevay) {
		this.differFromPrevay = differFromPrevay;
	}
	@Override
	public String toString() {
		return "LocationStats [cid=" + cid + ", state=" + state + ", country=" + country + ", latestTotalDeaths="
				+ latestTotalDeaths + ", differFromPrevay=" + differFromPrevay + "]";
	}
	
}

