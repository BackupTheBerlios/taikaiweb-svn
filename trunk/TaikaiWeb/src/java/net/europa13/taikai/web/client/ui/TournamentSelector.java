/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package net.europa13.taikai.web.client.ui;

import com.google.gwt.user.client.ui.ListBox;
import java.util.ArrayList;
import java.util.List;
import net.europa13.taikai.web.proxy.TournamentProxy;

/**
 *
 * @author daniel
 */
public class TournamentSelector extends ListBox {

    private List<TournamentProxy> tournaments;
    
    public TournamentSelector() {
        
    }
    
    public TournamentSelector(List<? extends TournamentProxy> tournaments) {
        setTournaments(tournaments);
    }
    
    public TournamentProxy getSelectedTournament() {
        int selectedIndex = getSelectedIndex();
        if (selectedIndex > -1) {
            return tournaments.get(getSelectedIndex());
        }
        else {
            return null;
        }
    }
    
    public void setSelectedTournament(TournamentProxy tournament) {
        int selectedIndex = tournaments.indexOf(tournament);
//        Logger.debug(TournamentSelector.class.getName()  + " setSelectedTournament: index = " + selectedIndex);
        if (selectedIndex > -1 && selectedIndex < tournaments.size()) {
            setSelectedIndex(selectedIndex);
        }
    }
    
    public void setTournaments(List<? extends TournamentProxy> tournaments) {
        this.tournaments = new ArrayList<TournamentProxy>(tournaments);
        updateList();
    }
    
    private void updateList() {
        TournamentProxy selectedTournament = getSelectedTournament();
        clear();
        
        for (int i = 0; i < tournaments.size(); ++i) {
            TournamentProxy tournament = tournaments.get(i);
            addItem(tournament.getName());
            if (tournament == selectedTournament) {
                setSelectedIndex(i);
            }
        }
        
        if (getSelectedIndex() == -1) {
            setSelectedIndex(0);
        }
        
    }
        
    
}
