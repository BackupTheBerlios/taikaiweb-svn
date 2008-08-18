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
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceUnit;
import net.europa13.taikai.web.client.TaikaiAdminService;
import net.europa13.taikai.web.entity.Player;
import net.europa13.taikai.web.entity.Taikai;
import net.europa13.taikai.web.entity.Tournament;
import net.europa13.taikai.web.proxy.PlayerProxy;
import net.europa13.taikai.web.proxy.TaikaiProxy;
import net.europa13.taikai.web.proxy.TournamentProxy;

/**
 *
 * @author Daniel Wentzel
 */
public class TaikaiAdminServiceImpl extends RemoteServiceServlet implements
    TaikaiAdminService {

    @PersistenceUnit
    EntityManagerFactory emf;

    public void createTaikai(TaikaiProxy proxy) {//        EntityManager em = emf.createEntityManager();
//
//        try {
//            Taikai taikai = new Taikai();
//            taikai.setName(proxy.getName());
//
//            em.getTransaction().begin();
//            em.persist(taikai);
//            em.getTransaction().commit();
//        }
//        finally {
//            em.close();
//        }
    }

    public TaikaiProxy getTaikai(int id) {

        EntityManager em = emf.createEntityManager();

        try {
            TaikaiProxy proxy = (TaikaiProxy) em.createQuery(
                "SELECT " +
                "NEW net.europa13.taikai.web.proxy.TaikaiProxy(" +
                "t.id, t.name. COUNT(DISTINCT p), COUNT(DISTINCT tmt)) " +
                "FROM Taikai t LEFT JOIN t.players p LEFT JOIN t.tournaments tmt " +
                "WHERE t.id = :taikaiId GROUP BY t.id, t.name").setParameter("taikaiId", id).getSingleResult();

            return proxy;
        }
        finally {
            em.close();
        }
    }

    @SuppressWarnings(value = "unchecked")
    public List<TaikaiProxy> getTaikais() {

        EntityManager em = emf.createEntityManager();

        try {
            List<TaikaiProxy> proxies = new ArrayList<TaikaiProxy>();

            List<TaikaiProxy> taikaiData = em.createQuery(
                "SELECT NEW net.europa13.taikai.web.proxy.TaikaiProxy(" +
                "t.id, t.name, COUNT(DISTINCT p), COUNT(DISTINCT tmt)) " +
                "FROM Taikai t LEFT JOIN t.players p LEFT JOIN t.tournaments tmt " +
                "GROUP BY t.id, t.name").getResultList();

            proxies.addAll(taikaiData);
            return proxies;

        }
        finally {
            em.close();
        }

    }

    @SuppressWarnings(value="unchecked")
    public List<TournamentProxy> getTournaments(TaikaiProxy taikaiProxy) {
        EntityManager em = emf.createEntityManager();

        try {
            Taikai taikai = em.find(Taikai.class, taikaiProxy.getId());
            if (taikai == null) {
                throw new RuntimeException("no taikai");
            }

            List<Object[]> tournamentData = em.createQuery(
                "SELECT t.id, t.name " +
                "FROM Tournament t" +
                "WHERE t.taikai = :taikai").setParameter("taikai", taikai).getResultList();


            List<TournamentProxy> proxies = new ArrayList<TournamentProxy>();

            for (Object[] data : tournamentData) {
                TournamentProxy proxy = new TournamentProxy();
                proxy.setId((Integer) data[0]);
                proxy.setName((String) data[1]);
                proxies.add(proxy);
            }

            return proxies;
        }
        finally {
            em.close();
        }
    }

    @SuppressWarnings(value="unchecked")
    public List<PlayerProxy> getPlayers(TaikaiProxy taikaiProxy) {
        EntityManager em = emf.createEntityManager();

        try {
            Taikai taikai = em.find(Taikai.class, taikaiProxy.getId());
            if (taikai == null) {
                throw new RuntimeException("no taikai");
            }

            List<Object[]> tournamentData = em.createQuery(
                "SELECT p.id, p.name " +
                "FROM Player p" +
                "WHERE p.taikai = :taikai").setParameter("taikai", taikai).getResultList();


            List<PlayerProxy> proxies = new ArrayList<PlayerProxy>();

            for (Object[] data : tournamentData) {
                PlayerProxy proxy = new PlayerProxy();
                proxy.setId((Integer) data[0]);
                proxy.setName((String) data[1]);
                proxies.add(proxy);
            }

            return proxies;

        }
        finally {
            em.close();
        }

    }

    @SuppressWarnings(value="unchecked")
    public List<PlayerProxy> getPlayers(TournamentProxy tournamentProxy) {
        EntityManager em = emf.createEntityManager();

        try {
            Tournament tournament = em.find(Tournament.class, tournamentProxy.getId());
            if (tournament == null) {
                throw new RuntimeException("no taikai");
            }

            List<Object[]> tournamentData = em.createQuery(
                "SELECT p.id, p.name " +
                "FROM Player p" +
                "WHERE p.tournament = :tournament").setParameter("tournament", tournament).getResultList();


            List<PlayerProxy> proxies = new ArrayList<PlayerProxy>();

            for (Object[] data : tournamentData) {
                PlayerProxy proxy = new PlayerProxy();
                proxy.setId((Integer) data[0]);
                proxy.setName((String) data[1]);
                proxies.add(proxy);
            }

            return proxies;
        }
        finally {
            em.close();
        }

    }

    public void storeTaikai(TaikaiProxy proxy) {
        EntityManager em = emf.createEntityManager();

        try {

            int key = proxy.getId();
            Taikai taikai = em.find(Taikai.class, key);

            em.getTransaction().begin();
            if (taikai == null) {
                taikai = new Taikai();
                taikai.setName(proxy.getName());
                em.persist(taikai);
            }
            else {
                taikai.setName(proxy.getName());
                em.merge(taikai);
            }

            em.getTransaction().commit();


        }
        finally {
            em.close();
        }

    }

    public void storeTournament(TournamentProxy proxy) {

        EntityManager em = emf.createEntityManager();
        try {

            Tournament tournament = em.find(Tournament.class, proxy.getId());

            em.getTransaction().begin();

            if (tournament == null) {
                tournament = new Tournament();
                int taikaiId = proxy.getTaikai().getId();
                Taikai taikai = em.find(Taikai.class, taikaiId);
                if (taikai == null) {
                    throw new RuntimeException("no taikai");
                }
                tournament.setName(proxy.getName());
                tournament.setTaikai(taikai);
                taikai.addTournament(tournament);


                em.merge(taikai);
            }
            else {
                tournament.setName(proxy.getName());
                em.merge(tournament);
            }

            em.getTransaction().commit();
        }
        finally {
            em.close();
        }
    }

    public void storePlayer(PlayerProxy proxy) {
        EntityManager em = emf.createEntityManager();

        try {
            Player player = em.find(Player.class, proxy.getId());

            em.getTransaction().begin();

            if (player == null) {
                Taikai taikai = em.find(Taikai.class, proxy.getTaikai().getId());
                if (taikai == null) {
                    throw new RuntimeException("no taikai");
                }


                player.setName(proxy.getName());
                player.setTaikai(taikai);
                taikai.addPlayer(player);

                em.merge(taikai);
            }
            else {
                player.setName(proxy.getName());
                em.merge(player);
            }

            em.getTransaction().commit();
        }
        finally {
            em.close();
        }
    }
}
