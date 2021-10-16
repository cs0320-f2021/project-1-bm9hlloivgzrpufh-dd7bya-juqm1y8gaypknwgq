package edu.brown.cs.student.main;

public class CommandAdd implements CommandInterface {
  public final String[] keywords = {"add"};

  @Override
  public void runCommand(String[] relevantData) {
    MathBot bot = new MathBot();
    System.out.println(bot.add(Double.parseDouble(relevantData[1]),
        Double.parseDouble(relevantData[2])));
  }
}
