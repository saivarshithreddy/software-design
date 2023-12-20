package game;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static game.MasterMind.*;
import static game.Match.*;
import static game.Status.*;
import static org.junit.jupiter.api.Assertions.*;

class MasterMindTest {

  @Test
  public void canary() {
    assertTrue(true);
  }

  @Test
  void testGuessWithAllColorsMatchInPosition() {
    List<Colors> selectedColors = List.of(Colors.GREEN, Colors.BLUE, Colors.ORANGE, Colors.MOCHA, Colors.YELLOW, Colors.CYAN);

    assertEquals(List.of(EXACT, EXACT, EXACT, EXACT, EXACT, EXACT), guess(selectedColors, selectedColors));
  }

  @Test
  void testGuessWithAllColorsMisMatch() {
    List<Colors> selectedColors = List.of(Colors.GREEN, Colors.BLUE, Colors.ORANGE, Colors.MOCHA, Colors.YELLOW, Colors.CYAN);
    List<Colors> userProvidedColors = List.of(Colors.RED, Colors.WHITE, Colors.PURPLE, Colors.VIOLET, Colors.BLACK, Colors.SILVER);

    assertEquals(List.of(WRONG, WRONG, WRONG, WRONG, WRONG, WRONG), guess(selectedColors, userProvidedColors));
  }

  @Test
  void testGuessWithAllColorsMatchOutOfPosition() {
    List<Colors> selectedColors = List.of(Colors.GREEN, Colors.BLUE, Colors.ORANGE, Colors.MOCHA, Colors.YELLOW, Colors.CYAN);
    List<Colors> userProvidedColors = List.of(Colors.CYAN, Colors.GREEN, Colors.BLUE, Colors.ORANGE, Colors.MOCHA, Colors.YELLOW);

    assertEquals(List.of(PARTIAL, PARTIAL, PARTIAL, PARTIAL, PARTIAL, PARTIAL), guess(selectedColors, userProvidedColors));
  }

  @Test
  void testGuessWithFirstFourColorsMatchInPosition() {
    List<Colors> selectedColors = List.of(Colors.GREEN, Colors.BLUE, Colors.ORANGE, Colors.MOCHA, Colors.YELLOW, Colors.CYAN);
    List<Colors> userProvidedColors = List.of(Colors.GREEN, Colors.BLUE, Colors.ORANGE, Colors.MOCHA, Colors.CYAN, Colors.YELLOW);

    assertEquals(List.of(EXACT, EXACT, EXACT, EXACT, PARTIAL, PARTIAL), guess(selectedColors, userProvidedColors));
  }
  
  @Test
  void testGuessWithLastFourColorsMatchInPosition() {
    List<Colors> selectedColors = List.of(Colors.GREEN, Colors.BLUE, Colors.ORANGE, Colors.MOCHA, Colors.YELLOW, Colors.CYAN);
    List<Colors> userProvidedColors = List.of(Colors.BLUE, Colors.GREEN, Colors.ORANGE, Colors.MOCHA, Colors.YELLOW, Colors.CYAN);

    assertEquals(List.of(EXACT, EXACT, EXACT, EXACT, PARTIAL, PARTIAL), guess(selectedColors, userProvidedColors));
  }

  @Test
  void testGuessWithThreeExactAndThreePartialMatch() {
    List<Colors> selectedColors = List.of(Colors.GREEN, Colors.BLUE, Colors.ORANGE, Colors.MOCHA, Colors.YELLOW, Colors.CYAN);
    List<Colors> userProvidedColors = List.of(Colors.GREEN, Colors.CYAN, Colors.ORANGE, Colors.BLUE, Colors.YELLOW, Colors.MOCHA);

    assertEquals(List.of(EXACT, EXACT, EXACT, PARTIAL, PARTIAL, PARTIAL), guess(selectedColors, userProvidedColors));
  }

  @Test
  void testGuessWithMixOfAllMatches() {
    List<Colors> selectedColors = List.of(Colors.GREEN, Colors.BLUE, Colors.ORANGE, Colors.MOCHA, Colors.YELLOW, Colors.CYAN);
    List<Colors> userProvidedColors = List.of(Colors.RED, Colors.BLUE, Colors.WHITE, Colors.CYAN, Colors.MOCHA, Colors.YELLOW);

    assertEquals(List.of(EXACT, PARTIAL, PARTIAL, PARTIAL, WRONG, WRONG), guess(selectedColors, userProvidedColors));
  }

