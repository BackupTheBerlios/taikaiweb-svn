/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package net.europa13.taikai.web.client.rpc;

import net.europa13.taikai.web.proxy.PlayerDetails;

/**
 *
 * @author daniel
 */
public class StorePlayerRequest implements Request {

    private PlayerDetails player;
    private PlayerDetailsTarget target;
    private Boolean reloadNecessary;
    
    public StorePlayerRequest(PlayerDetails player, PlayerDetailsTarget target) {
        this(player, target, false);
    }
    
    public StorePlayerRequest(PlayerDetails player, PlayerDetailsTarget target, boolean reloadNecessary) {
        this.player = player;
        this.target = target;
        this.reloadNecessary = reloadNecessary;
    }
    
    public PlayerDetails getPlayer() {
        return player;
    }
    
    public PlayerDetailsTarget getTarget() {
        return target;
    } 
    
    public boolean isReloadNecessary() {
        return reloadNecessary;
    }
    
}
