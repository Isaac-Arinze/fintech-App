package org.example;


public class Main {

    public static void main(String[] args) {

        int [] nums = {1,2,3,5,7,9,11,13,17};

        int target = 13;

        int result = linearSearch(nums, target);

        if (result != -1){
            System.out.println("index value founf at " + result);
        }
        else {
            System.out.println("Index value not found");
        }

    }

    private static int linearSearch(int[] nums, int target) {

        for (int i = 0; i < nums.length; i++) {
            if(nums[i] == target){
                return i;
            }

        }
        return -1;
    }

}