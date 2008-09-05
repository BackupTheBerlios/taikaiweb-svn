/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package net.europa13.taikai.web.client.rpc;

/**
 *
 * @author daniel
 */
public interface RequestHandler<C extends Request> {
    
    public void handleRequest(C request);
    
}
