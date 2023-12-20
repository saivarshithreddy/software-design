package evaluator.criterias;

import evaluator.Application;
import evaluator.CriteriaStatus;
import evaluator.EvaluationResult;

public interface SecurityClearanceCriteria {
  static EvaluationResult evaluateCriteria(Application application) {
    if (application.hasSecurityClearnace()) {
      return new EvaluationResult(CriteriaStatus.PASS, "Applicant passes Security Check.");
    }

    return new EvaluationResult(CriteriaStatus.FAIL, "Applicant not passes Security Check.");
  }
}
