package org.habr.sort.bin;

import org.habr.sort.SortableElement;

import java.util.Arrays;

/**
 * ������-����� ��� ������������ �������, ����������� ��������� �������
 * ���������� ��������
 */
public class SortableArray<T extends Comparable>
{
  public Comparable<T> list[];

  /**
   * ������� ����������� ������� ����������
   */
  public int swaps = 0;
  /**
   * ������� ����������� ��������� ������
   */
  public int compares = 0;
  /**
   * ������� ����������� ������ ���������� �� ����������� �������
   */
  public int reads = 0;
  /**
   * ������� ����������� ������� ���������� �� ����������� �������
   */
  public int writes = 0;
  /**
   * ������� ����� �������� ������ ����� ������, �� ������� ������������ ����������
   */
  public int real_reads = 0;

  public SortableArray(Comparable<T>[] list)
  {
    this.list = list;
  }



  public int compare(int a, int b) {
    compares++; real_reads+=2;
    return list[a].compareTo((T) list[b]);
  }

  public int length() { return list.length; }

  public Comparable<T> get(int index)
  {
    reads++;
    return list[index];
  }

  public void set(int index, Comparable<T> e) {
    list[index] = e;
    writes++;
  }

  public void swap(int a, int b)
  {
    Comparable<T> t = list[a]; list[a] = list[b]; list[b] = t;
    swaps++;
  }

  @Override
  public String toString() {
    Comparable tmp[]  = new Comparable[Math.min(20, length())];
    System.arraycopy(list, 0, tmp, 0, tmp.length);
    return Arrays.toString(tmp) + "...";
  }

  public String toFullString() {
    return Arrays.toString(list);
  }
}
