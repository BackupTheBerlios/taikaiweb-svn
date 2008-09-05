/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package net.europa13.taikai.web.client.rpc;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.AsyncCallback;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Queue;
import net.europa13.taikai.web.client.ListResult;
import net.europa13.taikai.web.client.PlayerAdminService;
import net.europa13.taikai.web.client.PlayerAdminServiceAsync;
import net.europa13.taikai.web.client.TournamentAdminService;
import net.europa13.taikai.web.client.TournamentAdminServiceAsync;
import net.europa13.taikai.web.client.logging.Logger;
import net.europa13.taikai.web.proxy.PlayerDetails;
import net.europa13.taikai.web.proxy.PlayerProxy;
import net.europa13.taikai.web.proxy.TournamentDetails;
import net.europa13.taikai.web.proxy.TournamentProxy;

/**
 *
 * @author daniel
 */
public class RpcScheduler {

    private final static TournamentAdminServiceAsync tournamentService =
        GWT.create(TournamentAdminService.class);
    private final static PlayerAdminServiceAsync playerService =
        GWT.create(PlayerAdminService.class);
    
    private final static Queue<Request> requestQueue = new LinkedList<Request>();
    private final static HashMap<Class<? extends Request>, RequestHandler> handlerMap =
        new HashMap<Class<? extends Request>, RequestHandler>();
    
    
    static {

        handlerMap.put(LoadTournamentDetailsRequest.class, new RequestHandler<LoadTournamentDetailsRequest>() {

            public void handleRequest(final LoadTournamentDetailsRequest request) {
                Logger.debug("LoadTournamentDetailsRequest");
                
                tournamentService.getTournament(request.getKey(), new AsyncCallback<TournamentDetails>() {

                    public void onFailure(Throwable t) {
                        Logger.debug("Could not find tournament " + request.getKey());
                        Logger.error(t.getLocalizedMessage());
                        performNextRequest();
                    }

                    public void onSuccess(TournamentDetails tournament) {
                        request.getTarget().setTournament(tournament);
                        performNextRequest();
                    }
                });
            }
            
        });
        
        handlerMap.put(StoreTournamentRequest.class, new RequestHandler<StoreTournamentRequest>() {

            public void handleRequest(final StoreTournamentRequest request) {
                Logger.debug("StoreTournamentRequest");
                tournamentService.storeTournament(request.getTournament(), new AsyncCallback<Integer>() {

                    public void onFailure(Throwable t) {
                        Logger.debug("Failed to store tournament " + request.getTournament().getId());
                        Logger.error(t.getLocalizedMessage());
                        performNextRequest();
                    }

                    public void onSuccess(Integer tournamentId) {
                        if (request.isReloadNecessary()) {
                            queueRequest(new LoadTournamentDetailsRequest(tournamentId, request.getTarget()));
                        }
                        performNextRequest();
                    }
                });
            }
        });
        
        handlerMap.put(LoadTournamentsRequest.class, new RequestHandler<LoadTournamentsRequest>() {

            public void handleRequest(final LoadTournamentsRequest request) {
                Logger.debug("LoadTournamentsRequest");
                
                tournamentService.getTournaments(request.getKey(), new AsyncCallback<ListResult<TournamentProxy>>() {

                    public void onFailure(Throwable t) {
                        Logger.debug("Failed to fetch tournament list");
                        performNextRequest();
                    }

                    public void onSuccess(ListResult<TournamentProxy> result) {
                        request.getContainer().setTournaments(result.getList());
                        performNextRequest();
                    }
                    
                });
                
            }
        });
        
        handlerMap.put(LoadPlayerDetailsRequest.class, new RequestHandler<LoadPlayerDetailsRequest>() {

            public void handleRequest(final LoadPlayerDetailsRequest request) {
                Logger.debug("LoadPlayerDetailsRequest");
                playerService.getPlayer(request.getKey(), new AsyncCallback<PlayerDetails>() {

                    public void onFailure(Throwable t) {
                        Logger.debug("Could not find player " + request.getKey());
                        Logger.error(t.getLocalizedMessage());
                        performNextRequest();
                    }

                    public void onSuccess(PlayerDetails player) {
                        request.getTarget().setPlayer(player);
                        performNextRequest();
                    }
                });
            }
        });
        
        handlerMap.put(StorePlayerRequest.class, new RequestHandler<StorePlayerRequest>() {

            public void handleRequest(final StorePlayerRequest request) {
                Logger.debug("StorePlayerRequest");
                playerService.storePlayer(request.getPlayer(), new AsyncCallback<Integer>() {

                    public void onFailure(Throwable t) {
                        Logger.debug("Failed to store player " + request.getPlayer().getId());
                        Logger.error(t.getLocalizedMessage());
                        performNextRequest();
                    }

                    public void onSuccess(Integer playerId) {
                        if (request.isReloadNecessary()) {
                            queueRequest(new LoadPlayerDetailsRequest(playerId, request.getTarget()));
                        }
                        performNextRequest();
                    }
                });
            }
        });
        
        handlerMap.put(LoadPlayersRequest.class, new RequestHandler<LoadPlayersRequest>() {

            public void handleRequest(final LoadPlayersRequest request) {
                Logger.debug("LoadPlayersRequest");
                
                playerService.getPlayers(request.getKey(), new AsyncCallback<ListResult<PlayerProxy>>() {

                    public void onFailure(Throwable arg0) {
                        Logger.debug("Failed to fetch player list");
                        performNextRequest();
                    }

                    public void onSuccess(ListResult<PlayerProxy> result) {
                        request.getContainer().setPlayers(result.getList());
                        performNextRequest();
                    }
                    
                });
                
            }
        });
        
    }
    
    public static void queueRequest(Request request) {
        
        boolean restartQueue = requestQueue.isEmpty();
        
        requestQueue.add(request);
        
        if (restartQueue) {
            performRequest(requestQueue.peek());
        }
        
    }
    
//    private static void performRequest(Request request) {
//        Logger.debug("RpcScheduler Request");
//    }
    
    private static void performNextRequest() {
        requestQueue.remove();
        
        if (requestQueue.isEmpty()) {
            Logger.debug("Request queue is empty.");
        }
        else {
            performRequest(requestQueue.peek());
        }
    }
    
    private  static <R extends Request> void performRequest(R request) {
        if (handlerMap.containsKey(request.getClass())) {
            handlerMap.get(request.getClass()).handleRequest(request);
        } 
        else {
            Logger.debug("RpcScheduler could not find request handler.");
        }
    }
}
