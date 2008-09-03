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
import net.europa13.taikai.web.client.logging.Logger;
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
        grid.setText(0, 0, "Ett");
        grid.setText(0, 1, "Två");
        grid.setText(0, 2, "Tre");
        grid.setText(0, 3, "Fyra");
        add(grid);
    }

    public void reset() {
        for (int i = 0; i < 4; ++i) {
            grid.setText(1, i, "");
        }
    }

    public void setSeededPlayer(int position, PlayerProxy player) {
        if (player == null) {
            Logger.debug("setSeededPlayer in TournamentSeedTable: player is null.");
            grid.setText(1, position - 1, "Ingen");
        }
        else {
            grid.setText(1, position - 1, player.getNumber() + " " + player.getName() + " " + player.getSurname());
        }
    }
}
