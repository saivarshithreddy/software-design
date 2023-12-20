package processor;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class DocumentSpellCheckTest {
  private DocumentSpellCheck documentSpellCheck;
  private SpellingChecker spellingChecker;

  @BeforeEach
  void init() {
    spellingChecker = mock(SpellingChecker.class);
    when(spellingChecker.isSpellingCorrect(anyString())).thenReturn(true);

    documentSpellCheck = new DocumentSpellCheck();
    documentSpellCheck.setSpellingChecker(spellingChecker);
  }

  @Test
  public void canary() {
    assertTrue(true);
  }

  @Test
  public void checkForCorrectionsInEmptyStringAsText() {
    assertEquals("", documentSpellCheck.processText(""));
  }

  @Test
  public void checkForCorrectionsWithCorrectWordInText() {
    assertEquals("hello", documentSpellCheck.processText("hello"));
  }

  @Test
  public void checkForCorrectionsWithWrongWordInText() {
    when(spellingChecker.isSpellingCorrect("blah")).thenReturn(false);

    assertEquals("[blah]", documentSpellCheck.processText("blah"));
  }

  @Test
  public void checkForTwoCorrectWordsInText() {
    assertEquals("hello there", documentSpellCheck.processText("hello there"));
  }

  @Test
  public void checkForTwoWordsWithSecondWordWrongInText() {
    when(spellingChecker.isSpellingCorrect("tyop")).thenReturn(false);

    assertEquals("hello [tyop]", documentSpellCheck.processText("hello tyop"));
  }

  @Test
  public void checkForTwoWordsWithFirstWordWrongInText() {
    when(spellingChecker.isSpellingCorrect("misp")).thenReturn(false);

    assertEquals("[misp] hello", documentSpellCheck.processText("misp hello"));
  }

  @Test
  public void checkForMultipleWordsWithIncorrectSpellingInText() {
    when(spellingChecker.isSpellingCorrect("tyop")).thenReturn(false);
    when(spellingChecker.isSpellingCorrect("misp")).thenReturn(false);

    assertEquals("hello [tyop] there [misp]", documentSpellCheck.processText("hello tyop there misp"));

  }

  @Test
  public void checkForTwoLinesWithcorrectSpelling() {
    when(spellingChecker.isSpellingCorrect("tyop")).thenReturn(true);
    when(spellingChecker.isSpellingCorrect("misp")).thenReturn(true);
    String text = """
        hello tyop there
        misp in the text""";

    assertEquals(text, documentSpellCheck.processText(text));
  }

  @Test
  public void checkForTwoLinesWithInCorrectSpelling() {
    when(spellingChecker.isSpellingCorrect("tyop")).thenReturn(false);
    when(spellingChecker.isSpellingCorrect("misp")).thenReturn(false);
    String text = """
        hello tyop there
        misp in the text""";
    String updatedText = """
        hello [tyop] there
        [misp] in the text""";


    assertEquals(updatedText, documentSpellCheck.processText(text));
  }

  @Test
  public void checkForThreeLinesWithCorrectSpelling() {
    when(spellingChecker.isSpellingCorrect("tyop")).thenReturn(true);
    when(spellingChecker.isSpellingCorrect("misp")).thenReturn(true);
    when(spellingChecker.isSpellingCorrect("correcion")).thenReturn(true);
    String text = """
        hello tyop there
        misp in the text
        check for correcion""";

    assertEquals(text, documentSpellCheck.processText(text));
  }

  @Test
  public void checkForThreeLinesWithWrongSpelling() {
    when(spellingChecker.isSpellingCorrect("tyop")).thenReturn(false);
    when(spellingChecker.isSpellingCorrect("misp")).thenReturn(false);
    when(spellingChecker.isSpellingCorrect("correcion")).thenReturn(false);
    String text = """
        hello tyop there
        misp in the text
        check for correcion""";
    String updatedText = """
        hello [tyop] there
        [misp] in the text
        check for [correcion]""";


    assertEquals(updatedText, documentSpellCheck.processText(text));
  }

  @Test
  public void checkForExceptionFromSpellChecker() {
    when(spellingChecker.isSpellingCorrect("there")).thenThrow(new RuntimeException("Network failure"));
    when(spellingChecker.isSpellingCorrect("aare")).thenReturn(false);

    String text = """
        hello there
        how aare you""";
    String updatedText = """
        hello ?there?
        how [aare] you""";

    assertEquals(updatedText, documentSpellCheck.processText(text));
  }

  @Test
  public void checkForTwoWordExceptionFromSpellChecker() {
    when(spellingChecker.isSpellingCorrect("there")).thenThrow(new RuntimeException("Network failure"));
    when(spellingChecker.isSpellingCorrect("aare")).thenThrow(new RuntimeException("Network failure"));
    when(spellingChecker.isSpellingCorrect("hwo")).thenReturn(false);

    String text = """
        hello there
        hwo aare you""";
    String updatedText = """
        hello ?there?
        [hwo] ?aare? you""";

    assertEquals(updatedText, documentSpellCheck.processText(text));
  }
}
