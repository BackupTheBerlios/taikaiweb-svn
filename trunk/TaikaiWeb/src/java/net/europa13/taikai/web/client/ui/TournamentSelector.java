/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.europa13.taikai.web.client.ui;

import com.google.gwt.user.client.ui.ListBox;
import java.util.ArrayList;
import java.util.List;
import net.europa13.taikai.web.client.rpc.TournamentListTarget;
import net.europa13.taikai.web.client.logging.Logger;
import net.europa13.taikai.web.proxy.TournamentProxy;

/**
 *
 * @author daniel
 */
public class TournamentSelector extends ListBox implements TournamentListTarget {

    private List<TournamentProxy> tournaments;
//    private boolean allowingNoTournament;

    public TournamentSelector() {
//        this(null);
    }
//
//    public TournamentSelector(boolean allowingNoTournament) {
//        this(null, allowingNoTournament);
//    }

    public TournamentSelector(List<? extends TournamentProxy> tournaments) {
        setTournaments(tournaments);
//        this(tournaments, false);
    }

//    public TournamentSelector(List<? extends TournamentProxy> tournaments, boolean allowingNoTournament) {
//        this.allowingNoTournament = allowingNoTournament;
//        setTournaments(tournaments);
//    }

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
        Logger.trace("entering setTournaments(List<? extends TournamentProxy>) in TournamentSelector");

        if (tournaments == null) {
            Logger.debug("setTournaments in TournamentSelector: tournaments is null");
            this.tournaments = new ArrayList<TournamentProxy>();
        }
        else {
            this.tournaments = new ArrayList<TournamentProxy>(tournaments);
        }
        updateList();
        Logger.trace("exiting setTournaments(List<? extends TournamentProxy>) in TournamentSelector");
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
