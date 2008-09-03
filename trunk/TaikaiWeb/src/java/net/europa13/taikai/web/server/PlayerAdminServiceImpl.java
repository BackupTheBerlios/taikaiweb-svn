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
import net.europa13.taikai.web.client.ListResult;
import net.europa13.taikai.web.client.PlayerAdminService;
import net.europa13.taikai.web.entity.Player;
import net.europa13.taikai.web.entity.Taikai;
import net.europa13.taikai.web.entity.Tournament;
import net.europa13.taikai.web.proxy.PlayerDetails;
import net.europa13.taikai.web.proxy.PlayerProxy;
import net.europa13.taikai.web.proxy.TaikaiProxy;
import net.europa13.taikai.web.proxy.TournamentProxy;

/**
 *
 * @author Daniel Wentzel
 */
public class PlayerAdminServiceImpl extends RemoteServiceServlet implements
    PlayerAdminService {

    @PersistenceUnit
    EntityManagerFactory emf;
    private final String baseListQuery =
        "SELECT p.id, p.name, p.surname, p.checkedIn, p.age, p.number " +
        "FROM Player p ";

    private List<PlayerProxy> dataToProxyList(List<Object[]> data) {
        List<PlayerProxy> proxies = new ArrayList<PlayerProxy>();

        for (Object[] datum : data) {
            PlayerProxy proxy = new PlayerProxy();
            proxy.setId((Integer) datum[0]);
            proxy.setName((String) datum[1]);
            proxy.setSurname((String) datum[2]);
            proxy.setCheckedIn((Boolean) datum[3]);
            proxy.setAge((Integer) datum[4]);
            proxy.setNumber((Integer) datum[5]);
            proxies.add(proxy);
        }

        return proxies;
    }
    
    public PlayerDetails getPlayer(int playerId) {
        EntityManager em = emf.createEntityManager();

        try {
            Player player = em.find(Player.class, playerId);

            PlayerDetails details = new PlayerDetails();
            EntityToDetails.player(player, details, em);

            System.out.println("name = " + player.getName() + " surname = " + player.getSurname());
            System.out.println("name = " + details.getName() + " surname = " + details.getSurname());
            
            
            return details;
        }
        finally {
            em.close();
        }
    }

    @SuppressWarnings(value = "unchecked")
    public ListResult<PlayerProxy> getPlayers(TaikaiProxy taikaiProxy) {
        EntityManager em = emf.createEntityManager();

        try {
            Taikai taikai = em.find(Taikai.class, taikaiProxy.getId());
            if (taikai == null) {
                throw new RuntimeException("no taikai");
            }

            StringBuilder query = new StringBuilder(baseListQuery);
            query.append("WHERE p.taikai = :owner ");

            return getPlayers(em, query.toString(), taikai);
        }
        finally {
            em.close();
        }
    }

    @SuppressWarnings(value = "unchecked")
    public ListResult<PlayerProxy> getPlayers(TournamentProxy tournamentProxy) {
        EntityManager em = emf.createEntityManager();

        try {
            Tournament tournament = em.find(Tournament.class, tournamentProxy.getId());
            if (tournament == null) {
                throw new RuntimeException("no taikai");
            }

            StringBuilder query = new StringBuilder(baseListQuery);
            query.append("WHERE p.tournament = :owner ");
            
            return getPlayers(em, query.toString(), tournament);

        }
        finally {
            em.close();
        }

    }

    private ListResult<PlayerProxy> getPlayers(EntityManager em, String query, Object owner) {

        List<Object[]> data =
            em.createQuery(query).
            setParameter("owner", owner).getResultList();

        ListResult<PlayerProxy> result =
            new ListResult<PlayerProxy>(dataToProxyList(data), 0, data.size());
        return result;

    }

    

    public void storePlayer(PlayerDetails details) {
        EntityManager em = emf.createEntityManager();

        try {
            Player player = em.find(Player.class, details.getId());

            em.getTransaction().begin();

            if (player == null) {
                player = new Player();
                Taikai taikai = em.find(Taikai.class, details.getTaikai());
                if (taikai == null) {
                    throw new RuntimeException("no taikai");
                }

                DetailsToEntity.player(details, player, em);

                player.setTaikai(taikai);
                taikai.addPlayer(player);
                em.merge(taikai);
            }
            else {
                DetailsToEntity.player(details, player, em);
                em.merge(player);
            }

            em.getTransaction().commit();
        }
        catch (Exception ex) {
            System.out.println("Exception: rollback!");
            ex.printStackTrace();
            em.getTransaction().rollback();
        }
//        catch (RuntimeException ex) {
//            em.getTransaction().rollback();
//        }
        finally {
            em.clear();
            em.close();
            
        }
    }
}
