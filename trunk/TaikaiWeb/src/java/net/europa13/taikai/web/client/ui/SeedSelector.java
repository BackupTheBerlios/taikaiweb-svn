/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package net.europa13.taikai.web.client.ui;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.ChangeListener;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.Widget;
import java.util.List;
import net.europa13.taikai.web.client.TournamentAdminService;
import net.europa13.taikai.web.client.TournamentAdminServiceAsync;
import net.europa13.taikai.web.client.logging.Logger;
import net.europa13.taikai.web.proxy.PlayerDetails;
import net.europa13.taikai.web.proxy.TournamentProxy;

/**
 *
 * @author daniel
 */
public class SeedSelector extends ListBox {

    private TournamentAdminServiceAsync service =
        GWT.create(TournamentAdminService.class);
    private TournamentProxy tournament;
    private PlayerDetails player;
    
    private int selectedSeed = 1;
    
    public SeedSelector() {
        addChangeListener(new ChangeListener() {

            public void onChange(Widget sender) {
                selectedSeed = Integer.parseInt(getValue(getSelectedIndex()));
            }
        });
        
    }
    
    public int getSelectedSeed() {
        return selectedSeed;
    }
    
//    private void setSelectedSeed(int selectedSeed) {
//        boolean selected = false;
//        
//        for (int i = 0; i < getItemCount(); ++i) {
//            if (Integer.parseInt(getValue(i)) == selectedSeed) {
//                setSelectedIndex(i);
//                this.selectedSeed = selectedSeed;
//                selected = true;
//            }
//        }
//        
//        if (!selected) {
//            Logger.debug("setSelectedSeed in SeedSelector: The selectedSeed was not found in the list.");
//        }
//    }
    
    public void setData(TournamentProxy tournament, final PlayerDetails player) {
        this.tournament = tournament;
        
//        final boolean enabled = isEnabled();
//        setEnabled(false);
        service.getAvailableSeeds(tournament, new AsyncCallback<List<Integer>>() {

            public void onFailure(Throwable t) {
                Logger.debug("Kunde inte hitta tillgängliga seedningar.");
            }

            public void onSuccess(List<Integer> seeds) {
                updateList(seeds, player);
//                setEnabled(enabled);
            }
            
        });
    }
    
    private void updateList(List<Integer> seeds, PlayerDetails player) {
        clear();
        
        int playerSeed = player.getPlayerSeedInTournament(tournament);
        
        if (playerSeed > - 1 && !seeds.contains(playerSeed)) {
            int position = 0;
            for (int i = 0; i < seeds.size(); ++i) {
                if (seeds.get(i) < playerSeed) {
                    position++;
                }
                else {
                    break;
                }
            }
//            Logger.debug("updateList in SeedSelector: insert playerSeed " + playerSeed + " at position " + position);
            seeds.add(position, playerSeed);
            selectedSeed = playerSeed;
//            setEnabled(true);
        }
        else if (seeds.size() > 0){
//            setEnabled(false);
            selectedSeed = seeds.get(0);
        }
        else {
            return;
        }
        
        for (int i = 0; i < seeds.size(); ++i) {
            int seed = seeds.get(i);
            addItem(String.valueOf(seed), String.valueOf(seed));
            if (selectedSeed == seed) {
                setSelectedIndex(i);
            }
        }
    }
    
}
