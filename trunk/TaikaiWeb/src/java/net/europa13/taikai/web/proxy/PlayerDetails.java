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

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author daniel
 */
public class PlayerDetails extends PlayerProxy {

    private List<TournamentSeedProxy> seeds;
    private List<TournamentProxy> tournaments;

    public int getPlayerSeedInTournament(TournamentProxy tournament) {
        
        int seedNumber = -1;
        
        for (TournamentSeedProxy seed : seeds) {
            if (seed.getTournament().equals(tournament)) {
                seedNumber = seed.getSeedNumber();
            }
        }
        
        return seedNumber;
        
    }
    
    public List<TournamentSeedProxy> getSeeds() {
        return seeds;
    }
    
    public List<TournamentProxy> getTournaments() {
        return tournaments;
    }

    public void setSeeds(List<TournamentSeedProxy> seeds) {
        this.seeds = seeds;
    }
    
    public void setTournaments(List<TournamentProxy> tournaments) {
        this.tournaments = new ArrayList<TournamentProxy>(tournaments);
    }

}
