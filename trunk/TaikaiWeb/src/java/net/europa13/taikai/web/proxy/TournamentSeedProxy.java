/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package net.europa13.taikai.web.proxy;

import java.io.Serializable;

/**
 *
 * @author daniel
 */
public class TournamentSeedProxy implements Serializable {

    private int id;
    private TournamentProxy tournament;
    private PlayerProxy player;
    private int seedNumber;
    
    public TournamentSeedProxy() {
        
    }
    
    public TournamentSeedProxy(int id, TournamentProxy tournament, PlayerProxy player, int seedNumber) {
        this.id = id;
        this.tournament = tournament;
        this.player = player;
        this.seedNumber = seedNumber;
    }
    
    public int getId() {
        return id;
    }
    
    public PlayerProxy getPlayer() {
        return player;
    }
    
    public int getSeedNumber() {
        return seedNumber;
    }
    
    public TournamentProxy getTournament() {
        return tournament;
    }
        
    public void setId(int id) {
        this.id = id;
    }
    
    public void setPlayer(PlayerProxy player) {
        this.player = player;
    }
    
    public void setSeedNumber(int seedNumber) {
        this.seedNumber = seedNumber;
    }
    
    public void setTournament(TournamentProxy tournament) {
        this.tournament = tournament;
    }
    
}
