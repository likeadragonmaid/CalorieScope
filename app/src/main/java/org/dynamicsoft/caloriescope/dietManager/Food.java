package org.dynamicsoft.caloriescope.dietManager;

public class Food {
    private String mImage;
    private String mName;
    private String mindi;
    private String murl;

    public Food(String image, String name, String indi,String url) {
        mImage = image;
        mName = name;
        mindi = indi;
        murl=url;
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

    public String getMurl()
    {
        return murl;
    }
}