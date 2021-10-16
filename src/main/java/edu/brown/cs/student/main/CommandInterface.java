package edu.brown.cs.student.main;

import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;

public interface CommandInterface {
  void runCommand(String[] relevantData)
      throws Exception;
}