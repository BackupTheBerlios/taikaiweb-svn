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
 * @author Daniel Wentzel
 */
public class TaikaiProxy implements Serializable {

    private int id = 0;
    private String name;
    private int playerCount;
    private int tournamentCount;

    public TaikaiProxy() {
        
    }
    
    public TaikaiProxy(Integer id, String name, Long playerCount, Long tournamentCount) {
        this.id = id;
        this.name = name;
        this.playerCount = playerCount.intValue();
        this.tournamentCount = tournamentCount.intValue();
    }
    
    /**
     * Get the value of id
     *
     * @return the value of id
     */
    public int getId() {
        return id;
    }
    
    /**
     * Get the value of name
     *
     * @return the value of name
     */
    public String getName() {
        return name;
    }
    
    /**
     * Get the value of playerCount
     *
     * @return the value of playerCount
     */
    public int getPlayerCount() {
        return playerCount;
    }
    
    /**
     * Get the value of tournamentCount
     *
     * @return the value of tournamentCount
     */
    public int getTournamentCount() {
        return tournamentCount;
    }

    public void setId(int id) {
        this.id = id;
    }
    
    public void setName(String name) {
        this.name = name;
    }

    public void setPlayerCount(int playerCount) {
        this.playerCount = playerCount;
    }
    
    public void setTournamentCount(int tournamentCount) {
        this.tournamentCount = tournamentCount;
    }
    
    
}
