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

import com.google.gwt.user.client.History;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.ClickListener;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.SourcesTableEvents;
import com.google.gwt.user.client.ui.TableListener;
import com.google.gwt.user.client.ui.Widget;
import java.util.ArrayList;
import java.util.List;
import net.europa13.taikai.web.client.ContentHandlerNotFoundException;
import net.europa13.taikai.web.client.NavigationPath;
import net.europa13.taikai.web.client.TaikaiWeb;
import net.europa13.taikai.web.client.logging.Logger;
import net.europa13.taikai.web.client.rpc.LoadTournamentsRequest;
import net.europa13.taikai.web.client.rpc.RpcScheduler;
import net.europa13.taikai.web.client.rpc.TournamentListTarget;
import net.europa13.taikai.web.proxy.TaikaiProxy;
import net.europa13.taikai.web.proxy.TournamentListKey;
import net.europa13.taikai.web.proxy.TournamentProxy;

/**
 *
 * @author daniel
 */
public class TournamentContent extends Content implements TournamentListTarget {

    private final TournamentTable tournamentTable;
    private List<TournamentProxy> tournamentList;
    private final String historyToken;
    private final Button btnNewTournament;
    private final TournamentDetailsContent tournamentDetailsContent;
    private final TournamentConfirmGenerateContent confirmGenerateContent;

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


        tournamentDetailsContent = new TournamentDetailsContent();
        confirmGenerateContent = new TournamentConfirmGenerateContent();
    }

    public Panel getPanel() {
        return tournamentTable;
    }

    @Override
    public void setActive(boolean active) {
        super.setActive(active);

        if (active) {
            updateControls();
            updateTournamentList();

        }
    }

    public void setTournaments(List<? extends TournamentProxy> tournaments) {
        tournamentList = new ArrayList<TournamentProxy>(tournaments);
        tournamentTable.setTournamentList(tournamentList);
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
            tournamentTable.reset();
            return;
        }

        RpcScheduler.queueRequest(new LoadTournamentsRequest(new TournamentListKey(TaikaiWeb.getSession().getTaikaiId()), this));

    }

    @Override
    public Content handleState(NavigationPath path) throws ContentHandlerNotFoundException {
        if ("new".equals(path.getPathItem(0))) {
            return tournamentDetailsContent.handleState(path.getSubPath());
        }
        else if (path.isEmpty()) {
            return this;
        }
        else if ("confirmGenerate".equals(path.getPathItem(0))) {
            return confirmGenerateContent.handleState(path.getSubPath());
        }
        else {
            try {
                return tournamentDetailsContent.handleState(path);
            }
            catch (ContentHandlerNotFoundException ex) {
                return this;
            }
        }

    }
}
