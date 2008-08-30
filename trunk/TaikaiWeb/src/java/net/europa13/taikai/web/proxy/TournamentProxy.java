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

import java.io.Serializable;

/**
 *
 * @author daniel
 */
public class TournamentProxy implements Serializable {

    private int id = 0;
    private String name;
    private int taikaiId;
    
    public int getId() {
        return id;
    }
    
    public String getName() {
        return name;
    }
    
    /**
     * Get the value of taikai
     *
     * @return the value of taikai
     */
    public int getTaikaiId() {
        return taikaiId;
    }

    public void setId(int id) {
        this.id = id;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    /**
     * Set the value of taikai
     *
     * @param taikai new value of taikai
     */
    public void setTaikaiId(int taikaiId) {
        this.taikaiId = taikaiId;
    }
}
