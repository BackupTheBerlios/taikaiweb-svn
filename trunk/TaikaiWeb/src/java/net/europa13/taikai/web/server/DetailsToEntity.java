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
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.EntityManager;
import net.europa13.taikai.web.entity.Player;
import net.europa13.taikai.web.entity.Taikai;
import net.europa13.taikai.web.entity.Tournament;
import net.europa13.taikai.web.entity.TournamentAdvancement;
import net.europa13.taikai.web.entity.TournamentSeed;
import net.europa13.taikai.web.proxy.PlayerDetails;
import net.europa13.taikai.web.proxy.TaikaiDetails;
import net.europa13.taikai.web.proxy.TournamentAdvancementProxy;
import net.europa13.taikai.web.proxy.TournamentDetails;
import net.europa13.taikai.web.proxy.TournamentProxy;
import net.europa13.taikai.web.proxy.TournamentSeedProxy;

/**
 * Class for copying detailed proxies into entities.
 * @author Daniel Wentzel
 */
public class DetailsToEntity {

    private static Logger logger = Logger.getLogger(DetailsToEntity.class.getName());

    /**
     * Copies a player proxy details object into a player entity object.
     * @param details the details to copy. Id is never copied.
     * @param entity the receiving entity.
     * @param em the entity manager managing the player entity.
     */
    public static void player(PlayerDetails details, Player entity, EntityManager em) {

        entity.setAge(details.getAge());
        entity.setCheckedIn(details.isCheckedIn());
        entity.setGender(details.getGender());
        entity.setGrade(details.getGrade());
        entity.setName(details.getName());
        entity.setSurname(details.getSurname());

        //*********************************************************************
        // Added and removed tournaments
        List<Tournament> tournaments =
            em.createNamedQuery("getTournamentsForPlayer").setParameter("player", entity).getResultList();
        List<TournamentProxy> tournamentProxies = details.getTournaments();

        List<Tournament> addedTournaments = new ArrayList<Tournament>();
        List<Tournament> removedTournaments = new ArrayList<Tournament>();

        for (TournamentProxy tournamentProxy : tournamentProxies) {
            boolean added = true;

            for (Tournament tournament : tournaments) {
                if (tournament.getId().equals(tournamentProxy.getId())) {
                    added = false;
                    break;
                }
            }

            if (added) {
                Tournament tournament = em.find(Tournament.class, tournamentProxy.getId());
                addedTournaments.add(tournament);
            }
        }

        for (Tournament tournament : tournaments) {
            boolean removed = true;

            for (TournamentProxy tournamentProxy : tournamentProxies) {
                if (tournament.getId().equals(tournamentProxy.getId())) {
                    removed = false;
                    break;
                }
            }

            if (removed) {
                removedTournaments.add(tournament);
            }
        }

        // Check sanity
        if (tournaments.size() + addedTournaments.size() - removedTournaments.size() != tournamentProxies.size()) {
            throw new RuntimeException("entity tournament count couldn't be made consistent with proxy tournament count");
        }

        for (Tournament tournament : addedTournaments) {
            if (logger.isLoggable(Level.FINE)) {
                logger.fine("Adding " + entity.toString() + " to " + tournament.toString());
            }
            tournament.addPlayer(entity);
            em.merge(tournament);
        }

        for (Tournament tournament : removedTournaments) {
            if (logger.isLoggable(Level.FINE)) {
                logger.fine("Removing " + entity.toString() + " from " + tournament.toString());
            }
            tournament.removePlayer(entity);
            em.merge(tournament);
        }

        em.flush();

        //*********************************************************************
        // Added and removed seeds
//        @SuppressWarnings(value="unchecked")
        List<TournamentSeed> tournamentSeeds =
            em.createNamedQuery("getTournamentSeedsForPlayer").setParameter("player", entity).getResultList();
        List<TournamentSeedProxy> tournamentSeedProxies = details.getSeeds();

        List<TournamentSeed> addedSeeds =
            new ArrayList<TournamentSeed>();
        List<TournamentSeed> removedSeeds =
            new ArrayList<TournamentSeed>();

        for (TournamentSeedProxy seedProxy : tournamentSeedProxies) {
            boolean added = true;

            for (TournamentSeed seed : tournamentSeeds) {
                if (seed.getTournament().getId().equals(seedProxy.getTournament().getId()) &&
                    seed.getSeedNumber() == seedProxy.getSeedNumber() &&
                    seed.getPlayer().getId().equals(seedProxy.getPlayer().getId())) {
                    added = false;
                    break;
                }
            }

            if (added) {
                TournamentSeed seed = em.find(TournamentSeed.class, seedProxy.getId());
                if (seed == null) {
                    seed = new TournamentSeed();
                }
                Tournament tournament = em.find(Tournament.class, seedProxy.getTournament().getId());
                seed.setTournament(tournament);
                seed.setPlayer(entity);
                seed.setSeedNumber(seedProxy.getSeedNumber());
                addedSeeds.add(seed);
            }
        }

        for (TournamentSeed seed : tournamentSeeds) {
            boolean removed = true;

            for (TournamentSeedProxy seedProxy : tournamentSeedProxies) {
                if (seed.getTournament().getId().equals(seedProxy.getTournament().getId()) &&
                    seed.getSeedNumber() == seedProxy.getSeedNumber() &&
                    seed.getPlayer().getId().equals(seedProxy.getPlayer().getId())) {
                    removed = false;
                    break;
                }
            }

            if (removed) {
                removedSeeds.add(seed);
            }
        }

        if (tournamentSeeds.size() + addedSeeds.size() - removedSeeds.size() != tournamentSeedProxies.size()) {
            throw new RuntimeException("entity seed count couldn't be made consistent with proxy seed count");
        }

        for (TournamentSeed seed : removedSeeds) {
            if (logger.isLoggable(Level.FINE)) {
                logger.fine("Removing " + seed);
            }
            seed.getTournament().removeSeed(seed);
            em.remove(seed);
        }

        em.flush();

        for (TournamentSeed seed : addedSeeds) {
            if (logger.isLoggable(Level.FINE)) {
                logger.fine("Adding " + seed);
            }
            seed.getTournament().addSeed(seed);
        }

        em.flush();

    }

