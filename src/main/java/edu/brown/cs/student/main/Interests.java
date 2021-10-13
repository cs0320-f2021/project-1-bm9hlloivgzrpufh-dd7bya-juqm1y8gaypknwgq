package edu.brown.cs.student.main;

import java.util.Map;

public class Interests {
  private String id;
  private String interest;

  public Interests() { }

  public Interests(Map<String, String> attributes) {
    this.id = attributes.get("id");
    this.interest = attributes.get("interest");
  }

  public String getId() {
    return this.id;
  }

  public String getInterest() {
    return this.interest;
  }

}
