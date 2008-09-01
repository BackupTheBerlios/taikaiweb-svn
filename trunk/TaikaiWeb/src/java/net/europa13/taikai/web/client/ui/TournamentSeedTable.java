/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package net.europa13.taikai.web.client.ui;

import com.google.gwt.user.client.ui.Grid;
import net.europa13.taikai.web.client.logging.Logger;
import net.europa13.taikai.web.proxy.PlayerProxy;

/**
 *
 * @author daniel
 */
public class TournamentSeedTable extends Grid {
    
    public TournamentSeedTable() {
        super(2, 4);
        
        setText(0, 0, "Ett");
        setText(0, 1, "Två");
        setText(0, 2, "Tre");
        setText(0, 3, "Fyra");
    }
    
    public void reset() {
        for (int i = 0; i < 4; ++i) {
            setText(1, i, "");
        }
    }
        
    
    public void setSeededPlayer(int position, PlayerProxy player) {
        if (player == null) {
            Logger.debug("setSeededPlayer in TournamentSeedTable: player is null.");
            return;
        }
        setText(1, position - 1, player.getNumber() + " " + player.getName() + " " + player.getSurname());
    }

}
