package org.habr.sort.bin;

import org.habr.sort.SortableElement;

/**
 * Восходящая сортировка кучей.
 * Описание алгоритма: https://habr.com/ru/companies/edison/articles/509330/
 *
 * Speed: O(n(log2 n))
 * Mem: O(1)
 */
public class AscentSort
{
  public void sort(SortableArray array)
  {
    int size = array.length();

    /*
      Формируем первоначальное сортирующее дерево
      Для этого справа-налево перебираем элементы массива
      (у которых есть потомки) и делаем для каждого из них просейку
     */
    for (int start = (size-2) >> 1; start > -1; start-- )
      sift(array, start, size - 1);

    /*
      Первый элемент массива всегда соответствует корню сортирующего дерева
      и поэтому является максимумом для неотсортированной части массива.
     */
    for (int end = size-1; end > 0; end--)
    {
      array.swap(end, 0);
      sift(array, 0, end - 1);
    }
  }

  /**
   * Восходящая просейка
   *
   * @param array сортируемый массив
   * @param start индекс начала дерева
   * @param end индекс конца дерева
   */
  private void sift(SortableArray array, int start, int end)
  {
     //  По бОльшим потомкам спускаемся до самого нижнего уровня
    int current = LeafSearch(array, start, end);

    // Поднимаемся вверх, пока не встретим узел
    // больший или равный корню поддерева
    while ( array.compare(start, current) > 0 )
      current = (current - 1) >> 1;

    // Найденный узел запоминаем,
    // в этот узел кладём корень поддерева
    Comparable temp = array.get(current);
    array.set(current, array.get(start));

    // всё что выше по ветке вплоть до корня
    // сдвигаем на один уровень вверх
    while (current > start )
    {
      current = (current - 1) >> 1;
      Comparable t = array.get(current);
      array.set(current, temp); temp = t;

      array.swaps++; // ведем статистику обменов
    }

  }

  /**
   * Спуск вниз до самого нижнего листа. Выбираем бОльших потомков
   *
   * @param array сортируемый массив
   * @param start индекс начала дерева
   * @param end индекс конца дерева
   * @return индекс потомка
   */
  private int LeafSearch(SortableArray array, int start, int end)
  {
    int current = start;

    // Спускаемся вниз, определяя какой
    // потомок (левый или правый) больше
    while ( true )
    {
      int child = current * 2 + 1; // Левый потомок

      // Прерываем цикл, если правый вне массива
      if (child + 1 > end) break;

      // Идём туда, где потомок больше
      current = ( array.compare(child+1, child) > 0 ) ? child+1 : child;
    }


    // Возможна ситуация, если левый потомок единственный
    int child = current * 2 + 1; // Левый потомок
    if ( child <= end ) current = child;

    return current;
  }
}
