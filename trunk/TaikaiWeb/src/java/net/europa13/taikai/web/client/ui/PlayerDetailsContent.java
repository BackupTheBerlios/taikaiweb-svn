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
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.ClickListener;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.Widget;
import java.util.List;
import net.europa13.taikai.web.client.ContentHandlerNotFoundException;
import net.europa13.taikai.web.client.ListResult;
import net.europa13.taikai.web.client.NavigationPath;
import net.europa13.taikai.web.client.PlayerAdminService;
import net.europa13.taikai.web.client.PlayerAdminServiceAsync;
import net.europa13.taikai.web.client.TaikaiWeb;
import net.europa13.taikai.web.client.TournamentAdminService;
import net.europa13.taikai.web.client.TournamentAdminServiceAsync;
import net.europa13.taikai.web.client.logging.Logger;
import net.europa13.taikai.web.proxy.PlayerDetails;
import net.europa13.taikai.web.proxy.TournamentProxy;

/**
 *
 * @author daniel
 */
public class PlayerDetailsContent extends Content {
    
    private final PlayerPanel panel;
    private final PlayerAdminServiceAsync playerService =
        GWT.create(PlayerAdminService.class);
    private final TournamentAdminServiceAsync tournamentService =
        GWT.create(TournamentAdminService.class);
    
    private List<TournamentProxy> tournamentList;
    
    
    public PlayerDetailsContent() {
        //*********************************************************************
        // Panel
        panel = new PlayerPanel();
        Button btnSave = new Button("Spara", new ClickListener() {

            public void onClick(Widget sender) {
                PlayerDetails player = panel.getPlayer();
                storePlayer(player);
            }
        });
        
        addControl(btnSave);
    }
    
    @Override
    public Panel getPanel() {
        return panel;
    }
    
    @Override
    public Content handleState(NavigationPath path) throws ContentHandlerNotFoundException {

        if (path.isEmpty()) {
//            panel.reset();
//            panel.setTaikai(TaikaiWeb.getSession().getTaikai());
            openPlayerPanel(0);
            return this;
        }
        else {
            try {
                final int playerId = Integer.parseInt(path.getPathItem(0));
                openPlayerPanel(playerId);
            }
            catch (NumberFormatException ex) {
                Logger.error(path + " är ett ogiltigt värde för deltagare.");
                throw new ContentHandlerNotFoundException(ex);
            
            }
            
            return this;
        }
    }
    
    private void reloadPlayer(int playerId) {
        setPlayer(playerId);
    }
    
    private void storePlayer(final PlayerDetails details) {
        playerService.storePlayer(details, new AsyncCallback<Integer>() {

            public void onFailure(Throwable t) {
                Logger.error("Det gick inte att spara deltagare " + details.getId() + ".");
                Logger.debug(t.getLocalizedMessage());
            }

            public void onSuccess(Integer playerId) {
                Logger.info(details.getName() + " sparad.");
//                updatePlayerList();
                if (details.getId() == 0 && playerId > 0) {
                    reloadPlayer(playerId);
                }
            }
        });
    }
    
    private void openPlayerPanel(final int playerId) {

        panel.setTaikai(TaikaiWeb.getSession().getTaikai());

        tournamentService.getTournaments(TaikaiWeb.getSession().getTaikai(), new AsyncCallback<ListResult<TournamentProxy>>() {

            public void onFailure(Throwable t) {
                Logger.debug("Misslyckades med att hämta listor i openPlayerPanel.");
                Logger.debug(t.getLocalizedMessage());
            }

            public void onSuccess(ListResult<TournamentProxy> result) {
                Logger.debug("Hämtade listor i openPlayerPanel.");
                tournamentList = result.getList();
                setPlayer(playerId);
            }
        });
    }

    private void setPlayer(final int playerId) {
        Logger.trace("entering setPlayerPanelPlayer in PlayerContent");
        
        if (playerId == 0) {
            panel.reset();
            panel.setTournamentList(tournamentList);
        }
        else {
            playerService.getPlayer(playerId, new AsyncCallback<PlayerDetails>() {

                public void onFailure(Throwable t) {
                    Logger.debug("Misslyckades med att hämta deltagare " + playerId + " i setPlayerPanelPlayer.");
                    Logger.debug(t.getLocalizedMessage());
                }

                public void onSuccess(PlayerDetails player) {
                    Logger.trace("entering playerService.getPlayer.onSuccess in PlayerContent");

                    Logger.debug(player.toString());
                    
                    panel.setPlayer(player);
                    panel.setTournamentList(tournamentList);

                    Logger.trace("exiting playerService.getPlayer.onSuccess in PlayerContent");
                }
            });
        }
        Logger.trace("exiting setPlayerPanelPlayer in PlayerContent");
    }
}
