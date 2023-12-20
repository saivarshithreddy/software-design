package airports;

import airports.airportsorters.*;
import org.junit.jupiter.api.Test;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

public class AirportsInfoProcessorTest {

  private Airport iad = new Airport("IAD", "Dulles Intl", "Washington", "DC", 71, true);
  private Airport ord = new Airport("ORD", "O'Hare International", "Chicago", "IL", 62, true);
  private Airport mdw = new Airport("MDW", "Midway International", "Chicago", "IL", 60, false);

  private Airport expectedIad = new Airport("IAD", "DULLES INTL", "Washington", "DC", 71, true);
  private Airport expectedOrd = new Airport("ORD", "O'HARE INTERNATIONAL", "Chicago", "IL", 62, true);
  private Airport expectedmdw = new Airport("MDW", "MIDWAY INTERNATIONAL", "Chicago", "IL", 60, false);

  @Test
  public void canary() {
    assertTrue(true);
  }

  @Test
  void getEmptyAirportsList() {
    assertEquals(List.of(), AirportsInfoProcessor.processAirports(List.of()));
  }

  @Test
  void checkAirportsListForOneAirportNameInCaps() {
    assertEquals(List.of(expectedIad), AirportsInfoProcessor.processAirports(List.of(iad)));
  }

  @Test
  void checkAirportsListForTwoAirportNameInCaps() {
    assertEquals(List.of(expectedOrd, expectedIad), AirportsInfoProcessor.processAirports(List.of(ord, iad)));
  }

  @Test
  void sortAirPortListByIATACode() {
    assertEquals(List.of(expectedIad, expectedOrd), AirportsInfoProcessor.processAirports(List.of(ord, iad), AirportIATACodeSorter.sorter()));
  }

  @Test
  void sortAirportsByname() {
    assertEquals(List.of(expectedIad, expectedmdw, expectedOrd), AirportsInfoProcessor.processAirports(List.of(iad, ord, mdw), AirportNameSorter.sorter()));
  }

  @Test
  void sortAirportsByCity() {
    assertEquals(List.of(expectedOrd, expectedmdw, expectedIad), AirportsInfoProcessor.processAirports(List.of(iad, ord, mdw), AirportCitySorter.sorter()));
  }

  @Test
  void sortAirportsByState() {
    assertEquals(List.of(expectedIad, expectedOrd, expectedmdw), AirportsInfoProcessor.processAirports(List.of(iad, ord, mdw), AirportStateSorter.sorter()));
  }

  @Test
  void sortAirportsByTemperature() {
    assertEquals(List.of(expectedmdw, expectedOrd, expectedIad), AirportsInfoProcessor.processAirports(List.of(iad, ord, mdw), AirportTemperatureSorter.sorter()));
  }

  @Test
  void sortAirportsByDelay() {
    assertEquals(List.of(expectedmdw, expectedIad, expectedOrd), AirportsInfoProcessor.processAirports(List.of(iad, ord, mdw), AirportDelaySorter.sorter()));
  }

  @Test
  void sortAirportsByCityAndName() {
    assertEquals(List.of(expectedmdw, expectedOrd, expectedIad), AirportsInfoProcessor.processAirports(List.of(iad, ord, mdw), AirportCityAndNameSorter.sorter()));
  }
}
