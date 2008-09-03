/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package net.europa13.taikai.web.client.ui;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.ClickListener;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.Widget;
import net.europa13.taikai.web.client.ContentHandlerNotFoundException;
import net.europa13.taikai.web.client.NavigationPath;
import net.europa13.taikai.web.client.TaikaiWeb;
import net.europa13.taikai.web.client.TournamentAdminService;
import net.europa13.taikai.web.client.TournamentAdminServiceAsync;
import net.europa13.taikai.web.client.logging.Logger;
import net.europa13.taikai.web.proxy.TournamentDetails;

/**
 *
 * @author daniel
 */
public class TournamentDetailsContent extends Content {
    
    private final TournamentAdminServiceAsync tournamentService =
        GWT.create(TournamentAdminService.class);
    private final TournamentPanel panel;
    
    public TournamentDetailsContent() {
        //*********************************************************************
        // Panel
        panel = new TournamentPanel();
        Button btnSave = new Button("Spara", new ClickListener() {

            public void onClick(Widget sender) {
                TournamentDetails tournament = panel.getTournament();
                storeTournament(tournament);
            }
        });
        
        addControl(btnSave);
    }

    @Override
    public Panel getPanel() {
        return panel;
    }
    
    private void storeTournament(final TournamentDetails details) {

        tournamentService.storeTournament(details, new AsyncCallback() {

            public void onFailure(Throwable t) {
                Logger.error("Det gick inte att spara turnering " + details.getId() + ".");
                Logger.debug(t.getLocalizedMessage());
            }

            public void onSuccess(Object nothing) {
                Logger.info(details.getName() + " sparad.");
//                updateTournamentList();
            }
        });
    }
    
    @Override
    public Content handleState(NavigationPath path) throws ContentHandlerNotFoundException {
        
        Logger.debug("handleState in TournamentDetailsContent: path is " + path);
        
        // If state is empty a new tournament should be created.
        if (path.isEmpty()) {
            panel.reset();
            panel.setTaikai(TaikaiWeb.getSession().getTaikai());
            return this;
        }
        else {
            try {
                final int tournamentId = Integer.parseInt(path.getPathItem(0));
                tournamentService.getTournament(tournamentId, new AsyncCallback<TournamentDetails>() {

                    public void onFailure(Throwable t) {
                        Logger.error("Det gick inte att hitta turnering " + tournamentId + ".");
                        Logger.debug(t.getLocalizedMessage());
                    }

                    public void onSuccess(TournamentDetails tournament) {
                        panel.setTournament(tournament);
                        panel.setTaikai(TaikaiWeb.getSession().getTaikai());
//                        panel.setWidget(panel);
                    }
                });
            }
            catch (NumberFormatException ex) {
                Logger.error(path + " är ett ogiltigt värde för turneringar.");
                throw new ContentHandlerNotFoundException(ex);
            }

            return this;
        }

    }

}
