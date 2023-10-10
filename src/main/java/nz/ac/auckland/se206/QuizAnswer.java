package nz.ac.auckland.se206;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.Random;

public class QuizAnswer {
  private static String[] possibleAnswers =
      new String[] {
        "Hydrochloric acid",
        "Bunsen Burner",
        "Calcium",
        "Magnesium",
        "Flask",
        "Sulfur",
        "Covalent Bond",
        "Ionic bond",
        "Hydrogen",
        "Pressure",
        "Equilibrium",
        "Electrolysis"
      };

  public static String[] getRandomAnswers() {
    String[] answers = new String[7];

    for (int i = 0; i < 7; i++) {
      answers[i] = possibleAnswers[new Random().nextInt(possibleAnswers.length)];
    }

    // Get the day of the week
    DayOfWeek dayOfWeek = LocalDate.now().getDayOfWeek();

    // Set the answer according to the day of the week
    switch (dayOfWeek) {
      case MONDAY:
        GameState.quizAnswer = answers[0];
        break;

      case TUESDAY:
        GameState.quizAnswer = answers[1];
        break;

      case WEDNESDAY:
        GameState.quizAnswer = answers[2];
        break;

      case THURSDAY:
        GameState.quizAnswer = answers[3];
        break;

      case FRIDAY:
        GameState.quizAnswer = answers[4];
        break;

      case SATURDAY:
        GameState.quizAnswer = answers[5];
        break;

      case SUNDAY:
        GameState.quizAnswer = answers[6];
        break;

      default:
        break;
    }
    // Print out the day of the week in the console
    System.out.println(dayOfWeek.toString());
    return answers;
  }
}
