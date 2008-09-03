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
import net.europa13.taikai.web.proxy.PlayerProxy;

/**
 *
 * @author daniel
 */
public class PlayerTable extends Grid {

    public PlayerTable() {
        super(1, 4);
        setText(0, 0, "Id");
        setText(0, 1, "Förnamn");
        setText(0, 2, "Efternamn");
        setText(0, 3, "Incheckad");

        setStyleName("taikaiweb-Table");
        getRowFormatter().setStyleName(0, "taikaiweb-TableHeader");
        getCellFormatter().setStyleName(0, 3, "taikaiweb-TableLastColumn");

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

//            ClickListener listener = new ClickListener() {
//
//                public void onClick(Widget arg0) {
//                    playerContent.setPlayer(player);
//                    MainPanel.setContent(playerContent);
//                }
//            };

            setText(i + 1, 0, String.valueOf(player.getId()));
//            Hyperlink name = new Hyperlink(player.getName(), "editPlayer");
//            name.addClickListener(listener);
            setText(i + 1, 1, player.getName());
//            Hyperlink surname = new Hyperlink(player.getSurname(), "editPlayer");
//            surname.addClickListener(listener);
            setText(i + 1, 2, player.getSurname());

            CheckBox checkedIn = new CheckBox();
            checkedIn.setChecked(player.isCheckedIn());
            checkedIn.setEnabled(false);
            setWidget(i + 1, 3, checkedIn);

        }

    }

    
}
