package evaluator;

import java.util.function.Function;
import java.util.stream.Stream;

public interface ApplicantEvaluator {

  @SafeVarargs
  static EvaluationResult processApplication(Application application, Function<Application, EvaluationResult>... evaluationFunctions) {

    return Stream.of(evaluationFunctions)
        .map(evaluator -> evaluator.apply(application))
        .reduce(ApplicantEvaluator::mergingTwoEvaluations)
        .orElse(new EvaluationResult(CriteriaStatus.PASS, "Nothing to Check"));
  }

  private static EvaluationResult mergingTwoEvaluations(EvaluationResult evaluationResult1, EvaluationResult evaluationResult2) {
    String mergedMessage = String.format("%s %s", evaluationResult1.message(), evaluationResult2.message());

    EvaluationResult evaluationResult = evaluationResult1.status() == CriteriaStatus.PASS && evaluationResult2.status() == CriteriaStatus.PASS ?
        new EvaluationResult(CriteriaStatus.PASS, mergedMessage) : new EvaluationResult(CriteriaStatus.FAIL, mergedMessage);

    return evaluationResult;
  }
}
