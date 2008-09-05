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

import com.google.gwt.user.client.ui.CaptionPanel;
import com.google.gwt.user.client.ui.CheckBox;
import com.google.gwt.user.client.ui.ClickListener;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.Widget;
import java.util.ArrayList;
import java.util.List;
import net.europa13.taikai.web.proxy.TournamentAdvancementProxy;
import net.europa13.taikai.web.proxy.TournamentDetails;
import net.europa13.taikai.web.proxy.TournamentProxy;

/**
 *
 * @author Daniel Wentzel
 */
public class TournamentAdvancementTable extends CaptionPanel  {

    private final ArrayList<TournamentSelector> selectors;
    private final CheckBox cbAllToSame;
//    private final CheckBox cbUseAdvancement;
    private final FlexTable grid;
    
    private boolean enabled;

    private TournamentProxy tournament;
    
    public TournamentAdvancementTable() {
        super("Avancemang");

        grid = new FlexTable();

        grid.setText(0, 0, "De fyra bästa går vidare till");

        selectors = new ArrayList<TournamentSelector>();

        for (int i = 0; i < 4; ++i) {
            selectors.add(new TournamentSelector());
            grid.setWidget(1, i, selectors.get(i));
        }

        grid.getFlexCellFormatter().setColSpan(0, 0, 2);

        cbAllToSame = new CheckBox("Alla till samma");
        cbAllToSame.setChecked(true);
        cbAllToSame.addClickListener(new ClickListener() {

            public void onClick(Widget arg0) {
                updateControls();
            }
        });
        grid.setWidget(0, 2, cbAllToSame);


        for (int i = 0; i < 4; ++i) {
            grid.getColumnFormatter().setWidth(i, 100 / selectors.size() + "%");
        }

        enabled = true;
        updateControls();
        add(grid);
    }

    public List<TournamentAdvancementProxy> getAdvancements() {
        ArrayList<TournamentAdvancementProxy> advancements = new ArrayList<TournamentAdvancementProxy>();
        
        for (int i = 0; i < selectors.size(); ++i) {
            TournamentAdvancementProxy advancement = 
                new TournamentAdvancementProxy(tournament, getSelectedTournament(i), i);
            advancements.add(advancement);
        }
        
        return advancements;
    }
    
    public TournamentProxy getSelectedTournament(int playerNumber) {
        if (cbAllToSame.isChecked()) {
            return selectors.get(0).getSelectedTournament();
        }
        else {
            return selectors.get(playerNumber).getSelectedTournament();
        }
    }

    public void setAdvancements(List<? extends TournamentAdvancementProxy> advancements) {

//        for (int i = 0; i < selectors.size(); ++i) {
//            selectors.get(i).setSelectedTournament(tournament);
//        }
        
        for (TournamentAdvancementProxy advancement : advancements) {
            int position = advancement.getPlayerNumber();
            
            selectors.get(position).setSelectedTournament(advancement.getAdvancementTournament());
        }

    }
    
    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
        
        cbAllToSame.setEnabled(enabled);
        if (!enabled) {
            for (int i = 0; i < selectors.size(); ++i) {
                selectors.get(i).setEnabled(false);
            }
        }
        else {
            updateControls();
        }
    }

    public void setTournament(TournamentDetails tournament) {
        this.tournament = tournament;
        
        setAdvancements(tournament.getAdvancements());
    }
     
    public void setTournaments(List<? extends TournamentProxy> tournaments) {
        for (int i = 0; i < selectors.size(); ++i) {
            selectors.get(i).setTournaments(tournaments);
        }
        
        

    }

    private void updateControls() {
        if (enabled) {
            for (int i = 1; i < selectors.size(); ++i) {
                selectors.get(i).setEnabled(!cbAllToSame.isChecked());
            }
        }
    }
}
