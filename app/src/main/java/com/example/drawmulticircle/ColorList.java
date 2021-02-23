package com.example.drawmulticircle;

import java.util.ArrayList;

public class ColorList {
    private static ArrayList<int[]> colorList = new ArrayList<>();

    public static ArrayList<int[]> getColorList(){
        colorList.add(new int[]{128, 255, 0, 0});
        colorList.add(new int[]{128, 0, 255, 0});
        colorList.add(new int[]{128, 255, 255, 0});
        colorList.add(new int[]{128, 0, 0, 255});
        colorList.add(new int[]{128, 255, 0, 255});
        colorList.add(new int[]{128, 0, 255, 255});
        colorList.add(new int[]{128, 255, 255, 255});

        colorList.add(new int[]{128, 128, 0, 0});
        colorList.add(new int[]{128, 0, 128, 0});
        colorList.add(new int[]{128, 128, 128, 0});
        colorList.add(new int[]{128, 0, 0, 128});
        colorList.add(new int[]{128, 128, 0, 128});
        colorList.add(new int[]{128, 0, 128, 128});
        colorList.add(new int[]{128, 128, 128, 128});

        return colorList;
    }

}
