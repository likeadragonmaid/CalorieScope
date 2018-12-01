/******************************************************************************************************************
 * org/dynamicsoft/caloriescope/accelerometerCounter/StepListener.java: StepListener java source for CalorieScope
 ******************************************************************************************************************
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
 ******************************************************************************************************************
 * Unmodified source can obtained at http://www.gadgetsaint.com/android/create-pedometer-step-counter-android/
 */

package org.dynamicsoft.caloriescope.accelerometerCounter;

// Will listen to step alerts
public interface StepListener {
    void step(long timeNs);
}