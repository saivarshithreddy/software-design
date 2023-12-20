package evaluator.criterias;

import evaluator.Application;
import evaluator.CriteriaStatus;
import evaluator.EvaluationResult;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class EmploymentCriteriaTest {

  @Test
  public void evaluateCriteriaPassesWhenEmployed() {
    Application employedApplicant = new Application(true, false, false, false);
    EvaluationResult result = EmploymentCriteria.evaluateCriteria(employedApplicant);

    assertEquals(CriteriaStatus.PASS, result.status());
  }

  @Test
  public void evaluateCriteriaFailsWhenNotEmployed() {
    Application unemployedApplicant = new Application(false, false, false, false);
    EvaluationResult result = EmploymentCriteria.evaluateCriteria(unemployedApplicant);

    assertEquals(CriteriaStatus.FAIL, result.status());
  }
}
