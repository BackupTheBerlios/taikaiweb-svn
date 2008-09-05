/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package net.europa13.taikai.web.client.rpc;

/**
 *
 * @author daniel
 */
public class LoadSeedListRequest implements Request {

    private int key;
    private SeedListTarget target;
    
    public LoadSeedListRequest(int key, SeedListTarget target) {
        this.key = key;
        this.target = target;
    }
    
    public int getKey() {
        return key;
    }
    
    public SeedListTarget getTarget() {
        return target;
    }
    
}
