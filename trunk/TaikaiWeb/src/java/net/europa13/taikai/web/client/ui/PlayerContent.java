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
import net.europa13.taikai.web.client.PlayerAdminService;
import net.europa13.taikai.web.client.PlayerAdminServiceAsync;
import net.europa13.taikai.web.client.TaikaiWeb;
import net.europa13.taikai.web.client.TournamentAdminService;
import net.europa13.taikai.web.client.TournamentAdminServiceAsync;
import net.europa13.taikai.web.client.logging.Logger;
import net.europa13.taikai.web.proxy.PlayerDetails;
import net.europa13.taikai.web.proxy.PlayerProxy;
import net.europa13.taikai.web.proxy.TaikaiProxy;
import net.europa13.taikai.web.proxy.TournamentProxy;

/**
 *
 * @author Daniel Wentzel
 */
public class PlayerContent extends Content {

    private final SimplePanel panel = new SimplePanel();
    private final PlayerTable playerTable;
    private final PlayerPanel playerPanel;
    private final PlayerAdminServiceAsync playerService =
        GWT.create(PlayerAdminService.class);
    private final TournamentAdminServiceAsync tournamentService = 
        GWT.create(TournamentAdminService.class);
    private List<PlayerProxy> playerList;
    private final String historyToken;
    private final Button btnNewPlayer;
    private final Button btnImportPlayers;
    
    private String state;
    
    public PlayerContent(final String historyToken) {

        setTitle("Deltagare");

        this.historyToken = historyToken;

        //*********************************************************************
        // Toolbar
        btnNewPlayer = new Button("Ny deltagare...", new ClickListener() {

            public void onClick(Widget arg0) {
                History.newItem(historyToken + "/new");
            }
        });
        addControl(btnNewPlayer);

        btnImportPlayers = new Button("Importera...", new ClickListener() {

            public void onClick(Widget arg0) {
                History.newItem(historyToken + "/import");
            }
        });
        addControl(btnImportPlayers);

        //*********************************************************************
        // Table
        playerTable = new PlayerTable();
        playerTable.addTableListener(new TableListener() {

            public void onCellClicked(SourcesTableEvents sender, int row, int col) {
                PlayerProxy player = playerList.get(row - 1);
                History.newItem(historyToken + "/" + player.getId());
            }
        });

        //*********************************************************************
        // Panel
        playerPanel = new PlayerPanel();
        playerPanel.addSaveListener(new ClickListener() {

            public void onClick(Widget arg0) {
                PlayerDetails player = playerPanel.getPlayer();
                storePlayer(player);
            }
        });
    }

    @Override
    public Panel getPanel() {
        return panel;
    }

    @Override
    public void setActive(boolean active) {
        super.setActive(active);

        if (active) {
            updateControls();
            updatePlayerList();
        }

    }

    private void storePlayer(final PlayerDetails details) {
        playerService.storePlayer(details, new AsyncCallback() {

            public void onFailure(Throwable t) {
                Logger.error("Det gick inte att spara deltagare " + details.getId() + ".");
                Logger.debug(t.getLocalizedMessage());
            }

            public void onSuccess(Object nothing) {
                Logger.info(details.getName() + " sparad.");
                updatePlayerList();
            }
        });
    }

    private void updateControls() {
        TaikaiProxy taikai = TaikaiWeb.getSession().getTaikai();
        
        if (taikai == null) {
            btnNewPlayer.setEnabled(false);
            btnImportPlayers.setEnabled(false);
        }
        else {
            btnNewPlayer.setEnabled(true);
            btnImportPlayers.setEnabled(true);
        }
        
        if ("details".equals(state)) {
            btnImportPlayers.setVisible(false);
        }
        else {
            btnImportPlayers.setVisible(true);
        }
        
    }

    private void updatePlayerList() {
        if (TaikaiWeb.getSession().getTaikai() == null) {
            Logger.warn("Inget evenemang aktiverat. Listan över deltagare " +
                "kan inte hämtas.");
            return;
        }
        playerService.getPlayers(TaikaiWeb.getSession().getTaikai(),
            new CustomCallback<ListResult<PlayerProxy>>() {

                public void onSuccess(ListResult<PlayerProxy> result) {
                    playerList = result.getList();
                    playerTable.setPlayerList(playerList);
                }
            });
    }

    @Override
    public void handleState(String stateToken) {
        
        tournamentService.getTournaments(TaikaiWeb.getSession().getTaikai(), new AsyncCallback<ListResult<TournamentProxy>>() {

            public void onFailure(Throwable arg0) {
                Logger.error("Fel i player handle state.");
                //throw new UnsupportedOperationException("Not supported yet.");
            }

            public void onSuccess(ListResult<TournamentProxy> result) {
                Logger.info("Rätt i player handle state.");
                playerPanel.getActiveTournamentsTable().setTournamentList(result.getList());
            }
        });
        
        
        if ("new".equals(stateToken)) {
            state = "details";
            
            playerPanel.reset();
            playerPanel.setTaikai(TaikaiWeb.getSession().getTaikai());
            panel.setWidget(playerPanel);
        }
        else if ("import".equals(stateToken)) {
            state = "import";
            Logger.debug("Import är inte implementerad");
        }
        else if (stateToken.isEmpty()) {
            state = "list";
            panel.setWidget(playerTable);
        }
        else {
            try {
                final int playerId = Integer.parseInt(stateToken);
                playerService.getPlayer(playerId, new AsyncCallback<PlayerDetails>() {

                    public void onFailure(Throwable t) {
                        Logger.error("Det gick inte att hitta deltagare " + playerId + ".");
                        Logger.debug(t.getLocalizedMessage());
                    }

                    public void onSuccess(PlayerDetails player) {
                        state = "details";
                        
                        playerPanel.setPlayer(player);
                        playerPanel.setTaikai(TaikaiWeb.getSession().getTaikai());
                        panel.setWidget(playerPanel);
                    }
                });
            }
            catch (NumberFormatException ex) {
                Logger.error(stateToken + " är ett ogiltigt värde för deltagare.");
            }
        }
    }
    
//    private void preparePlayerPanel() {
//        
//    }
}
