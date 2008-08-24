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
import com.google.gwt.user.client.ui.CheckBox;
import com.google.gwt.user.client.ui.ClickListener;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.Hyperlink;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.PushButton;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.Widget;
import java.util.List;
import net.europa13.taikai.web.client.Controllers;
import net.europa13.taikai.web.client.PlayerView;
import net.europa13.taikai.web.client.logging.Logger;
import net.europa13.taikai.web.proxy.PlayerProxy;

/**
 *
 * @author daniel
 */
public class PlayerListContent extends Content implements PlayerView {

    private final Panel panel = new SimplePanel();
    private final Grid playerGrid;
    private final PlayerContent playerContent = new PlayerContent();

    public PlayerListContent() {

        setTitle("Deltagare");

        playerGrid = new Grid(1, 4);
        playerGrid.setText(0, 0, "Id");
        playerGrid.setText(0, 1, "Förnamn");
        playerGrid.setText(0, 2, "Efternamn");
        playerGrid.setText(0, 3, "Incheckad");

        playerGrid.setStyleName("taikaiweb-Table");
        playerGrid.getRowFormatter().setStyleName(0, "taikaiweb-TableHeader");

        playerGrid.getCellFormatter().setStyleName(0, 3, "taikaiweb-TableLastColumn");
        panel.add(playerGrid);

        createToolbar();
    }

    private void createToolbar() {
        PushButton btnNewPlayer = new PushButton("Ny deltagare...");
        btnNewPlayer.addClickListener(new ClickListener() {

            public void onClick(Widget arg0) {
                MainPanel.setContent(playerContent);
            }
        });

        addControl(btnNewPlayer);
    }

    @Override
    public Panel getPanel() {
        return panel;
    }

    @Override
    public void setActive(boolean active) {
        super.setActive(active);

        if (active) {
            Controllers.playerControl.addPlayerView(this);
            Controllers.playerControl.updatePlayerList();
        }
        else {
            Controllers.playerControl.removePlayerView(this);
        }
    }

    protected void setPlayerList(List<PlayerProxy> playerList) {
        int columnCount = playerGrid.getColumnCount();

        int playerCount = playerList.size();
        playerGrid.resize(playerCount + 1, columnCount);

        for (int i = 0; i < playerCount; ++i) {
            final PlayerProxy player = playerList.get(i);

            ClickListener listener = new ClickListener() {

                public void onClick(Widget arg0) {
                    playerContent.setPlayer(player);
                    MainPanel.setContent(playerContent);
                }
            };

            playerGrid.setText(i + 1, 0, String.valueOf(player.getId()));
            Hyperlink name = new Hyperlink(player.getName(), "editPlayer");
            name.addClickListener(listener);
            playerGrid.setWidget(i + 1, 1, name);
            Hyperlink surname = new Hyperlink(player.getSurname(), "editPlayer");
            surname.addClickListener(listener);
            playerGrid.setWidget(i + 1, 2, surname);

            CheckBox checkedIn = new CheckBox();
            checkedIn.setChecked(player.isCheckedIn());
            checkedIn.setEnabled(false);
            playerGrid.setWidget(i + 1, 3, checkedIn);

        }

    }

    public void playerListUpdated(List<PlayerProxy> playerList) {
        setPlayerList(playerList);
    }

    @Override
    public void handleState(String state) {
        Logger.debug(state);
    }
}
