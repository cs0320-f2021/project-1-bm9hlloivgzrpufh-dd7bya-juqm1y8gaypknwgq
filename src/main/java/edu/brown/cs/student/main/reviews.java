package edu.brown.cs.student.main;

import java.util.Date;

public class reviews {
  private String review_text;
  private String review_summary;
  private Date review_date; //Date(int year, int month, int date)
  private int id;

  public reviews(String review_text, String review_summary, Date review_date, int id){
    this.review_text = review_text;
    this.review_summary = review_summary;
    this.review_date = review_date;
    this.id = id;
  }
}
