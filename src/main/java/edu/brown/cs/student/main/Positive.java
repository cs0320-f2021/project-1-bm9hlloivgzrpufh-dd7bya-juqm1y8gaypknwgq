package edu.brown.cs.student.main;

import java.util.Map;

public class Positive {
  private String id;
  private String trait;

  public Positive() { }

  public Positive(Map<String, String> attributes) {
    this.id = attributes.get("id");
    this.trait = attributes.get("trait");
  }

  public String getId() {
    return this.id;
  }

  public String getTrait() {
    return this.trait;
  }
}
