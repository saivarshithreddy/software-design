package evaluator;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;

public interface ClassesFetcher {

  static List<String> getClassesInPackage(String packageName) throws IOException {
    try (InputStream stream = ClassLoader.getSystemClassLoader()
        .getResourceAsStream(packageName.replaceAll("[.]", "/"));
         BufferedReader reader = new BufferedReader(new InputStreamReader(stream))) {

      return reader.lines()
          .filter(line -> line.endsWith(".class"))
          .filter(line -> !line.endsWith("Test.class"))
          .map(line -> line.substring(0, line.lastIndexOf('.')))
          .toList();
    }
  }
}
