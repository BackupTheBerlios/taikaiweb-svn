/*
 * TaikaiWeb - a web application for managing and running kendo tournaments.
 * Copyright (C) 2008  Daniel Wentzel & Jonatan Wentzel
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package net.europa13.taikai.web.client.ui;

import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.CheckBox;
import com.google.gwt.user.client.ui.ClickListener;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.Widget;
import java.util.ArrayList;
import java.util.List;
import net.europa13.taikai.web.client.logging.Logger;
import net.europa13.taikai.web.proxy.TournamentProxy;

/**
 *
 * @author jonatan
 */
public class PlayerTournamentsList extends FlexTable {

    private Button btnAddTournamentConnection;
//    private List<TournamentProxy> selectedTournaments;
    private List<? extends TournamentProxy> tournaments;
    private List<ListBox> tournamentControls = new ArrayList<ListBox>();
    private List<ListBox> seedControls = new ArrayList<ListBox>();
    private List<ListBox> activateSeedControls = new ArrayList<ListBox>();

    PlayerTournamentsList() {

        btnAddTournamentConnection = new Button("En till turnering");
        btnAddTournamentConnection.addClickListener(new ClickListener() {

            public void onClick(Widget arg0) {
                addTournamentConnection();
            }
        });

        setWidget(0, 0, btnAddTournamentConnection);
        getFlexCellFormatter().setColSpan(0, 0, 2);
//        addTournamentConnection();
    }

    private void addTournamentConnection() {
        addTournamentConnection(-1);
    }

    private void addTournamentConnection(long selectedTournament) {

//        final int nbrTournaments = tournamentControls.size();
        final int nbrTournaments = sizeConsitencyCheck();
        ListBox tournamentControl = new ListBox();
        CheckBox activateSeedControl = new CheckBox("Seeda");
        ListBox seedControl = new ListBox();
        setTournamentControlData(tournamentControl, selectedTournament);
        setActivateSeedControlData(activateSeedControl, selectedTournament);
        setSeedControlData(seedControl, selectedTournament);
        tournamentControls.add(tournamentControl);
        activateSeedControls.add(tournamentControl);
        seedControls.add(tournamentControl);
                
        activateSeedControl.addClickListener(new ClickListener() {
//            private int row = nbrTournaments;
            public void onClick(Widget checkBox) {
                Logger.debug("Klickade på checkBox");
                int rowToChange = -1;
                for (int i = 0; i < getRowCount(); i++) {
                    if (checkBox.equals(getWidget(i, 1))) {
                        rowToChange = i;
                        break;
                    }
                }
                Logger.debug("rowToChange="+rowToChange);
                if (rowToChange >= 0) {
                    Widget w = getWidget(rowToChange, 2);
                    if (w instanceof ListBox) {
                        Logger.debug("Ändrar Enabled på rad " + rowToChange +
                                     " kolumn 2");
                        ((CheckBox)w).setVisible(false);
                    }
                }
//                removeTournamentConnection(row);
            }
        });
        
//        if (nbrTournaments > 0) {
        Button drbtn = new Button("Ta bort");
        drbtn.addClickListener(new ClickListener() {
//            private int row = nbrTournaments;
            public void onClick(Widget button) {
                int rowToRemove = -1;
                for (int i = 0; i < getRowCount(); i++) {
                    if (button.equals(getWidget(i, 3))) {
                        rowToRemove = i;
                        break;
                    }
                }
                if (rowToRemove >= 0) {
                    removeTournamentConnection(rowToRemove);
                }
//                removeTournamentConnection(row);
            }
        });
//        }

        setWidget(nbrTournaments, 0, tournamentControl);
        setWidget(nbrTournaments, 1, activateSeedControl);
        setWidget(nbrTournaments, 2, seedControl);
        setWidget(nbrTournaments, 3, drbtn);
        setWidget(nbrTournaments + 1, 0, btnAddTournamentConnection);
        getFlexCellFormatter().setColSpan(nbrTournaments + 1, 0, 2);


    }

    
    
    public List<TournamentProxy> getSelectedTournaments() {

        List<TournamentProxy> selectedTournaments = new ArrayList<TournamentProxy>();

        for (ListBox control : tournamentControls) {
            TournamentProxy tournament = tournaments.get(control.getSelectedIndex());
            selectedTournaments.add(tournament);
        }

        return selectedTournaments;
    }

    private void removeTournamentConnection(int row) {
        Logger.debug("Tar bort rad " + row);
        removeRow(row);
        tournamentControls.remove(row);
        activateSeedControls.remove(row);
        seedControls.remove(row);
    }

    public void reset() {
        while (!tournamentControls.isEmpty()) {
            removeTournamentConnection(0);
        }
    }

    private void setControlData(ListBox control) {
        setTournamentControlData(control, -1);
    }

    private void setTournamentControlData(ListBox control, long selectedId) {
        control.clear();

        for (int i = 0; i < tournaments.size(); ++i) {
            TournamentProxy tournament = tournaments.get(i);
            control.addItem(tournament.getName(), String.valueOf(tournament.getId()));
            if (tournament.getId() == selectedId) {
                control.setSelectedIndex(i);
            }
        }
    }

    private void setActivateSeedControlData(CheckBox control, long selectedId) {
//        control.clear();
//
//        for (int i = 0; i < tournaments.size(); ++i) {
//            TournamentProxy tournament = tournaments.get(i);
//            control.addItem(tournament.getName(), String.valueOf(tournament.getId()));
//            if (tournament.getId() == selectedId) {
//                control.setSelectedIndex(i);
//            }
//        }
    }
    
        private void setSeedControlData(ListBox control, long selectedId) {
//        control.clear();
//
//        for (int i = 0; i < tournaments.size(); ++i) {
//            TournamentProxy tournament = tournaments.get(i);
//            control.addItem(tournament.getName(), String.valueOf(tournament.getId()));
//            if (tournament.getId() == selectedId) {
//                control.setSelectedIndex(i);
//            }
//        }
            
           
            // Temporary solution, data should be acquired from database
            control.addItem("1");
            control.addItem("2");
            control.addItem("3");
            control.addItem("4");
    }
    
    
    public void setSelectedTournaments(List<? extends TournamentProxy> selectedTournaments) {

        if (selectedTournaments == null) {
            Logger.debug(PlayerTournamentsList.class.getName() + ".setSelectedTournaments: selectedTournaments == null");
            return;
        }
        
        reset();

//        if (!tournaments.containsAll(selectedTournaments)) {
//            Logger.debug("PlayerTournamentList.setSelectedTournaments: selectedTournaments innehåller " +
//                "turneringar som inte finns i tournaments.");
//            return;
//        }

        for (TournamentProxy tournament : selectedTournaments) {
//            if (tournaments.contains(tournament))
//            int index = tournaments.indexOf(tournament);
            addTournamentConnection(tournament.getId());
        }

    }

    public void setTournamentList(List<? extends TournamentProxy> tournaments) {
//        Logger.info("setTournaments size = " + tournaments.size());
        this.tournaments = tournaments;

        for (ListBox control : tournamentControls) {
            setTournamentControlData(control, control.getSelectedIndex());
        }

    }

    private int sizeConsitencyCheck() {
        int result = -1;
        if ((tournamentControls.size() == activateSeedControls.size()) &&
            (activateSeedControls.size() == seedControls.size())) {
            result = tournamentControls.size();
        }
        if (result < 0) {
            Logger.error("Inkonsekvent liststorlek i PlayerTournamentList");
        }
        return result;
    }
}
