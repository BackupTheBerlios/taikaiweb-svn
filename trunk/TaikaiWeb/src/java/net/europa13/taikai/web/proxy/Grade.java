/*
 * TaikaiWeb - a web application for managing and running kendo tournaments.
 * Copyright (C) 2008  Daniel Wentzel & Jonatan Wentzel
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package net.europa13.taikai.web.proxy;

/**
 *
 * @author daniel
 */
public enum Grade {

    Kyu10,
    Kyu9,
    Kyu8,
    Kyu7,
    Kyu6,
    Kyu5,
    Kyu4,
    Kyu3,
    Kyu2,
    Kyu1,
    Dan1,
    Dan2,
    Dan3,
    Dan4,
    Dan5,
    Dan6,
    Dan7,
    Dan8,
    Dan9,
    Dan10;

    @Override
    public String toString() {

        switch (this) {
            case Kyu10:
                return "10 Kyu";
            case Kyu9:
                return "9 Kyu";
            case Kyu8:
                return "8 Kyu";
            case Kyu7:
                return "7 Kyu";
            case Kyu6:
                return "6 Kyu";
            case Kyu5:
                return "5 Kyu";
            case Kyu4:
                return "4 Kyu";
            case Kyu3:
                return "3 Kyu";
            case Kyu2:
                return "2 Kyu";
            case Kyu1:
                return "1 Kyu";
            case Dan1:
                return "1 Dan";
            case Dan2:
                return "2 Dan";
            case Dan3:
                return "3 Dan";
            case Dan4:
                return "4 Dan";
            case Dan5:
                return "5 Dan";
            case Dan6:
                return "6 Dan";
            case Dan7:
                return "7 Dan";
            case Dan8:
                return "8 Dan";
            case Dan9:
                return "9 Dan";
            case Dan10:
                return "10 Dan";
            default:
                return "Error";
        }
    }
}
