package edu.brown.cs.student.main;

import edu.brown.cs.student.coordinates.Coordinate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class StudentQuant implements Coordinate<Integer>, StudentAttributes {
  private final int id;
  private String name;
  private final Double[] coordinates = new Double[7];
  //private final int commenting;
  //private final int testing;
  //private final int OOP;
  //private final int algorithms;
  //private final int teamwork;
  //private final int frontend;
  //private final int yearsOfExperience;

  public StudentQuant(int id) {
    this.id = id;
    this.name = "";
  }

  public StudentQuant(int id, String name, Double commenting, Double testing, Double OOP, Double algorithms,
                      Double teamwork, Double frontend, Double yearsOfExperience) {
    this.id = id;
    this.name = name;

    coordinates[0] = commenting;
    coordinates[1] = testing;
    coordinates[2] = OOP;
    coordinates[3] = algorithms;
    coordinates[4] = teamwork;
    coordinates[5] = frontend;
    coordinates[6] = yearsOfExperience;

    //this.commenting = commenting;
    //this.testing = testing;
    //this.OOP = OOP;
    //this.algorithms = algorithms;
    //this.teamwork = teamwork;
    //this.frontend = frontend;
    //this.yearsOfExperience = yearsOfExperience;
  }

  public void setName(String name) {
    this.name = name;
  }

  public void setCommenting(Double commenting) {
    coordinates[0] = commenting;
  }

  public void setTesting(Double testing) {
    coordinates[1] = testing;
  }

  public void setOOP(Double OOP) {
    coordinates[2] = OOP;
  }

  public void setAlgorithms(Double algorithms) {
    coordinates[3] = algorithms;
  }

  public void setTeamwork(Double teamwork) {
    coordinates[4] = teamwork;
  }

  public void setFrontend(Double frontend) {
    coordinates[5] = frontend;
  }

  public void setYearsofExperience(Double yearsOfExperience) {
    coordinates[6] = yearsOfExperience;
  }

  public String getName() {
    return this.name;
  }

  public Double getCommenting() {
    return this.coordinates[0];
  }

  public Double getTesting() {
    return this.coordinates[1];
  }

  public Double getOOP() {
    return this.coordinates[2];
  }

  public Double getAlgorithms() {
    return this.coordinates[3];
  }

  public Double getTeamwork() {
    return this.coordinates[4];
  }

  public Double getFrontend() {
    return this.coordinates[5];
  }

  public Double getYearsofExperience() {
    return this.coordinates[6];
  }

  @Override
  public Double getCoordinateVal(int dim) {
    return coordinates[dim];
  }

  @Override
  public Integer getId() {
    return this.id;
  }

  @Override
  public List<Double> getCoordinates() {
    return Arrays.asList(coordinates.clone());
  }

  @Override
  public String toString() {
    StringBuilder allCoords = new StringBuilder();

    for (Double c : coordinates) {
      allCoords.append(c.toString()).append(", ");
    }

    return "Coordinate{"
        + "id=" + id
        + ", coordinates=[" + allCoords + "]"
        + '}';
  }
}
