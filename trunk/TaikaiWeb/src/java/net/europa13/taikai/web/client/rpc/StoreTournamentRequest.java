/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package net.europa13.taikai.web.client.rpc;

import net.europa13.taikai.web.proxy.TournamentDetails;

/**
 *
 * @author daniel
 */
public class StoreTournamentRequest implements Request {
    
    private TournamentDetails tournament;
    private TournamentDetailsTarget target;
    private boolean reloadNecessary;
    
    public StoreTournamentRequest(TournamentDetails tournament, TournamentDetailsTarget target) {
        this(tournament, target, false);
    }
    
    public StoreTournamentRequest(TournamentDetails tournament, TournamentDetailsTarget target, boolean reloadNecessary) {
        this.tournament = tournament;
        this.target = target;
        this.reloadNecessary = reloadNecessary;
    }
    
    public TournamentDetails getTournament() {
        return tournament;
    }
    
    public TournamentDetailsTarget getTarget() {
        return target;
    }
    
    public boolean isReloadNecessary() {
        return reloadNecessary;
    }
    
}
