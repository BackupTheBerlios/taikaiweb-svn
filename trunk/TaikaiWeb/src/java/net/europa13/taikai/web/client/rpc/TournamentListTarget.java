/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package net.europa13.taikai.web.client.rpc;

import java.util.List;
import net.europa13.taikai.web.proxy.TournamentProxy;

/**
 *
 * @author daniel
 */
public interface TournamentListTarget {

//    public List<TournamentProxy> getTournaments();
    public void setTournaments(List<? extends TournamentProxy> tournaments);
    
}
