package org.habr.sort;

import org.habr.sort.ter.TernaryArray;
import org.junit.Test;

import static org.junit.Assert.*;

public class TernaryArrayTest {

  @Test
  public void testCompareAndOrder() throws Exception
  {
    Integer[][] samples = getTriples();
    for (Integer[] e : samples)
    {
      TernaryArray<Integer> c = new TernaryArray<Integer>(e);

      c.compareAndOrder(0, 1, 2);
      //System.out.println(Arrays.toString(e));

      assertTrue(e[0].compareTo(e[1]) <= 0);
      assertTrue(e[1].compareTo(e[2]) <= 0);
    }
  }

  @Test
  public void testMax() throws Exception
  {
    Integer[][] samples = getTriples();
    for (Integer[] e : samples)
    {
      TernaryArray<Integer> c = new TernaryArray<Integer>(e);

      int m = c.max(0, 1, 2);
      //System.out.println(Arrays.toString(e));
      //System.out.println(m);

      assertTrue(e[m].compareTo(e[(m + 1) % 3]) >= 0);
      assertTrue(e[m].compareTo(e[(m + 2) % 3]) >= 0);
    }
  }



  private Integer[][] getTriples()
  {
    return new Integer[][]
            {
                    {1, 2, 3}, // < <
                    {2, 1, 3}, // > <
                    {2, 3, 1}, // < >

                    {1, 3, 2}, // < >
                    {3, 1, 2}, // > <
                    {3, 2, 1}, // > >

                    {1, 1, 1}, // = =

                    {1, 1, 2}, // = <
                    {1, 2, 1}, // < >
                    {2, 1, 1}, // > =
                    {2, 2, 1}, // = >
                    {1, 2, 2}, // < =
            };
  }
}