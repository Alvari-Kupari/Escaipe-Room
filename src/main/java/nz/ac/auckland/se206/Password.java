package nz.ac.auckland.se206;

import java.util.Random;

public enum Password {
  BANANA("BANANA"),
  PHONE("PHONE"),
  HORSE("HORSE"),
  ZEBRA("ZEBRA"),
  CHICKEN("CHICKEN"),
  CAT("CAT"),
  FARM("FARM"),
  CLOCK("CLOCK"),
  BUS("BUS"),
  BEE("BEE"),
  CANDLE("CANDLE"),
  MOON("MOON"),
  SUN("SUN"),
  PILLOW("PILLOW");

  private String word;

  private Password(String word) {
    this.word = word;
  }

  /** Returns a string view of the password. */
  public String toString() {
    return word;
  }

  /**
   * Gets a random password.
   *
   * @return a random password.
   */
  public static Password getRandomPassword() {
    return Password.values()[new Random().nextInt(Password.values().length)];
  }
}
