package hello;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Arrays;

// Description problem solved:
// This program accepts a list of METAR stations from the METAR station list.
// https://aviationweather.gov/docs/metar/stations.txt
//
// It then iterates through the valid list of METAR stations in the ArrayList
//
// It retrieves in XML format the Weather data from the https://api.laminardata.aero/v1/aerodromes/ REST API
//
// It stores the results retrieved for the METARStationID, into an aresults ArrayList, with the XML Meta Data.
//
// Future enhancements would be to have the XML results, and the Geolocation(Latitude, and Longitude), for each METAR Station

@SpringBootApplication
public class Application {

	private static final Logger log = LoggerFactory.getLogger(Application.class);

	public static void main(String args[]) {
		SpringApplication.run(Application.class);
	}
	
	@Bean
	public RestTemplate restTemplate(RestTemplateBuilder builder) {
		return builder.build();
	}

	@Bean
	public CommandLineRunner run(RestTemplate restTemplate) throws Exception {
		return args -> {

			// METAR stations list.
			// https://aviationweather.gov/docs/metar/stations.txt
            //
			// I retrieved a partial list, and this could be automated to read from the above file.
			// METARStationID are listed in an ArrayList
			ArrayList<String> a = new ArrayList<>(Arrays.asList("KABE", "KXLL", "KAOO", "KAVP", "KMDT"));

			ArrayList<String> aresults = new ArrayList<>();

			for(int i=0; i< a.size(); i++) {

				aresults.add((restTemplate.getForObject("https://api.laminardata.aero/v1/aerodromes/" + a.get(i) + "/metar?user_key=a17a9a1e9c484c0aa4ac45c2dbf9f686", String.class)));

				// could consider XML jackson mapper or conversion to JSON, and create POJO

				log.info(aresults.get(i));

			}



		};
	}
}