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

    PlayerTournamentsList() {

        btnAddTournamentConnection = new Button("En till turnering");
        btnAddTournamentConnection.addClickListener(new ClickListener() {

            public void onClick(Widget arg0) {
                addTournamentConnection();
            }
        });

        setWidget(0, 0, btnAddTournamentConnection);
//        addTournamentConnection();
    }

    private void addTournamentConnection() {
        addTournamentConnection(-1);
    }

    private void addTournamentConnection(long selectedTournament) {
//        table.insertCell(btnAddTournamentConnectionRow, btnAddTournamentConnectionCol);
//        table.insertCell(btnAddTournamentConnectionRow, btnAddTournamentConnectionCol);
//        table.setWidget(btnAddTournamentConnectionRow, btnAddTournamentConnectionCol, new ListBox());
//        Logger.debug("Ny ListBox på rad " + btnAddTournamentConnectionRow
//                + ", kolumn " + btnAddTournamentConnectionCol);
//        btnAddTournamentConnectionRow++;

//        nbrTournamentConnections++;
        final int nbrTournaments = tournamentControls.size(); //nbr of rows minus the button
        ListBox control = new ListBox();
        setControlData(control, selectedTournament);
        tournamentControls.add(control);
        setWidget(nbrTournaments, 0, control);
//        if (nbrTournaments > 0) {
        Button drbtn = new Button("Ta bort");
        drbtn.addClickListener(new ClickListener() {
//            private int row = nbrTournaments;
            public void onClick(Widget button) {
                int rowToRemove = -1;
                for (int i = 0; i < getRowCount(); i++) {
                    if (button.equals(getWidget(i, 1))) {
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

//        Logger.debug("Lägger till TournamentConnection på rad " + nbrTournaments);
        setWidget(nbrTournaments, 1, drbtn);
        setWidget(nbrTournaments + 1, 0, btnAddTournamentConnection);

//        table.getFlexCellFormatter().setRowSpan(btnAddTournamentConnectionRow - 1,
//                btnAddTournamentConnectionCol,
//                nbrTournamentConnections);

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
        removeRow(row);
        tournamentControls.remove(row);
    }

    public void reset() {
        while (!tournamentControls.isEmpty()) {
            removeRow(0);
            tournamentControls.remove(0);
        }
    }

    private void setControlData(ListBox control) {
        setControlData(control, -1);
    }

    private void setControlData(ListBox control, long selectedId) {
        control.clear();

        for (int i = 0; i < tournaments.size(); ++i) {
            TournamentProxy tournament = tournaments.get(i);
            control.addItem(tournament.getName(), String.valueOf(tournament.getId()));
            if (tournament.getId() == selectedId) {
                control.setSelectedIndex(i);
            }
        }

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
            setControlData(control, control.getSelectedIndex());
        }

    }
}
