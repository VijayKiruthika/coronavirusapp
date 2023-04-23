package com.example.service;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;
import java.io.StringReader;
import javax.annotation.PostConstruct;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import com.example.model.LocationStats;


@Service
public class coronaDataService 
{
	private static String VIRUS_DATA_URL="https://raw.githubusercontent.com/CSSEGISandData/COVID-19/master/csse_covid_19_data/csse_covid_19_time_series/time_series_covid19_deaths_global.csv";
	
	private List<LocationStats> allstates=new ArrayList<LocationStats>();
	
	@Autowired
	CoronaVirusRepository repository;
	
	public List<LocationStats> getAllstates() {
		return allstates;
	}


	public void setAllstates(List<LocationStats> allstates) {
		this.allstates = allstates;
	}


	@PostConstruct
	@Scheduled(cron="* * * 1 * *")
	public void fetchVirusData() throws IOException, InterruptedException
	{
		List<LocationStats> newstates=new ArrayList<LocationStats>();
	    HttpClient client=HttpClient.newHttpClient();
	    HttpRequest request=HttpRequest.newBuilder().uri(URI.create(VIRUS_DATA_URL)).build();
	    HttpResponse<String> httpResponse=client.send(request, HttpResponse.BodyHandlers.ofString());
	    StringReader csvBodyReader=new StringReader(httpResponse.body());
	    System.out.println(httpResponse.body());
	    Iterable<CSVRecord> records = CSVFormat.DEFAULT.withFirstRecordAsHeader().parse(csvBodyReader);
	    for (CSVRecord record : records) {
	    	LocationStats locationState=new LocationStats();
	    	locationState.setState(record.get("Province/State"));
	    	locationState.setCountry(record.get("Country/Region"));
	    	int latestCase=Integer.parseInt(record.get(record.size()-1));
	 	    int PrevCase=Integer.parseInt(record.get(record.size()-2));
	 	   locationState.setLatestTotalDeaths(latestCase);
	 	   locationState.setDifferFromPrevay(latestCase-PrevCase);
	    	System.out.println(locationState);
	    	newstates.add(locationState);
	        }
	    this.allstates=newstates;
	}
	
	public List<LocationStats> collectCountRows()
	{
		Sort sort=Sort.by(Sort.Direction.DESC,"latestTotalDeaths");
		 List<LocationStats> locationState=repository.findAll(sort);
		 for(int i=0;i<=10;i++)
		 {
			 System.out.println(""+locationState.get(i).getCountry()+""+locationState.get(i).getLatestTotalDeaths());
		 }
		 return locationState;
	}
}
