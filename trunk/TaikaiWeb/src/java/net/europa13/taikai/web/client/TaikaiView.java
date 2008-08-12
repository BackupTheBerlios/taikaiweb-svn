/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package net.europa13.taikai.web.client;

import java.util.List;
import net.europa13.taikai.web.proxy.TaikaiProxy;

/**
 *
 * @author daniel
 */
public interface TaikaiView {

    public void taikaiListUpdated(List<TaikaiProxy> taikaiList);
    
}
