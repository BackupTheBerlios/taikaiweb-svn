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
import net.europa13.taikai.web.proxy.PlayerDetails;
import net.europa13.taikai.web.proxy.PlayerProxy;
import net.europa13.taikai.web.proxy.TaikaiDetails;
import net.europa13.taikai.web.proxy.TournamentDetails;
import net.europa13.taikai.web.proxy.TournamentProxy;

/**
 *
 * @author Daniel Wentzel
 */
public class EntityToDetails {

    public static void player(Player entity, PlayerDetails details, EntityManager em) {
        details.setAge(entity.getAge());
        details.setCheckedIn(entity.isCheckedIn());
        details.setGender(entity.getGender());
        details.setGrade(entity.getGrade());
        details.setId(entity.getId());
        details.setName(entity.getName());
        details.setNumber(entity.getNumber());
        details.setSurname(entity.getSurname());
        details.setTaikaiId(entity.getTaikai().getId());
        
//        System.out.println("Before getting tournaments");
        
        // Felaktigt cast, det här blir ju inte TournamentProxies...
        List<Tournament> tournaments =
            em.createNamedQuery("getTournamentsForPlayer").setParameter("player", entity).getResultList();
        
//        System.out.println("After getting tournaments. Tournaments size = " + tournaments.size());
        
        List<TournamentProxy> tournamentProxies = 
            new ArrayList<TournamentProxy>();
        
        for (Tournament tournament : tournaments) {
            TournamentDetails td = new TournamentDetails();
            tournament(tournament, td, em);
            tournamentProxies.add(td);
        }
        details.setTournaments(tournamentProxies);
        
    }

    public static void taikai(Taikai entity, TaikaiDetails details, EntityManager em) {
    }

    public static void tournament(Tournament entity, TournamentDetails details, EntityManager em) {
        details.setId(entity.getId());
        details.setName(entity.getName());

//        for (int i = 0; i < 4; ++i) {
//            PlayerDetails playerDetails = new PlayerDetails();
//            EntityToDetails.player(entity.getSeededPlayer(i), playerDetails, em);
//            details.setPlayerSeed(i, playerDetails);
//        }

        details.setTaikaiId(entity.getTaikai().getId());

    }
}
