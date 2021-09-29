package edu.brown.cs.student.main;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class StarsTest {

  @Test
  public void testDistance() {
    Star star00 = new Star(00, "teststar0", 0, 0, 0);
    double dist1 = star00.distance(3, 4 , 0);
    double dist2 = star00.distance(.1, 0 , 0);
    double dist3 = star00.distance(-.1, 0 , 0);


    assertEquals(5, dist1, 0.01);
    assertEquals(.1, dist2, 0.01);
    assertEquals(.1, dist3, 0.01);
  }

//  @Test
//  public void testGetName() {
//    Star star00 = new Star(123, "billybob", -9123, 1.2342, 5);
//    String name = star00.getName();
//
//    assertEquals("billybob", name);
//  }

  @Test
  public void testGetX() {
    Star star00 = new Star(123, "billybob", -9123, 1.2342, 5);
    double x = star00.getX();

    assertEquals(-9123, x, 0.01);
  }

  @Test
  public void testGetY() {
    Star star00 = new Star(123, "billybob", -9123, 1.2342, 5);
    double y = star00.getY();

    assertEquals(1.2342, y,0.01);
  }

  @Test
  public void testGetZ() {
    Star star00 = new Star(123, "billybob", -9123, 1.2342, 5);
    double z = star00.getZ();

    assertEquals(5, z, 0.01);
  }
}
