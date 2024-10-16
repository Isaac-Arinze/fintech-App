package org.example;

public class BinarySearch {

    public static void main(String[] args) {

        int[] numSearch = {1, 2, 3, 4, 6, 8, 9};

        int target = 9;

        int result = binarySearch(numSearch, target);

        if (result != -1) {
            System.out.println("Index value found: " + result);
        } else {
            System.out.println("Index value not found");
        }
    }

    private static int binarySearch(int[] numSearch, int target) {

        //5,7,9,12,14, 16, 19

        int left = 0;

        int right = numSearch.length - 1;

        while (left <= right) {

            int mid = (left + right) / 2;

            if (numSearch[mid] == target) {
                return mid;
            } else if (numSearch[mid] < target) {
                left = mid + 1;
            } else
                right = mid - 1;

        }
        return -1;

    }
}
