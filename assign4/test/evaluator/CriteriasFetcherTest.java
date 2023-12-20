package evaluator;

import org.junit.jupiter.api.Test;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

public class CriteriasFetcherTest {

  @Test
  void checkFetchedCriterionIsEmploymentCriteria() throws ClassNotFoundException, NoSuchMethodException {
    Application employedApplicant = new Application(true, false, false, false);
    EvaluationResult expectedResult = new EvaluationResult(CriteriaStatus.PASS, "Applicant has had previous employments.");

    assertEquals(expectedResult, CriteriasFetcher.fetchCriterion("employment criteria").apply(employedApplicant));
  }

  @Test
  void checkFetchedCriterionIsCrimialRecordsCriteria() throws ClassNotFoundException, NoSuchMethodException {
    Application employedApplicant = new Application(false, true, false, false);
    EvaluationResult expectedResult = new EvaluationResult(CriteriaStatus.FAIL, "Applicant has criminal records.");
    assertEquals(expectedResult, CriteriasFetcher.fetchCriterion("criminal records criteria").apply(employedApplicant));
  }

  @Test
  void checkFunctionConversionWithException() throws InvocationTargetException, IllegalAccessException, NoSuchMethodException {
    Application employedApplicant = new Application(false, true, false, false);

    assertThrows(RuntimeException.class, () -> CriteriasFetcher.createFunctionFromMethod(null, employedApplicant));
  }

  @Test
  void checkCriteriaListHasEmploymentCriteria() throws IOException {
    List<String> criterias = CriteriasFetcher.fetchAllCriterias();

    assertTrue(criterias.stream().anyMatch("employment criteria"::equalsIgnoreCase));
  }

  @Test
  void checkCriteriaListHasCriminalRecordsCriteria() throws IOException {
    List<String> criterias = CriteriasFetcher.fetchAllCriterias();

    assertTrue(criterias.stream().anyMatch("criminal records criteria"::equalsIgnoreCase));
  }
}
