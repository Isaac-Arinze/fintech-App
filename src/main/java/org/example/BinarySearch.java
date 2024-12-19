package org.example;

public class BinarySearch {

    public static void main(String[] args) {

        int[] numSearch = {1, 2, 3, 4, 6, 8, 9};

        int target = 9;

        int result = binarySearch(numSearch, target);
        int result1 = linearSearch(numSearch, target);

        if (result != -1) {
            System.out.println("Index value found: " + result);
        } else {
            System.out.println("Index value not found");
        }
    }
    public static int linearSearch (int [] numSearch, int target){

        int steps = 0;
        for (int i = 0; i < numSearch.length; i++) {
            steps++;
            if (numSearch[i] == target){
                System.out.println("Steps taken by linear : " + steps);
                return i;

            }

        }
        System.out.println("Steps taken by linear : " + steps);
        return -1;
    }


    public static int binarySearch(int[] numSearch, int target) {


        //5,7,9,12,14, 16, 19

        int left = 0;

        int right = numSearch.length - 1;

        int steps = 0;
        while (left <= right) {
            steps++;

            int mid = (left + right) / 2;

            if (numSearch[mid] == target) {
                System.out.println("Steps taken by binary : " + steps);
                return mid;
            } else if (numSearch[mid] < target) {
                left = mid + 1;
            } else
                right = mid - 1;

        }
        System.out.println("Steps taken by bianry : " + steps);
        return -1;

    }
}
