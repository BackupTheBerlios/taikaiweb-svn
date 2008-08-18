/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package net.europa13.taikai.web.client;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.AsyncCallback;
import java.util.ArrayList;
import java.util.List;
import net.europa13.taikai.web.client.logging.Logger;
import net.europa13.taikai.web.proxy.TournamentProxy;

/**
 *
 * @author daniel
 */
public class TournamentControl {
    
    private TaikaiAdminServiceAsync taikaiService =
        GWT.create(TaikaiAdminService.class);
    private List<TournamentView> views = new ArrayList<TournamentView>();
    private List<TournamentProxy> tournamentList;

    public TournamentControl() {
    }

    public void addTaikaiView(TournamentView view) {
        views.add(view);
    }

    protected void fireTournamentListUpdated() {
        for (TournamentView view : views) {
            view.tournamentListUpdated(tournamentList);
        }
    }
    
    public void removeTournamentView(TournamentView view) {
        views.remove(view);
    }

    public void storeTournament(TournamentProxy proxy) {
        taikaiService.storeTournament(proxy, new AsyncCallback() {

            public void onFailure(Throwable t) {
                Logger.error(t.getLocalizedMessage());
            }

            public void onSuccess(Object nothing) {
                updateTournamentList();
            }
        });
    }
    
    public void updateTournamentList() {
        taikaiService.getTaikais(new AsyncCallback<List<TournamentProxy>>() {

            public void onFailure(Throwable t) {
                Logger.error(t.getLocalizedMessage());
            }

            public void onSuccess(List<TournamentProxy> taikaiList) {
                TournamentControl.this.tournamentList = tournamentList;
                fireTournamentListUpdated();
            }
        });
    }
}