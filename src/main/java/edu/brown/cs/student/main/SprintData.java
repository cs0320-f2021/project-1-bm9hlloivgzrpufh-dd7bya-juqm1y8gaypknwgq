package edu.brown.cs.student.main;

import edu.brown.cs.student.coordinates.Coordinate;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

public class SprintData {
  public HashMap<Integer, StudentAttributes[]> allData =
      new HashMap<Integer, StudentAttributes[]>();
  public List<Coordinate<Integer>> studentQuant = new LinkedList<Coordinate<Integer>>();
  public HashMap<String, StudentQual> studentQual = new HashMap<String, StudentQual>();

}
