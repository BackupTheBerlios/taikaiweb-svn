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

import javax.persistence.EntityManager;
import net.europa13.taikai.web.entity.Player;
import net.europa13.taikai.web.entity.Taikai;
import net.europa13.taikai.web.entity.Tournament;
import net.europa13.taikai.web.proxy.PlayerDetails;
import net.europa13.taikai.web.proxy.PlayerProxy;
import net.europa13.taikai.web.proxy.TaikaiDetails;
import net.europa13.taikai.web.proxy.TournamentDetails;

/**
 *
 * @author Daniel Wentzel
 */
public class DetailsToEntity {

    public static void player(PlayerDetails details, Player entity, EntityManager em) {
        entity.setAge(details.getAge());
        entity.setCheckedIn(details.isCheckedIn());
        entity.setGender(details.getGender());
        entity.setGrade(details.getGrade());
        entity.setName(details.getName());
        entity.setSurname(details.getSurname());
    }

    public static void taikai(TaikaiDetails details, Taikai entity, EntityManager em) {
    }

    public static void tournament(TournamentDetails details, Tournament entity, EntityManager em) {
        entity.setName(details.getName());


//        for (int i = 0; i < 4; ++i) {
//            PlayerProxy playerDetails = details.getSeededPlayer(i);
//            
//            if (playerDetails != null) {
//                Player player = em.find(Player.class, playerDetails.getId());
//                entity.setPlayerSeed(player, i);
//            }
//            else {
//                entity.setPlayerSeed(null, i);
//            }
//        }

        entity.setPoolSize(details.getPoolSize());
        entity.setPreferringLargerPools(details.isPreferringLargerPools());

    }
}
