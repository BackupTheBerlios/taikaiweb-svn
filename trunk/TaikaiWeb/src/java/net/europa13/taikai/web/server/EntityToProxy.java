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
import net.europa13.taikai.web.entity.TournamentSeed;
import net.europa13.taikai.web.proxy.PlayerProxy;
import net.europa13.taikai.web.proxy.TaikaiProxy;
import net.europa13.taikai.web.proxy.TournamentProxy;
import net.europa13.taikai.web.proxy.TournamentSeedProxy;

/**
 * Class for generating proxies from entities.
 * @author Daniel Wentzel
 */
public class EntityToProxy {

    public static void player(Player entity, PlayerProxy proxy, EntityManager em) {
        proxy.setAge(entity.getAge());
        proxy.setCheckedIn(entity.isCheckedIn());
        proxy.setGender(entity.getGender());
        proxy.setGrade(entity.getGrade());
        proxy.setId(entity.getId());
        proxy.setName(entity.getName());
        proxy.setNumber(entity.getNumber());
        proxy.setSurname(entity.getSurname());

        TaikaiProxy taikaiProxy = new TaikaiProxy();
        taikai(entity.getTaikai(), taikaiProxy, em);
        proxy.setTaikai(taikaiProxy);

    }

    public static void taikai(Taikai entity, TaikaiProxy proxy, EntityManager em) {
        proxy.setId(entity.getId());
        proxy.setName(entity.getName());
        proxy.setPlayerCount(entity.getPlayers().size());
        proxy.setTournamentCount(entity.getTournaments().size());
    }

    public static void tournament(Tournament entity, TournamentProxy proxy, EntityManager em) {
        proxy.setId(entity.getId());
        proxy.setName(entity.getName());
        TaikaiProxy taikaiProxy = new TaikaiProxy();
        taikai(entity.getTaikai(), taikaiProxy, em);
        proxy.setTaikai(taikaiProxy);

    }

    public static void tournamentSeed(TournamentSeed entity, TournamentSeedProxy proxy, EntityManager em) {
        proxy.setId(entity.getId());
        PlayerProxy playerProxy = new PlayerProxy();
        player(entity.getPlayer(), playerProxy, em);
        proxy.setPlayer(playerProxy);
        TournamentProxy tournamentProxy = new TournamentProxy();
        tournament(entity.getTournament(), tournamentProxy, em);
        proxy.setTournament(tournamentProxy);
        proxy.setSeedNumber(entity.getSeedNumber());

    }
}
