/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package net.europa13.taikai.web.client;

import java.util.List;
import net.europa13.taikai.web.proxy.TournamentProxy;

/**
 *
 * @author daniel
 */
public interface TournamentView extends View {

    public void tournamentListUpdated(List<TournamentProxy> tournamentList);

}
