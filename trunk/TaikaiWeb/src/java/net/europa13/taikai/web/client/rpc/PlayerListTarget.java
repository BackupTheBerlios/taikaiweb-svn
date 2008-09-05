/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.europa13.taikai.web.client.rpc;

import java.util.List;
import net.europa13.taikai.web.proxy.PlayerProxy;

/**
 *
 * @author daniel
 */
public interface PlayerListTarget {

    public void setPlayers(List<? extends PlayerProxy> players);
}
