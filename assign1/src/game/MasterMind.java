package game;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;


import static game.Match.EXACT;
import static game.Status;


public interface MasterMind {
  static final int LENGTH = 6;

  static final int MAX_RETRIES = 20;

  static List<Match> guess(List<Colors> selectedColors, List<Colors> userProvidedColors) {
    checkIfBothListsAreValid(selectedColors, userProvidedColors);

    return IntStream.range(0, LENGTH)
        .mapToObj(index -> matchForPosition(selectedColors, userProvidedColors, index))
        .sorted()
        .toList();

  }
  static Result playGame(List<Colors> selectedColors, List<Colors> userProvidedColors, int retries) {

    return Optional.of(retries)
        .filter(r -> r <= MAX_RETRIES)
        .map(r-> checkWhetherGameIsWon(selectedColors,userProvidedColors))
        .orElseGet(() -> new Result(LOSE));
  }

  static List<Colors> generateRandomizedColors(){
    ArrayList<Colors> colors= new ArrayList<>(List.of(Colors.values()));

    return IntStream.range(0, LENGTH)
        .mapToObj(i -> {
          int position = (int) (Math.random() * (colors.size()-2));
          return colors.remove(position);
        })
        .collect(Collectors.toList());
  }


  private static Result checkWhetherGameIsWon(List<Colors> selectedColors, List<Colors> userProvidedColors){
    List<Match> matchings = guess(selectedColors, userProvidedColors);
    Result result= new Result(matchings.stream().filter(element->element!= EXACT).count()>=1 ? IN_PROGRESS : WIN,matchings);
    return result;
  }

  private static void checkIfBothListsAreValid(List<Colors> selectedColors, List<Colors> userProvidedColors){
    checkIfListIsOfDesiredSize(userProvidedColors, "User Picked List");
    checkIfListIsOfDesiredSize(selectedColors, "Program Generated List");
    checkForDuplicateElements(userProvidedColors, "User Picked List");
    checkForDuplicateElements(userProvidedColors, "Program Generated List");
  }

  private static void checkIfListIsOfDesiredSize(List<Colors> colors, String ListName) {

    Optional.of(colors)
      .filter(c -> c.size() != LENGTH)
      .ifPresent(c -> {
        throw new IllegalArgumentException("Colors in the" + ListName + "List are not of size " + LENGTH);
      });
  }

  private static void checkForDuplicateElements(List<Colors> colors, String ListName) {

    Optional.of(colors.stream().distinct().count())
        .filter(distinctList-> distinctList!= colors.size())
        .ifPresent(c -> {
           throw new IllegalArgumentException("Colors in the" + ListName + "List are duplicates");
        });
  }

  private static Match matchForPosition(List<Colors> selectedColors, List<Colors> userProvidedColors, int index) {
    if (selectedColors.get(index) == userProvidedColors.get(index)) {
      return EXACT;
    }

    if (userProvidedColors.contains(selectedColors.get(index))) {
      return Match.PARTIAL;
    }

    return Match.WRONG;
  }
  
  
}

