package org.dynamicsoft.caloriescope.videos;

public class Video {
    private String thumbnailhighurl;
    private String title;
    private String description;
    private String videoId;

    public Video(String videoId, String title, String description, String thumbnailhighurl) {
        this.videoId = videoId;
        this.title = title;
        this.description = description;
        this.thumbnailhighurl = thumbnailhighurl;
    }

    public String getvideoId() {
        return videoId;
    }

    public String getImage() {
        return thumbnailhighurl;
    }

    public void setImage(String thumbnailhighurl) {
        this.thumbnailhighurl = thumbnailhighurl;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}