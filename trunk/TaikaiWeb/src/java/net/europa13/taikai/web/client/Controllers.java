/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package net.europa13.taikai.web.client;

/**
 *
 * @author daniel
 */
public class Controllers {

    public final static TaikaiControl taikaiControl;
    public final static TournamentControl tournamentControl;
    public final static PlayerControl playerControl;
    
    static {
        taikaiControl = new TaikaiControl();
        tournamentControl = new TournamentControl();
        playerControl = new PlayerControl();
    }
    
}