  @Test
  void guessWithFirstColorOfSelectionInAllUserGuesses(){
    List<Colors> selectedColors = List.of(Colors.GREEN, Colors.BLUE, Colors.ORANGE, Colors.MOCHA, Colors.YELLOW, Colors.CYAN);
    List<Colors> userProvidedColors = List.of(Colors.GREEN, Colors.GREEN, Colors.GREEN, Colors.GREEN, Colors.GREEN, Colors.GREEN);

    assertThrows(IllegalArgumentException.class, ()-> guess(selectedColors, userProvidedColors));
  }

  @Test
  void guessWithLastColorOfSelectionInAllUserGuesses(){
    List<Colors> selectedColors = List.of(Colors.GREEN, Colors.BLUE, Colors.ORANGE, Colors.MOCHA, Colors.YELLOW, Colors.CYAN);
    List<Colors> userProvidedColors = List.of(Colors.CYAN, Colors.CYAN, Colors.CYAN, Colors.CYAN, Colors.CYAN, Colors.CYAN);

    assertThrows(IllegalArgumentException.class, ()-> guess(selectedColors, userProvidedColors));
  }

  @Test
  void guessWithFirstAsSecondAndRemainingAsFirstFromSelection(){
    List<Colors> selectedColors = List.of(Colors.GREEN, Colors.BLUE, Colors.ORANGE, Colors.MOCHA, Colors.YELLOW, Colors.CYAN);
    List<Colors> userProvidedColors = List.of(Colors.BLUE, Colors.GREEN, Colors.GREEN, Colors.GREEN, Colors.GREEN, Colors.GREEN);

    assertThrows(IllegalArgumentException.class, ()-> guess(selectedColors, userProvidedColors));
  }

  @Test
  void guessWithFirstAsWrongAndRemainingAsFirstFromSelection(){
    List<Colors> selectedColors = List.of(Colors.GREEN, Colors.BLUE, Colors.ORANGE, Colors.MOCHA, Colors.YELLOW, Colors.CYAN);
    List<Colors> userProvidedColors = List.of(Colors.WHITE, Colors.GREEN, Colors.GREEN, Colors.GREEN, Colors.GREEN, Colors.GREEN);

    assertThrows(IllegalArgumentException.class, ()-> guess(selectedColors, userProvidedColors));
  }

  @Test
  void guessWithFirstAndLastColorSameAsSelection(){
    List<Colors> selectedColors = List.of(Colors.GREEN, Colors.BLUE, Colors.ORANGE, Colors.MOCHA, Colors.YELLOW, Colors.CYAN);
    List<Colors> userProvidedColors = List.of(Colors.GREEN, Colors.WHITE, Colors.VIOLET, Colors.RED, Colors.PURPLE, Colors.CYAN);

    assertEquals(List.of(EXACT, EXACT, WRONG, WRONG, WRONG, WRONG), guess(selectedColors, userProvidedColors));
  }

  @Test
  void guessLessColorsInsteadOfSixInUserPickedList(){
    List<Colors> selectedColors = List.of(Colors.GREEN, Colors.BLUE, Colors.ORANGE, Colors.MOCHA, Colors.YELLOW, Colors.CYAN);
    List<Colors> userProvidedColors = List.of(Colors.GREEN, Colors.WHITE, Colors.VIOLET, Colors.RED, Colors.PURPLE);

    assertThrows(IllegalArgumentException.class, ()-> guess(selectedColors, userProvidedColors));
  }

  @Test
  void testLessColorsInsteadOfSixInProgramSelectedList(){
    List<Colors> selectedColors = List.of(Colors.GREEN, Colors.BLUE, Colors.ORANGE, Colors.MOCHA, Colors.YELLOW);
    List<Colors> userProvidedColors = List.of(Colors.GREEN, Colors.WHITE, Colors.VIOLET, Colors.RED, Colors.PURPLE, Colors.CYAN);

    assertThrows(IllegalArgumentException.class, ()-> guess(selectedColors, userProvidedColors));
  }

  @Test
  void testDuplicateColorsInUserPickedList(){
    List<Colors> selectedColors = List.of(Colors.GREEN, Colors.BLUE, Colors.ORANGE, Colors.MOCHA, Colors.YELLOW, Colors.CYAN);
    List<Colors> userProvidedColors = List.of(Colors.GREEN, Colors.WHITE, Colors.VIOLET, Colors.RED, Colors.PURPLE);

    assertThrows(IllegalArgumentException.class, ()-> guess(selectedColors, userProvidedColors));
  }

