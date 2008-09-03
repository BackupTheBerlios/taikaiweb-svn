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
import java.util.List;
import net.europa13.taikai.web.client.ContentHandlerNotFoundException;
import net.europa13.taikai.web.client.ListResult;
import net.europa13.taikai.web.client.NavigationPath;
import net.europa13.taikai.web.client.PlayerAdminService;
import net.europa13.taikai.web.client.PlayerAdminServiceAsync;
import net.europa13.taikai.web.client.TaikaiWeb;
import net.europa13.taikai.web.client.TournamentAdminService;
import net.europa13.taikai.web.client.TournamentAdminServiceAsync;
import net.europa13.taikai.web.client.logging.Logger;
import net.europa13.taikai.web.proxy.PlayerDetails;
import net.europa13.taikai.web.proxy.TournamentProxy;

/**
 *
 * @author daniel
 */
public class PlayerDetailsContent extends Content {
    
    private final PlayerPanel panel;
    private final PlayerAdminServiceAsync playerService =
        GWT.create(PlayerAdminService.class);
    private final TournamentAdminServiceAsync tournamentService =
        GWT.create(TournamentAdminService.class);
    
    
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
            panel.reset();
            panel.setTaikai(TaikaiWeb.getSession().getTaikai());
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
        playerService.storePlayer(details, new AsyncCallback() {

            public void onFailure(Throwable t) {
                Logger.error("Det gick inte att spara deltagare " + details.getId() + ".");
                Logger.debug(t.getLocalizedMessage());
            }

            public void onSuccess(Object nothing) {
                Logger.info(details.getName() + " sparad.");
//                updatePlayerList();
            }
        });
    }
    
    private void openPlayerPanel(final int playerId) {

        panel.setTaikai(TaikaiWeb.getSession().getTaikai());

        tournamentService.getTournaments(TaikaiWeb.getSession().getTaikai(), new AsyncCallback<ListResult<TournamentProxy>>() {

            public void onFailure(Throwable t) {
                Logger.debug("Misslyckades med att hämta listor i openPlayerPanel.");
                Logger.debug(t.getLocalizedMessage());
            //throw new UnsupportedOperationException("Not supported yet.");
            }

            public void onSuccess(ListResult<TournamentProxy> result) {
                Logger.debug("Hämtade listor i openPlayerPanel.");
                setPlayerPanelPlayer(playerId, result.getList());
            }
        });
    }

    private void setPlayerPanelPlayer(final int playerId, final List<TournamentProxy> tournaments) {
        Logger.trace("entering setPlayerPanelPlayer in PlayerContent");
//        throw new RuntimeException("test");
        
//        assert(false);
        
        Logger.debug("setPlayerPanelPlayer in PlayerContent: playerId = " + playerId + " tournaments = " + tournaments);
        
        if (playerId == 0) {
            panel.reset();
            panel.setTournamentList(tournaments);
//            panel.setWidget(panel);
//            state = "details";
        }
        else {
            playerService.getPlayer(playerId, new AsyncCallback<PlayerDetails>() {

                public void onFailure(Throwable t) {
                    Logger.debug("Misslyckades med att hämta deltagare " + playerId + " i setPlayerPanelPlayer.");
                    Logger.debug(t.getLocalizedMessage());
                }

                public void onSuccess(PlayerDetails player) {
                    Logger.trace("entering playerService.getPlayer.onSuccess in PlayerContent");
//                throw new UnsupportedOperationException("Not supported yet.");
                    panel.setPlayer(player);
                    panel.setTournamentList(tournaments);
//                playerPanel.setTaikai(TaikaiWeb.getSession().getTaikai());
//                    panel.setWidget(panel);
//                    state = "details";
                    Logger.trace("exiting playerService.getPlayer.onSuccess in PlayerContent");
                }
            });
        }
        Logger.trace("exiting setPlayerPanelPlayer in PlayerContent");
    }
}
