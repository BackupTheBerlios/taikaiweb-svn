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
 * @author daniel
 */
public class TaikaiAdminServiceImpl extends RemoteServiceServlet implements
    TaikaiAdminService {

    @PersistenceUnit
    EntityManagerFactory emf;
    
    public List<TaikaiProxy> getTaikais() {
        
        EntityManager em = emf.createEntityManager();
        
        List<TaikaiProxy> proxies = new ArrayList<TaikaiProxy>();
        List<Object[]> taikaiData = em.createQuery("SELECT t.id, t.name FROM Taikai t").getResultList();
        
        for (Object[] data : taikaiData) {
            TaikaiProxy proxy = new TaikaiProxy((Integer)data[0], (String)data[1], 0, 0);
            proxies.add(proxy);
        }
        
        return proxies;
    }
}
