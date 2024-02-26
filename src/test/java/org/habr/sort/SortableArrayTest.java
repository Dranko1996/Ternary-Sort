package org.habr.sort;

import org.habr.sort.bin.SortableArray;
import org.junit.Test;

import java.util.Random;

import static org.junit.Assert.assertEquals;

public class SortableArrayTest
{

  @Test
  public void testCompare() throws Exception
  {
    SortableArray a = makeArray();
    a.compare(1, 2);
    a.compare(5, 6);

    assertEquals(a.compares, 2);
    assertEquals(a.reads, 0);
    assertEquals(a.writes, 0);
    assertEquals(a.swaps, 0);
    assertEquals(a.real_reads, 4);
  }

  @Test
  public void testGet() throws Exception
  {
    SortableArray<Integer> a = makeArray();
    a.get(0);
    a.get(1);

    assertEquals(a.compares, 0);
    assertEquals(a.reads, 2);
    assertEquals(a.writes, 0);
    assertEquals(a.swaps, 0);
    assertEquals(a.real_reads, 0);
  }

  @Test
  public void testSet() throws Exception {
    SortableArray<Integer> a = makeArray();
    a.set(0, a.get(1));
    a.set(1, a.get(2));

    assertEquals(a.compares, 0);
    assertEquals(a.reads, 2);
    assertEquals(a.writes, 2);
    assertEquals(a.swaps, 0);
    assertEquals(a.real_reads, 0);
  }

  @Test
  public void testSwap() throws Exception {
    SortableArray<Integer> a = makeArray();
    a.swap(3, 4);
    a.swap(5, 6);

    assertEquals(a.compares, 0);
    assertEquals(a.reads, 0);
    assertEquals(a.writes, 0);
    assertEquals(a.swaps, 2);
    assertEquals(a.real_reads, 0);
  }




  private SortableArray<Integer> makeArray()
  {
    return new SortableArray<Integer>(makeRandomArray(100));
  }

  private Integer[] makeRandomArray(int size)
  {
    Integer[] a = new Integer[size];
    Random r = new Random();
    for (int i = 0; i < size; i++)
    {
      a[i] = r.nextInt(2*size);
    }
    return a;
  }

}