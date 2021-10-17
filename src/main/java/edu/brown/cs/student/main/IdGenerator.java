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

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
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

public class IdGenerator {
  //temp
  private HashMap<Integer, StudentAttributes[]> allData =
      new HashMap<Integer, StudentAttributes[]>();
  private HashMap<Integer, StudentQuant> studentQuant = new HashMap<Integer, StudentQuant>();
  private HashMap<String, StudentQual> studentQual = new HashMap<String, StudentQual>();

  public IdGenerator() {

  }

  public HashMap<Integer, StudentAttributes[]> loadDb() throws Exception {
    Database responsesDB = new Database("integration.sqlite3");
    Interests interestsTable = new Interests();
    Class<Interests> interestsClass = (Class<Interests>) interestsTable.getClass();
    List<Interests> allInterestsList = responsesDB.selectAll(interestsClass);

    //Interest table
    for (Interests i : allInterestsList) {
      int interestID = Integer.parseInt(i.getId());
      String interest = i.getInterest();

      if (allData.containsKey(interestID)) {
        if (allData.get(interestID)[0] != null) {
          StudentQual oneStudentQual = (StudentQual) allData.get(interestID)[0];
          oneStudentQual.addInterests(interest);
        } else {
          allData.get(interestID)[0] = new StudentQual(interestID);
          StudentQual oneStudentQual = (StudentQual) allData.get(interestID)[0];
          oneStudentQual.addInterests(interest);
        }
      } else {
        allData.put(interestID, new StudentAttributes[2]);
        allData.get(interestID)[0] = new StudentQual(interestID);
        StudentQual oneStudentQual = (StudentQual) allData.get(interestID)[0];
        oneStudentQual.addInterests(interest);
      }
    }

    //Negative Table
    Negative negativeTable = new Negative();
    Class<Negative> negativeClass = (Class<Negative>) negativeTable.getClass();
    List<Negative> allNegativeList = responsesDB.selectAll(negativeClass);

    for (Negative n : allNegativeList) {
      int negativeID = Integer.parseInt(n.getId());
      String negativeTrait = n.getTrait();

      if (allData.containsKey(negativeID)) {
        if (allData.get(negativeID)[0] != null) {
          StudentQual oneStudentQual = (StudentQual) allData.get(negativeID)[0];
          oneStudentQual.addTraits(negativeTrait);
        } else {
          allData.get(negativeID)[0] = new StudentQual(negativeID);
          StudentQual oneStudentQual = (StudentQual) allData.get(negativeID)[0];
          oneStudentQual.addTraits(negativeTrait);
        }
      } else {
        allData.put(negativeID, new StudentAttributes[2]);
        allData.get(negativeID)[0] = new StudentQual(negativeID);
        StudentQual oneStudentQual = (StudentQual) allData.get(negativeID)[0];
        oneStudentQual.addTraits(negativeTrait);
      }
    }


    Positive positiveTable = new Positive();
    Class<Positive> positiveClass = (Class<Positive>) positiveTable.getClass();
    List<Positive> allPositiveList = responsesDB.selectAll(positiveClass);

    for (Positive p : allPositiveList) {
      int positiveID = Integer.parseInt(p.getId());
      String positiveTrait = p.getTrait();


      if (allData.containsKey(positiveID)) {
        if (allData.get(positiveID)[0] != null) {
          StudentQual oneStudentQual = (StudentQual) allData.get(positiveID)[0];
          oneStudentQual.addTraits(positiveTrait);
        } else {
          allData.get(positiveID)[0] = new StudentQual(positiveID);
          StudentQual oneStudentQual = (StudentQual) allData.get(positiveID)[0];
          oneStudentQual.addTraits(positiveTrait);
        }
      } else {
        allData.put(positiveID, new StudentAttributes[2]);
        allData.get(positiveID)[0] = new StudentQual(positiveID);
        StudentQual oneStudentQual = (StudentQual) allData.get(positiveID)[0];
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


      if (allData.containsKey(id)) {
        if (allData.get(id)[1] != null) {
          StudentQuant oneStudentQuant = (StudentQuant) allData.get(id)[1];
          oneStudentQuant.setName(name);
          oneStudentQuant.setCommenting(commenting);
          oneStudentQuant.setTesting(testing);
          oneStudentQuant.setOOP(OOP);
          oneStudentQuant.setAlgorithms(algorithms);
          oneStudentQuant.setTeamwork(teamwork);
          oneStudentQuant.setFrontend(frontend);
        } else {
          allData.get(id)[1] = new StudentQuant(id, name, commenting, testing, OOP, algorithms,
              teamwork, frontend, yearsOfExperience);
        }
      } else {
        allData.put(id, new StudentAttributes[2]);
        allData.get(id)[1] = new StudentQuant(id, name, commenting, testing, OOP, algorithms,
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


      if (allData.containsKey(id)) {
        if (allData.get(id)[0] != null) {
          StudentQual oneStudQualitative = (StudentQual) allData.get(id)[0];
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
          allData.get(id)[0] = new StudentQual(id);
          StudentQual oneStudQualitative = (StudentQual) allData.get(id)[0];
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
        if (allData.get(id)[1] != null) {
          StudentQuant oneStudQuantitative = (StudentQuant) allData.get(id)[1];
          oneStudQuantitative.setYearsofExperience(yearsOfExperience);
        } else {
          allData.get(id)[1] = new StudentQuant(id);
          StudentQuant oneStudQuantitative = (StudentQuant) allData.get(id)[1];
          oneStudQuantitative.setYearsofExperience(yearsOfExperience);
        }
      } else {
        allData.put(id, new StudentAttributes[2]);
        allData.get(id)[0] = new StudentQual(id);
        StudentQual oneStudQualitative = (StudentQual) allData.get(id)[0];
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

        allData.get(id)[1] = new StudentQuant(id);
        StudentQuant oneStudQuantitative = (StudentQuant) allData.get(id)[1];
        oneStudQuantitative.setYearsofExperience(yearsOfExperience);
      }
    }

    for (Integer key : allData.keySet()) {
      this.studentQual.put(key.toString(), (StudentQual) allData.get(key)[0]);
      this.studentQuant.put(key, (StudentQuant) allData.get(key)[1]);
    }

//    System.out.println("studentCount "+ studentCount);
    return allData;

  }

  public List<Integer> kRecStudents(int numberOfRecs, int targetID,
                                    HashMap<Integer, StudentAttributes[]> students) {
    List<Coordinate<Integer>> quant = new ArrayList<>();
    HashMap<String, StudentQual> qual = new HashMap<>();

    List<Integer> remainder = new ArrayList<>(students.keySet());
    for (Integer r : remainder) {
      qual.put(r.toString(), (StudentQual) students.get(r)[0]);
      quant.add((StudentQuant) students.get(r)[1]);
    }
    int allDataSize = students.size() - 1;
    StudentQuant targetQuantAttr = (StudentQuant) students.get(targetID)[1];
    StudentQual targetQualAttr = (StudentQual) students.get(targetID)[0];

    HashMap<String, Integer> kdTreeResults = new HashMap<String, Integer>();
    HashMap<String, Integer> allResults = new HashMap<String, Integer>();

    KdTree<Integer> studentTree = new KdTree<Integer>(7, quant);
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
        qual, r);
    studentBloom.setBloomFilterComparator(
        new AndSimilarityComparator(new BloomFilter<String>(m, allDataSize, k)));
    List<StudentQual> qualComps = studentBloom.getTopKRecommendations(targetQualAttr, allDataSize);

    for (int i = 0; i < allDataSize; i++) {
      String relevantID = qualComps.get(i).getId();
      allResults.put(relevantID, (3 * i + kdTreeResults.get(relevantID)) / 4);
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
    List<Integer> studentIdLs = new ArrayList<>();
    for (int i = 0; i < numberOfRecs; i++) {
      int finalID = Integer.parseInt(sortedEntries.get(i).getKey());
      StudentQuant finalStudent = (StudentQuant) students.get(finalID)[1];
      studentIdLs.add(finalStudent.getId());
      System.out.println(finalStudent.getId());
    }
    return studentIdLs;
  }

  public void kStudentsTeam(String num_k, HashMap<Integer, StudentAttributes[]> students)
      throws IOException {
    File file = new File(
        "data/teams.txt");
    FileWriter fw = new FileWriter(
        file);
    BufferedWriter filewriter = new BufferedWriter(fw);
    int k = Integer.parseInt(num_k);
    List<Integer> studentIds = new ArrayList<>(students.keySet());
    List<List<Integer>> kStudentsTeamLs = new ArrayList<>();
    HashMap<Integer, StudentAttributes[]> studentData =
        (HashMap<Integer, StudentAttributes[]>) students.clone();
    while (studentData.size() > k) {
      int targetId = new ArrayList<>(studentData.keySet()).get(0);
      List<Integer> kStudents = kRecStudents(k, targetId, studentData);

      kStudentsTeamLs.add(kStudents);
      for (int student : kStudents) {
        filewriter.write(student + ", ");
        studentData.remove(student);
      }
      filewriter.write(System.lineSeparator());
    }
    List<Integer> remainder = new ArrayList<>(studentData.keySet());
    kStudentsTeamLs.add(remainder);
    for (int i = 0; i < remainder.size(); i++) {
      Integer append = remainder.get(i);
      if (i == remainder.size() - 1) {
        filewriter.write(remainder.get(i));
      } else {
        filewriter.write(remainder.get(i) + " ,");
      }
    }
    filewriter.flush();
    filewriter.close();
  }
}