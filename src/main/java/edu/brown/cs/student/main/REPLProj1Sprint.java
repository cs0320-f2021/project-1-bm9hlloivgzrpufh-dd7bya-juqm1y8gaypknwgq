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

public class REPLProj1Sprint implements REPLInterface{
  private final List<String> commandsList = new LinkedList<String>();

  //temp
  private final HashMap<Integer, StudentAttributes[]> allData = new HashMap<Integer, StudentAttributes[]>();
  private final List<Coordinate<Integer>> studentQuant = new LinkedList<Coordinate<Integer>>();
  private final HashMap<String, StudentQual> studentQual = new HashMap<String, StudentQual>();

  REPLProj1Sprint() {
    this.commandsList.add("recsys_load");
    this.commandsList.add("recsys_rec");
    this.commandsList.add("recsys_gen_groups");
  }


  @Override
  public void parse(String[] arguments) throws Exception {
    if (commandsList.contains(arguments[0])) {
      if (arguments[0].equals("recsys_load")) {

        Database responsesDB = new Database("C:\\Users\\jchu4\\CSCI0320\\project-1-bm9hlloivgzrpufh-dd7bya-juqm1y8gaypknwgq\\data\\project-1\\integration.sqlite3");
        Interests interestsTable = new Interests();
        Class<Interests> interestsClass = (Class<Interests>) interestsTable.getClass();
        List<Interests> allInterestsList = responsesDB.selectAll(interestsClass);

        for(Interests i : allInterestsList) {
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


        Negative negativeTable = new Negative();
        Class<Negative> negativeClass = (Class<Negative>) negativeTable.getClass();
        List<Negative> allNegativeList = responsesDB.selectAll(negativeClass);

        for(Negative n: allNegativeList) {
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

        for(Positive p: allPositiveList) {
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

        for(Skills s: allSkillsList) {
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

        for (String oneStudentQual: allQualData) {
          List<String> oneStudentQualClean = new LinkedList<String>();

          Pattern p = Pattern.compile("\"(.*?)\"");
          Matcher m = p.matcher(oneStudentQual);
          while(m.find()) {
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
                for (String time: meetingTimesSplit) {
                  oneStudQualitative.addMeetingTimes(time);
                }
              } else {
                oneStudQualitative.addMeetingTimes(meetingTimes);
              }
              oneStudQualitative.addPreferredLanguage(preferredLanguage);
              if (marginalziedGroups.contains(";")) {
                String[] marginalizedGroupsSplit = marginalziedGroups.split("; ");
                for (String group: marginalizedGroupsSplit) {
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
                for (String time: meetingTimesSplit) {
                  oneStudQualitative.addMeetingTimes(time);
                }
              } else {
                oneStudQualitative.addMeetingTimes(meetingTimes);
              }
              oneStudQualitative.addPreferredLanguage(preferredLanguage);
              if (marginalziedGroups.contains(";")) {
                String[] marginalizedGroupsSplit = marginalziedGroups.split("; ");
                for (String group: marginalizedGroupsSplit) {
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
              for (String time: meetingTimesSplit) {
                oneStudQualitative.addMeetingTimes(time);
              }
            } else {
              oneStudQualitative.addMeetingTimes(meetingTimes);
            }
            oneStudQualitative.addPreferredLanguage(preferredLanguage);
            if (marginalziedGroups.contains(";")) {
              String[] marginalizedGroupsSplit = marginalziedGroups.split("; ");
              for (String group: marginalizedGroupsSplit) {
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

        for (Integer key: allData.keySet()) {
          studentQual.put(key.toString(), (StudentQual) allData.get(key)[0]);
          studentQuant.add((StudentQuant) allData.get(key)[1]);
        }

        int studentCount = allData.size();
        System.out.println("Loaded Recommender with " + studentCount + " students.");

      } else if(arguments[0].equals("recsys_rec")) {
        int numberOfRecs = Integer.parseInt(arguments[1]);
        int allDataSize = allData.size() - 1;
        int targetID = Integer.parseInt(arguments[2]);
        StudentQuant targetQuantAttr = (StudentQuant) allData.get(targetID)[1];
        StudentQual targetQualAttr = (StudentQual) allData.get(targetID)[0];

        HashMap<String, Integer> kdTreeResults = new HashMap<String, Integer>();
        HashMap<String, Integer> allResults = new HashMap<String, Integer>();

        KdTree<Integer> studentTree = new KdTree<Integer>(7, studentQuant);
        Node<Coordinate<Integer>> studentRoot = studentTree.getRoot();
        KdTreeSearch<Integer> treeSearch = new KdTreeSearch<Integer>();
        List<Coordinate<Integer>> quantComps = treeSearch.getNearestNeighborsResult(
            allDataSize, targetQuantAttr, studentRoot, true);

        for (int i=0; i < allDataSize; i++) {
          kdTreeResults.put(quantComps.get(i).getId().toString(), i);
        }

        double r = 0.01;
        int k = (int) Math.round(ceil(-1 * (Math.log(r) / Math.log(2))));
        double m = ceil((k * allDataSize) / Math.log(2));
        BloomFilterRecommender<StudentQual> studentBloom = new BloomFilterRecommender<StudentQual>(
            this.studentQual, r);
        studentBloom.setBloomFilterComparator(new AndSimilarityComparator(new BloomFilter<String>(m, allDataSize, k)));
        List<StudentQual> qualComps = studentBloom.getTopKRecommendations(targetQualAttr, allDataSize);

        for (int i=0; i < allDataSize; i++) {
          String relevantID = qualComps.get(i).getId();
          allResults.put(relevantID, i + kdTreeResults.get(relevantID));
        }


        Set<Entry<String,Integer>> entries = allResults.entrySet();
        List<Entry<String,Integer>> sortedEntries = new ArrayList<>(entries);
        Collections.sort(sortedEntries, new Comparator<Entry<String, Integer>>()
        {
          public int compare(Entry<String, Integer> o1,
                             Entry<String, Integer> o2)
          { return o1.getValue().compareTo(o2.getValue()); }
        }
        );

        for (int i=0; i < numberOfRecs; i++) {
          int finalID = Integer.parseInt(sortedEntries.get(i).getKey());
          StudentQuant finalStudent = (StudentQuant) allData.get(finalID)[1];
          System.out.println(finalStudent.getId());
        }
      }
    } else {
        throw new Exception("No such command");
    }
  }

}
