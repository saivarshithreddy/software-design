package processor;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public class AgileCSSpellCheckerTest {

  AgileCSSpellChecker spellingChecker;

  @BeforeEach
  void init() {
    spellingChecker = spy(new AgileCSSpellChecker());
  }

  @Test
  public void testForAPIReponseForWordWithLengthGreaterThanZero() throws IOException {
    assertTrue(spellingChecker.getResponse("test").length() > 0);
  }

  @Test
  public void testForParseTextForTrueResponse() {
    assertTrue(spellingChecker.parseString("true"));
  }

  @Test
  public void testForParseTextForFalseResponse() {
    assertFalse(spellingChecker.parseString("false"));
  }

  @Test
  public void testForApiWithCorrectWord() throws IOException {
    doReturn("true").when(spellingChecker).getResponse("there");

    assertTrue(spellingChecker.isSpellingCorrect("there"));
    verify(spellingChecker).getResponse("there");
    verify(spellingChecker).parseString("true");
  }

  @Test
  public void testForApiThrowingException() throws IOException {
    doThrow(new IOException()).when(spellingChecker).getResponse(anyString());

    assertThrows(RuntimeException.class, () -> spellingChecker.isSpellingCorrect("test"));
  }
}
