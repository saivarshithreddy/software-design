package processor;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Scanner;

public class SpellCheckerUI {

  public static void main(String[] args) {
    DocumentSpellCheck spellCheck = new DocumentSpellCheck();
    spellCheck.setSpellingChecker(new AgileCSSpellChecker());
    System.out.println("Please Enter a filename along with its path :");
    String fileName = new Scanner(System.in).nextLine();

    try {
      String text = new String(Files.readAllBytes(Paths.get(fileName)));
      System.out.println(spellCheck.processText(text));
    } catch (IOException e) {
      System.out.println("Please Provide a valid file path");
    }
  }
}
