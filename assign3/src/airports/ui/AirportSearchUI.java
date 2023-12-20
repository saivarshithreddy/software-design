package airports.ui;

import airports.Airport;
import airports.AirportSorterFetcher;
import airports.AirportsInfoProcessor;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;
import java.util.stream.IntStream;

public class AirportSearchUI {

  public static void main(String[] args) throws IOException, ClassNotFoundException, InvocationTargetException, NoSuchMethodException, IllegalAccessException {
    List<String> criterias = AirportSorterFetcher.fetchsSortersCriteria();

    System.out.println("Enter a sort criteria displayed above :");
    printAirportSortOptions(criterias);

    Scanner sc = new Scanner(System.in);
    int inputCriteria = sc.nextInt();

    printAirportsByCriteria(criterias, inputCriteria);
  }

  private static void printAirportsByCriteria(List<String> criterias, int inputCriteria) throws ClassNotFoundException, InvocationTargetException, NoSuchMethodException, IllegalAccessException {
    if (inputCriteria <= criterias.size() && inputCriteria >= 0) {
      List<Airport> airports = AirportsData.fetchAirports();

      System.out.println("""
                    
          Sorted Airports List :
          """);
      if (inputCriteria == 0) {
        AirportsInfoProcessor.processAirports(airports).forEach(System.out::println);
      } else {
        Comparator<Airport> sorter = AirportSorterFetcher.fetchASorter(criterias.get(inputCriteria - 1));
        AirportsInfoProcessor.processAirports(airports, sorter).forEach(System.out::println);
      }
    } else {
      System.out.println("Criteria entered in not valid");
    }
  }

  private static void printAirportSortOptions(List<String> criterias) {
    System.out.println("0. Nothing (no sorting)");
    IntStream.range(1, criterias.size() + 1).
        forEach(i -> System.out.println(String.format("%s. %s", i, criterias.get(i - 1))));
  }
}
