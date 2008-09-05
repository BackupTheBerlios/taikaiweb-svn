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

import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceUnit;
import net.europa13.taikai.web.client.ListResult;
import net.europa13.taikai.web.client.TournamentAdminService;
import net.europa13.taikai.web.entity.Player;
import net.europa13.taikai.web.entity.Taikai;
import net.europa13.taikai.web.entity.Tournament;
import net.europa13.taikai.web.entity.TournamentSeed;
import net.europa13.taikai.web.proxy.PlayerProxy;
import net.europa13.taikai.web.proxy.TaikaiProxy;
import net.europa13.taikai.web.proxy.TournamentDetails;
import net.europa13.taikai.web.proxy.TournamentGenerationInfo;
import net.europa13.taikai.web.proxy.TournamentListKey;
import net.europa13.taikai.web.proxy.TournamentProxy;
import net.europa13.taikai.web.proxy.TournamentSeedProxy;
import net.europa13.taikai.web.server.tournament.GenerationException;
import net.europa13.taikai.web.server.tournament.Generator;
import net.europa13.taikai.web.server.tournament.TournamentGenerationData;

/**
 *
 * @author daniel
 */
public class TournamentAdminServiceImpl extends RemoteServiceServlet implements
    TournamentAdminService {

    private static Logger logger =
        Logger.getLogger(TournamentAdminServiceImpl.class.getName());
    @PersistenceUnit
    private EntityManagerFactory emf;
    private final String baseListQuery =
        "SELECT tmt.id, tmt.name " +
        "FROM Tournament tmt ";

    private List<TournamentProxy> dataToProxyList(List<Object[]> data) {
        List<TournamentProxy> proxies =
            new ArrayList<TournamentProxy>();

        for (Object[] datum : data) {
            TournamentProxy proxy = new TournamentProxy();
            proxy.setId((Integer) datum[0]);
            proxy.setName((String) datum[1]);
            proxies.add(proxy);
        }

        return proxies;
    }

    public List<Integer> getAvailableSeeds(int tournamentId) {

        EntityManager em = emf.createEntityManager();

        try {

//            if (proxy == null) {
//                System.out.println("proxy == null");
//            }
//
//            int tournamentId = proxy.getId();
            Tournament tournament = em.find(Tournament.class, tournamentId);

            List<Integer> availableSeeds = new ArrayList<Integer>();
            availableSeeds.add(1);
            availableSeeds.add(2);
            availableSeeds.add(3);
            availableSeeds.add(4);

            List<TournamentSeed> takenSeeds = tournament.getSeeds();
            for (TournamentSeed seed : takenSeeds) {
                availableSeeds.remove((Integer) seed.getSeedNumber());
            }

            return availableSeeds;
        }
        finally {
            em.close();
        }

    }

    public TournamentGenerationInfo getGenerationInfo(int tournamentId) {
        EntityManager em = emf.createEntityManager();

        try {

            TournamentGenerationInfo info = new TournamentGenerationInfo();

            Tournament tournament = em.find(Tournament.class, tournamentId);

            if (tournament == null) {
                throw new RuntimeException("Tournament " + tournamentId + " not found");
            }

            TournamentDetails tournamentDetails = new TournamentDetails();
            EntityToDetails.tournament(tournament, tournamentDetails, em);
            info.setTournament(tournamentDetails);


            List<Player> checkedPlayers = em.createNamedQuery("getCheckedPlayers").setParameter("tournament", tournament).getResultList();
            List<Player> uncheckedPlayers = em.createNamedQuery("getUncheckedPlayers").setParameter("tournament", tournament).getResultList();
            List<PlayerProxy> uncheckedPlayerProxies = new ArrayList<PlayerProxy>();
            for (Player player : uncheckedPlayers) {
//                em.merge(player);

                logger.fine("Unchecked player " + player.getId() + " has taikai " + player.getTaikai());

                PlayerProxy proxy = new PlayerProxy();
                EntityToProxy.player(player, proxy, em);
                uncheckedPlayerProxies.add(proxy);
            }

            info.setPlayerCount(checkedPlayers.size());
            info.setUncheckedPlayers(uncheckedPlayerProxies);

            TournamentGenerationData data = new TournamentGenerationData();
            data.setTournament(tournament);
            data.setCheckedPlayers(checkedPlayers);
            data.setUncheckedPlayers(uncheckedPlayers);

            try {
                Generator.getGenerationInfo(data, info);
                
                info.setGenerationPossible(true);
            }
            catch (GenerationException ex) {
                info.setGenerationPossible(false);
                logger.log(Level.FINE, "", ex);
            }

            return info;
        }
        finally {
            em.close();
        }
    }

    public TournamentDetails getTournament(int tournamentId) {

        EntityManager em = emf.createEntityManager();

        try {
            Tournament tournament = em.find(Tournament.class, tournamentId);

            TournamentDetails proxy = new TournamentDetails();
            EntityToDetails.tournament(tournament, proxy, em);

            return proxy;
        }
        finally {
            em.close();
        }

    }

    public ListResult<TournamentSeedProxy> getTournamentSeeds(TournamentProxy proxy) {

        EntityManager em = emf.createEntityManager();

        try {
            Tournament tournament = em.find(Tournament.class, proxy.getId());



            List<TournamentSeedProxy> seedProxies = new ArrayList<TournamentSeedProxy>();

            for (TournamentSeed seed : tournament.getSeeds()) {

                PlayerProxy playerProxy = new PlayerProxy();
                EntityToProxy.player(seed.getPlayer(), playerProxy, em);
                TournamentProxy tournamentProxy = new TournamentProxy();
                EntityToProxy.tournament(tournament, tournamentProxy, em);

                TournamentSeedProxy seedProxy =
                    new TournamentSeedProxy(seed.getId(), tournamentProxy, playerProxy, seed.getSeedNumber());
                seedProxies.add(seedProxy);
            }

            return new ListResult<TournamentSeedProxy>(seedProxies, 0, seedProxies.size());

        }
        finally {
            em.close();
        }

    }

//    public ListResult<TournamentProxy> getTournaments(TournamentListKey key) {
//        
//    }
    
    @SuppressWarnings(value = "unchecked")
    public ListResult<TournamentProxy> getTournaments(TournamentListKey key) {
        EntityManager em = emf.createEntityManager();

        try {
            int taikaiId = key.getOwnerId();
            Taikai taikai = em.find(Taikai.class, taikaiId);
            if (taikai == null) {
                throw new RuntimeException("no taikai");
            }

            StringBuilder query = new StringBuilder(baseListQuery);
            query.append("WHERE tmt.taikai = :owner ");


            return getTournaments(em, query.toString(), taikai);
        }
        finally {
            em.close();
        }
    }

    @SuppressWarnings(value = "unchecked")
    private ListResult<TournamentProxy> getTournaments(EntityManager em, String query, Object owner) {
        List<Object[]> data =
            em.createQuery(query + " ORDER BY tmt.name ").
            setParameter("owner", owner).getResultList();

        ListResult<TournamentProxy> result =
            new ListResult<TournamentProxy>(dataToProxyList(data), 0, data.size());
        return result;
    }

    public int storeTournament(TournamentDetails details) {

        EntityManager em = emf.createEntityManager();
        try {

            Tournament tournament = em.find(Tournament.class, details.getId());

            em.getTransaction().begin();

            if (tournament == null) {
                tournament = new Tournament();
                int taikaiId = details.getTaikai().getId();
                Taikai taikai = em.find(Taikai.class, taikaiId);
                if (taikai == null) {
                    throw new RuntimeException("no taikai");
                }
//                detailsToEntity(details, tournament);
                DetailsToEntity.tournament(details, tournament, em);

                tournament.setTaikai(taikai);
                taikai.addTournament(tournament);

                em.persist(tournament);
                em.merge(taikai);
            }
            else {
                DetailsToEntity.tournament(details, tournament, em);

                em.merge(tournament);
            }

            em.getTransaction().commit();

            return tournament.getId();
        }
        finally {
            em.close();
        }
    }
}
