/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package net.europa13.taikai.web.client.ui;

import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.SimplePanel;
import java.util.List;
import net.europa13.taikai.web.client.Controllers;
import net.europa13.taikai.web.client.TournamentView;
import net.europa13.taikai.web.client.logging.Logger;
import net.europa13.taikai.web.proxy.TournamentProxy;

/**
 *
 * @author daniel
 */
public class TournamentListPanel extends SimplePanel implements TournamentView {

    private final Grid tournamentGrid;
    private boolean active;


    public TournamentListPanel() {
//        Grid tournamentGrid;
        tournamentGrid = new Grid(1, 4);
    
        tournamentGrid.setWidget(0, 0, new HTML("<h3>Id</h3>"));
        tournamentGrid.setWidget(0, 1, new HTML("<h3>Namn</h3>"));
        tournamentGrid.setWidget(0, 2, new HTML("<h3>Deltagare</h3>"));
        tournamentGrid.setWidget(0, 3, new HTML(""));
        
        add(tournamentGrid);
    }
    
    public Panel getPanel() {
        return this;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        if (this.active == active) {
            return;
        }
        
        this.active = active;
        
        if (active) {
            Controllers.tournamentControl.addTaikaiView(this);
            Controllers.tournamentControl.updateTournamentList();
        }
        else {
            Controllers.tournamentControl.removeTournamentView(this);
        }
    }
    
    protected void setTournamentList(List<TournamentProxy> tournamentList) {
//        Grid tournamentGrid;
//        Logger.trace("TournamentListPanel.setTournamentList()");
//        Logger.debug("setTournamentList list.size() = " + tournamentList.size());
        
        tournamentGrid.resize(tournamentList.size() + 1, 4);
        for (int i = 0; i < tournamentList.size(); ++i) {
            TournamentProxy tournament = tournamentList.get(i);
            tournamentGrid.setText(i + 1, 0, String.valueOf(tournament.getId()));
            tournamentGrid.setText(i + 1, 1, tournament.getName());
//            tournamentGrid.setText(i + 1, 2, String.valueOf(tournament.getPlayerCount()));
//            tournamentGrid.setText(i + 1, 3, String.valueOf(tournament.getTournamentCount()));
        }
    }

    public void tournamentListUpdated(List<TournamentProxy> tournamentList) {
//        Logger.trace("TournamentListPanel.tournamentListUpdated()");
        setTournamentList(tournamentList);
    }

}
