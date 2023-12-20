package evaluator.criterias;

import evaluator.Application;
import evaluator.CriteriaStatus;
import evaluator.EvaluationResult;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class CriminalRecordsCriteriaTest {

  @Test
  public void evaluateCriteriaPassesWhenNoCriminalHistory() {
    Application applicantWithCleanRecord = new Application(false, false, false, false);
    EvaluationResult result = CriminalRecordsCriteria.evaluateCriteria(applicantWithCleanRecord);

    assertEquals(CriteriaStatus.PASS, result.status());
  }

  @Test
  public void evaluateCriteriaFailsWhenCriminalHistory() {
    Application applicantWithCriminalHistory = new Application(false, true, false, false);
    EvaluationResult result = CriminalRecordsCriteria.evaluateCriteria(applicantWithCriminalHistory);

    assertEquals(CriteriaStatus.FAIL, result.status());
  }
}
