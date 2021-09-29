package edu.brown.cs.student.main;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class RawUserDataTest {

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
