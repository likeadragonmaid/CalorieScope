package org.dynamicsoft.caloriescope.dietManager;

public class Food {
    private String mImage;
    private String mName;
    private String mindi;

    public Food(String image, String name, String indi) {
        mImage = image;
        mName = name;
        mindi = indi;
    }

    public String getmImage() {
        return mImage;
    }

    public String getmName() {
        return mName;
    }

    public String getMindi() {
        return mindi;
    }
}