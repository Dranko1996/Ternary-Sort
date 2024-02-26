package org.habr.sort;

import org.habr.sort.ter.TernaryAccentSort;
import org.habr.sort.ter.TernaryArray;
import org.junit.Test;

import java.util.Arrays;
import java.util.Random;

import static org.junit.Assert.*;

public class TernaryAccentSortTest
{
  /**
   * Стандартный тест корретности сортировки новым алгоритмом.
   * На входе: случайный массив.
   */
  @Test
  public void developTestSort() throws Exception
  {
    Integer[] source = makeDebugArray();
    Integer[] simple = Arrays.copyOf(source, source.length);
    TernaryArray<Integer> ternary = new TernaryArray<Integer>(source);

    // сортируем новой сортировкой троичным деревом
    TernaryAccentSort sort = new TernaryAccentSort();
    sort.sort(ternary);
    String actual = Arrays.toString(source);

    // сортируем стандартным встроенным алгоритмом
    Arrays.sort(simple);
    String expected = Arrays.toString(simple);

    // результаты должны совпадать
    assertEquals(expected, actual);
  }

  /**
   * Стандартный тест корретности сортировки новым алгоритмом.
   * На входе: случайный массив.
   */
  @Test
  public void testSort() throws Exception
  {
    Integer[] random = makeRandomArray(100000);
    Integer[] classic = Arrays.copyOf(random, random.length);
    TernaryArray<Integer> ternary = new TernaryArray<Integer>(random);

    // сортируем новой сортировкой троичным деревом
    TernaryAccentSort sort = new TernaryAccentSort();
    sort.sort(ternary);
    String actual = Arrays.toString(random);

    // сортируем стандартным встроенным алгоритмом
    Arrays.sort(classic);
    String expected = Arrays.toString(classic);

    // результаты должны совпадать
    assertEquals(expected, actual);
  }

  /**
   * Стандартный тест корретности сортировки новым алгоритмом.
   * На входе: отсортированный массив.
   */
  @Test
  public void testSortedArray() throws Exception
  {
    Integer[] array = makeSortedArray(10000);
    Integer[] array2 = Arrays.copyOf(array, array.length);
    TernaryArray<Integer> collection = new TernaryArray<Integer>(array);

    // сортируем новой сортировкой троичным деревом
    TernaryAccentSort sort = new TernaryAccentSort();
    sort.sort(collection);
    String actual = Arrays.toString(array);

    // сортируем стандартным встроенным алгоритмом
    Arrays.sort(array2);
    String expected = Arrays.toString(array2);

    // результаты должны совпадать
    assertEquals(expected, actual);
  }





  private static Integer[] makeDebugArray()
  {
    Integer[] a = new Integer[15];
    a[0] = 1;
    a[1] = 11;
    a[2] = 4;
    a[3] = 13;
    a[4] = 2;
    a[5] = 3;
    a[6] = 14;
    a[7] = 5;
    a[8] = 9;
    a[9] = 15;
    a[10] = 6;
    a[11] = 10;
    a[12] = 8;
    a[13] = 7;
    a[14] = 12;
    return a;
  }

  private static Integer[] makeRandomArray(int size)
  {
    Integer[] a = new Integer[size];
    Random r = new Random();
    for (int i = 0; i < size; i++)
    {
      a[i] = r.nextInt(2*size);
    }
    return a;
  }

  private static Integer[] makeSortedArray(int size)
  {
    Integer[] a = new Integer[size];
    for (int i = 0; i < size; i++)
    {
      a[i] = i+1;
    }
    return a;
  }
}