package com.tscloud.common.utils;

/**
 * 排序工具类
 *
 * @author lei.yang1
 * @date 2017/7/13
 */
public class RankUtil {
    public void quick(Comparable[] str) {
        if (str.length > 0) {
            quickSort(str, 0, str.length - 1);
        }
    }

    private void quickSort(Comparable[] list, int low, int high) {
        if (low < high) {
            int middle = getMiddle(list, low, high);
            quickSort(list, low, middle - 1);
            quickSort(list, middle + 1, high);
        }
    }

    private int getMiddle(Comparable[] list, int low, int high) {
        Comparable tmp = list[low];
        while (low < high) {
            while (low < high && list[high].compareTo(tmp) >= 0) {
                high--;
            }
            list[low] = list[high];
            while (low < high && list[low].compareTo(tmp) < 0) {
                low++;
            }
            list[high] = list[low];
        }
        list[low] = tmp;
        return low;
    }
}
