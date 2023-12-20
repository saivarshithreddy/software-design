package evaluator.criterias;

import evaluator.Application;
import evaluator.CriteriaStatus;
import evaluator.EvaluationResult;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class SecurityClearanceCriteriaTest {

  @Test
  public void evaluateCriteriaPassesWhenClearanceIsGranted() {
    Application applicantWithClearance = new Application(false, false, false, true);
    EvaluationResult result = SecurityClearanceCriteria.evaluateCriteria(applicantWithClearance);

    assertEquals(CriteriaStatus.PASS, result.status());
  }

  @Test
  public void evaluateCriteriaFailsWhenClearanceIsDenied() {
    Application applicantWithoutClearance = new Application(false, false, false, false);
    EvaluationResult result = SecurityClearanceCriteria.evaluateCriteria(applicantWithoutClearance);

    assertEquals(CriteriaStatus.FAIL, result.status());
  }
}
