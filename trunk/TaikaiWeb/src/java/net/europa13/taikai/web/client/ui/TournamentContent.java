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
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.ClickListener;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.SourcesTableEvents;
import com.google.gwt.user.client.ui.TableListener;
import com.google.gwt.user.client.ui.Widget;
import java.util.List;
import net.europa13.taikai.web.client.CustomCallback;
import net.europa13.taikai.web.client.ListResult;
import net.europa13.taikai.web.client.TaikaiAdminService;
import net.europa13.taikai.web.client.TaikaiAdminServiceAsync;
import net.europa13.taikai.web.client.TaikaiWeb;
import net.europa13.taikai.web.client.TournamentAdminService;
import net.europa13.taikai.web.client.TournamentAdminServiceAsync;
import net.europa13.taikai.web.client.logging.Logger;
import net.europa13.taikai.web.proxy.TaikaiProxy;
import net.europa13.taikai.web.proxy.TournamentProxy;

/**
 *
 * @author daniel
 */
public class TournamentContent extends Content {

//    private final Grid tournamentGrid;
    private final SimplePanel panel = new SimplePanel();
    private final TournamentTable tournamentTable;
    private final TournamentPanel tournamentPanel;
    private final TournamentAdminServiceAsync tournamentService =
        GWT.create(TournamentAdminService.class);
    private List<TournamentProxy> tournamentList;
    private final String historyToken;
    private final Button btnNewTournament;

    
    
    public TournamentContent(final String historyToken) {

        setTitle("Turneringar");

        this.historyToken = historyToken;

        //*********************************************************************
        // Toolbar
        btnNewTournament = new Button("Ny turnering...", new ClickListener() {

            public void onClick(Widget w) {
                History.newItem(historyToken + "/new");
            }
        });

        addControl(btnNewTournament);


        //*********************************************************************
        // Table
        tournamentTable = new TournamentTable();
        tournamentTable.addTableListener(new TableListener() {

            public void onCellClicked(SourcesTableEvents sender, int row, int col) {
                TournamentProxy tournament = tournamentList.get(row - 1);
                History.newItem(historyToken + "/" + tournament.getId());
            }
        });

        //*********************************************************************
        // Panel
        tournamentPanel = new TournamentPanel();
        tournamentPanel.addSaveListener(new ClickListener() {

            public void onClick(Widget arg0) {
                TournamentProxy tournament = tournamentPanel.getTournament();
                storeTournament(tournament);
            }
        });

    }

    public Panel getPanel() {
        return panel;
    }

    @Override
    public void setActive(boolean active) {
        super.setActive(active);

        if (active) {
            updateControls();
            updateTournamentList();

        }
    }

    private void storeTournament(final TournamentProxy proxy) {

        tournamentService.storeTournament(proxy, new AsyncCallback() {

            public void onFailure(Throwable t) {
                Logger.error("Det gick inte att spara turnering " + proxy.getId() + ".");
                Logger.debug(t.getLocalizedMessage());
            }

            public void onSuccess(Object nothing) {
                Logger.info(proxy.getName() + " sparad.");
                updateTournamentList();
            }
        });
    }

    private void updateControls() {
        TaikaiProxy taikai = TaikaiWeb.getSession().getTaikai();

        if (taikai == null) {
            btnNewTournament.setEnabled(false);
        }
        else {
            btnNewTournament.setEnabled(true);
        }
        
    }

    private void updateTournamentList() {
        if (TaikaiWeb.getSession().getTaikai() == null) {
            Logger.warn("Inget evenemang aktiverat. Listan över turneringar " +
                "kan inte hämtas.");
            return;
        }
        tournamentService.getTournaments(TaikaiWeb.getSession().getTaikai(),
            new CustomCallback<ListResult<TournamentProxy>>() {

                public void onSuccess(ListResult<TournamentProxy> result) {
                    tournamentList = result.getList();
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
            try {
                final int tournamentId = Integer.parseInt(state);
                tournamentService.getTournament(tournamentId, new AsyncCallback<TournamentProxy>() {

                    public void onFailure(Throwable t) {
                        Logger.error("Det gick inte att hitta turnering " + tournamentId + ".");
                        Logger.debug(t.getLocalizedMessage());
                    }

                    public void onSuccess(TournamentProxy tournament) {
                        tournamentPanel.setTournament(tournament);
                        tournamentPanel.setTaikai(TaikaiWeb.getSession().getTaikai());
                        panel.setWidget(tournamentPanel);
                    }
                });
            }
            catch (NumberFormatException ex) {
                Logger.error(state + " är ett ogiltigt värde för turneringar.");
            }

        }

    }
}
