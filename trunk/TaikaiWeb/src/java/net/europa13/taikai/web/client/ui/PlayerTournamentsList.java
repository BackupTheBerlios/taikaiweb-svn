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
import com.google.gwt.user.client.ui.ChangeListener;
import com.google.gwt.user.client.ui.CheckBox;
import com.google.gwt.user.client.ui.ClickListener;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.Widget;
import java.util.ArrayList;
import java.util.List;
import net.europa13.taikai.web.client.logging.Logger;
import net.europa13.taikai.web.proxy.PlayerDetails;
import net.europa13.taikai.web.proxy.TournamentProxy;
import net.europa13.taikai.web.proxy.TournamentSeedProxy;

/**
 *
 * @author jonatan
 */
public class PlayerTournamentsList extends FlexTable {

    private Button btnAddTournamentConnection;
    private List<TournamentProxy> tournaments;
    private PlayerDetails player;
    private List<TournamentSelector> tournamentControls = new ArrayList<TournamentSelector>();
    private List<SeedSelector> seedControls = new ArrayList<SeedSelector>();
    private List<CheckBox> activateSeedControls = new ArrayList<CheckBox>();

    PlayerTournamentsList() {

        btnAddTournamentConnection = new Button("En till turnering");
        btnAddTournamentConnection.addClickListener(new ClickListener() {

            public void onClick(Widget arg0) {
                addTournamentConnection();
            }
        });

        setWidget(0, 0, btnAddTournamentConnection);

    }

    private void addTournamentConnection() {
        addTournamentConnection(null);
    }

//    private class TournamentChangeListener implements ChangeListener {
//
//        private SeedSelector seedSelector;
//
//        public TournamentChangeListener(SeedSelector seedSelector) {
//            this.seedSelector = seedSelector;
//        }
//
//        public void onChange(Widget sender) {
//        }
//    }

    private void addTournamentConnection(TournamentProxy selectedTournament) {

        final int nbrTournaments = sizeConsitencyCheck();

        TournamentSelector tournamentControl =
            new TournamentSelector();
        setTournamentControlData(tournamentControl, selectedTournament);
        tournamentControls.add(tournamentControl);

        final CheckBox activateSeedControl = new CheckBox("Seeda");

        activateSeedControls.add(activateSeedControl);



        final SeedSelector seedControl = new SeedSelector();
        Logger.debug("seedControl.setTournament");
        seedControl.setData(tournamentControl.getSelectedTournament(), player);
        seedControls.add(seedControl);



        activateSeedControl.addClickListener(new ClickListener() {

            public void onClick(Widget sender) {
//                CheckBox cb = (CheckBox)sender;
                seedControl.setEnabled(activateSeedControl.isChecked());
            }
        });

        if (player != null && player.getPlayerSeedInTournament(selectedTournament) > -1) {
            activateSeedControl.setChecked(true);
            seedControl.setEnabled(true);
        }
        else {
            seedControl.setEnabled(false);
        }

//        tournamentControl.addChangeListener(new TournamentChangeListener(seedControl));
        tournamentControl.addChangeListener(new ChangeListener() {

            public void onChange(Widget sender) {
                TournamentSelector tournamentSelector = (TournamentSelector) sender;
                seedControl.setData(tournamentSelector.getSelectedTournament(), player);
                
                activateSeedControl.setChecked(false);
                seedControl.setEnabled(false);
            }
        });

                
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

    public List<TournamentSeedProxy> getSeeds() {
        List<TournamentSeedProxy> seeds = new ArrayList<TournamentSeedProxy>();

        for (int i = 0; i < seedControls.size(); ++i) {
            if (activateSeedControls.get(i).isChecked()) {
                TournamentSeedProxy seed = new TournamentSeedProxy();
                seed.setTournament(tournamentControls.get(i).getSelectedTournament());
                seed.setSeedNumber(seedControls.get(i).getSelectedSeed());
                seeds.add(seed);
            }

        }

        return seeds;

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
//        Logger.debug("Tar bort rad " + row);
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

//    public void setPlayer(PlayerDetails player) {
//        this.player = player;
//    }

//    private void setControlData(ListBox control) {
//        setTournamentControlData(control, -1);
//    }
    private void setTournamentControlData(TournamentSelector control, TournamentProxy tournament) {

        control.setTournaments(tournaments);
        control.setSelectedTournament(tournament);
    }

//    private void setActivateSeedControlData(CheckBox control, long selectedId) {
//
//    }
//
//    private void setSeedControlData(ListBox control,  TournamentProxy tournament) {
//        control.addItem("1");
//        control.addItem("2");
//        control.addItem("3");
//        control.addItem("4");
//    }

//    public void setSeedData() {
//
//    }
    private void setSelectedTournaments(List<? extends TournamentProxy> selectedTournaments) {

        if (selectedTournaments == null) {
            Logger.debug(PlayerTournamentsList.class.getName() + ".setSelectedTournaments: selectedTournaments == null");
            return;
        }

//        Logger.debug(PlayerTournamentsList.class.getName() + ".setSelectedTournaments: selectedTournaments.size = " + selectedTournaments.size());
        reset();

        for (TournamentProxy tournament : selectedTournaments) {
            addTournamentConnection(tournament);
        }

    }

    public void setData(List<? extends TournamentProxy> tournaments, PlayerDetails player) {
        Logger.trace("entering setDate in PlayerTournamentList");

        if (tournaments == null) {
            throw new RuntimeException("tournaments is null");
        }

        this.tournaments = new ArrayList<TournamentProxy>(tournaments);

        for (TournamentSelector control : tournamentControls) {
            control.setTournaments(tournaments);
        }

        // player == null means that we are creating a new player.
        this.player = player;
        if (player != null) {
            setSelectedTournaments(player.getTournaments());
        }
        
        Logger.trace("exiting setDate in PlayerTournamentList");
    }

    private int sizeConsitencyCheck() {
        int result = -1;
        if ((tournamentControls.size() == activateSeedControls.size()) &&
            (activateSeedControls.size() == seedControls.size())) {
            result = tournamentControls.size();
        }
        if (result < 0) {
            Logger.debug("Inkonsekvent liststorlek i PlayerTournamentList");
        }
        return result;
    }
}
