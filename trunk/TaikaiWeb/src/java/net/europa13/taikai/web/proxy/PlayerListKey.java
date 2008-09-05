/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package net.europa13.taikai.web.proxy;

import java.io.Serializable;

/**
 *
 * @author Daniel Wentzel
 */
public class PlayerListKey implements Serializable {

    public static enum Owner {
        TAIKAI,
        TOURNAMENT,
        POOL,
        MATCH
    }
    
    private Owner owner;
    private int ownerId;
    
    protected PlayerListKey() {
        
    }
    
    public PlayerListKey(Owner owner, int ownerId) {
        this.owner = owner;
        this.ownerId = ownerId;
    } 
    
    public Owner getOwner() {
        return owner;
    }
    
    public int getOwnerId() {
        return ownerId;
    }
    
}
