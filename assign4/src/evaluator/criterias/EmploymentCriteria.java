package evaluator.criterias;

import evaluator.Application;
import evaluator.CriteriaStatus;
import evaluator.EvaluationResult;

public interface EmploymentCriteria {
  static EvaluationResult evaluateCriteria(Application application) {
    if (application.isEmployed()) {
      return new EvaluationResult(CriteriaStatus.PASS, "Applicant has had previous employments.");
    }

    return new EvaluationResult(CriteriaStatus.FAIL, "Applicant has no previous employments.");
  }
}
