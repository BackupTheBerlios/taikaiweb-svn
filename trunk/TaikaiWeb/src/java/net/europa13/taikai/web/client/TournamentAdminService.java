/*
 * TournamentAdminService.java
 *
 * Created on August 26, 2008, 9:13 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */
package net.europa13.taikai.web.client;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
import net.europa13.taikai.web.proxy.TaikaiProxy;
import net.europa13.taikai.web.proxy.TournamentProxy;

/**
 *
 * @author daniel
 */
@RemoteServiceRelativePath(value = "tournamentadminservice")
public interface TournamentAdminService extends RemoteService {

    public TournamentProxy getTournament(int tournamentId);

    public ListResult<TournamentProxy> getTournaments(TaikaiProxy proxy);

    public void storeTournament(TournamentProxy proxy);
}
