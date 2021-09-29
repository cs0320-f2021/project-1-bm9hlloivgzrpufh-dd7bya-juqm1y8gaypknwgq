package edu.brown.cs.student.main;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class MathBotTest {

  @Test
  public void testAddition() {
    MathBot matherator9000 = new MathBot();
    double output = matherator9000.add(10.5, 3);
    assertEquals(13.5, output, 0.01);
  }

  @Test
  public void testLargerNumbers() {
    MathBot matherator9001 = new MathBot();
    double output = matherator9001.add(100000, 200303);
    assertEquals(300303, output, 0.01);
  }

  @Test
  public void testSubtraction() {
    MathBot matherator9002 = new MathBot();
    double output = matherator9002.subtract(18, 17);
    assertEquals(1, output, 0.01);
  }

  // todo: add more unit tests of your own

  @Test
  public void testMixed() {
    MathBot matherator9003 = new MathBot();
    double output = matherator9003.add(18.5, 17);
    assertEquals(35.5, output, 0.01);
  }

  @Test
  public void testAddDoubles() {
    MathBot matherator9004 = new MathBot();
    double output = matherator9004.add(18.0, 17.0);
    assertEquals(35, output, 0.01);
  }

  @Test
  public void testSubtractDoubles() {
    MathBot matherator9005 = new MathBot();
    double output = matherator9005.subtract(18.0, 17.0);
    assertEquals(1.0, output, 0.01);
  }

  @Test
  public void testDecimals() {
    MathBot matherator9006 = new MathBot();
    double output = matherator9006.add(0.06, 0.08);
    assertEquals(0.14, output, 0.01);
  }

  @Test
  public void testMixedDecimals() {
    MathBot matherator9007 = new MathBot();
    double output = matherator9007.add(0.06, 0.0008);
    assertEquals(0.0608, output, 0.01);
  }
}
