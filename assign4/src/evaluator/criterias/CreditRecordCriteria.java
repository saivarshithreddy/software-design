package evaluator.criterias;

import evaluator.Application;
import evaluator.CriteriaStatus;
import evaluator.EvaluationResult;

public interface CreditRecordCriteria {
  static EvaluationResult evaluateCriteria(Application application) {
    if (application.hasCreditHistory()) {
      return new EvaluationResult(CriteriaStatus.PASS, "Applicant has credit history.");
    }

    return new EvaluationResult(CriteriaStatus.FAIL, "Applicant has no credit History.");
  }
}
