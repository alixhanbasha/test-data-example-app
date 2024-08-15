package com.alixhanbasha;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MultiDimensionalArray {

    private static final int GROW_BY = 10;

    private String[][] arrayOfArrays;

    private int index;

    public MultiDimensionalArray() {
        this.index = 0;
        this.arrayOfArrays = new String[1][];
    }

    private String[][] copyOf(String[][] toCopy) {
        String[][] temp = new String[toCopy.length][4];
        for (int i = 0; i < toCopy.length; i++) {
            temp[i] = toCopy[i];
        }
        return temp;
    }

    public void add(String[] array) {
        if (this.index >= this.arrayOfArrays.length) {
            String[][] tempArray = this.copyOf(this.arrayOfArrays);

            this.arrayOfArrays = new String[this.arrayOfArrays.length + GROW_BY][];

            for (int i = 0; i < tempArray.length; i++) {
                this.arrayOfArrays[i] = tempArray[i];
            }
        }
        this.arrayOfArrays[this.index++] = array;
    }

    public String[][] getArrayOfArrays() {
        // find the index of the fits null entry
        int firstNullEntry = 0;
        for (int i = 0; i < this.arrayOfArrays.length; i++) {
            if (this.arrayOfArrays[i] == null) {
                firstNullEntry = i;
                break;
            }
        }

        // create new temp array with the proper length
        // that will not have null entries
        String[][] temp = new String[firstNullEntry][];
        for (int i = 0; i < temp.length; i++) {
            // copy stuff over
            temp[i] = this.arrayOfArrays[i];
        }
        // return null-free array
        return temp;
    }

    public String[][] searchCandidates(String key) {
        // to keep track of arrays that match the given key
        List<String[]> arraysMatchingCondition = new ArrayList<>();

        // first stream the arrayOfArrays
        // get rid of all null arrays
        // then for each viable String[], search for the key in its elements
        // if the key is found, add the String[] to arraysMatchingCondition
        // NOTE: there is definitely a better way of doing this
        Arrays.stream(this.arrayOfArrays)
                .map(array -> array != null ? array : new String[]{})
                .forEach(array -> {
                    Arrays.stream(array).forEach(el -> {

                        Pattern pattern = Pattern.compile(key, Pattern.CASE_INSENSITIVE);
                        Matcher matcher = pattern.matcher(el);

                        if (matcher.find() && !arraysMatchingCondition.contains(array))
                            arraysMatchingCondition.add(array);
                    });
                });

        // create a new temp String[][]
        // it has the length of arraysMatchingCondition
        // then sequentially populate the array
        // NOTE: This is the best I can come up with at the moment.
        //       Ideally, there is a way to convert the list to a String[][]
        //       but I don't know any better
        String[][] temp = new String[arraysMatchingCondition.size()][];
        for (int i = 0; i < temp.length; i++) {
            temp[i] = arraysMatchingCondition.get(i);
        }

        return temp;
    }

    public void printAll() {
        for (int i = 0; i < this.index; i++) {
            System.out.println(Arrays.toString(this.arrayOfArrays[i]));
        }
    }

    public int length() {
        return this.index;
    }

}
