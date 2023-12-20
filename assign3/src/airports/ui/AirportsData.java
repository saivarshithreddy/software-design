package airports.ui;

import airports.Airport;
import java.util.List;

public interface AirportsData {

  static List<Airport> fetchAirports() {
    return List.of(
        new Airport("IAD", "Dulles Intl", "Washington", "DC", 71, true),
        new Airport("ORD", "O'Hare International", "Chicago", "IL", 62, true),
        new Airport("MDW", "Midway International", "Chicago", "IL", 60, false),
        new Airport("IAH", "George Bush Intercont.", "Houston", "TX", 82, true),
        new Airport("SFO", "San Francisco Intl.", "San Francisco", "CA", 59, false),
        new Airport("LAX", "Los Angeles Intl.", "Los Angeles", "CA", 84, false),
        new Airport("HOU", "Hobby", "Houston", "TX", 80, false));
  }
}
