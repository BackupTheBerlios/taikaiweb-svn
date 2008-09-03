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
package net.europa13.taikai.web.server;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import net.europa13.taikai.web.entity.Player;
import net.europa13.taikai.web.entity.Taikai;
import net.europa13.taikai.web.entity.Tournament;
import net.europa13.taikai.web.entity.TournamentSeed;
import net.europa13.taikai.web.proxy.PlayerDetails;
import net.europa13.taikai.web.proxy.PlayerProxy;
import net.europa13.taikai.web.proxy.TaikaiDetails;
import net.europa13.taikai.web.proxy.TournamentDetails;
import net.europa13.taikai.web.proxy.TournamentProxy;
import net.europa13.taikai.web.proxy.TournamentSeedProxy;

/**
 *
 * @author Daniel Wentzel
 */
public class EntityToDetails {

    public static void player(Player entity, PlayerDetails details, EntityManager em) {
        EntityToProxy.player(entity, details, em);
        
        List<Tournament> tournaments =
            em.createNamedQuery("getTournamentsForPlayer").setParameter("player", entity).getResultList();
        
        List<TournamentProxy> tournamentProxies = 
            new ArrayList<TournamentProxy>();            
        
        List<TournamentSeedProxy> tournamentSeedProxies =
            new ArrayList<TournamentSeedProxy>();

        
        for (Tournament tournament : tournaments) {
            TournamentProxy tp = new TournamentProxy();
            EntityToProxy.tournament(tournament, tp, em);
            tournamentProxies.add(tp);
            
            for (TournamentSeed seed : tournament.getSeeds()) {
                if (seed.getPlayer().equals(entity)) {
                    TournamentSeedProxy seedProxy =
                        new TournamentSeedProxy();
                    
                    PlayerProxy pp = new PlayerProxy();
                    EntityToProxy.player(seed.getPlayer(), pp, em);
                    
                    seedProxy.setId(seed.getId());
                    seedProxy.setSeedNumber(seed.getSeedNumber());
                    seedProxy.setPlayer(pp);
                    seedProxy.setTournament(tp);
                    
                    tournamentSeedProxies.add(seedProxy);
                }
            }
        }
        details.setTournaments(tournamentProxies);
        details.setSeeds(tournamentSeedProxies);
        
    }

    public static void taikai(Taikai entity, TaikaiDetails details, EntityManager em) {
        EntityToProxy.taikai(entity, details, em);
    }

    public static void tournament(Tournament entity, TournamentDetails details, EntityManager em) {
        EntityToProxy.tournament(entity, details, em);
        
        details.setPoolSize(entity.getPoolSize());
        details.setPreferringLargerPools(entity.isPreferringLargerPools());
        
        List<TournamentSeed> seeds = entity.getSeeds();
        List<TournamentSeedProxy> seedProxies = new ArrayList<TournamentSeedProxy>();
        
        for (TournamentSeed seed : seeds) {
            TournamentSeedProxy seedProxy = new TournamentSeedProxy();
            EntityToProxy.tournamentSeed(seed, seedProxy, em);
            seedProxies.add(seedProxy);
        }
        details.setSeeds(seedProxies);
    }
    
}
