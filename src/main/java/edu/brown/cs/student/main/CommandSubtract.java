package edu.brown.cs.student.main;

public class CommandSubtract implements CommandInterface {
  public final String[] keywords = {"subtract"};

  @Override
  public void runCommand(String[] relevantData) {
      MathBot bot = new MathBot();
      System.out.println(bot.subtract(Double.parseDouble(relevantData[1]),
          Double.parseDouble(relevantData[2])));
  }
}
