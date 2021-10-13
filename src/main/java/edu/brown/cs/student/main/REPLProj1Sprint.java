package edu.brown.cs.student.main;

import edu.brown.cs.student.orm.Database;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

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
