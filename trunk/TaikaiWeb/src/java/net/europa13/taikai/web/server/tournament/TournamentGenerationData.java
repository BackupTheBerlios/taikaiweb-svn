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
package net.europa13.taikai.web.server.tournament;

import java.util.ArrayList;
import java.util.List;
import net.europa13.taikai.web.entity.Player;
import net.europa13.taikai.web.entity.Tournament;
import net.europa13.taikai.web.entity.TournamentSeed;

/**
 *
 * @author daniel
 */
public class TournamentGenerationData {

    private List<Player> checkedPlayers;
    private List<Player> uncheckedPlayers;
    
    private List<TournamentSeed> seeds;
    private Tournament tournament;
    
    
    public List<Player> getCheckedPlayers() {
        return checkedPlayers;
    }
    
    public Tournament getTournament() {
        return tournament;
    }
    
    public List<Player> getUncheckedPlayers() {
        return uncheckedPlayers;
    }
    
    public void setCheckedPlayers(List<? extends Player> checkedPlayers) {
        this.checkedPlayers = new ArrayList<Player>(checkedPlayers);
    }
    
    public void setTournament(Tournament tournament) {
        this.tournament = tournament;
    }
    
    public void setUncheckedPlayers(List<? extends Player> uncheckedPlayers) {
        this.uncheckedPlayers = new ArrayList<Player>(uncheckedPlayers);
    }
    
}
