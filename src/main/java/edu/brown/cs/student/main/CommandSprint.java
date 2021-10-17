package edu.brown.cs.student.main;

import edu.brown.cs.student.bloomfilter.AndSimilarityComparator;
import edu.brown.cs.student.bloomfilter.BloomFilter;
import edu.brown.cs.student.bloomfilter.BloomFilterRecommender;
import edu.brown.cs.student.client.ApiClient;
import edu.brown.cs.student.client.ClientRequestGenerator;
import edu.brown.cs.student.coordinates.Coordinate;
import edu.brown.cs.student.coordinates.KdTree;
import edu.brown.cs.student.node.Node;
import edu.brown.cs.student.orm.Database;
import edu.brown.cs.student.searchAlgorithms.KdTreeSearch;

import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static java.lang.Math.ceil;

public class CommandSprint implements CommandInterface {
  public final String[] keywords = {"recsys_load", "recsys_rec"};

  public final SprintData sprintData = new SprintData();

  public final IdGenerator recGenerator = new IdGenerator();

  @Override
  public void runCommand(String[] relevantData)
      throws Exception {

    if (relevantData[0].equals("recsys_load")) {
      Database responsesDB = new Database(
          "integration.sqlite3");
      sprintData.allData = recGenerator.loadDb();
      System.out.println("Loaded Recommender with " + sprintData.allData.size() + " students.");

    } else if (relevantData[0].equals("recsys_rec")) {
      int numberOfRecs = Integer.parseInt(relevantData[1]);
      int targetID = Integer.parseInt(relevantData[2]);
      List<Integer> kStudentLs = recGenerator.kRecStudents(numberOfRecs, targetID, sprintData.allData);
    }
    else if (relevantData[0].equals("recsys_gen_groups")) {
      recGenerator.kStudentsTeam(relevantData[1], sprintData.allData);
      System.out.println("k sized teams generated in data/teams.txt");
    }
  }
}