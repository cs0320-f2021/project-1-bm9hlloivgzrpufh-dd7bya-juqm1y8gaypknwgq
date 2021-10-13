package edu.brown.cs.student.main;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class REPLStars implements REPLInterface{
  private final List<String> commandsList = new LinkedList<String>();
  HashMap<Integer, Star> starsList = new HashMap<Integer, Star>();
  List<StarDistPair> distList = new LinkedList<StarDistPair>();

  REPLStars() {
    this.commandsList.add("stars");
    this.commandsList.add("naive_neighbors");
  }

  public void parse(String[] arguments) throws Exception {

    if (commandsList.contains(arguments[0])) {

        if (arguments[0].equals("stars")) {
          CSVParser parsingMachine = new CSVParser();
          this.starsList = parsingMachine.parse(arguments[1]);
          System.out.println("finished loading star data");

          // naive neighbors
        } else if (arguments[0].equals("naive_neighbors")) {

          distList = new LinkedList<StarDistPair>();
          if (arguments.length == 5) {
            //int tempCount = 1;
            for (Map.Entry<Integer, Star> entry : this.starsList.entrySet()) {
              int tempID = entry.getKey();
              Star tempStar = entry.getValue();
              double dist = tempStar.distance(Double.parseDouble(arguments[2]),
                  Double.parseDouble(arguments[3]), Double.parseDouble(arguments[4]));
              StarDistPair tempPair = new StarDistPair(tempID, dist);
              int listLength = this.distList.size();
              int desiredLength = Integer.parseInt(arguments[1]);
              for (int i = 0; i <= desiredLength; i++) {
                if (this.distList.isEmpty()) {
                  this.distList.add(tempPair);
                  break;
                } else if (listLength == 1) {
                  if (dist < this.distList.get(i).getDist()) {
                    this.distList.add(0, tempPair);
                    break;
                  } else if (dist > this.distList.get(i).getDist()) {
                    this.distList.add(1, tempPair);
                    break;
                  } else {
                    desiredLength = desiredLength + 1;
                    Random rand = new Random();
                    int randomNumber = rand.nextInt(2);
                    this.distList.add(randomNumber, tempPair);
                    break;
                  }
                } else if (dist < this.distList.get(0).getDist()) {
                  this.distList.add(0, tempPair);
                  break;
                } else if (dist == this.distList.get(i).getDist()) {
                  desiredLength = desiredLength + 1;
                  int count = 1;
                  for (int n = 1; i + n != listLength; n++) {
                    if (dist == this.distList.get(i + n).getDist()) {
                      count = count + 1;
                    } else {
                      break;
                    }
                  }
                  Random rand = new Random();
                  int randomNumber = rand.nextInt(count + 1);
                  this.distList.add(i + randomNumber, tempPair);
                  break;
                } else if (i == listLength - 1) {
                  this.distList.add(i + 1, tempPair);
                  break;
                } else if (dist < this.distList.get(i + 1).getDist()
                    && dist > this.distList.get(i).getDist()) {
                  this.distList.add(i + 1, tempPair);
                  break;
                }
              }
              if (this.distList.size() > desiredLength) {
                this.distList.remove(desiredLength);
              }
            }
            int neighbors = Integer.parseInt(arguments[1]);
            if (neighbors > this.starsList.size()) {
              for (int i = 0; i < this.starsList.size(); i++) {
                StarDistPair pair = this.distList.get(i);
                int starID = pair.getID();
                Star star = this.starsList.get(starID);
                System.out.println(star.getName());
                System.out.println(star.getID());
              }
            } else {
              for (int i = 0; i < neighbors; i++) {
                StarDistPair pair = this.distList.get(i);
                int starID = pair.getID();
                Star star = this.starsList.get(starID);
                System.out.println(star.getName());
                System.out.println(star.getID());
              }
            }
          } else if (arguments.length == 3) {
            String targetName = arguments[2].replace("\"", "");
            int targetID = -1;
            for (Map.Entry<Integer, Star> entry : this.starsList.entrySet()) {
              int tempID = entry.getKey();
              Star tempStar = entry.getValue();
              if (tempStar.getName().equals(targetName)) {
                targetID = tempID;
                break;
              }
            }
            Star targetStar = this.starsList.get(targetID);
            this.starsList.remove(targetID);
            for (Map.Entry<Integer, Star> entry : this.starsList.entrySet()) {
              int tempID = entry.getKey();
              Star tempStar = entry.getValue();
              double dist = tempStar.distance(targetStar.getX(), targetStar.getY(),
                  targetStar.getZ());
              StarDistPair tempPair = new StarDistPair(tempID, dist);
              int listLength = this.distList.size();
              int desiredLength = Integer.parseInt(arguments[1]);
              for (int i = 0; i <= desiredLength; i++) {
                if (this.distList.isEmpty()) {
                  this.distList.add(tempPair);
                  break;
                } else if (listLength == 1) {
                  if (dist < this.distList.get(i).getDist()) {
                    this.distList.add(0, tempPair);
                    break;
                  } else if (dist > this.distList.get(i).getDist()) {
                    this.distList.add(1, tempPair);
                    break;
                  } else {
                    desiredLength = desiredLength + 1;
                    Random rand = new Random();
                    int randomNumber = rand.nextInt(2);
                    this.distList.add(randomNumber, tempPair);
                    break;
                  }
                } else if (dist < this.distList.get(0).getDist()) {
                  this.distList.add(0, tempPair);
                  break;
                } else if (dist == this.distList.get(i).getDist()) {
                  desiredLength = desiredLength + 1;
                  int count = 1;
                  for (int n = 1; i + n != listLength; n++) {
                    if (dist == this.distList.get(i + n).getDist()) {
                      count = count + 1;
                    } else {
                      break;
                    }
                  }
                  Random rand = new Random();
                  int randomNumber = rand.nextInt(count + 1);
                  this.distList.add(i + randomNumber, tempPair);
                  break;
                } else if (i == listLength - 1) {
                  this.distList.add(i + 1, tempPair);
                  break;
                } else if (dist < this.distList.get(i + 1).getDist()
                    && dist > this.distList.get(i).getDist()) {
                  this.distList.add(i + 1, tempPair);
                  break;
                }
              }
              if (this.distList.size() > desiredLength) {
                this.distList.remove(desiredLength);
              }
            }
            int neighbors = Integer.parseInt(arguments[1]);
            if (neighbors > this.starsList.size()) {
              for (int i = 0; i < this.starsList.size(); i++) {
                StarDistPair pair = this.distList.get(i);
                int starID = pair.getID();
                Star star = this.starsList.get(starID);
                System.out.println(star.getName());
                System.out.println(star.getID());
              }
            } else {
              for (int i = 0; i < neighbors; i++) {
                StarDistPair pair = this.distList.get(i);
                int starID = pair.getID();
                Star star = this.starsList.get(starID);
                System.out.println(star.getName());
                System.out.println(star.getID());
              }
            }
          } else {
            throw new Exception();
          }
        }
    }
    else {
      throw new Exception("No such command");
    }
  }
  }
