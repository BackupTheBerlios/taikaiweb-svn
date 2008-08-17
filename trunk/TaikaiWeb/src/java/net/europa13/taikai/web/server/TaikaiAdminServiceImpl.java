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
import net.europa13.taikai.web.entity.Taikai;
import net.europa13.taikai.web.proxy.TaikaiProxy;

/**
 *
 * @author Daniel Wentzel
 */
public class TaikaiAdminServiceImpl extends RemoteServiceServlet implements
    TaikaiAdminService {

    @PersistenceUnit
    EntityManagerFactory emf;
    
    public void createTaikai(TaikaiProxy proxy) {
        
        EntityManager em = emf.createEntityManager();
        
        try {
            Taikai taikai = new Taikai();
            taikai.setName(proxy.getName());
            
            em.getTransaction().begin();
            em.persist(em);
            em.getTransaction().commit();
        }
        finally {
            em.close();
        }
        
    }
    
    public TaikaiProxy getTaikai(int id) {
        
        EntityManager em = emf.createEntityManager();
        
        try {
            TaikaiProxy proxy = (TaikaiProxy)em.createQuery(
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
}
