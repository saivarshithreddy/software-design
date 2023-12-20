package evaluator;

import evaluator.criterias.CriminalRecordsCriteria;
import evaluator.criterias.EmploymentCriteria;
import org.junit.jupiter.api.Test;
import static evaluator.ApplicantEvaluator.processApplication;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ApplicantEvaluatorTest {

  @Test
  public void canary() {
    assertTrue(true);
  }

  @Test
  public void processApplicationWithNoCriteriaProvided() {
    EvaluationResult expectedResult = new EvaluationResult(CriteriaStatus.PASS, "Nothing to Check");

    assertEquals(expectedResult, processApplication(new Application(false, false, false, false)));
  }

  @Test
  public void processApplicationWithCriteriaProvided() {
    EvaluationResult expectedResult = new EvaluationResult(CriteriaStatus.PASS, "Applicant has had previous employments.");

    assertEquals(expectedResult, processApplication(new Application(true, false, false, false), EmploymentCriteria::evaluateCriteria));
  }

  @Test
  public void processApplicationWithCriteriaProvidedAndNotMatched() {
    EvaluationResult expectedResult = new EvaluationResult(CriteriaStatus.FAIL, "Applicant has no previous employments.");

    assertEquals(expectedResult, processApplication(new Application(false, false, false, false), EmploymentCriteria::evaluateCriteria));
  }

  @Test
  public void processApplicationWithEmplymentCriteriaAndNoCriminalRecord() {
    EvaluationResult expectedResult = new EvaluationResult(CriteriaStatus.PASS, "Applicant has had previous employments. Applicant has no criminal records.");

    assertEquals(expectedResult, processApplication(new Application(true, false, false, false), EmploymentCriteria::evaluateCriteria, CriminalRecordsCriteria::evaluateCriteria));
  }

  @Test
  public void processApplicationWithNoEmplymentCriteriaAndNoCriminalRecord() {
    EvaluationResult expectedResult = new EvaluationResult(CriteriaStatus.FAIL, "Applicant has no previous employments. Applicant has no criminal records.");

    assertEquals(expectedResult, processApplication(new Application(false, false, false, false), EmploymentCriteria::evaluateCriteria, CriminalRecordsCriteria::evaluateCriteria));
  }

  @Test
  public void processApplicationWithEmplymentCriteriaAndCriminalRecord() {
    EvaluationResult expectedResult = new EvaluationResult(CriteriaStatus.FAIL, "Applicant has had previous employments. Applicant has criminal records.");

    assertEquals(expectedResult, processApplication(new Application(true, true, false, false), EmploymentCriteria::evaluateCriteria, CriminalRecordsCriteria::evaluateCriteria));
  }
}
