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

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.History;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.ClickListener;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.SourcesTableEvents;
import com.google.gwt.user.client.ui.TableListener;
import com.google.gwt.user.client.ui.Widget;
import java.util.List;
import net.europa13.taikai.web.client.CustomCallback;
import net.europa13.taikai.web.client.TaikaiAdminService;
import net.europa13.taikai.web.client.TaikaiAdminServiceAsync;
import net.europa13.taikai.web.client.TaikaiWeb;
import net.europa13.taikai.web.client.logging.Logger;
import net.europa13.taikai.web.proxy.TournamentProxy;

/**
 *
 * @author daniel
 */
public class TournamentListContent extends Content {

//    private final Grid tournamentGrid;
    private final SimplePanel panel = new SimplePanel();
    private final TournamentTable tournamentTable;
    private final TournamentPanel tournamentPanel;
    private TaikaiAdminServiceAsync taikaiService =
        GWT.create(TaikaiAdminService.class);
    private List<TournamentProxy> tournamentList;

    public TournamentListContent() {

        setTitle("Turneringar");
        createToolbar();

        tournamentTable = new TournamentTable();
        tournamentTable.addTableListener(new TableListener() {

            public void onCellClicked(SourcesTableEvents sender, int row, int col) {
                TournamentProxy tournament = tournamentList.get(row - 1);
                History.newItem("tournaments/" + tournament.getId());
            }
        });


        tournamentPanel = new TournamentPanel();
        tournamentPanel.addSaveListener(new ClickListener() {

            public void onClick(Widget arg0) {
//                Logger.debug("saveListener");
                TournamentProxy tournament = tournamentPanel.getTournament();
                storeTournament(tournament);
            }
        });

    }

    private void createToolbar() {

        Button btnCreateTaikai = new Button("Ny turnering...");
        btnCreateTaikai.addClickListener(new ClickListener() {

            public void onClick(Widget w) {
                History.newItem("tournaments/new");
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

            updateTournamentList();
        }
        else {
        }
    }

    private void storeTournament(TournamentProxy proxy) {
        
//        Logger.debug("storeTournament");
        
        taikaiService.storeTournament(proxy, new CustomCallback() {

            public void onSuccess(Object nothing) {
                updateTournamentList();
            }
        });
    }

    private void updateTournamentList() {
        if (TaikaiWeb.getSession().getTaikai() == null) {
            Logger.warn("Inget evenemang aktiverat. Listan över turneringar" +
                "kan inte hämtas.");
            return;
        }
        taikaiService.getTournaments(TaikaiWeb.getSession().getTaikai(),
            new CustomCallback<List<TournamentProxy>>() {

                public void onSuccess(List<TournamentProxy> tournamentList) {
                    TournamentListContent.this.tournamentList = tournamentList;
                    tournamentTable.setTournamentList(tournamentList);
                }
            });
    }

    @Override
    public void handleState(String state) {
        if ("new".equals(state)) {
            tournamentPanel.reset();
            tournamentPanel.setTaikai(TaikaiWeb.getSession().getTaikai());
            panel.setWidget(tournamentPanel);
        }
        else if (state.isEmpty()) {
            panel.setWidget(tournamentTable);
        }
        else {
            int tournamentId = Integer.parseInt(state);
            taikaiService.getTournament(tournamentId, new CustomCallback<TournamentProxy>() {

                public void onSuccess(TournamentProxy tournament) {
                    tournamentPanel.setTournament(tournament);
                    tournamentPanel.setTaikai(TaikaiWeb.getSession().getTaikai());
                    panel.setWidget(tournamentPanel);
                }
            });

        }

//        Logger.debug("TaikaiList" + state);
    }
}
