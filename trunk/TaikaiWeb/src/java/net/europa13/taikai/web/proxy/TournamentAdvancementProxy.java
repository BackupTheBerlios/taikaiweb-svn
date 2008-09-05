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
public class TournamentAdvancementProxy implements Serializable {

    private TournamentProxy qualifyingTournament;
    private TournamentProxy advancementTournament;
    private int playerNumber;
    
    private TournamentAdvancementProxy() {
        
    }
    
    public TournamentAdvancementProxy(TournamentProxy qualifyingTournament, TournamentProxy advancementTournament, int playerNumber) {
        this.qualifyingTournament = qualifyingTournament;
        this.advancementTournament = advancementTournament;
        this.playerNumber = playerNumber;
    }
    
    public TournamentProxy getQualifyingTournament() {
        return qualifyingTournament;
    }
    
    public TournamentProxy getAdvancementTournament() {
        return advancementTournament;
    }
    
    public int getPlayerNumber() {
        return playerNumber;
    }
}
