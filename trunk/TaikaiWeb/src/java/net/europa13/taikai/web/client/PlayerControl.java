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
package net.europa13.taikai.web.client;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.AsyncCallback;
import java.util.ArrayList;
import java.util.List;
import net.europa13.taikai.web.client.logging.Logger;
import net.europa13.taikai.web.proxy.PlayerProxy;

/**
 *
 * @author daniel
 */
public class PlayerControl {

    private TaikaiAdminServiceAsync taikaiService =
            GWT.create(TaikaiAdminService.class);
    private List<PlayerView> playerViews = new ArrayList<PlayerView>();
    private List<PlayerProxy> playerList;
    
    public PlayerControl() {
        
    }
    
    public void addPlayerView(PlayerView view) {
        playerViews.add(view);
    }
    
    protected void firePlayerListUpdated() {
        for (PlayerView view : playerViews) {
            view.playerListUpdated(playerList);
        }
    }
    
    
    
    public void removePlayerView(PlayerView view) {
        playerViews.remove(view);
    }
    
    public void storePlayer(PlayerProxy player) {
        taikaiService.storePlayer(player, new AsyncCallback() {

            public void onFailure(Throwable t) {
                Logger.error(t.getLocalizedMessage());
            }

            public void onSuccess(Object nothing) {
                updatePlayerList();
            }
        });
    }
    
    public void updatePlayerList() {
        if (TaikaiWeb.getSession().getTaikai() == null) {
            Logger.warn("Inget evenemang aktiverat. Listan över deltagare" +
                    "kan inte hämtas.");
            return;
        }
        taikaiService.getPlayers(TaikaiWeb.getSession().getTaikai(), 
                new AsyncCallback<List<PlayerProxy>>() {

            public void onFailure(Throwable t) {
                Logger.error(t.getLocalizedMessage());
            }

            public void onSuccess(List<PlayerProxy> playerList) {
//                Logger.trace("TournamentControl.updateTournamentList.onSuccess()");
                PlayerControl.this.playerList = playerList;
                firePlayerListUpdated();
            }
        });
    }
}
