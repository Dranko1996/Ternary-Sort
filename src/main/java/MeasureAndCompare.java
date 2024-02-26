import org.habr.sort.bin.AscentSort;
import org.habr.sort.bin.SortableArray;
import org.habr.sort.ter.TernaryAccentSort;
import org.habr.sort.ter.TernaryArray;

import java.util.Arrays;
import java.util.Random;

/**
 * Класс сравнения эффективности двух алгоритмов
 * по совершаемым операциям
 */
public class MeasureAndCompare
{
  private static final int SIZE = 1+3+9; // размер массивов при измерении

    public static void main(String args[]) throws Exception
  {
    Integer[] source = makeRandomArray(SIZE);//makeSortedArray
    Integer[] source2 = Arrays.copyOf(source, source.length);

    TernaryArray<Integer> ar1 = new TernaryArray<Integer>(source);
    TernaryAccentSort ternary = new TernaryAccentSort();
    ternary.sort(ar1);
    System.out.println("Ожидаемая сложность новой сортировки: " + Math.floor(Math.log(source.length)/Math.log(3)*source.length) );
    System.out.println("Обменов: " + ar1.swaps);
    System.out.println("Классических сравнений: " + ar1.real_compares);
    System.out.println("Новых сравнений: " + ar1.compares);

    System.out.println("----------------");

    SortableArray<Integer> ar2 = new SortableArray<Integer>(source2);
    AscentSort ascent = new AscentSort();
    ascent.sort(ar2);
    System.out.println("Ожидаемая сложность старой сортировки: " + Math.floor(Math.log(source.length)/Math.log(2)*source.length) );
    System.out.println("Обменов: " + ar2.swaps);
    System.out.println("Классических сравнений: " + ar2.compares);
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
