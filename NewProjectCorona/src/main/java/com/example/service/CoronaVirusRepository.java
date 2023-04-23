package com.example.service;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.jpa.repository.Query;
import com.example.model.LocationStats;
import org.springframework.stereotype.Repository;


@Repository
public interface CoronaVirusRepository extends JpaRepository <LocationStats, Integer> , CrudRepository<LocationStats, Integer>
{
	LocationStats findBycountry(String countryName);
	
	@Query(value="select * from location_stats order by latest_total_deaths desc limit ?;",nativeQuery=true)
	List<LocationStats> findCountryByLatestTotalDeaths(int count);
	
}
