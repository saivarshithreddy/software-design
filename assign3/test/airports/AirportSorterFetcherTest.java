package airports;

import org.junit.jupiter.api.Test;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

public class AirportSorterFetcherTest {

  private Airport iad = new Airport("IAD", "Dulles Intl", "Washington", "DC", 71, true);
  private Airport ord = new Airport("ORD", "O'Hare International", "Chicago", "IL", 62, true);
  private Airport mdw = new Airport("MDW", "Midway International", "Chicago", "IL", 60, false);


  @Test
  void checkFetchedSorterIsNameSorter() throws ClassNotFoundException, NoSuchMethodException, InvocationTargetException, IllegalAccessException {
    assertTrue(AirportSorterFetcher.fetchASorter("name").compare(iad, ord) < 0);
  }

  @Test
  void checkFetchedSorterIsCitySorter() throws ClassNotFoundException, NoSuchMethodException, InvocationTargetException, IllegalAccessException {
    assertTrue(AirportSorterFetcher.fetchASorter("city").compare(iad, ord) > 0);
  }

  @Test
  void checkFetchedSorterIsCityAndNameSorter() throws ClassNotFoundException, NoSuchMethodException, InvocationTargetException, IllegalAccessException {
    assertTrue(AirportSorterFetcher.fetchASorter("city and name").compare(ord, mdw) > 0);
  }

  @Test
  void checkSorterCriteriaListHasName() throws IOException {
    List<String> criterias = AirportSorterFetcher.fetchsSortersCriteria();

    assertTrue(criterias.stream().anyMatch("name"::equalsIgnoreCase));
  }

  @Test
  void checkSorterCriteriaListHasCity() throws IOException {
    List<String> criterias = AirportSorterFetcher.fetchsSortersCriteria();

    assertTrue(criterias.stream().anyMatch("city"::equalsIgnoreCase));
  }

  @Test
  void checkSorterCriteriaListHasCityAndName() throws IOException {
    List<String> criterias = AirportSorterFetcher.fetchsSortersCriteria();

    assertTrue(criterias.stream().anyMatch("city and name"::equalsIgnoreCase));
  }
}
