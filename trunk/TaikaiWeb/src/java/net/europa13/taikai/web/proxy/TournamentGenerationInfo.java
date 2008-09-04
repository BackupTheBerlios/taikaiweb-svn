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
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Daniel Wentzel
 */
public class TournamentGenerationInfo implements Serializable {
    
    private TournamentDetails tournament;
    private ArrayList<PlayerProxy> uncheckedPlayers;
    
//    private int poolCount;
    private int[] poolCountsBySize;
    private int playerCount;
    private boolean generationPossible = false;
    
    public TournamentGenerationInfo() {
        poolCountsBySize = new int[3];
    }
    
    public int getPoolCount() {
        int poolCount = 0;
        
        for (int i = 0; i < 3; ++i) {
            poolCount += poolCountsBySize[i];
        }
        return poolCount;
    }
    
    public int getPlayerCount() {
        return playerCount;
    }
    
    public int getPoolCount(int poolSize) {
        if (poolSize < 2 || poolSize > 4) {
            return 0;
        }
        else {
            return poolCountsBySize[poolSize - 2];
        }
    }
    
    public TournamentDetails getTournament() {
        return tournament;
    }

    public List<PlayerProxy> getUncheckedPlayers() {
        return uncheckedPlayers;
    }
    
    public void setPlayerCount(int playerCount) {
        this.playerCount = playerCount;
    }
    
    public boolean isGenerationPossible() {
        return generationPossible;
    }
    
    public void setGenerationPossible(boolean generationPossible) {
        this.generationPossible = generationPossible;
    }
    
    public void setPoolCount(int poolSize, int poolCount) {
        if (poolSize < 2 || poolSize > 4) {
            return;
        }
        else {
            poolCountsBySize[poolSize - 2] = poolCount;
        }
    }
    
    public void setTournament(TournamentDetails tournament) {
        this.tournament = tournament;
    }

    public void setUncheckedPlayers(List<? extends PlayerProxy> uncheckedPlayers) {
        this.uncheckedPlayers = new ArrayList<PlayerProxy>(uncheckedPlayers);
    }
}
