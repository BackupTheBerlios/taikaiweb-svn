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
import com.google.gwt.user.client.ui.Widget;
import net.europa13.taikai.web.client.ContentHandlerNotFoundException;
import net.europa13.taikai.web.client.NavigationPath;
import net.europa13.taikai.web.client.TaikaiWeb;
import net.europa13.taikai.web.client.TournamentAdminService;
import net.europa13.taikai.web.client.TournamentAdminServiceAsync;
import net.europa13.taikai.web.client.logging.Logger;
import net.europa13.taikai.web.proxy.TournamentDetails;
import net.europa13.taikai.web.proxy.TournamentProxy;

/**
 *
 * @author daniel
 */
public class TournamentDetailsContent extends Content {
    
    private final TournamentAdminServiceAsync tournamentService =
        GWT.create(TournamentAdminService.class);
    private final TournamentPanel panel;
    private final Button btnGenerate;
    
    public TournamentDetailsContent() {
        //*********************************************************************
        // Panel
        panel = new TournamentPanel();
        Button btnSave = new Button("Spara", new ClickListener() {

            public void onClick(Widget sender) {
                TournamentDetails tournament = panel.getTournament();
                storeTournament(tournament);
            }
        });
        addControl(btnSave);
        
        btnGenerate = new Button("Generera...", new ClickListener() {

            public void onClick(Widget sender) {
                TournamentProxy tournament = panel.getTournament();
                History.newItem("tournaments/confirmGenerate/" + tournament.getId());
            }
            
        });
        addControl(btnGenerate);
    }

    @Override
    public Panel getPanel() {
        return panel;
    }
    
    private void reloadTournament(final int tournamentId) {
        
        tournamentService.getTournament(tournamentId, new AsyncCallback<TournamentDetails>() {

            public void onFailure(Throwable t) {
                Logger.error(t.getLocalizedMessage());
                Logger.debug("reloadTournament in TournamentDetailsContent: failed to reload tournament.");
            }

            public void onSuccess(TournamentDetails tournament) {
                setTournament(tournament);
            }
        });
        
    }
    
    private void setTournament(TournamentDetails details) {
        if (details != null) {
            btnGenerate.setEnabled(true);
        }
        else {
            btnGenerate.setEnabled(false);
        }
        
        panel.setTournament(details);
    }
    
    private void storeTournament(final TournamentDetails details) {

        tournamentService.storeTournament(details, new AsyncCallback<Integer>() {

            public void onFailure(Throwable t) {
                Logger.error("Det gick inte att spara turnering " + details.getId() + ".");
                Logger.debug(t.getLocalizedMessage());
            }

            public void onSuccess(Integer tournamentId) {
                Logger.info(details.getName() + " sparad.");
                if (details.getId() == 0 && tournamentId > 0) {
                    reloadTournament(tournamentId);
                }
            }
        });
    }
    
    @Override
    public Content handleState(NavigationPath path) throws ContentHandlerNotFoundException {
        
        Logger.debug("handleState in TournamentDetailsContent: path is " + path);
        
        // If state is empty a new tournament should be created.
        if (path.isEmpty()) {
//            panel.reset();
            setTournament(null);
            panel.setTaikai(TaikaiWeb.getSession().getTaikai());
            return this;
        }
        else {
            try {
                final int tournamentId = Integer.parseInt(path.getPathItem(0));
                tournamentService.getTournament(tournamentId, new AsyncCallback<TournamentDetails>() {

                    public void onFailure(Throwable t) {
                        Logger.error("Det gick inte att hitta turnering " + tournamentId + ".");
                        Logger.debug(t.getLocalizedMessage());
                    }

                    public void onSuccess(TournamentDetails tournament) {
                        setTournament(tournament);
                        panel.setTaikai(TaikaiWeb.getSession().getTaikai());
//                        panel.setWidget(panel);
                    }
                });
            }
            catch (NumberFormatException ex) {
                Logger.error(path + " är ett ogiltigt värde för turneringar.");
                throw new ContentHandlerNotFoundException(ex);
            }

            return this;
        }

    }

}