  @Test
  void testDuplicateColorsInProgramSelectedList(){
    List<Colors> selectedColors = List.of(Colors.GREEN, Colors.BLUE, Colors.ORANGE, Colors.MOCHA, Colors.YELLOW);
    List<Colors> userProvidedColors = List.of(Colors.GREEN, Colors.WHITE, Colors.VIOLET, Colors.RED, Colors.PURPLE, Colors.CYAN);

    assertThrows(IllegalArgumentException.class, ()-> guess(selectedColors, userProvidedColors));
  }

  @Test
  void testBothTheListsWithDIfferentSizes(){
    List<Colors> selectedColors = List.of(Colors.GREEN, Colors.BLUE, Colors.ORANGE, Colors.MOCHA, Colors.YELLOW, Colors.WHITE, Colors.SILVER);
    List<Colors> userProvidedColors = List.of(Colors.GREEN, Colors.WHITE, Colors.VIOLET, Colors.RED, Colors.PURPLE);

    assertThrows(IllegalArgumentException.class, ()-> guess(selectedColors, userProvidedColors));
  }

  @Test
  void testForFirstAttemptWithAllExactMatches(){
    List<Colors> selectedColors = List.of(Colors.GREEN, Colors.BLUE, Colors.ORANGE, Colors.MOCHA, Colors.YELLOW, Colors.CYAN);
    Result result = new Result(WIN, List.of(EXACT, EXACT, EXACT, EXACT, EXACT, EXACT));

    assertEquals(result, playGame(selectedColors, selectedColors,1));
  }

  @Test
  void testForFirstAttemptWithNoMatches(){
    List<Colors> selectedColors = List.of(Colors.GREEN, Colors.BLUE, Colors.ORANGE, Colors.MOCHA, Colors.YELLOW, Colors.CYAN);
    List<Colors> userProvidedColors = List.of(Colors.RED, Colors.WHITE, Colors.PURPLE, Colors.VIOLET, Colors.BLACK, Colors.SILVER);
    Result result = new Result(IN_PROGRESS, List.of(WRONG, WRONG, WRONG, WRONG, WRONG, WRONG));

    assertEquals(result, playGame(selectedColors, userProvidedColors,1));
  }

  @Test
  void testForFirstAttemptWithPartialExactAndNonExactMatches(){
    List<Colors> selectedColors = List.of(Colors.GREEN, Colors.BLUE, Colors.ORANGE, Colors.MOCHA, Colors.YELLOW, Colors.CYAN);
    List<Colors> userProvidedColors = List.of(Colors.GREEN, Colors.BLUE, Colors.ORANGE, Colors.VIOLET, Colors.BLACK, Colors.SILVER);
    Result result = new Result(IN_PROGRESS, List.of(EXACT, EXACT, EXACT, WRONG, WRONG, WRONG));

    assertEquals(result, playGame(selectedColors, userProvidedColors,1));
  }

  @Test
  void testForSecondAttemptWithAllExactMatches(){
    List<Colors> selectedColors = List.of(Colors.GREEN, Colors.BLUE, Colors.ORANGE, Colors.MOCHA, Colors.YELLOW, Colors.CYAN);
    Result result = new Result(WIN, List.of(EXACT, EXACT, EXACT, EXACT, EXACT, EXACT));

    assertEquals(result, playGame(selectedColors, selectedColors,2));
  }

  @Test
  void testForSecondAttemptWithNoMatches(){
    List<Colors> selectedColors = List.of(Colors.GREEN, Colors.BLUE, Colors.ORANGE, Colors.MOCHA, Colors.YELLOW, Colors.CYAN);
    List<Colors> userProvidedColors = List.of(Colors.RED, Colors.WHITE, Colors.PURPLE, Colors.VIOLET, Colors.BLACK, Colors.SILVER);
    Result result = new Result(IN_PROGRESS, List.of(WRONG, WRONG, WRONG, WRONG, WRONG, WRONG));

    assertEquals(result, playGame(selectedColors, userProvidedColors,2));
  }

