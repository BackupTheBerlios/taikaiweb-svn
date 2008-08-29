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
import java.util.List;
import net.europa13.taikai.web.client.logging.Logger;
import net.europa13.taikai.web.proxy.TournamentProxy;

/**
 *
 * @author jonatan
 */
public class PlayerTournamentsList extends FlexTable {

    private Button btnAddTournamentConnection;    
    private List<TournamentProxy> tournaments;
    
    PlayerTournamentsList () {
                
        btnAddTournamentConnection = new Button("En till turnering");
        btnAddTournamentConnection.addClickListener(new ClickListener() {

            public void onClick(Widget arg0) {
                addTournamentConnection();
            }
        });
        
        setWidget(0, 0, btnAddTournamentConnection);
        addTournamentConnection();
    }
        
    private void addTournamentConnection() {
//        table.insertCell(btnAddTournamentConnectionRow, btnAddTournamentConnectionCol);
//        table.insertCell(btnAddTournamentConnectionRow, btnAddTournamentConnectionCol);
//        table.setWidget(btnAddTournamentConnectionRow, btnAddTournamentConnectionCol, new ListBox());
//        Logger.debug("Ny ListBox på rad " + btnAddTournamentConnectionRow
//                + ", kolumn " + btnAddTournamentConnectionCol);
//        btnAddTournamentConnectionRow++;

//        nbrTournamentConnections++;
        int nbrTournaments = getRowCount() - 1; //nbr of rows minus the button
        setWidget(nbrTournaments, 0, new ListBox());
        if (nbrTournaments > 0) {
            Button drbtn = new Button("Ta bort");
            setWidget(nbrTournaments, 1, drbtn);
            drbtn.addClickListener(new ClickListener() {

                public void onClick(Widget arg0) {
                    int rowToRemove = -1;
                    for (int i = 1; i < getRowCount(); i++) {
                        // starts at 1 because fist line has no element at col 1
                        if (arg0.equals(getWidget(i, 1))) {
                            rowToRemove = i;
                            break;
                        }
                    }
                    if (rowToRemove >= 0) {
                        removeTournamentConnection(rowToRemove);
                    }
                }
            });
        }
        
//        Logger.debug("Lägger till TournamentConnection på rad " + nbrTournaments);
        setWidget(nbrTournaments + 1, 0, btnAddTournamentConnection);
        
//        table.getFlexCellFormatter().setRowSpan(btnAddTournamentConnectionRow - 1,
//                btnAddTournamentConnectionCol,
//                nbrTournamentConnections);

    }
    
    private void removeTournamentConnection(int row) {
        removeRow(row);
    }
    
    public void setTournamentList(List<TournamentProxy> tournaments) {
        Logger.info("setTournaments size = " + tournaments.size());
        this.tournaments = tournaments;
//        throw new RuntimeException(String.valueOf(tournaments));
//        lbTournaments.clear();
        ListBox lb;
        for (int i = 0; i < getRowCount() - 1; i++) {
            lb = (ListBox)getWidget(i, 0);
            for (TournamentProxy tournament : tournaments) {
                lb.addItem(tournament.getName(), String.valueOf(tournament.getId()));
            }
        }
    }
    
}
