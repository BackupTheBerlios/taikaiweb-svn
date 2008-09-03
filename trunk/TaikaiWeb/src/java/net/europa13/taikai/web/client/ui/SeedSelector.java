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
        
    public void setData(TournamentProxy tournament, final PlayerDetails player) {
        this.tournament = tournament;
        
        service.getAvailableSeeds(tournament, new AsyncCallback<List<Integer>>() {

            public void onFailure(Throwable t) {
                Logger.debug("Kunde inte hitta tillgängliga seedningar.");
            }

            public void onSuccess(List<Integer> seeds) {
                updateList(seeds, player);
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
        }
        else if (seeds.size() > 0){
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
