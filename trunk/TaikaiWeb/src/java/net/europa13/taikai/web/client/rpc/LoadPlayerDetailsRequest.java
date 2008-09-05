/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package net.europa13.taikai.web.client.rpc;

/**
 *
 * @author daniel
 */
public class LoadPlayerDetailsRequest implements Request {

    private int key;
    private PlayerDetailsTarget target;
    
    public LoadPlayerDetailsRequest(int key, PlayerDetailsTarget target) {
        this.key = key;
        this.target = target;
    }
    
    public int getKey() {
        return key;
    }
    
    public PlayerDetailsTarget getTarget() {
        return target;
    }
    
}
