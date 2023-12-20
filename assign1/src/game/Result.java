package game;

import java.util.List;
import java.util.Objects;

public class Result {
  private Status status;
  private List<Match> matches;
  public Result(Status status, List<Match> matches) {
    this.status = status;
    this.matches = matches;
  }
  public Result(Status status) {
    this.status = status;
  }
  public Status getStatus() {
    return status;
  }
  public void setStatus(Status status) {
    this.status = status;
  }
  public List<Match> getMatches() {
    return matches;
  }
  public void setMatches(List<Match> matches) {
    this.matches = matches;
  }
  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    Result result = (Result) o;
    return status == result.status && Objects.equals(matches, result.matches);
  }
}
