import org.habr.sort.bin.AscentSort;
import org.habr.sort.bin.SortableArray;
import org.habr.sort.ter.TernaryAccentSort;
import org.habr.sort.ter.TernaryArray;

import java.util.Arrays;
import java.util.Random;

/**
 * Класс измерения времени работы алгоритмов
 */
public class MeasureTimes
{
  private static final int LOOPS = 100; // сколько делать повторов сортировки при измерении
  private static final int SIZE = 100000; // размер массивов при измерении
  private static final float SCALE = 1e9f; // кол-во наносекунд в секунде


  public static void main(String[] args) throws Exception
  {
    Integer[] source = makeRandomArray(SIZE);
    Integer[] source2 = Arrays.copyOf(source, source.length);
    TernaryArray<Integer> ternary = new TernaryArray<Integer>(source);
    TernaryAccentSort newSort = new TernaryAccentSort();
    SortableArray<Integer> list = new SortableArray<Integer>(source2);
    AscentSort oldSort = new AscentSort();

    {
      long t2 = System.nanoTime();
      for (int i = 0; i < LOOPS; i++) {
        oldSort.sort(list);
      }
      long t1 = System.nanoTime();
      System.out.println("Время сортировки старым алгоритмом: " + (t1 - t2)/SCALE);
    }

    {
      long t2 = System.nanoTime();
      for (int i = 0; i < LOOPS; i++) {
        newSort.sort(ternary);
      }
      long t1 = System.nanoTime();
      System.out.println("Время сортировки новым алгоритмом: " + (t1 - t2)/SCALE);
    }
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
}
