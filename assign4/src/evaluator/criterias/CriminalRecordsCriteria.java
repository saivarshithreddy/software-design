package evaluator.criterias;

import evaluator.Application;
import evaluator.CriteriaStatus;
import evaluator.EvaluationResult;

public interface CriminalRecordsCriteria {
  static EvaluationResult evaluateCriteria(Application application) {
    if (application.hasCriminalHistory()) {
      return new EvaluationResult(CriteriaStatus.FAIL, "Applicant has criminal records.");
    }

    return new EvaluationResult(CriteriaStatus.PASS, "Applicant has no criminal records.");
  }
}
