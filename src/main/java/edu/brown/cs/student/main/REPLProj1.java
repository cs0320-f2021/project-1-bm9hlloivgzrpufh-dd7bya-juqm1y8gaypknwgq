package edu.brown.cs.student.main;

import javax.xml.crypto.Data;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

public class REPLProj1 implements REPLInterface{
  private final List<String> commandsList = new LinkedList<String>();
  private KDTree tree = null;
  private HashMap<Integer, dNode> nodesMap = new HashMap<Integer, dNode>();

  REPLProj1() {
    this.commandsList.add("database");
    this.commandsList.add("INSERT");
    this.commandsList.add("DELETE");
    this.commandsList.add("SELECT");
    this.commandsList.add("UPDATE");
    this.commandsList.add("RAWQUERY");
    this.commandsList.add("users");
    this.commandsList.add("similar");
    this.commandsList.add("classify");
  }

  @Override
  public void parse(String[] arguments) throws Exception {

    DataBot dataBot = new DataBot();

    Rent small = new Rent();
    Rent fit = new Rent();
    Rent large = new Rent();
    Rent delete = new Rent();
    //rent small = new rent("small", 1, 135, 4, "some_event",
    //    "dress", "huge", 1);
    //rent fit = new rent("fit", 2, 132, 4, "some_event",
    //    "dress", "huge", 2);
    //rent large = new rent("large", 3, 131, 4, "some_event",
    //   "dress", "huge", 3);
    //rent delete = new rent("delete", 4, 139, 4, "some_event",
    //    "dress", "huge", 4);
    PrintWriter pw = new PrintWriter(System.out, true);

    if (commandsList.contains(arguments[0])) {
      if (arguments[0].equals("database")) {
        DataBot.loadDb(arguments[1]);
        pw.println("database loaded");
      } else if (arguments[0].equals("INSERT")) {
        dataBot.insert(small);
        dataBot.insert(fit);
        dataBot.insert(large);
        dataBot.insert(delete);
        pw.println("inserted");
      } else if (arguments[0].equals("DELETE")) {
        dataBot.delete(delete);
        pw.println("deleted");
      } else if (arguments[0].equals("SELECT")) {
        List<?> objLs = dataBot.select(arguments[1], Arrays.asList(arguments).subList(2,
            arguments.length));
        int length = objLs.size();
        pw.println(length);
      } else if (arguments[0].equals("UPDATE")) {
        dataBot.update(small, arguments[1], arguments[2], arguments[3]);
        pw.println("update finished");
      } else if (arguments[0].equals("RAWQUERY")) {
        List<String> ls = Arrays.asList(arguments).subList(1, arguments.length);
        String sqlStatement = String.join(" ", ls);
        dataBot.rawQuery(sqlStatement);
        pw.println("rawQuery finished");
      }

      else if (arguments[0].equals("users")) {

        ArrayList<dNode> nodeList = new ArrayList<dNode>();

        // DO NOT DELETE: uses a different dataset from DataBot
        Class.forName("org.sqlite.JDBC");
        String urlToDB = "jdbc:sqlite:" + arguments[1];
        Connection conn = DriverManager.getConnection(urlToDB);
        Statement stat = conn.createStatement();
        stat.executeUpdate("PRAGMA foreign_keys=ON;");

        PreparedStatement prep = conn.prepareStatement(
            "SELECT user_id, weight, height, age, horoscope FROM users;");
        ResultSet rs = prep.executeQuery();

        while (rs.next()) {
          RawUserData data = new RawUserData(
              rs.getInt(1), rs.getString(2), rs.getString(3),
              rs.getInt(4), rs.getString(5));

          dNode node = new dNode(data.getID(), data.cleanWeight(), data.cleanHeight(),
              data.getAge(), data.getHoroscope());

          nodeList.add(node);
          this.nodesMap.put(node.getID(), node);
        }

        prep.close();
        rs.close();
        conn.close();

        this.tree = new KDTree(nodeList.get(0));
        this.tree.buildTree(nodeList);

        System.out.println("done loading data");
      }
      // similar
      else if (arguments[0].equals("similar") || arguments[0].equals("classify")) {
        if (arguments.length == 5) {
          int neighbors = Integer.parseInt(arguments[1]);
          int weight = Integer.parseInt(arguments[2]);
          int height = Integer.parseInt(arguments[3]);
          int age = Integer.parseInt(arguments[4]);

          ArrayList<Integer> target = new ArrayList<Integer>();
          target.add(weight);
          target.add(height);
          target.add(age);

          ArrayList<dNode> neighborsList = new ArrayList<dNode>();

          this.tree.search(neighbors, this.tree.getRoot(), neighborsList, target);
          System.out.println("done searching");

          if (arguments[0].equals("similar")) {
            for (dNode neighbor : neighborsList) {
              System.out.println(neighbor.getID());
            }
          } else if (arguments[0].equals("classify")) {
            String[] allSigns = new String[12];
            allSigns[0] = "Aries";
            allSigns[1] = "Taurus";
            allSigns[2] = "Gemini";
            allSigns[3] = "Cancer";
            allSigns[4] = "Leo";
            allSigns[5] = "Virgo";
            allSigns[6] = "Libra";
            allSigns[7] = "Scorpio";
            allSigns[8] = "Sagittarius";
            allSigns[9] = "Capricorn";
            allSigns[10] = "Aquarius";
            allSigns[11] = "Pisces";

            HashMap<String, Integer> horoscopeMap = new HashMap<String, Integer>();

            for (dNode neighbor : neighborsList) {
              String horoscope = neighbor.getHoroscope();
              if (horoscopeMap.containsKey(horoscope)) {
                int count = horoscopeMap.get(horoscope);
                horoscopeMap.put(horoscope, count + 1);
              } else {
                horoscopeMap.put(horoscope, 1);
              }
            }

            for (String sign : allSigns) {
              if (!(horoscopeMap.containsKey(sign))) {
                horoscopeMap.put(sign, 0);
              }
              System.out.println(sign + ": " + horoscopeMap.get(sign));
            }
          }
        } else if (arguments.length == 3) {
          int neighbors = Integer.parseInt(arguments[1]);
          int id = Integer.parseInt(arguments[2]);

          dNode targetNode = this.nodesMap.get(id);

          int weight = targetNode.getWeight();
          int height = targetNode.getHeight();
          int age = targetNode.getAge();

          ArrayList<Integer> target = new ArrayList<Integer>();
          target.add(weight);
          target.add(height);
          target.add(age);

          ArrayList<dNode> neighborsList = new ArrayList<dNode>();

          this.tree.search(neighbors + 1, this.tree.getRoot(), neighborsList, target);
          neighborsList.remove(0);
          System.out.println("done searching");

          if (arguments[0].equals("similar")) {
            for (dNode neighbor : neighborsList) {
              System.out.println(neighbor.getID());
            }
          } else {
            String[] allSigns = new String[12];
            allSigns[0] = "Aries";
            allSigns[1] = "Taurus";
            allSigns[2] = "Gemini";
            allSigns[3] = "Cancer";
            allSigns[4] = "Leo";
            allSigns[5] = "Virgo";
            allSigns[6] = "Libra";
            allSigns[7] = "Scorpio";
            allSigns[8] = "Sagittarius";
            allSigns[9] = "Capricorn";
            allSigns[10] = "Aquarius";
            allSigns[11] = "Pisces";

            HashMap<String, Integer> horoscopeMap = new HashMap<String, Integer>();

            for (dNode neighbor : neighborsList) {
              String horoscope = neighbor.getHoroscope();
              if (horoscopeMap.containsKey(horoscope)) {
                int count = horoscopeMap.get(horoscope);
                horoscopeMap.put(horoscope, count + 1);
              } else {
                horoscopeMap.put(horoscope, 1);
              }
            }

            for (String sign : allSigns) {
              if (!(horoscopeMap.containsKey(sign))) {
                horoscopeMap.put(sign, 0);
              }
              System.out.println(sign + ": " + horoscopeMap.get(sign));
            }
          }
        }
      }

    } else {
        throw new Exception("No such command");
    }
  }
}
