package org.habr.sort.ter;

import org.habr.sort.bin.SortableArray;

/**
 * Экспериментальная сортировка троичным деревом.
 * Реимплементация классической восходящей сортировки.
 * TODO: Описание алгоритма: https://habr.com/ru/companies/edison/articles/509330/
 *
 * Протестирован: работает!
 *
 * Speed: O(n(log3 n))*
 * Mem: O(1)
 */
public class TernaryAccentSort
{
  private static final int ROOT = 0;
  private int LAST;

  public void sort(TernaryArray array)
  {
    LAST = array.size()-1;
    heapify(array);

    while (LAST - ROOT + 1 > 4)
    {
      array.swap(ROOT, LAST--);
      sift(array, 0);
    }
    // завершающий этап, последние 4 элемента
    array.swap(0, 3);
    array.compareAndOrder(0, 1, 2);
  }



  /**
   * Строим сортируемое дерево
   *
   * @param array сортируемый массив
   */
  private void heapify(TernaryArray array)
  {
    int node = fixTail(array);

    while ( node != ROOT )
    {
      int rt = node; // node всегда указывает на чей-то правый потомок
      int md = node-1;
      int lf = node-2;
      int pr = getParent(node); // родитель этой тройки

      // получим индекс максиального элемента из четверки
      int mx = array.max(lf, md, rt, pr);

      if ( mx != pr ) // если это не родитель
      {
        array.swap(pr, mx);
        sift(array, mx);
      }

      // к следующей тройке элементов
      node -= 3;
    }
  }

  /**
   * Выполняет корректировку концевика дерева. Обычно это 1 или 2 листа некоего родителя,
   * замыкающего массив. Ставит на место родителя максимальный из листов.
   *
   * @param array массив
   * @return номер узла, с котого можно продолжать heapify
   */
  private int fixTail(TernaryArray array)
  {
    int node = LAST;

    // обрабатываем край дерева
    if ( isLeftChild(node) ) // у последнего родителя нет среднего и правого потомка
    {
      int pr = getParent(node);
      int lf = node;

      if ( array.compare(pr, lf) < 0 )
        array.swap(pr, lf); // родитель должен быть всегда больше потомков

      node--;
    }
    else if ( isMiddleChild(node) ) // у последнего родителя нет только правого потомка
    {
      int pr = getParent(node);
      int lf = node-1;
      int md = node;

      // ставим на место родителя максимальный элемент из тройки: родитель, левый потомок и средний потомок
      array.compareAndOrder(lf, md, pr);

      node -= 2;
    }
    // на выходе node всегда будет показывать на правого потомка предпоследнего родителя
    return node;
  }


  /**
   * Выполняет просейку сортирующего поддерева дерева для сохранения инварианта:
   * родитель всегда больше своих потомков. Вызывается в случае нарушения родителем node
   * этого условия
   *
   * @param array сортируемое дерево
   * @param node элемент, нарушающий инвариант
   */
  private void sift(TernaryArray array, int node)
  {
    // сравниваем корень и два его потомка; помещаем корень куда следует
    int lf, rt, md, mx, pr = node;

    while ( hasLeftChild(pr) )
    {
      lf = getLeftChild(pr);
      md = getMiddleChild(pr);
      rt = getRightChild(pr);

      // определим, кто из этих элементов максимальный?
      if ( hasRightChild(pr) )
        mx = array.max(lf, md, rt, pr);

      else if ( hasMiddleChild(pr) )
        mx = array.max(lf, md, pr);

      else
        mx  = array.max(lf, pr);

      if ( mx != pr ) // если это не родитель
      {
        array.swap(pr, mx);
        pr = mx;
      }
      else
        break;
    }
  }





  private int getLeftChild(int node)
  {
    return node*3 + 1;
  }

  private int getMiddleChild(int node)
  {
    return node*3 + 2;
  }

  private int getRightChild(int node)
  {
    return node*3 + 3;
  }

  private int getParent(int child)
  {
    return (child-1) / 3;
  }

  private boolean isLeftChild(int node)
  {
    return (node % 3) == 1;
  }

  private boolean isMiddleChild(int node)
  {
    return (node % 3) == 2;
  }

//  private boolean isRightChild(int node)
//  {
//    return (node % 3) == 0;
//  }

  private boolean hasLeftChild(int node)
  {
    return getLeftChild(node) <= LAST;
  }

  private boolean hasMiddleChild(int node)
  {
    return getMiddleChild(node) <= LAST;
  }

  private boolean hasRightChild(int node)
  {
    return getRightChild(node) <= LAST;
  }
}
