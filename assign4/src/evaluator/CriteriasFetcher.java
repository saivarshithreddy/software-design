package evaluator;

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.List;
import java.util.function.Function;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public interface CriteriasFetcher {

  @SuppressWarnings("unchecked")
  static Function<Application, EvaluationResult> fetchCriterion(String criteriaName) throws ClassNotFoundException, NoSuchMethodException {
    String className = "evaluator.criterias.%s";

    Class klass = Class.forName(String.format(className, camelCaseCriteraName(criteriaName)));

    Method method = klass.getMethod("evaluateCriteria", Application.class);

    return application -> createFunctionFromMethod(method, application);
  }

  static EvaluationResult createFunctionFromMethod(Method method, Application application) {
    try {

      return (EvaluationResult) method.invoke(Object.class, application);
    } catch (Exception e) {
      throw new RuntimeException(e.getMessage());
    }
  }

  static List<String> fetchAllCriterias() throws IOException {
    List<String> airPortSorters = ClassesFetcher.getClassesInPackage("evaluator.criterias");

    return airPortSorters.stream()
        .map(CriteriasFetcher::splitCamelCaseWord)
        .toList();
  }


  private static String camelCaseCriteraName(String criteria) {
    return Stream.of(criteria.split(" "))
        .map(CriteriasFetcher::camelCaseEachWord)
        .collect(Collectors.joining());
  }

  private static String camelCaseEachWord(String word) {
    return word.substring(0, 1).toUpperCase() + word.substring(1);
  }

  static String splitCamelCaseWord(String criteria) {
    Pattern pattern = Pattern.compile("(?<!(^|[A-Z]))(?=[A-Z])|(?<!^)(?=[A-Z][a-z])");

    return String.join(" ", pattern.split(criteria));
  }
}
