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
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.Hyperlink;
import net.europa13.taikai.web.proxy.PlayerProxy;

/**
 *
 * @author Daniel Wentzel
 */
public class TournamentSeedPanel extends CaptionPanel {

    private final Grid grid;

    public TournamentSeedPanel() {

        super("Seedning");
        grid = new Grid(2, 4);
        grid.setText(0, 0, "1:a");
        
        grid.setText(0, 1, "2:a");
        grid.setText(0, 2, "3:e");
        grid.setText(0, 3, "4:e");
        add(grid);
        
//        grid.setWidth("100%");
        for (int i = 0; i < 3; ++i) {
            grid.getColumnFormatter().setWidth(i, "25%");
        }
    }

    public void reset() {
        for (int i = 0; i < 4; ++i) {
            grid.setText(1, i, "");
        }
    }

    public void setSeededPlayer(int position, PlayerProxy player) {
        
        int column = position - 1;
        
        if (player == null) {
//            Logger.debug("setSeededPlayer in TournamentSeedTable: player is null.");
            grid.setText(1, column, "Ingen");
        }
        else {
            Hyperlink link = new Hyperlink(player.getNumber() + " " + player.getName() + " " + player.getSurname(),
                "players/" + player.getId()); 
//            grid.setText(1, position - 1, player.getNumber() + " " + player.getName() + " " + player.getSurname());
            grid.setWidget(1, column, link);
            
            if (!player.isCheckedIn()) {
                grid.getColumnFormatter().addStyleName(column, "taikaiweb-UncheckedSeed");
//                link.addStyleName("taikaiweb-UncheckedSeed");
            }
            else {
                grid.getColumnFormatter().removeStyleName(column, "taikaiweb-UncheckedSeed");
            }
            
        }
    }

//    public void setSeeds(List<? extends TournamentSeedProxy> seeds) {
//        for (TournamentSeedProxy seed : seeds) {
//            
//            setSeededPlayer(seed.getSeedNumber(), seed.getPlayer());
//            
//        }
//    }
}
