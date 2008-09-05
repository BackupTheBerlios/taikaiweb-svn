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
public class TournamentListKey implements Serializable {
    
    private int ownerId;
    
    private TournamentListKey() {
        
    }
    
    public TournamentListKey(int ownerId) {
        this.ownerId = ownerId;
    }

    public int getOwnerId() {
        return ownerId;
    }
    
}
