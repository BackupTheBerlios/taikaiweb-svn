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

import com.google.gwt.user.client.ui.CheckBox;
import com.google.gwt.user.client.ui.Grid;
import java.util.List;
import net.europa13.taikai.web.proxy.Gender;
import net.europa13.taikai.web.proxy.PlayerProxy;

/**
 *
 * @author daniel
 */
public class PlayerTable extends Grid {

    private final int columnCount = 7;

    public PlayerTable() {

        int col = 0;

        resize(1, columnCount);

        setText(0, col++, "Incheckad");
        setText(0, col++, "Id");
        setText(0, col++, "Nummer");
        setText(0, col++, "Förnamn");
        setText(0, col++, "Efternamn");
        setText(0, col++, "Grad");
        setText(0, col++, "K/M");


        setStyleName("taikaiweb-Table");
        getRowFormatter().setStyleName(0, "taikaiweb-TableHeader");
        getCellFormatter().setStyleName(0, getColumnCount() - 1, "taikaiweb-TableLastColumn");

    }

    public void reset() {
        resize(1, getColumnCount());
    }

    public void setPlayerList(List<PlayerProxy> playerList) {
        if (playerList == null) {
            reset();
            return;
        }

        int columnCount = getColumnCount();

        int playerCount = playerList.size();
        resize(playerCount + 1, columnCount);

        for (int i = 0; i < playerCount; ++i) {
            final PlayerProxy player = playerList.get(i);

            CheckBox checkedIn = new CheckBox();
            checkedIn.setChecked(player.isCheckedIn());
            checkedIn.setEnabled(false);
            setWidget(i + 1, 0, checkedIn);

            setText(i + 1, 1, String.valueOf(player.getId()));
            setText(i + 1, 2, String.valueOf(player.getNumber()));
            setText(i + 1, 3, player.getName());
            setText(i + 1, 4, player.getSurname());
            setText(i + 1, 5, player.getGrade().toString());
            
            String mfText = player.getGender() == Gender.FEMALE ? "K" : "M";
            setText(i + 1, 6, mfText);

        }

    }
}
