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

import com.google.gwt.user.client.rpc.AsyncCallback;
import net.europa13.taikai.web.proxy.PlayerDetails;
import net.europa13.taikai.web.proxy.PlayerListKey;
import net.europa13.taikai.web.proxy.PlayerProxy;
import net.europa13.taikai.web.proxy.TaikaiProxy;
import net.europa13.taikai.web.proxy.TournamentProxy;

/**
 *
 * @author daniel
 */
public interface PlayerAdminServiceAsync {

    public void getPlayer(int playerId, AsyncCallback<PlayerDetails> callback);

//    public void getPlayers(TaikaiProxy proxy, AsyncCallback<ListResult<PlayerProxy>> callback);
//
//    public void getPlayers(TournamentProxy proxy, AsyncCallback<ListResult<PlayerProxy>> callback);
    
    public void getPlayers(PlayerListKey key, AsyncCallback<ListResult<PlayerProxy>> callback);

    public void storePlayer(PlayerDetails details, AsyncCallback<Integer> callback);
}
