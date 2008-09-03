/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
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
 *
 * @author daniel
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

        proxy.setTaikaiId(entity.getTaikai().getId());

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
