package edu.brown.cs.student.main;

import java.util.Map;

public class Skills {
  private String id;
  private String name;
  private String commenting;
  private String testing;
  private String OOP;
  private String algorithms;
  private String teamwork;
  private String frontend;

  public Skills() { }

  public Skills(Map<String, String> attributes) {
    this.id = attributes.get("id");
    this.name = attributes.get("name");
    this.commenting = attributes.get("commenting");
    this.testing = attributes.get("testing");
    this.OOP = attributes.get("OOP");
    this.algorithms = attributes.get("algorithms");
    this.teamwork = attributes.get("teamwork");
    this.frontend = attributes.get("frontend");
  }

  public String getId() {
    return this.id;
  }

  public String getName() {
    return this.name;
  }

  public String getCommenting() {
    return this.commenting;
  }

  public String getTesting() {
    return this.testing;
  }

  public String getOOP() {
    return this.OOP;
  }

  public String getAlgorithms() {
    return this.algorithms;
  }

  public String getTeamwork() {
    return this.teamwork;
  }

  public String getFrontend() {
    return this.frontend;
  }
}
