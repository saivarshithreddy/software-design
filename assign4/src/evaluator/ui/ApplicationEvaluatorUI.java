package evaluator.ui;

import evaluator.ApplicantEvaluator;
import evaluator.Application;
import evaluator.CriteriasFetcher;
import evaluator.EvaluationResult;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Scanner;
import java.util.function.Function;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class ApplicationEvaluatorUI {

  @SuppressWarnings("unchecked")
  public static void main(String[] args) {
    try (var scanner = new Scanner(System.in)) {
      var selectedCriterias = selectCriterias();
      do {
        var application = fillApplication();

        var evaluationResult = ApplicantEvaluator.processApplication(application, selectedCriterias.toArray(Function[]::new));

        printEvaluationResult(evaluationResult);

        System.out.print("\nEvaluate Another Application (yes/no): ");
      } while (scanner.nextLine().equalsIgnoreCase("yes"));
    } catch (Exception e) {
      System.err.println("Error: " + e.getMessage());
    }
  }

  private static void printEvaluationResult(EvaluationResult evaluationResult) {
    String resultPlaceHolder = """
                
        Evaluation Results :
        Status : %s
        Summary : %s""";

    String result = String.format(resultPlaceHolder, evaluationResult.status(), evaluationResult.message());
    System.out.println(result);
  }

  private static Function<Application, EvaluationResult> getFunctionByCriteriaName(String criteriaName) {
    try {
      return CriteriasFetcher.fetchCriterion(criteriaName);
    } catch (Exception e) {
      throw new RuntimeException(e.getMessage());
    }
  }

  @SuppressWarnings("unchecked")
  private static Application fillApplication() throws InvocationTargetException, InstantiationException, IllegalAccessException {
    System.out.println("\nEnter the Application Details:");
    Field[] applicationFields = Application.class.getDeclaredFields();

    List<Boolean> inputs = Stream.of(applicationFields).map(ApplicationEvaluatorUI::askInputForAField).toList();

    Constructor<Application> constructor = (Constructor<Application>) Application.class.getConstructors()[0];

    return constructor.newInstance(inputs.toArray());
  }

  private static boolean askInputForAField(Field field) {
    Scanner scanner = new Scanner(System.in);
    System.out.print(String.format("%s (yes/no) : ", CriteriasFetcher.splitCamelCaseWord(field.getName())));
    String input = scanner.nextLine();

    if ("yes".equals(input) || "no".equals(input)) {
      return "yes".equals(input);
    }

    System.out.println("Enter a valid input !");
    return askInputForAField(field);
  }

  private static List<Function<Application, EvaluationResult>> selectCriterias() throws IOException {
    List<String> criterias = CriteriasFetcher.fetchAllCriterias();

    System.out.println("\nPlease Select Criterias for which Application should be evaluated :");
    IntStream.range(0, criterias.size()).
        forEach(i -> System.out.println(String.format("%s. %s", i + 1, criterias.get(i))));

    Scanner scanner = new Scanner(System.in);
    System.out.print("\nEnter comma-separated criteria numbers: ");
    String selectedCriterias = scanner.next();

    return Stream.of(selectedCriterias.split(","))
        .map(criteria -> criterias.get(Integer.parseInt(criteria.trim()) - 1))
        .map(ApplicationEvaluatorUI::getFunctionByCriteriaName)
        .toList();
  }
}
