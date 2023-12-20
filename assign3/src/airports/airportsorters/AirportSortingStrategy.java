package airports.airportsorters;

import airports.Airport;
import java.util.Comparator;
import static java.util.Comparator.comparing;

public interface AirportSortingStrategy {

  static Comparator<Airport> sorter() {
    return comparing(Airport::city).thenComparing(Airport::name);
  }
  
}