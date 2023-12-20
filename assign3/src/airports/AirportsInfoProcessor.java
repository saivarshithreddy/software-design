package airports;

import java.util.Comparator;
import java.util.List;

public interface AirportsInfoProcessor {
  static List<Airport> processAirports(List<Airport> airports) {
    return processAirports(airports, (airport1, airport2) -> 0);
  }

  static List<Airport> processAirports(List<Airport> airports, Comparator<Airport> sorter) {
    return airports.stream()
        .map(airport -> convertNameToUppercase(airport))
        .sorted(sorter)
        .toList();
  }

  static Airport convertNameToUppercase(Airport airport) {
    return new Airport(airport.IATACode(), airport.name().toUpperCase(), airport.city(),
        airport.state(), airport.temperature(), airport.delay());
  }
}
