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

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map.Entry;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static java.lang.Math.ceil;

public class REPLProj1Sprint implements REPLInterface {
  private final List<String> commandsList = new LinkedList<String>();

  //temp
  private HashMap<Integer, StudentAttributes[]> allData;

  REPLProj1Sprint() {
    this.commandsList.add("recsys_load");
    this.commandsList.add("recsys_rec");
    this.commandsList.add("recsys_gen_groups");
  }


  @Override
  public void parse(String[] arguments) throws Exception {
    IdGenerator recGenerator = new IdGenerator();
    if (commandsList.contains(arguments[0])) {
      if (arguments[0].equals("recsys_load")) {
        this.allData = recGenerator.loadDb();
        System.out.println("Loaded Recommender with " + allData.size() + " students.");
      } else if (arguments[0].equals("recsys_rec")) {
        int numberOfRecs = Integer.parseInt(arguments[1]);
        int targetID = Integer.parseInt(arguments[2]);
        List<Integer> kStudentLs = recGenerator.kRecStudents(numberOfRecs, targetID, allData);
      } else if (arguments[0].equals("recsys_gen_groups")) {
        recGenerator.kStudentsTeam(arguments[1], allData);
        System.out.println("k sized teams generated in data/teams.txt");
      }
    }
      else {
        throw new Exception("No such command");
      }
    }

  }

