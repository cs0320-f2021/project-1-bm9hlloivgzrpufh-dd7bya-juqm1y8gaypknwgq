package edu.brown.cs.student.main;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class RawUserDataTest {

  @Test
  public void testGetID() {
    RawUserData raw1 = new RawUserData(0, "150lbs", "5\' 10\"", 21, "Aries");

    assertEquals(raw1.getID(), 0, 0.01);
  }

  @Test
  public void testGetWeight() {
    RawUserData raw1 = new RawUserData(0, "150lbs", "5\' 10\"", 21, "Aries");

    assertEquals(raw1.getWeight(), "150lbs");
  }

  @Test
  public void testGetHeight() {
    RawUserData raw1 = new RawUserData(0, "150lbs", "5\' 10\"", 21, "Aries");

    assertEquals(raw1.getHeight(), "5\' 10\"");
  }

  @Test
  public void testGetAge() {
    RawUserData raw1 = new RawUserData(0, "150lbs", "5\' 10\"", 21, "Aries");

    assertEquals(raw1.getAge(), 21, 0.01);
  }

  @Test
  public void testGetHoroscope() {
    RawUserData raw1 = new RawUserData(0, "150lbs", "5\' 10\"", 21, "Aries");

    assertEquals(raw1.getHoroscope(), "Aries");
  }

  @Test
  public void testCleanWeight() {
    RawUserData raw1 = new RawUserData(0, "150lbs", "5\' 10\"", 21, "Aries");

    assertEquals(raw1.cleanWeight(), 150, 0.01);
  }

  @Test
  public void testCleanHeight() {
    RawUserData raw1 = new RawUserData(0, "150lbs", "5\' 10\"", 21, "Aries");

    assertEquals(raw1.cleanHeight(), 70, 0.01);
  }

}
