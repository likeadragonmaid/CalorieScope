/**************************************************************************************
 * org/dynamicsoft/caloriescope/videos/Video.java: Video java source for CalorieScope
 **************************************************************************************
 * Copyright (C) 2018 Karanvir Singh
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301, USA.
 **************************************************************************************/

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