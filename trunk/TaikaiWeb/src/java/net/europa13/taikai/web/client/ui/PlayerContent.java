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
import com.google.gwt.user.client.ui.SourcesTableEvents;
import com.google.gwt.user.client.ui.TableListener;
import com.google.gwt.user.client.ui.Widget;
import java.util.ArrayList;
import java.util.List;
import net.europa13.taikai.web.client.ContentHandlerNotFoundException;
import net.europa13.taikai.web.client.NavigationPath;
import net.europa13.taikai.web.client.TaikaiWeb;
import net.europa13.taikai.web.client.logging.Logger;
import net.europa13.taikai.web.client.rpc.LoadPlayersRequest;
import net.europa13.taikai.web.client.rpc.PlayerListTarget;
import net.europa13.taikai.web.client.rpc.RpcScheduler;
import net.europa13.taikai.web.proxy.PlayerListKey;
import net.europa13.taikai.web.proxy.PlayerProxy;
import net.europa13.taikai.web.proxy.TaikaiProxy;

/**
 *
 * @author Daniel Wentzel
 */
public class PlayerContent extends Content implements PlayerListTarget {


    private final PlayerTable playerTable;
    private List<PlayerProxy> playerList;
    private final String historyToken;
    private final Button btnNewPlayer;
    private final Button btnImportPlayers;
    private final PlayerDetailsContent playerDetailsContent;
    
    private PlayerListKey playerListKey;

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
        playerTable.setCaption("Deltagare");
        playerTable.addTableListener(new TableListener() {

            public void onCellClicked(SourcesTableEvents sender, int row, int col) {
                if (row > 1) {
                    PlayerProxy player = playerList.get(row - 2);
                    History.newItem(historyToken + "/" + player.getId());
                }
            }
        });

        playerDetailsContent = new PlayerDetailsContent();
    }

    @Override
    public Widget getPanel() {
        return playerTable;
    }

    @Override
    public void setActive(boolean active) {
        super.setActive(active);

        if (active) {
            updateControls();
            updatePlayerList();
        }

    }

    public void setPlayers(List<? extends PlayerProxy> players) {
        this.playerList = new ArrayList<PlayerProxy>(players);
        playerTable.setPlayerList(playerList);
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
    }

    private void updatePlayerList() {
        if (TaikaiWeb.getSession().getTaikai() == null) {
            Logger.warn("Inget evenemang aktiverat. Listan över deltagare " +
                "kan inte hämtas.");
            playerTable.reset();
            return;
        }
        RpcScheduler.queueRequest(new LoadPlayersRequest(playerListKey, this));

    }
    
    @Override
    public Content handleState(NavigationPath path) throws ContentHandlerNotFoundException {

        if ("new".equals(path.getPathItem(0))) {
            return playerDetailsContent.handleState(path.getSubPath());
        }
        else if ("import".equals(path)) {
            Logger.debug("Import är inte implementerad");
            return this;
        }
        else if (path.isEmpty()) {
            playerListKey = new PlayerListKey(PlayerListKey.Owner.TAIKAI, TaikaiWeb.getSession().getTaikaiId());
            return this;
        }
        else if ("tournament".equals(path.getPathItem(0))) {
            try {
                int tournamentId = Integer.parseInt(path.getPathItem(1)); 
                playerListKey = new PlayerListKey(PlayerListKey.Owner.TOURNAMENT, tournamentId);
                return this;
            }
            catch (NumberFormatException ex) {
                return this;
            }
        }
        else {
            try {
                return playerDetailsContent.handleState(path);
            }
            catch (ContentHandlerNotFoundException ex) {
                return this;
            }
        }
    }
}
