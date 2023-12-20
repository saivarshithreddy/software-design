package processor;

import java.util.stream.Collectors;
import java.util.stream.Stream;

public class DocumentSpellCheck {
  private SpellingChecker checker;

  public void setSpellingChecker(SpellingChecker spellingChecker) {
    checker = spellingChecker;
  }

  public String processText(String text) {
    String LINESPACE = System.lineSeparator();

    return Stream.of(text.split(LINESPACE)).
        map(this::processEachLine)
        .collect(Collectors.joining(LINESPACE));
  }

  private String processEachLine(String line) {
    String SPACE = " ";

    return Stream.of(line.split(SPACE))
        .map(this::processWord)
        .collect(Collectors.joining(SPACE));
  }

  private String processWord(String word) {
    try {
      return checker.isSpellingCorrect(word) ? word : String.format("[%s]", word);
    } catch (Exception e) {
      return String.format("?%s?", word);
    }
  }
}
