package com.zhaojh.aidemo;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CodingTest {

    /**
     * This method finds the turning point in an array that's sorted in ascending order and
     * then descending order (unimodal array). It identifies the peak element where the
     * transition happens.
     * @param arr the input array that is partially sorted as described
     * @return the index of the turning point
     */
    public int findingTurningPoint(int[] arr) {
        if (arr == null || arr.length < 3) {
            throw new IllegalArgumentException("Array must have at least 3 elements.");
        }

        int low = 0;
        int high = arr.length - 1;

        // Using a binary search approach to find the peak element
        while (low < high) {
            int mid = low + (high - low) / 2;

            // Check if mid is the turning point
            if (arr[mid] > arr[mid - 1] && arr[mid] > arr[mid + 1]) {
                return mid; // The peak index
            } else if (arr[mid -1] < arr[mid] && arr[mid] < arr[mid + 1]) {
                low = mid;
            } else if (arr[mid -1] > arr[mid] && arr[mid] > arr[mid + 1]) {
                high = mid;
            }
        }

        return low; // At this point, low or high should point to the peak
    }

    @Test
    public void testFindingTurningPoint() {
        int[] array = new int[] {1, 5,7,9, 12, 16, 23, 50, 48, 33, 21, 18, 2, 1};

        int turningPoint = findingTurningPoint(array);
        assertEquals(50, array[turningPoint]);
    }
}
