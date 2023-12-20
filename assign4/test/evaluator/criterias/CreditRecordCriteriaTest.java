package evaluator.criterias;

import evaluator.Application;
import evaluator.CriteriaStatus;
import evaluator.EvaluationResult;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class CreditRecordCriteriaTest {

  @Test
  public void evaluateCriteriaPassesWhenGoodCreditHistory() {
    Application applicantWithGoodCredit = new Application(false, false, true, false);
    EvaluationResult result = CreditRecordCriteria.evaluateCriteria(applicantWithGoodCredit);

    assertEquals(CriteriaStatus.PASS, result.status());
  }

  @Test
  public void evaluateCriteriaFailsWhenPoorCreditHistory() {
    Application applicantWithPoorCredit = new Application(false, false, false, false);
    EvaluationResult result = CreditRecordCriteria.evaluateCriteria(applicantWithPoorCredit);

    assertEquals(CriteriaStatus.FAIL, result.status());
  }
}
