/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package net.europa13.taikai.web.client.rpc;

/**
 *
 * @author daniel
 */
public class LoadTournamentDetailsRequest implements Request {

    private int key;
    private TournamentDetailsTarget target;
    
    public LoadTournamentDetailsRequest(int key, TournamentDetailsTarget target) {
        this.key = key;
        this.target = target;
    }
    
    public int getKey() {
        return key;
    }
    
    public TournamentDetailsTarget getTarget() {
        return target;
    }
    
    
}