    public static void taikai(TaikaiDetails details, Taikai entity, EntityManager em) {
    }

    public static void tournament(TournamentDetails details, Tournament entity, EntityManager em) {
        entity.setName(details.getName());
        entity.setPoolSize(details.getPoolSize());
        entity.setPreferringLargerPools(details.isPreferringLargerPools());

        List<TournamentAdvancementProxy> advancementProxies = details.getAdvancements();
        List<TournamentAdvancement> advancementEntities = entity.getAdvancements();

        List<TournamentAdvancement> addedAdvancements = new ArrayList<TournamentAdvancement>();
        List<TournamentAdvancement> removedAdvancements = new ArrayList<TournamentAdvancement>();

        for (TournamentAdvancementProxy advancementProxy : advancementProxies) {
            
            boolean added = true;
            
            for (TournamentAdvancement advancement : advancementEntities) {
                if (advancement.getQualifyingTournament().getId().equals(advancementProxy.getQualifyingTournament().getId()) &&
                    advancement.getAdvancementTournament().getId().equals(advancementProxy.getAdvancementTournament().getId()) &&
                    advancement.getPlayerPosition() == advancementProxy.getPlayerNumber()) {
                    
                    added = false;
                }
            }
            
            if (added) {
                TournamentAdvancement advancement = new TournamentAdvancement();
                
                int tournamentId = advancementProxy.getAdvancementTournament().getId();
                Tournament advancementTournament = em.find(Tournament.class, tournamentId);
                
                advancement.setAdvancementTournament(advancementTournament);
                advancement.setQualifyingTournament(entity);
                advancement.setPlayerPosition(advancementProxy.getPlayerNumber());
                   
                addedAdvancements.add(advancement);
                
            }
        
        }
        
        for (TournamentAdvancement advancement : advancementEntities) {
            
            boolean removed = true;
            
            for (TournamentAdvancementProxy advancementProxy : advancementProxies) {
                if (advancement.getQualifyingTournament().getId().equals(advancementProxy.getQualifyingTournament().getId()) &&
                    advancement.getAdvancementTournament().getId().equals(advancementProxy.getAdvancementTournament().getId()) &&
                    advancement.getPlayerPosition() == advancementProxy.getPlayerNumber()) {
                    
                    removed = false;
                }
            }
            
            if (removed) {   
                removedAdvancements.add(advancement);
            }
        
        }
        
        for (TournamentAdvancement advancement : removedAdvancements) {
            if (logger.isLoggable(Level.FINE)) {
                logger.fine("Removing " + advancement);
            }
            advancement.getQualifyingTournament().removeAdvancement(advancement);
            em.remove(advancement);
        }

        em.flush();

        for (TournamentAdvancement advancement : addedAdvancements) {
            if (logger.isLoggable(Level.FINE)) {
                logger.fine("Adding " + advancement);
            }
            advancement.getQualifyingTournament().addAdvancement(advancement);
        }

        em.flush();

    }
}
