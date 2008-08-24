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
import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
import java.util.List;
import net.europa13.taikai.web.proxy.PlayerProxy;
import net.europa13.taikai.web.proxy.TaikaiProxy;
import net.europa13.taikai.web.proxy.TournamentProxy;

/**
 *
 * @author Daniel Wentzel
 */
@RemoteServiceRelativePath(value="taikaiadminservice")
public interface TaikaiAdminService extends RemoteService{
    
    public TaikaiProxy getTaikai(int id);
    public List<TaikaiProxy> getTaikais();
    
    public List<TournamentProxy> getTournaments(TaikaiProxy proxy);
    public List<PlayerProxy> getPlayers(TaikaiProxy proxy);
    public List<PlayerProxy> getPlayers(TournamentProxy proxy);
    
    public void storeTaikai(TaikaiProxy proxy);
    public void storeTournament(TournamentProxy proxy);
    public void storePlayer(PlayerProxy proxy);
}
