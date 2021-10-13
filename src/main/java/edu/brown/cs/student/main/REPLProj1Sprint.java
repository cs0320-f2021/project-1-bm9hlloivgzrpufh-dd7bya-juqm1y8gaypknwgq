package edu.brown.cs.student.main;

import edu.brown.cs.student.client.ApiClient;
import edu.brown.cs.student.client.ClientRequestGenerator;
import edu.brown.cs.student.orm.Database;

import java.util.LinkedList;
import java.util.List;

public class REPLProj1Sprint implements REPLInterface{
  private final List<String> commandsList = new LinkedList<String>();

  REPLProj1Sprint() {
    this.commandsList.add("test");
    this.commandsList.add("recsys_rec");
    this.commandsList.add("recsys_gen_groups");
  }


  @Override
  public void parse(String[] arguments) throws Exception {
    if (commandsList.contains(arguments[0])) {
      if (arguments[0].equals("test")) {

        Database responsesDB = new Database("C:\\Users\\jchu4\\CSCI0320\\project-1-bm9hlloivgzrpufh-dd7bya-juqm1y8gaypknwgq\\data\\project-1\\integration.sqlite3");
        Interests interestsTable = new Interests();
        Class<Interests> interestsClass = (Class<Interests>) interestsTable.getClass();
        List<Interests> allInterestsList = responsesDB.selectAll(interestsClass);

        Negative negativeTable = new Negative();
        Class<Negative> negativeClass = (Class<Negative>) negativeTable.getClass();
        List<Negative> allNegativeList = responsesDB.selectAll(negativeClass);

        Positive positiveTable = new Positive();
        Class<Positive> positiveClass = (Class<Positive>) positiveTable.getClass();
        List<Positive> allPositiveList = responsesDB.selectAll(positiveClass);

        Skills skillsTable = new Skills();
        Class<Skills> skillsClass = (Class<Skills>) skillsTable.getClass();
        List<Skills> allSkillsList = responsesDB.selectAll(skillsClass);


        ApiClient client = new ApiClient();
        String data = client.makeRequest(ClientRequestGenerator.getSecuredPostRequest());
        System.out.println(data);

        System.out.println("Loaded Recommender with k students.");



        //Database usersDB = new Database("C:\\Users\\jchu4\\CSCI0320\\project-1-bm9hlloivgzrpufh-dd7bya-juqm1y8gaypknwgq\\data\\project-1\\runwaySMALL.sqlite3");
        //Rent rentTable = new Rent();
        //Class<Rent> rentClass = (Class<Rent>) rentTable.getClass();
        //
        //List<Rent> allRentList = usersDB.selectAll(rentClass);
        //for (Rent r: allRentList) {
        //  System.out.println(r.getCategory());
        //}


      }

      } else {
        throw new Exception("No such command");
    }
  }

}
