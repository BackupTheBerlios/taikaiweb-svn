/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package net.europa13.taikai.web.client.rpc;

import net.europa13.taikai.web.proxy.TournamentListKey;

/**
 *
 * @author daniel
 */
public class LoadTournamentsRequest implements Request {

    private TournamentListKey key;
    private TournamentListTarget container;
    
    public LoadTournamentsRequest(TournamentListKey key, TournamentListTarget container) {
        this.key = key;
        this.container = container;
    }
    
    public TournamentListTarget getContainer() {
        return container;
    }
    
    public TournamentListKey getKey() {
        return key;
    }
    
}
