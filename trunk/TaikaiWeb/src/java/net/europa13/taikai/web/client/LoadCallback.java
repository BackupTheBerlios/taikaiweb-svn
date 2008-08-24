/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package net.europa13.taikai.web.client;

import com.google.gwt.user.client.rpc.AsyncCallback;
import net.europa13.taikai.web.client.logging.Logger;

/**
 *
 * @author daniel
 */
public abstract class LoadCallback<C> implements AsyncCallback<C> {
    
    public void onFailure(Throwable t) {
        Logger.error(t.getLocalizedMessage());
    }
    
    public void onSuccess(C object) {
        objectLoaded(object);
    }
    
    public abstract void objectLoaded(C object);

}
