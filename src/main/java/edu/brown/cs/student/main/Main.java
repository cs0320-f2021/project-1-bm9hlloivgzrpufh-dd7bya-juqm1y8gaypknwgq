package edu.brown.cs.student.main;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Random;

import com.google.common.collect.ImmutableMap;

import freemarker.template.Configuration;
import joptsimple.OptionParser;
import joptsimple.OptionSet;
import spark.ExceptionHandler;
import spark.ModelAndView;
import spark.Request;
import spark.Response;
import spark.Spark;
import spark.TemplateViewRoute;
import spark.template.freemarker.FreeMarkerEngine;

/**
 * The Main class of our project. This is where execution begins.
 */
public final class Main {

  // use port 4567 by default when running server
  private static final int DEFAULT_PORT = 4567;

  /**
   * The initial method called when execution begins.
   *
   * @param args An array of command line arguments
   */
  public static void main(String[] args) {
    new Main(args).run();
  }

  private String[] args;

  private Main(String[] args) {
    this.args = args;
  }

  private void run() {
    // set up parsing of command line flags
    OptionParser parser = new OptionParser();

    // "./run --gui" will start a web server
    parser.accepts("gui");

    // use "--port <n>" to specify what port on which the server runs
    parser.accepts("port").withRequiredArg().ofType(Integer.class)
        .defaultsTo(DEFAULT_PORT);

    OptionSet options = parser.parse(args);
    if (options.has("gui")) {
      runSparkServer((int) options.valueOf("port"));
    }

    // todo: Add your REPL here!
    try (BufferedReader br = new BufferedReader(new InputStreamReader(System.in))) {
      String input;
      HashMap<Integer, Star> starsList = new HashMap<Integer, Star>();
      List<StarDistPair> distList = new LinkedList<StarDistPair>();
      KDTree tree = null;
      HashMap<Integer, dNode> nodesMap = new HashMap<Integer, dNode>();
      while ((input = br.readLine()) != null) {
        try {
          input = input.trim();
          String[] arguments = input.split(" ");

          // add
          if (arguments[0].equals("add")) {
            MathBot bot = new MathBot();
            System.out.println(bot.add(Double.parseDouble(arguments[1]),
                Double.parseDouble(arguments[2])));
          // subtract
          } else if (arguments[0].equals("subtract")) {
            MathBot bot = new MathBot();
            System.out.println(bot.subtract(Double.parseDouble(arguments[1]),
                Double.parseDouble(arguments[2])));
          // stars
          } else if (arguments[0].equals("stars")) {
            CSVParser parsingMachine = new CSVParser();
            starsList = parsingMachine.parse(arguments[1]);
          // naive neighbors
          } else if (arguments[0].equals("naive_neighbors")) {
            distList = new LinkedList<StarDistPair>();
            if (arguments.length == 5) {
              //int tempCount = 1;
              for (Map.Entry<Integer, Star> entry : starsList.entrySet()) {
                int tempID = entry.getKey();
                Star tempStar = entry.getValue();
                double dist = tempStar.distance(Double.parseDouble(arguments[2]),
                    Double.parseDouble(arguments[3]), Double.parseDouble(arguments[4]));
                StarDistPair tempPair = new StarDistPair(tempID, dist);
                int listLength = distList.size();
                int desiredLength = Integer.parseInt(arguments[1]);
                for (int i = 0; i <= desiredLength; i++) {
                  if (distList.isEmpty()) {
                    distList.add(tempPair);
                    break;
                  } else if (listLength == 1) {
                    if (dist < distList.get(i).getDist()) {
                      distList.add(0, tempPair);
                      break;
                    } else if (dist > distList.get(i).getDist()) {
                      distList.add(1, tempPair);
                      break;
                    } else {
                      desiredLength = desiredLength + 1;
                      Random rand = new Random();
                      int randomNumber = rand.nextInt(2);
                      distList.add(randomNumber, tempPair);
                      break;
                    }
                  } else if (dist < distList.get(0).getDist()) {
                    distList.add(0, tempPair);
                    break;
                  } else if (dist == distList.get(i).getDist()) {
                    desiredLength = desiredLength + 1;
                    int count = 1;
                    for (int n = 1; i + n != listLength; n++) {
                      if (dist == distList.get(i + n).getDist()) {
                        count = count + 1;
                      } else {
                        break;
                      }
                    }
                    Random rand = new Random();
                    int randomNumber = rand.nextInt(count + 1);
                    distList.add(i + randomNumber, tempPair);
                    break;
                  } else if (i == listLength - 1) {
                    distList.add(i + 1, tempPair);
                    break;
                  } else if (dist < distList.get(i + 1).getDist()
                      && dist > distList.get(i).getDist()) {
                    distList.add(i + 1, tempPair);
                    break;
                  }
                }
                if (distList.size() > desiredLength) {
                  distList.remove(desiredLength);
                }
              }
              int neighbors = Integer.parseInt(arguments[1]);
              if (neighbors > starsList.size()) {
                for (int i = 0; i < starsList.size(); i++) {
                  StarDistPair pair = distList.get(i);
                  int starID = pair.getID();
                  Star star = starsList.get(starID);
                  System.out.println(star.getName());
                  System.out.println(star.getID());
                }
              } else {
                for (int i = 0; i < neighbors; i++) {
                  StarDistPair pair = distList.get(i);
                  int starID = pair.getID();
                  Star star = starsList.get(starID);
                  System.out.println(star.getName());
                  System.out.println(star.getID());
                }
              }
            } else if (arguments.length == 3) {
              String targetName = arguments[2].replace("\"", "");
              int targetID = -1;
              for (Map.Entry<Integer, Star> entry : starsList.entrySet()) {
                int tempID = entry.getKey();
                Star tempStar = entry.getValue();
                if (tempStar.getName().equals(targetName)) {
                  targetID = tempID;
                  break;
                }
              }
              Star targetStar = starsList.get(targetID);
              starsList.remove(targetID);
              for (Map.Entry<Integer, Star> entry : starsList.entrySet()) {
                int tempID = entry.getKey();
                Star tempStar = entry.getValue();
                double dist = tempStar.distance(targetStar.getX(), targetStar.getY(),
                    targetStar.getZ());
                StarDistPair tempPair = new StarDistPair(tempID, dist);
                int listLength = distList.size();
                int desiredLength = Integer.parseInt(arguments[1]);
                for (int i = 0; i <= desiredLength; i++) {
                  if (distList.isEmpty()) {
                    distList.add(tempPair);
                    break;
                  } else if (listLength == 1) {
                    if (dist < distList.get(i).getDist()) {
                      distList.add(0, tempPair);
                      break;
                    } else if (dist > distList.get(i).getDist()) {
                      distList.add(1, tempPair);
                      break;
                    } else {
                      desiredLength = desiredLength + 1;
                      Random rand = new Random();
                      int randomNumber = rand.nextInt(2);
                      distList.add(randomNumber, tempPair);
                      break;
                    }
                  } else if (dist < distList.get(0).getDist()) {
                    distList.add(0, tempPair);
                    break;
                  } else if (dist == distList.get(i).getDist()) {
                    desiredLength = desiredLength + 1;
                    int count = 1;
                    for (int n = 1; i + n != listLength; n++) {
                      if (dist == distList.get(i + n).getDist()) {
                        count = count + 1;
                      } else {
                        break;
                      }
                    }
                    Random rand = new Random();
                    int randomNumber = rand.nextInt(count + 1);
                    distList.add(i + randomNumber, tempPair);
                    break;
                  } else if (i == listLength - 1) {
                    distList.add(i + 1, tempPair);
                    break;
                  } else if (dist < distList.get(i + 1).getDist()
                      && dist > distList.get(i).getDist()) {
                    distList.add(i + 1, tempPair);
                    break;
                  }
                }
                if (distList.size() > desiredLength) {
                  distList.remove(desiredLength);
                }
              }
              int neighbors = Integer.parseInt(arguments[1]);
              if (neighbors > starsList.size()) {
                for (int i = 0; i < starsList.size(); i++) {
                  StarDistPair pair = distList.get(i);
                  int starID = pair.getID();
                  Star star = starsList.get(starID);
                  System.out.println(star.getName());
                  System.out.println(star.getID());
                }
              } else {
                for (int i = 0; i < neighbors; i++) {
                  StarDistPair pair = distList.get(i);
                  int starID = pair.getID();
                  Star star = starsList.get(starID);
                  System.out.println(star.getName());
                  System.out.println(star.getID());
                }
              }
            } else {
              throw new Exception();
            }
          }




          //Project 1 starts here
          // users
          else if (arguments[0].equals("users")) {

            ArrayList<dNode> nodeList = new ArrayList<dNode>();

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
              nodesMap.put(node.getID(), node);
            }

            prep.close();
            rs.close();
            conn.close();

            //System.out.println(nodeList);

            tree = new KDTree(nodeList.get(0));
            tree.buildTree(nodeList);

            //System.out.println(tree.getRoot().getLeft());
            //tree.check(0, tree.getRoot());
            System.out.println("done");
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

              tree.search(neighbors, tree.getRoot(), neighborsList, target);
              System.out.println("done searching");
              /*
              System.out.println(neighborsList.get(0).getWeight());
              System.out.println(neighborsList.get(0).getHeight());
              System.out.println(neighborsList.get(0).getAge());
              System.out.println(neighborsList.get(1).getWeight());
              System.out.println(neighborsList.get(1).getHeight());
              System.out.println(neighborsList.get(1).getAge());

              System.out.println(neighborsList.get(0).getID());
              System.out.println(neighborsList.get(1).getID());
              System.out.println(neighborsList.get(2).getID());
              System.out.println(neighborsList.get(3).getID());
              System.out.println(neighborsList.get(4).getID());
               */
              //System.out.println(neighborsList.size());
              if (arguments[0].equals("similar")) {
                for (dNode neighbor : neighborsList) {
                  System.out.println(neighbor.getID());
                  //System.out.println(neighbor.calcDistance(target.get(0), target.get(1), target.get(2)));
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

              dNode targetNode = nodesMap.get(id);

              int weight = targetNode.getWeight();
              int height = targetNode.getHeight();
              int age = targetNode.getAge();

              ArrayList<Integer> target = new ArrayList<Integer>();
              target.add(weight);
              target.add(height);
              target.add(age);

              ArrayList<dNode> neighborsList = new ArrayList<dNode>();

              tree.search(neighbors + 1, tree.getRoot(), neighborsList, target);
              neighborsList.remove(0);
              System.out.println("done searching");
              /*
              System.out.println(neighborsList.get(0).getID());
              System.out.println(neighborsList.get(1).getID());
              System.out.println(neighborsList.get(2).getID());
              System.out.println(neighborsList.get(3).getID());
              System.out.println(neighborsList.get(4).getID());
               */
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
          else {
            throw new Exception();
          }
        } catch (Exception e) {
          // e.printStackTrace();
          System.out.println("ERROR: We couldn't process your input");
        }
      }
    } catch (Exception e) {
      e.printStackTrace();
      System.out.println("ERROR: Invalid input for REPL");
    }

  }

  private static FreeMarkerEngine createEngine() {
    Configuration config = new Configuration(Configuration.VERSION_2_3_0);

    // this is the directory where FreeMarker templates are placed
    File templates = new File("src/main/resources/spark/template/freemarker");
    try {
      config.setDirectoryForTemplateLoading(templates);
    } catch (IOException ioe) {
      System.out.printf("ERROR: Unable use %s for template loading.%n",
          templates);
      System.exit(1);
    }
    return new FreeMarkerEngine(config);
  }

  private void runSparkServer(int port) {
    // set port to run the server on
    Spark.port(port);

    // specify location of static resources (HTML, CSS, JS, images, etc.)
    Spark.externalStaticFileLocation("src/main/resources/static");

    // when there's a server error, use ExceptionPrinter to display error on GUI
    Spark.exception(Exception.class, new ExceptionPrinter());

    // initialize FreeMarker template engine (converts .ftl templates to HTML)
    FreeMarkerEngine freeMarker = createEngine();

    // setup Spark Routes
    Spark.get("/", new MainHandler(), freeMarker);
  }

  /**
   * Display an error page when an exception occurs in the server.
   */
  private static class ExceptionPrinter implements ExceptionHandler<Exception> {
    @Override
    public void handle(Exception e, Request req, Response res) {
      // status 500 generally means there was an internal server error
      res.status(500);

      // write stack trace to GUI
      StringWriter stacktrace = new StringWriter();
      try (PrintWriter pw = new PrintWriter(stacktrace)) {
        pw.println("<pre>");
        e.printStackTrace(pw);
        pw.println("</pre>");
      }
      res.body(stacktrace.toString());
    }
  }

  /**
   * A handler to serve the site's main page.
   *
   * @return ModelAndView to render.
   * (main.ftl).
   */
  private static class MainHandler implements TemplateViewRoute {
    @Override
    public ModelAndView handle(Request req, Response res) {
      // this is a map of variables that are used in the FreeMarker template
      Map<String, Object> variables = ImmutableMap.of("title",
          "Go go GUI");

      return new ModelAndView(variables, "main.ftl");
    }
  }
}
