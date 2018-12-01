/*****************************************************************************************
 * org/dynamicsoft/caloriescope/dietManager/Food.java: Food java source for CalorieScope
 *****************************************************************************************
 * Copyright (C) 2018 Karanvir Singh
 *
 * Modified by Sourav Kainth
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
 *****************************************************************************************/

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

    String getmImage() {
        return mImage;
    }

    public void setmImage(String mImage) {
        this.mImage = mImage;
    }

    String getmName() {
        return mName;
    }

    public void setmName(String mName) {
        this.mName = mName;
    }

    String getMindi() {
        return mindi;
    }

    public void setMindi(String mindi) {
        this.mindi = mindi;
    }

    String getMurl() {
        return murl;
    }

    public void setMurl(String murl) {
        this.murl = murl;
    }
}