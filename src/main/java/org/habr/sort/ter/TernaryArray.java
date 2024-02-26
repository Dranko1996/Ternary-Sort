package org.habr.sort.ter;

/**
 * Имплементация экспериментального интерфейса сортировки триплетами
 */
public class TernaryArray<T extends Comparable>
{
  private final Comparable<T> ar[];

  /**
   * Счетчик выполненных обменов указателей
   */
  public int swaps = 0;
  /**
   * Счетчик выполненных сравнений ключей
   */
  public int compares = 0;
  /**
   * Счетчик числа реальных бинарных сравнений, увеличиваемый, даже
   * если идет сравнение "новым пособом"
   */
  public int real_compares = 0;
  /**
   * Счетчик числа реальных чтений самих ключей, по которым производится сортировка
   */
  public int real_reads = 0;


  public TernaryArray(Comparable<T>[] array) {
    this.ar = array;
  }


  private static final int REVERSED = 4;
  private static final int FIRST = 0;
  private static final int MIDDLE = 1;
  private static final int LAST = 2;
  private static final int EQUALS = 0;

  /**
   * @return размер коллекции
   */
  public int size() {
    return ar.length;
  }

  /**
   * Выполняет классическое попарное сравнение.
   *
   * @param a индекс первого сравниваемого элемента
   * @param b индекс второго сравниваемого элемента
   * @return <0, если a < b;
   *          0, когда a = b;
   *       и >0, когда a > b;
   */
  public int compare(int a, int b)
  {
    compares++;
    real_compares++;
    return ar[a].compareTo((T) ar[b]);
  }

  /**
   * Выполняет сравнение сразу трех элементов коллекции
   * и возвращает число, определяющее порядок их следования
   *
   * @param a индекс первого сравниваемого элемента
   * @param b индекс второго сравниваемого элемента
   * @param c индекс третьего сравниваемого элемента
   * @return код, определяющий порядок:
   * Код состоит из 3-х бит. Первые два бита, определяют правильную позицию первого элемента 'a'
   * Третий бит задает позицию между собой элементов 'b' и 'c'
   * Если третий бит 0, то b < c, иначе - c < b
   *  0 - элементы идут в порядке a < b < c
   *  1 - элементы идут в порядке b < a < c
   *  2 - элементу идут в порядке b < c < a
   *  Таким образом функция возвращает 6 кобинаций, по которым понятно, как должны быть упорядочены
   *  выбранные элементы
   */
  public int compare(int a, int b, int c) {
    compares++;

    int c1 = ar[a].compareTo((T) ar[b]);
    real_compares++;
    int c2 = ar[b].compareTo((T) ar[c]);
    real_compares++;

    if (c1 == 0 && c2 == 0) return EQUALS; // a == b == c

    if (c1 <= 0 && c2 <= 0) return FIRST; // a < b < c      a <= b < c    a < b <= c
    if (c1 > 0 && c2 > 0) return REVERSED + LAST; // c < b < a
    if (c1 > 0 && c2 == 0) return LAST; // c < b <= a
    if (c1 == 0 && c2 < 0) return LAST; // b < c <= a
    if (c1 == 0 && c2 > 0) return REVERSED + LAST; // c < b <= a

    if (c1 < 0 && c2 > 0) // a < b, но и c < b
    {
      int c3 = ar[a].compareTo((T) ar[c]);
      real_compares++;
      if (c3 <= 0) return REVERSED + FIRST; // a <= c < b
      return REVERSED + MIDDLE; // c < a < b
    }
    if (c1 > 0 && c2 < 0) // b < a и b < c
    {
      int c3 = ar[a].compareTo((T) ar[c]);
      real_compares++;
      if (c3 <= 0) return MIDDLE; // b < a <= c
      return LAST; // b < c < a
    }

    throw new IllegalStateException();
  }

  /**
   * Выполняет классическое попарное сравнение.
   *
   * @param a индекс первого сравниваемого элемента
   * @param b индекс второго сравниваемого элемента
   * @return индекс максимального элемента
   */
    public int max(int a, int b) {
      return compare(a, b) < 0 ? b : a;
    }

