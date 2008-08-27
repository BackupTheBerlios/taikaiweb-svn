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
import net.europa13.taikai.web.client.TournamentAdminService;
import net.europa13.taikai.web.entity.Taikai;
import net.europa13.taikai.web.entity.Tournament;
import net.europa13.taikai.web.proxy.TaikaiProxy;
import net.europa13.taikai.web.proxy.TournamentProxy;

/**
 *
 * @author daniel
 */
public class TournamentAdminServiceImpl extends RemoteServiceServlet implements
    TournamentAdminService {
    
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
    
    private void entityToProxy(Tournament entity, TournamentProxy proxy) {
        proxy.setId(entity.getId());
        proxy.setName(entity.getName());
        proxy.setTaikaiId(entity.getTaikai().getId());

    }
    
    public TournamentProxy getTournament(int tournamentId) {

        EntityManager em = emf.createEntityManager();

        try {
            Tournament tournament = em.find(Tournament.class, tournamentId);
            
            TournamentProxy proxy = new TournamentProxy();
            entityToProxy(tournament, proxy);
            
            return proxy;
        }
        finally {
            em.close();
        }

    }

    @SuppressWarnings(value = "unchecked")
    public ListResult<TournamentProxy> getTournaments(TaikaiProxy taikaiProxy) {
        EntityManager em = emf.createEntityManager();

        try {
            Taikai taikai = em.find(Taikai.class, taikaiProxy.getId());
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
    
    private ListResult<TournamentProxy> getTournaments(EntityManager em, String query, Object owner) {
        List<Object[]> data =
            em.createQuery(query).
            setParameter("owner", owner).getResultList();

        ListResult<TournamentProxy> result =
            new ListResult<TournamentProxy>(dataToProxyList(data), 0, data.size());
        return result;
    }
    
    private void proxyToEntity(TournamentProxy proxy, Tournament entity) {
        entity.setName(proxy.getName());
    }
    
    public void storeTournament(TournamentProxy proxy) {

        EntityManager em = emf.createEntityManager();
        try {

            Tournament tournament = em.find(Tournament.class, proxy.getId());

            em.getTransaction().begin();

            if (tournament == null) {
                tournament = new Tournament();
                int taikaiId = proxy.getTaikaiId();
                Taikai taikai = em.find(Taikai.class, taikaiId);
                if (taikai == null) {
                    throw new RuntimeException("no taikai");
                }
                proxyToEntity(proxy, tournament);
                
                tournament.setTaikai(taikai);
                taikai.addTournament(tournament);

                em.merge(taikai);
            }
            else {
                proxyToEntity(proxy, tournament);
                
                em.merge(tournament);
            }

            em.getTransaction().commit();
        }
        finally {
            em.close();
        }
    }
}
