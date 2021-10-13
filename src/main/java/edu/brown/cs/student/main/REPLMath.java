package edu.brown.cs.student.main;

import java.util.LinkedList;
import java.util.List;

public class REPLMath implements REPLInterface{
  private final List<String> commandsList = new LinkedList<String>();

  REPLMath() {
    this.commandsList.add("add");
    this.commandsList.add("subtract");
  }

  @Override
  public void parse(String[] arguments) throws Exception {
    if (commandsList.contains(arguments[0])) {
      if (arguments[0].equals("add")) {
        MathBot bot = new MathBot();
        System.out.println(bot.add(Double.parseDouble(arguments[1]),
            Double.parseDouble(arguments[2])));
        // subtract
      } else if (arguments[0].equals("subtract")) {
        MathBot bot = new MathBot();
        System.out.println(bot.subtract(Double.parseDouble(arguments[1]),
            Double.parseDouble(arguments[2])));
        // stars
      } else {
        throw new Exception("No such command");
      }
    }
  }
}