  /**
   * Выполняет тройное-сравнение.
   *
   * @param a индекс первого сравниваемого элемента
   * @param b индекс второго сравниваемого элемента
   * @param c индекс третьего сравниваемого элемента
   * @return индекс максимального элемента (a, b или c)
   */
  public int max(int a, int b, int c)
  {
/*    // этот код написан в духе новой операции, и он работет; протестирован
      // но код ниже - проще, и сравнений делает меньше
    int p = compare(a, b, c);
    if (p == 2 || p == 6) return a;
    if (p == 4 || p == 5) return b;
    if (p == 0 || p == 1) return c;
    throw new AssertionError("Inalid implementation!");*/

    compares -= 1; // каждая функция compare(), вызываемая здесь, сама подсчитывает сравнения;
    // чтобы не было дублирования мы здесь вычитаем 2, и прибавляем 1.
    // Т.е. считаем, что функция max аппаратная и делает одно сравнение, а не два.
    int m = compare(a, b) < 0 ? b : a;
    return compare(m, c) < 0 ? c : m;
  }

  /**
   * Выполняет квадро-сравнение.
   *
   * @param a индекс первого сравниваемого элемента
   * @param b индекс второго сравниваемого элемента
   * @param c индекс третьего сравниваемого элемента
   * @param d индекс четвертого сравниваемого элемента
   * @return индекс максимального элемента (a, b, c или d)
   */
  public int max(int a, int b, int c, int d)
  {
    compares -= 2;
    int m1 = compare(a, b) < 0 ? b : a;
    int m2 = compare(c, d) < 0 ? d : c;
    return compare(m1, m2) < 0 ? m2 : m1;
  }

  /**
   * Выполняет обмен местами двух элементов в коллекции, заданных своими индексами
   *
   * @param a индекс первого элемента
   * @param b индекс второго элемента
   */
  public void swap(int a, int b) {
    swaps++;
    Comparable<T> t = ar[a];
    ar[a] = ar[b];
    ar[b] = t;
  }

  /**
   * Выполняет сравнение и упорядочивание сразу трех элементов в коллекции.
   * Уподядочивание идет по возрастанию. Хотя это определяется реализацией
   * функции compareTo сортируемых элементов.
   *
   * @param a индекс первого сравниваемого элемента
   * @param b индекс второго сравниваемого элемента
   * @param c индекс третьего сравниваемого элемента
   * @return результат операции compare(a, b, c)
   */
  public int compareAndOrder(int a, int b, int c) {
    int r = compare(a, b, c);
    if (r == FIRST) return r;

    if ((r & REVERSED) == 0) {
      if (r == MIDDLE)
        swap(a, b);  // b, a, c
      else // r == LAST
      {
        Comparable<T> ea = ar[a];
        Comparable<T> eb = ar[b];
        Comparable<T> ec = ar[c];
        ar[a] = eb;
        ar[b] = ec;
        ar[c] = ea;
        swaps += 2;
      }
      return r;
    } else {
      r &= 3;
      if (r == FIRST) // a, c, b
        swap(b, c);
      else if (r == MIDDLE) // c, a, b
      {
        Comparable<T> ea = ar[a];
        Comparable<T> eb = ar[b];
        Comparable<T> ec = ar[c];
        ar[a] = ec;
        ar[b] = ea;
        ar[c] = eb;
        swaps += 2;
      } else // r == LAST
      {
        swap(a, c); // c, b, a
      }
      return r | REVERSED;
    }
  }

  /**
   * Читает элемент коллекции с индексом i
   *
   * @param i индекс элемента
   * @return элемент коллекции
   */
  public Comparable<T> get(int i) {
    return ar[i];
  }

  /**
   * Переписывает элемент коллекции с индексом i
   *
   * @param i индекс элемента
   * @param e новый элемент
   */
  public void set(int i, Comparable<T> e) {
    ar[i] = e;
  }
}
