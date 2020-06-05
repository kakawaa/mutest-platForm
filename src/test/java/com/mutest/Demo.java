package com.mutest;

public class Demo {
    public static void main(String[] args) throws Exception {
//        int[] array = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26, 27, 28, 29, 30, 31, 32, 33, 34, 35, 36, 37, 38, 39, 40, 41, 42, 43, 44, 45, 46, 47, 48, 49, 50, 51, 52, 53, 54, 55, 56, 57, 58, 59, 60, 61, 62, 63, 64, 65, 66, 67, 68, 69, 70};
//
//        for (int i = 0; i < 10; i++) {
//            int end = i + 60;
//            System.out.println(Arrays.toString(getSubArray(array, 60, i, end)));
//        }
//        List<String> list1 = Arrays.asList("1", "4", "7", "9");
//        List<String> list2 = Arrays.asList("7");
//
//        System.out.println(list1.containsAll(list2));
        System.out.println(getNum(4));

    }

    public static int getNum(int i) {
        int r = 0;
        if (i > 2) {
            int num = getNum(i - 1) + getNum(i - 2);
            return num;
        } else if (i == 1) {
            r = 1;
        } else if (i == 2) {
            r = 2;
        }
        return r;
    }


    public static int[] getSubArray(int[] array, int length, int start, int end) throws Exception {
        int[] subArray = new int[length];
        if (end - start > length)
            throw new Exception("子数组长度不足！");

        for (int i = start; i < end; i++) {
            subArray[i - start] = array[i];
        }
        return subArray;
    }
}