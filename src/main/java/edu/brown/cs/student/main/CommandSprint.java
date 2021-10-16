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

  @Override
  public void runCommand(String[] relevantData)
      throws Exception {

    if (relevantData[0].equals("recsys_load")) {
      Database responsesDB = new Database(
          "integration.sqlite3");

      Interests interestsTable = new Interests();
      Class<Interests> interestsClass = (Class<Interests>) interestsTable.getClass();
      List<Interests> allInterestsList = responsesDB.selectAll(interestsClass);

      for (Interests i : allInterestsList) {
        int interestID = Integer.parseInt(i.getId());
        String interest = i.getInterest();

        if (sprintData.allData.containsKey(interestID)) {
          if (sprintData.allData.get(interestID)[0] != null) {
            StudentQual oneStudentQual = (StudentQual) sprintData.allData.get(interestID)[0];
            oneStudentQual.addInterests(interest);
          } else {
            sprintData.allData.get(interestID)[0] = new StudentQual(interestID);
            StudentQual oneStudentQual = (StudentQual) sprintData.allData.get(interestID)[0];
            oneStudentQual.addInterests(interest);
          }
        } else {
          sprintData.allData.put(interestID, new StudentAttributes[2]);
          sprintData.allData.get(interestID)[0] = new StudentQual(interestID);
          StudentQual oneStudentQual = (StudentQual) sprintData.allData.get(interestID)[0];
          oneStudentQual.addInterests(interest);
        }
      }

      Negative negativeTable = new Negative();
      Class<Negative> negativeClass = (Class<Negative>) negativeTable.getClass();
      List<Negative> allNegativeList = responsesDB.selectAll(negativeClass);

      for (Negative n : allNegativeList) {
        int negativeID = Integer.parseInt(n.getId());
        String negativeTrait = n.getTrait();

        if (sprintData.allData.containsKey(negativeID)) {
          if (sprintData.allData.get(negativeID)[0] != null) {
            StudentQual oneStudentQual = (StudentQual) sprintData.allData.get(negativeID)[0];
            oneStudentQual.addTraits(negativeTrait);
          } else {
            sprintData.allData.get(negativeID)[0] = new StudentQual(negativeID);
            StudentQual oneStudentQual = (StudentQual) sprintData.allData.get(negativeID)[0];
            oneStudentQual.addTraits(negativeTrait);
          }
        } else {
          sprintData.allData.put(negativeID, new StudentAttributes[2]);
          sprintData.allData.get(negativeID)[0] = new StudentQual(negativeID);
          StudentQual oneStudentQual = (StudentQual) sprintData.allData.get(negativeID)[0];
          oneStudentQual.addTraits(negativeTrait);
        }
      }


      Positive positiveTable = new Positive();
      Class<Positive> positiveClass = (Class<Positive>) positiveTable.getClass();
      List<Positive> allPositiveList = responsesDB.selectAll(positiveClass);

      for (Positive p : allPositiveList) {
        int positiveID = Integer.parseInt(p.getId());
        String positiveTrait = p.getTrait();

        if (sprintData.allData.containsKey(positiveID)) {
          if (sprintData.allData.get(positiveID)[0] != null) {
            StudentQual oneStudentQual = (StudentQual) sprintData.allData.get(positiveID)[0];
            oneStudentQual.addTraits(positiveTrait);
          } else {
            sprintData.allData.get(positiveID)[0] = new StudentQual(positiveID);
            StudentQual oneStudentQual = (StudentQual) sprintData.allData.get(positiveID)[0];
            oneStudentQual.addTraits(positiveTrait);
          }
        } else {
          sprintData.allData.put(positiveID, new StudentAttributes[2]);
          sprintData.allData.get(positiveID)[0] = new StudentQual(positiveID);
          StudentQual oneStudentQual = (StudentQual) sprintData.allData.get(positiveID)[0];
          oneStudentQual.addTraits(positiveTrait);
        }
      }


      Skills skillsTable = new Skills();
      Class<Skills> skillsClass = (Class<Skills>) skillsTable.getClass();
      List<Skills> allSkillsList = responsesDB.selectAll(skillsClass);

      for (Skills s : allSkillsList) {
        int id = Integer.parseInt(s.getId());
        String name = s.getName();
        double commenting = Double.parseDouble(s.getCommenting());
        double testing = Double.parseDouble(s.getTesting());
        double OOP = Double.parseDouble(s.getOOP());
        double algorithms = Double.parseDouble(s.getAlgorithms());
        double teamwork = Double.parseDouble(s.getTeamwork());
        double frontend = Double.parseDouble(s.getFrontend());
        //placeholder
        double yearsOfExperience = 0.0;

        if (sprintData.allData.containsKey(id)) {
          if (sprintData.allData.get(id)[1] != null) {
            StudentQuant oneStudentQuant = (StudentQuant) sprintData.allData.get(id)[1];
            oneStudentQuant.setName(name);
            oneStudentQuant.setCommenting(commenting);
            oneStudentQuant.setTesting(testing);
            oneStudentQuant.setOOP(OOP);
            oneStudentQuant.setAlgorithms(algorithms);
            oneStudentQuant.setTeamwork(teamwork);
            oneStudentQuant.setFrontend(frontend);
          } else {
            sprintData.allData.get(id)[1] =
                new StudentQuant(id, name, commenting, testing, OOP, algorithms,
                    teamwork, frontend, yearsOfExperience);
          }
        } else {
          sprintData.allData.put(id, new StudentAttributes[2]);
          sprintData.allData.get(id)[1] =
              new StudentQuant(id, name, commenting, testing, OOP, algorithms,
                  teamwork, frontend, yearsOfExperience);
        }
      }

      ApiClient client = new ApiClient();
      String data = client.makeRequest(ClientRequestGenerator.getSecuredPostRequest());

      data = data.substring(1, data.length() - 1);
      data = data.concat(", ");
      String[] allQualData = data.split("}, ");

      for (String oneStudentQual : allQualData) {
        List<String> oneStudentQualClean = new LinkedList<String>();

        Pattern p = Pattern.compile("\"(.*?)\"");
        Matcher m = p.matcher(oneStudentQual);
        while (m.find()) {
          oneStudentQualClean.add(m.group().substring(1, m.group().length() - 1));
        }

        Integer id = Integer.parseInt(oneStudentQualClean.get(1));
        String name = oneStudentQualClean.get(3);
        String meeting = oneStudentQualClean.get(5);
        String grade = oneStudentQualClean.get(7);
        Double yearsOfExperience = Double.parseDouble(oneStudentQualClean.get(9));
        String horoscope = oneStudentQualClean.get(11);
        String meetingTimes = oneStudentQualClean.get(13);
        String preferredLanguage = oneStudentQualClean.get(15);
        String marginalziedGroups = oneStudentQualClean.get(17);
        String preferGroup = oneStudentQualClean.get(19);


        if (sprintData.allData.containsKey(id)) {
          if (sprintData.allData.get(id)[0] != null) {
            StudentQual oneStudQualitative = (StudentQual) sprintData.allData.get(id)[0];
            oneStudQualitative.setName(name);
            oneStudQualitative.addMeeting(meeting);
            oneStudQualitative.addGrade(grade);
            oneStudQualitative.addHoroscope(horoscope);
            if (meetingTimes.contains(";")) {
              String[] meetingTimesSplit = meetingTimes.split("; ");
              for (String time : meetingTimesSplit) {
                oneStudQualitative.addMeetingTimes(time);
              }
            } else {
              oneStudQualitative.addMeetingTimes(meetingTimes);
            }
            oneStudQualitative.addPreferredLanguage(preferredLanguage);
            if (marginalziedGroups.contains(";")) {
              String[] marginalizedGroupsSplit = marginalziedGroups.split("; ");
              for (String group : marginalizedGroupsSplit) {
                oneStudQualitative.addMarginalizedGroup(group);
              }
            } else {
              oneStudQualitative.addMarginalizedGroup(marginalziedGroups);
            }
            oneStudQualitative.addPreferredGroup(preferGroup);
          } else {
            sprintData.allData.get(id)[0] = new StudentQual(id);
            StudentQual oneStudQualitative = (StudentQual) sprintData.allData.get(id)[0];
            oneStudQualitative.setName(name);
            oneStudQualitative.addMeeting(meeting);
            oneStudQualitative.addGrade(grade);
            oneStudQualitative.addHoroscope(horoscope);
            if (meetingTimes.contains(";")) {
              String[] meetingTimesSplit = meetingTimes.split("; ");
              for (String time : meetingTimesSplit) {
                oneStudQualitative.addMeetingTimes(time);
              }
            } else {
              oneStudQualitative.addMeetingTimes(meetingTimes);
            }
            oneStudQualitative.addPreferredLanguage(preferredLanguage);
            if (marginalziedGroups.contains(";")) {
              String[] marginalizedGroupsSplit = marginalziedGroups.split("; ");
              for (String group : marginalizedGroupsSplit) {
                oneStudQualitative.addMarginalizedGroup(group);
              }
            } else {
              oneStudQualitative.addMarginalizedGroup(marginalziedGroups);
            }
            oneStudQualitative.addPreferredGroup(preferGroup);
          }
          if (sprintData.allData.get(id)[1] != null) {
            StudentQuant oneStudQuantitative = (StudentQuant) sprintData.allData.get(id)[1];
            oneStudQuantitative.setYearsofExperience(yearsOfExperience);
          } else {
            sprintData.allData.get(id)[1] = new StudentQuant(id);
            StudentQuant oneStudQuantitative = (StudentQuant) sprintData.allData.get(id)[1];
            oneStudQuantitative.setYearsofExperience(yearsOfExperience);
          }
        } else {
          sprintData.allData.put(id, new StudentAttributes[2]);
          sprintData.allData.get(id)[0] = new StudentQual(id);
          StudentQual oneStudQualitative = (StudentQual) sprintData.allData.get(id)[0];
          oneStudQualitative.setName(name);
          oneStudQualitative.addMeeting(meeting);
          oneStudQualitative.addGrade(grade);
          oneStudQualitative.addHoroscope(horoscope);
          if (meetingTimes.contains(";")) {
            String[] meetingTimesSplit = meetingTimes.split("; ");
            for (String time : meetingTimesSplit) {
              oneStudQualitative.addMeetingTimes(time);
            }
          } else {
            oneStudQualitative.addMeetingTimes(meetingTimes);
          }
          oneStudQualitative.addPreferredLanguage(preferredLanguage);
          if (marginalziedGroups.contains(";")) {
            String[] marginalizedGroupsSplit = marginalziedGroups.split("; ");
            for (String group : marginalizedGroupsSplit) {
              oneStudQualitative.addMarginalizedGroup(group);
            }
          } else {
            oneStudQualitative.addMarginalizedGroup(marginalziedGroups);
          }
          oneStudQualitative.addPreferredGroup(preferGroup);

          sprintData.allData.get(id)[1] = new StudentQuant(id);
          StudentQuant oneStudQuantitative = (StudentQuant) sprintData.allData.get(id)[1];
          oneStudQuantitative.setYearsofExperience(yearsOfExperience);
        }
      }

      for (Integer key : sprintData.allData.keySet()) {
        sprintData.studentQual.put(key.toString(), (StudentQual) sprintData.allData.get(key)[0]);
        sprintData.studentQuant.add((StudentQuant) sprintData.allData.get(key)[1]);
      }

      int studentCount = sprintData.allData.size();
      System.out.println("Loaded Recommender with " + studentCount + " students.");

    } else if (relevantData[0].equals("recsys_rec")) {


      int numberOfRecs = Integer.parseInt(relevantData[1]);
      int allDataSize = sprintData.allData.size() - 1;
      int targetID = Integer.parseInt(relevantData[2]);
      StudentQuant targetQuantAttr = (StudentQuant) sprintData.allData.get(targetID)[1];
      StudentQual targetQualAttr = (StudentQual) sprintData.allData.get(targetID)[0];

      HashMap<String, Integer> kdTreeResults = new HashMap<String, Integer>();
      HashMap<String, Integer> allResults = new HashMap<String, Integer>();

      KdTree<Integer> studentTree = new KdTree<Integer>(7, sprintData.studentQuant);
      Node<Coordinate<Integer>> studentRoot = studentTree.getRoot();
      KdTreeSearch<Integer> treeSearch = new KdTreeSearch<Integer>();
      List<Coordinate<Integer>> quantComps = treeSearch.getNearestNeighborsResult(
          allDataSize, targetQuantAttr, studentRoot, true);

      for (int i = 0; i < allDataSize; i++) {
        kdTreeResults.put(quantComps.get(i).getId().toString(), i);
      }

      double r = 0.01;
      int k = (int) Math.round(ceil(-1 * (Math.log(r) / Math.log(2))));
      double m = ceil((k * allDataSize) / Math.log(2));
      BloomFilterRecommender<StudentQual> studentBloom = new BloomFilterRecommender<StudentQual>(
          sprintData.studentQual, r);
      studentBloom.setBloomFilterComparator(
          new AndSimilarityComparator(new BloomFilter<String>(m, allDataSize, k)));
      List<StudentQual> qualComps =
          studentBloom.getTopKRecommendations(targetQualAttr, allDataSize);

      for (int i = 0; i < allDataSize; i++) {
        String relevantID = qualComps.get(i).getId();
        allResults.put(relevantID, i + kdTreeResults.get(relevantID));
      }


      Set<Map.Entry<String, Integer>> entries = allResults.entrySet();
      List<Map.Entry<String, Integer>> sortedEntries = new ArrayList<>(entries);
      Collections.sort(sortedEntries, new Comparator<Map.Entry<String, Integer>>() {
            public int compare(Map.Entry<String, Integer> o1,
                               Map.Entry<String, Integer> o2) {
              return o1.getValue().compareTo(o2.getValue());
            }
          }
      );

      for (int i = 0; i < numberOfRecs; i++) {
        int finalID = Integer.parseInt(sortedEntries.get(i).getKey());
        StudentQuant finalStudent = (StudentQuant) sprintData.allData.get(finalID)[1];
        System.out.println(finalStudent.getId());
      }
    }
  }
}