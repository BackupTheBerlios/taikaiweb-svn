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
import java.util.List;
import net.europa13.taikai.web.proxy.TaikaiProxy;
import net.europa13.taikai.web.proxy.TournamentDetails;
import net.europa13.taikai.web.proxy.TournamentGenerationInfo;
import net.europa13.taikai.web.proxy.TournamentListKey;
import net.europa13.taikai.web.proxy.TournamentProxy;

/**
 *
 * @author daniel
 */
public interface TournamentAdminServiceAsync {

    public void generate(int tournamentId, AsyncCallback<Void> callback);
    
    public void getAvailableSeeds(int tournamentId, AsyncCallback<List<Integer>> callback);

    public void getGenerationInfo(int tournamentId, AsyncCallback<TournamentGenerationInfo> callback);
    
    public void getTournament(int tournamentId, AsyncCallback<TournamentDetails> callback);

//    public void getTournaments(TaikaiProxy proxy, AsyncCallback<ListResult<TournamentProxy>> callback);
    public void getTournaments(TournamentListKey key, AsyncCallback<ListResult<TournamentProxy>> callback);
    

    public void storeTournament(TournamentDetails details, AsyncCallback<Integer> callback);
}
