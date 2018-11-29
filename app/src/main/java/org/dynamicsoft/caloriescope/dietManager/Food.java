package org.dynamicsoft.caloriescope.dietManager;

class Food {
    private String mImage;
    private String mName;
    private String mindi;
    private String murl;

    public Food(String image, String name, String indi, String url) {
        this.mImage = image;
        this.mName = name;
        this.mindi = indi;
        this.murl = url;
    }

    public String getmImage() {
        return mImage;
    }

    public void setmImage(String mImage) {
        this.mImage = mImage;
    }

    public String getmName() {
        return mName;
    }

    public void setmName(String mName) {
        this.mName = mName;
    }

    public String getMindi() {
        return mindi;
    }

    public void setMindi(String mindi) {
        this.mindi = mindi;
    }

    public String getMurl() {
        return murl;
    }

    public void setMurl(String murl) {
        this.murl = murl;
    }
}