  @Test
  void testForSecondAttemptWithPartialExactAndNonExactMatches(){
    List<Colors> selectedColors = List.of(Colors.GREEN, Colors.BLUE, Colors.ORANGE, Colors.MOCHA, Colors.YELLOW, Colors.CYAN);
    List<Colors> userProvidedColors = List.of(Colors.GREEN, Colors.BLUE, Colors.ORANGE, Colors.VIOLET, Colors.BLACK, Colors.SILVER);
    Result result = new Result(IN_PROGRESS, List.of(EXACT, EXACT, EXACT, WRONG, WRONG, WRONG));

    assertEquals(result, playGame(selectedColors, userProvidedColors,2));
  }

  @Test
  void testForFinalAttemptWithAllExactMatches(){
    List<Colors> selectedColors = List.of(Colors.GREEN, Colors.BLUE, Colors.ORANGE, Colors.MOCHA, Colors.YELLOW, Colors.CYAN);
    Result result = new Result(WIN, List.of(EXACT, EXACT, EXACT, EXACT, EXACT, EXACT));

    assertEquals(result, playGame(selectedColors, selectedColors,20));
  }

  @Test
  void testForFinalAttemptWithNoMatches(){
    List<Colors> selectedColors = List.of(Colors.GREEN, Colors.BLUE, Colors.ORANGE, Colors.MOCHA, Colors.YELLOW, Colors.CYAN);
    List<Colors> userProvidedColors = List.of(Colors.RED, Colors.WHITE, Colors.PURPLE, Colors.VIOLET, Colors.BLACK, Colors.SILVER);
    Result result = new Result(IN_PROGRESS, List.of(WRONG, WRONG, WRONG, WRONG, WRONG, WRONG));

    assertEquals(result, playGame(selectedColors, userProvidedColors,20));
  }

  @Test
  void testForFinalAttemptWithPartialExactAndNonExactMatches(){
    List<Colors> selectedColors = List.of(Colors.GREEN, Colors.BLUE, Colors.ORANGE, Colors.MOCHA, Colors.YELLOW, Colors.CYAN);
    List<Colors> userProvidedColors = List.of(Colors.GREEN, Colors.BLUE, Colors.ORANGE, Colors.VIOLET, Colors.BLACK, Colors.SILVER);
    Result result = new Result(IN_PROGRESS, List.of(EXACT, EXACT, EXACT, WRONG, WRONG, WRONG));

    assertEquals(result, playGame(selectedColors, userProvidedColors,20));
  }

  @Test
  void testWithCrossingAttemptWithAllExactMatches(){
    List<Colors> selectedColors = List.of(Colors.GREEN, Colors.BLUE, Colors.ORANGE, Colors.MOCHA, Colors.YELLOW, Colors.CYAN);
    Result result = new Result(LOSE);

    assertEquals(result, playGame(selectedColors, selectedColors,21));
  }

  @Test
  void testWithCrossingAttemptWithNoMatches(){
    List<Colors> selectedColors = List.of(Colors.GREEN, Colors.BLUE, Colors.ORANGE, Colors.MOCHA, Colors.YELLOW, Colors.CYAN);
    List<Colors> userProvidedColors = List.of(Colors.RED, Colors.WHITE, Colors.PURPLE, Colors.VIOLET, Colors.BLACK, Colors.SILVER);
    Result result = new Result(LOSE);

    assertEquals(result, playGame(selectedColors, userProvidedColors,21));
  }

  @Test
  void testWithCrossingAttemptWithPartialExactAndNonExactMatches(){
    List<Colors> selectedColors = List.of(Colors.GREEN, Colors.BLUE, Colors.ORANGE, Colors.MOCHA, Colors.YELLOW, Colors.CYAN);
    List<Colors> userProvidedColors = List.of(Colors.GREEN, Colors.BLUE, Colors.ORANGE, Colors.VIOLET, Colors.BLACK, Colors.SILVER);
    Result result = new Result(LOSE);

    assertEquals(result, playGame(selectedColors, userProvidedColors,21));
  }

  @Test
  void testWhetherTheColorsAreRandomized(){
    List<Colors> sourceColorsList = Arrays.asList(Colors.values()).subList(0, 6);

    assertNotEquals(sourceColorsList, generateRandomizedColors());
  }

  @Test
  void testWhetherTheColorsAreGenerated(){

    assertNotNull(generateRandomizedColors());
  }

  @Test
  void testWhetherTheColorsAreNotSameInEveryGeneration(){
    
    assertNotEquals(generateRandomizedColors(), generateRandomizedColors());
  }
}
