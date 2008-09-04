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
public class TournamentDetails extends TournamentProxy {

    private List<CourtProxy> courts = new ArrayList<CourtProxy>();
    private List<TournamentSeedProxy> seeds = new ArrayList<TournamentSeedProxy>();
//    private List<PlayerProxy> seededPlayers =
//        new ArrayList<PlayerProxy>(4);

    private int poolSize;
    private boolean preferringLargerPools;
    
    public TournamentDetails() {
//        seededPlayers.add(null);
//        seededPlayers.add(null);
//        seededPlayers.add(null);
//        seededPlayers.add(null);
    }
    
    public void addCourt(CourtProxy court) {
        courts.add(court);
    }

    public int getPoolSize() {
        return poolSize;
    }
    
    public PlayerProxy getSeededPlayer(int seedNumber) {
        
        for (TournamentSeedProxy seed : seeds) {
            if (seed.getSeedNumber() == seedNumber) {
                return seed.getPlayer();
            }
        }
        
        return null;
    }

    public List<TournamentSeedProxy> getSeeds() {
        return seeds;
    }
    
    public boolean isPreferringLargerPools() {
        return preferringLargerPools;
    }
    
    public void removeCourt(CourtProxy court) {
        courts.remove(court);
    }

//    public void setPlayerSeed(int seed, PlayerProxy player) {
//        seededPlayers.set(seed, player);
//    }
    
    public void setPoolSize(int poolSize) {
        this.poolSize = poolSize;
    }
    
    public void setPreferringLargerPools(boolean preferringLargerPools) {
        this.preferringLargerPools = preferringLargerPools;
    }
    
    public void setSeeds(List<? extends TournamentSeedProxy> seeds) {
        this.seeds = new ArrayList<TournamentSeedProxy>(seeds);
    }
}
