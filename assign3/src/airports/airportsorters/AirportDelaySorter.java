package airports.airportsorters;

import airports.Airport;
import java.util.Comparator;
import static java.util.Comparator.comparing;

public interface AirportDelaySorter {
  static Comparator<Airport> sorter() {
    return comparing(Airport::delay);
  }
}
