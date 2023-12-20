package game;

public enum Colors {
  GREEN("#90EE90"),
  RED("#EE4B2B"),
  BLUE("#0096FF"),
  WHITE("#FFFFFF"),
  ORANGE("#FFBF00"),
  PURPLE("#C3B1E1"),
  CYAN("#00FFFF"),
  YELLOW("#FFEA00"),
  VIOLET("#CF9FFF"),
  MOCHA("#967969"),
  BLACK("#000000"),
  SILVER("#C0C0C0");

  private final String hexCode;
  Colors(String hexCode) {
    this.hexCode= hexCode;
  }

  public String getHexCode()
  {
    return this.hexCode;
  }

}
