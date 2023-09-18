package nz.ac.auckland.se206;

public enum Password {
  BANANA("BANANA"),
  MAGNESIUM("MAGNESIUM"),
  FLASK("FLASK");

  private String word;

  private Password(String word) {
    this.word = word;
  }

  public String toString() {
    return word;
  }

  public static Password getRandomPassword() {
    return Password.values()[(int) Math.random() * Password.values().length];
  }
}
