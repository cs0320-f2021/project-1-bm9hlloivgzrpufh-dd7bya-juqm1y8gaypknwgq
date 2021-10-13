package edu.brown.cs.student.main;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.lang.reflect.InvocationTargetException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class REPL {
  private final REPLInterface REPLObj;

  REPL(REPLInterface REPLObj) {
    this.REPLObj = REPLObj;
  }

  public void start(String startMessage) throws IOException {
    System.out.println(startMessage);
    try(BufferedReader br = new BufferedReader(new InputStreamReader(System.in))) {
    String input;

    while ((input = br.readLine()) != null) {
      try {
        input = input.trim();
        String[] arguments = input.split(" ");

        this.REPLObj.parse(arguments);

      } catch (SQLException throwables) {
        throwables.printStackTrace();
      } catch (IOException e) {
        e.printStackTrace();
      } catch (ClassNotFoundException e) {
        e.printStackTrace();
      } catch (InvocationTargetException e) {
        e.printStackTrace();
      } catch (IllegalAccessException e) {
        e.printStackTrace();
      } catch (NoSuchMethodException e) {
        e.printStackTrace();
      } catch (InstantiationException e) {
        e.printStackTrace();
      } catch (Exception e) {
        e.printStackTrace();
        System.out.println("ERROR: Invalid input for REPL"); }
    }

    }
  }
}
