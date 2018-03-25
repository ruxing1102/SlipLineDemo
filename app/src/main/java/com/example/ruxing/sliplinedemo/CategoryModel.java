package com.example.ruxing.sliplinedemo;

/**
 * Created by ruxing on 2018/3/25.
 */

public class CategoryModel {
    private String name;
    private boolean isShowLine=false;
    private boolean isSelected=false;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isShowLine() {
        return isShowLine;
    }

    public void setShowLine(boolean showLine) {
        isShowLine = showLine;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }
}
