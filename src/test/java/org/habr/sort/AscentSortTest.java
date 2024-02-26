package org.habr.sort;

import org.habr.sort.bin.AscentSort;
import org.habr.sort.bin.SortableArray;
import org.habr.sort.ter.TernaryArray;
import org.junit.Test;

import java.util.Arrays;
import java.util.Random;

import static org.junit.Assert.*;

public class AscentSortTest {

  /**
   * Стандартный тест корретности сортировки старым алгоритмом.
   * На входе: случайный массив.
   */
  @Test
  public void testSort() throws Exception
  {
    Integer[] sourceArray = makeRandomArray(10000);

    Integer[] simpleArray = Arrays.copyOf(sourceArray, sourceArray.length);
    SortableArray<Integer> sortableArray = new SortableArray<Integer>(sourceArray);

    // сортируем новой сортировкой троичным деревом
    AscentSort sort = new AscentSort();
    sort.sort(sortableArray);
    String actual = sortableArray.toFullString();

    // сортируем стандартным встроенным алгоритмом
    Arrays.sort(simpleArray);
    String expected = Arrays.toString(simpleArray);

    // результаты должны совпадать
    assertEquals(expected, actual);
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
