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
import com.google.gwt.user.client.ui.DecoratorPanel;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Hyperlink;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.Widget;
import java.util.List;
import net.europa13.taikai.web.client.Controllers;
import net.europa13.taikai.web.client.TournamentView;
import net.europa13.taikai.web.proxy.TournamentProxy;

/**
 *
 * @author daniel
 */
public class TournamentListContent extends Content implements TournamentView {

    private final Grid tournamentGrid;
    private final Panel panel = new SimplePanel();
    private final TournamentContent tournamentContent = 
            new TournamentContent();

    public TournamentListContent() {
        
        setTitle("Turneringar");
        
        tournamentGrid = new Grid(1, 4);
    
        tournamentGrid.setText(0, 0, "Id");
        tournamentGrid.setText(0, 1, "Namn");
        tournamentGrid.setText(0, 2, "Deltagare");
        tournamentGrid.setText(0, 3, "");
        
        tournamentGrid.setStyleName("taikaiweb-Table");
        tournamentGrid.getRowFormatter().setStyleName(0, "taikaiweb-TableHeader");
        tournamentGrid.getCellFormatter().setStyleName(0, 3, "taikaiweb-TableLastColumn");
        panel.add(tournamentGrid);
        
        createToolbar();
    }
    
    private void createToolbar() {
//        final Content createTournamentContent = new TournamentContent();

        Button btnCreateTaikai = new Button("Ny turnering...");
        btnCreateTaikai.addClickListener(new ClickListener() {

            public void onClick(Widget w) {
                MainPanel.setContent(tournamentContent);
            }
        });

        addControl(btnCreateTaikai);
    }
    
    public Panel getPanel() {
        return panel;
    }


    @Override
    public void setActive(boolean active) {
        super.setActive(active);
        
        if (active) {
            Controllers.tournamentControl.addTournamentView(this);
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
            final TournamentProxy tournament = tournamentList.get(i);
            
            ClickListener listener = new ClickListener() {

                public void onClick(Widget arg0) {
                    tournamentContent.setTournament(tournament);
                    MainPanel.setContent(tournamentContent);
                }
            };
            
            tournamentGrid.setText(i + 1, 0, String.valueOf(tournament.getId()));
            Hyperlink name = new Hyperlink(tournament.getName(), "editTournament");
            name.addClickListener(listener);
            tournamentGrid.setWidget(i + 1, 1, name);
//            tournamentGrid.setText(i + 1, 2, String.valueOf(tournament.getPlayerCount()));
//            tournamentGrid.setText(i + 1, 3, String.valueOf(tournament.getTournamentCount()));
        }
    }

    public void tournamentListUpdated(List<TournamentProxy> tournamentList) {
//        Logger.trace("TournamentListPanel.tournamentListUpdated()");
        setTournamentList(tournamentList);
    }

}
