/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package net.europa13.taikai.web.client.rpc;

import net.europa13.taikai.web.proxy.PlayerListKey;

/**
 *
 * @author daniel
 */
public class LoadPlayersRequest implements Request {

    private PlayerListTarget container;
    private PlayerListKey key;
    
    public LoadPlayersRequest(PlayerListKey key, PlayerListTarget container) {
        this.key = key;
        this.container = container;
    }
    
    public PlayerListTarget getContainer() {
        return container;
    }
    
    public PlayerListKey getKey() {
        return key;
    }
}
