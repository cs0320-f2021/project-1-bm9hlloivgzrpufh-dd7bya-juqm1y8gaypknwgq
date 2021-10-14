package edu.brown.cs.student.main;

import edu.brown.cs.student.recommender.Item;

import java.util.LinkedList;
import java.util.List;

public class StudentQual implements Item, StudentAttributes{
  private final Integer id;
  private String name;
  private final List<String> meeting = new LinkedList<>();
  private final List<String> grade = new LinkedList<>();
  private final List<String> horoscope = new LinkedList<>();
  private final List<String> meetingTimes = new LinkedList<>();
  private final List<String> preferredLanguage = new LinkedList<>();
  private final List<String> marginalizedGroup = new LinkedList<>();
  private final List<String> preferredGroup = new LinkedList<>();
  private final List<String> interests = new LinkedList<>();
  private final List<String> traits = new LinkedList<>();


  public StudentQual(Integer id) {
    this.id = id;
    this.name = "";
  }

  public StudentQual(Integer id, String name, String meeting, String grade, String horoscope,
                     String meetingTimes, String preferredLanguage, String marginalizedGroup,
                     String preferredGroup, String interests, String traits) {
    this.id = id;
    this.name = name;
    this.meeting.add(meeting);
    this.grade.add(grade);
    this.horoscope.add(horoscope);
    this.meetingTimes.add(meetingTimes);
    this.preferredLanguage.add(preferredLanguage);
    this.marginalizedGroup.add(marginalizedGroup);
    this.preferredGroup.add(preferredGroup);
    this.interests.add(interests);
    this.traits.add(traits);
  }

  public void setName(String name) {
    this.name = name;
  }

  public void addMeeting(String meeting) {
    this.meeting.add(meeting);
  }

  public void addGrade(String grade) {
    this.grade.add(grade);
  }

  public void addHoroscope(String horoscope) {
    this.horoscope.add(horoscope);
  }

  public void addMeetingTimes(String meetingTimes) {
    this.meetingTimes.add(meetingTimes);
  }

  public void addPreferredLanguage(String preferredLanguage) {
    this.preferredLanguage.add(preferredLanguage);
  }

  public void addMarginalizedGroup(String marginalizedGroup) {
    this.marginalizedGroup.add(marginalizedGroup);
  }

  public void addPreferredGroup(String preferredGroup) {
    this.preferredGroup.add(preferredGroup);
  }

  public void addInterests(String interests) {
    this.interests.add(interests);
  }

  public void addTraits(String traits) {
    this.traits.add(traits);
  }

  @Override
  public List<String> getVectorRepresentation() {
    List<String> outputList = new LinkedList<String>();
    outputList.addAll(this.meeting);
    outputList.addAll(this.grade);
    outputList.addAll(this.horoscope);
    outputList.addAll(this.meetingTimes);
    outputList.addAll(this.preferredLanguage);
    outputList.addAll(this.marginalizedGroup);
    outputList.addAll(this.preferredGroup);
    outputList.addAll(this.interests);
    outputList.addAll(this.traits);

    return outputList;
  }

  @Override
  public String getId() {
    return this.id.toString();
  }
}
