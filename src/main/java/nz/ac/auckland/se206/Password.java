package nz.ac.auckland.se206;

import java.util.Random;

/**
 * A bunch of possible passwords for one of the mini games. Each password has an associated string
 * with it.
 */
public class Password {
  private static String[] passwords =
      new String[] {
        "banana", "phone", "horse", "zebra", "chicken", "cat", "farm", "clock", "bus", "bee",
        "candle", "moon", "sun", "pillow"
      };

  /**
   * Gets a random password from all the possible values.
   *
   * @return a random password.
   */
  public static String getRandomPassword() {
    return passwords[new Random().nextInt(passwords.length)];
  }
}
