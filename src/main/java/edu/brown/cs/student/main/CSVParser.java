package edu.brown.cs.student.main;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;

public class CSVParser {
  public CSVParser() { }

  public HashMap<Integer, Star> parse(String csvFile) throws IOException {
    File file = new File(csvFile);
    FileReader fr = new FileReader(file);
    BufferedReader br = new BufferedReader(fr);
    br.readLine();
    String line = "";
    String[] tempArr;
    HashMap<Integer, Star> finalList = new HashMap<Integer, Star>();
    while ((line = br.readLine()) != null) {
      tempArr = line.split(",");
      Star tempStar = new Star(Integer.parseInt(tempArr[0]), tempArr[1], Double.parseDouble(
          tempArr[2]), Double.parseDouble(tempArr[3]), Double.parseDouble(tempArr[4]));
      finalList.put(Integer.parseInt(tempArr[0]), tempStar); }
    br.close();
    return finalList;
  }
}
