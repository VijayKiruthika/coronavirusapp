package com.example.controller;

import org.springframework.stereotype.Controller;
import java.util.List;
import java.util.Optional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.ui.Model;
import com.example.model.LocationStats;
import com.example.service.coronaDataService;
import com.example.service.CoronaVirusRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;



@Controller
public class HomeController 
{
	coronaDataService crnService;
	
	@Autowired
	CoronaVirusRepository repository;
	
	@Autowired
	public void setCrnService(coronaDataService crnService) {
		this.crnService = crnService;
	}

	
 
	@GetMapping("/")
	public String home(Model model) 
	{
		List<LocationStats> allstates=crnService.getAllstates();
		int totalDeathsReported=allstates.stream().mapToInt(stat->stat.getLatestTotalDeaths()).sum();
		model.addAttribute("LocationStates",allstates);
		model.addAttribute("totalDeathsReported",totalDeathsReported);
		repository.saveAll(allstates);
	    return "home";
}
	@RequestMapping(path="/collectChartData",produces = {"application/json"})
	@ResponseBody
	public  List<LocationStats> collectChartData(Model model)
	{
	  System.out.println("View Chart Details");
	  List<LocationStats> allstates=crnService.getAllstates();
	  int totalDeathsReported=allstates.stream().mapToInt(stat->stat.getLatestTotalDeaths()).sum();
	  model.addAttribute("LocationStates",allstates);
	  model.addAttribute("totalDeathsReported",totalDeathsReported);
	  return allstates;
	  }
	
	@RequestMapping(path="/collectChartData/{cid}",produces = {"application/json"})
	@ResponseBody
	public Optional<LocationStats> collectchartDataByCountryId(@PathVariable("cid")int cid,Model model)
	{
		System.out.println("Here view Chart data by country ID.....");
		Optional<LocationStats> locationStates=repository.findById(cid);
		return locationStates;
	}
	@RequestMapping(path="/ccollectChartData/{name}",produces = {"application/json"})
	@ResponseBody
	public LocationStats collectchartDataByCountryName(@PathVariable("name") String countryName, Model model)
	{
		System.out.println("Here view Chart data by country Name.....");
	    LocationStats locationStates=repository.findBycountry(countryName);
		return locationStates;
	}
	
	@RequestMapping(path="/ccollectChartData/top={count}",produces = {"application/json"})
	@ResponseBody
	public List<LocationStats> collectchartDataByCountryTop(@PathVariable("count")int count,Model model)
	{
		
		List<LocationStats> local=crnService.collectCountRows();
		
	    List<LocationStats> locationStates=repository.findCountryByLatestTotalDeaths(count);
		
	    return locationStates;
	}
        
	@RequestMapping(value ="/viewChart",method = RequestMethod.GET)
	public ModelAndView viewchart()
	{
		return new ModelAndView("viewChart").addObject("myURL",new String("http://localhost:8080/collectChartData"));
	}
	
	@GetMapping("/viewChart/{id}")
	public String ViewChartById(@PathVariable("id")int id,Model m)
	{
		m.addAttribute("id",id);
		m.addAttribute("myURL","http://localhost:8080/collectChartData/"+id);
		return "viewChart";
	}
	
	public String viewChartByCountryName(@RequestParam String name,Model m)
	{
		m.addAttribute("myURL","http://localhost:8080/collectChartData/name?"+name);
		return "viewChart";
	}
	
	
}