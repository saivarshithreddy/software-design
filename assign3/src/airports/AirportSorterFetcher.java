package airports;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.Comparator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public interface AirportSorterFetcher {

  @SuppressWarnings("unchecked")
  static Comparator<Airport> fetchASorter(String criteria) throws ClassNotFoundException, NoSuchMethodException, InvocationTargetException, IllegalAccessException {
    String className = "airports.airportsorters.Airport%sSorter";

    Class klass = Class.forName(String.format(className, camelCaseCritera(criteria)));

    return (Comparator<Airport>) klass.getMethod("sorter").invoke(new Object[]{});
  }

  static List<String> fetchsSortersCriteria() throws IOException {
    List<String> airPortSorters = ClassesFetcher.getClassesInPackage("airports.airportsorters");

    return airPortSorters.stream()
        .map(AirportSorterFetcher::getCriteriaFromClassName)
        .toList();
  }

  static String getCriteriaFromClassName(String className) {
    String regexPattern = "Airport(.*?)Sorter";

    Pattern pattern = Pattern.compile(regexPattern);
    Matcher matcher = pattern.matcher(className);
    matcher.matches();

    return splitCamelCaseWord(matcher.group(1));
  }

  private static String splitCamelCaseWord(String criteria) {
    Pattern pattern = Pattern.compile("(?<!(^|[A-Z]))(?=[A-Z])|(?<!^)(?=[A-Z][a-z])");

    return Stream.of(pattern.split(criteria)).collect(Collectors.joining(" "));
  }

  private static String camelCaseCritera(String criteria) {
    return Stream.of(criteria.split(" "))
        .map(AirportSorterFetcher::camelCaseEachWord)
        .collect(Collectors.joining());
  }

  private static String camelCaseEachWord(String word) {
    return word.substring(0, 1).toUpperCase() + word.substring(1);
  }
}