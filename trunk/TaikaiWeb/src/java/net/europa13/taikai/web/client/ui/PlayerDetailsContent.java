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
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.Widget;
import java.util.ArrayList;
import java.util.List;
import net.europa13.taikai.web.client.ContentHandlerNotFoundException;
import net.europa13.taikai.web.client.NavigationPath;
import net.europa13.taikai.web.client.TaikaiWeb;
import net.europa13.taikai.web.client.logging.Logger;
import net.europa13.taikai.web.client.rpc.LoadPlayerDetailsRequest;
import net.europa13.taikai.web.client.rpc.LoadTournamentsRequest;
import net.europa13.taikai.web.client.rpc.PlayerDetailsTarget;
import net.europa13.taikai.web.client.rpc.RpcScheduler;
import net.europa13.taikai.web.client.rpc.StorePlayerRequest;
import net.europa13.taikai.web.client.rpc.TournamentListTarget;
import net.europa13.taikai.web.proxy.PlayerDetails;
import net.europa13.taikai.web.proxy.TournamentListKey;
import net.europa13.taikai.web.proxy.TournamentProxy;

/**
 *
 * @author daniel
 */
public class PlayerDetailsContent extends Content implements TournamentListTarget, PlayerDetailsTarget {

    private final PlayerPanel panel;
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

    private void storePlayer(final PlayerDetails details) {
        RpcScheduler.queueRequest(new StorePlayerRequest(details, this, details.getId() == 0));
    }

    private void openPlayerPanel(final int playerId) {

        int taikaiId = TaikaiWeb.getSession().getTaikaiId();
        panel.setTaikai(TaikaiWeb.getSession().getTaikai());

        if (playerId == 0) {
            RpcScheduler.queueRequest(new LoadTournamentsRequest(new TournamentListKey(taikaiId), new TournamentListTarget() {

                public void setTournaments(List<? extends TournamentProxy> tournaments) {
                    PlayerDetailsContent.this.setTournaments(tournaments);
                    panel.reset();
                    panel.setTournamentList(tournaments);
                }
            }));
        }
        else {
            RpcScheduler.queueRequest(new LoadTournamentsRequest(new TournamentListKey(taikaiId), this));
            RpcScheduler.queueRequest(new LoadPlayerDetailsRequest(playerId, this));
        }
        
    }

    public void setPlayer(PlayerDetails player) {
        panel.setPlayer(player);
        panel.setTournamentList(tournamentList);
    }

    public void setTournaments(List<? extends TournamentProxy> tournaments) {
        this.tournamentList = new ArrayList<TournamentProxy>(tournaments);
    }

}
