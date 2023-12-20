package processor;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import java.io.IOException;

public class AgileCSSpellChecker implements SpellingChecker {

  @Override
  public boolean isSpellingCorrect(String word) {
    try {
      return parseString(getResponse(word));
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  public boolean parseString(String response) {
    return response.equals("true");
  }

  public String getResponse(String word) throws IOException {
    String URL = String.format("http://agilec.cs.uh.edu/spell?check=%s", word);
    CloseableHttpClient httpClient = HttpClients.createDefault();
    HttpGet httpGet = new HttpGet(URL);
    HttpResponse response = httpClient.execute(httpGet);

    return EntityUtils.toString(response.getEntity());
  }

}
