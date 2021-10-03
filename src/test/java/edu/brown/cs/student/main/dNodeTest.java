package edu.brown.cs.student.main;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class dNodeTest {

  @Test
  public void testSetDimension() {
    dNode node1 = new dNode(100, 150, 60, 21, "Aries");
    node1.setDimension(2);

    assertEquals(node1.getDimension(), 2, 0.01);
  }

  @Test
  public void testSetLeft() {
    dNode node1 = new dNode(100, 150, 60, 21, "Aries");
    dNode node2 = new dNode(600, 10, 10, 10, "Aries");

    node1.setLeft(node2);

    assertEquals(node1.getLeft(), node2);
  }

  @Test
  public void testSetRight() {
    dNode node1 = new dNode(100, 150, 60, 21, "Aries");
    dNode node2 = new dNode(600, 10, 10, 10, "Aries");

    node1.setRight(node2);

    assertEquals(node1.getRight(), node2);
  }

  @Test
  public void testGetID() {
    dNode node1 = new dNode(100, 150, 60, 21, "Aries");

    assertEquals(node1.getID(), 100, 0.01);
  }

  @Test
  public void testGetWeight() {
    dNode node1 = new dNode(100, 150, 60, 21, "Aries");


    assertEquals(node1.getWeight(), 150, 0.01);
  }

  @Test
  public void testGetHeight() {
    dNode node1 = new dNode(100, 150, 60, 21, "Aries");

    assertEquals(node1.getHeight(), 60, 0.01);
  }

  @Test
  public void testGetAge() {
    dNode node1 = new dNode(100, 150, 60, 21, "Aries");

    assertEquals(node1.getAge(), 21, 0.01);
  }

  @Test
  public void testGetHoroscope() {
    dNode node1 = new dNode(100, 150, 60, 21, "Aries");

    assertEquals(node1.getHoroscope(), "Aries");
  }

  @Test
  public void testCheckDim() {
    dNode node1 = new dNode(100, 150, 60, 21, "Aries");
    node1.setDimension(2);


    assertEquals(node1.checkDim(50), false);
    assertEquals(node1.checkDim(10), true);
    assertEquals(node1.checkDim(21), false);
  }

  @Test
  public void checkRecur() {
    dNode node1 = new dNode(100, 150, 60, 21, "Aries");
    node1.setDimension(0);

    assertEquals(node1.checkRecur(120), -30, 0.01);
    assertEquals(node1.checkRecur(200), 50, 0.01);
    assertEquals(node1.checkRecur(150), 0, 0.01);

  }

  @Test
  public void testCalcDist() {
    dNode node1 = new dNode(100, 150, 60, 21, "Aries");

    assertEquals(node1.calcDistance(147, 60, 17), 5, 0.01);
  }

}
