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
import com.google.gwt.user.client.ui.FlexTable;
import java.util.List;
import net.europa13.taikai.web.proxy.Gender;
import net.europa13.taikai.web.proxy.PlayerProxy;

/**
 *
 * @author daniel
 */
public class PlayerTable extends FlexTable {

    private final int columnCount = 7;

    public PlayerTable() {

        int col = 0;


        
        
//        resize(2, columnCount);

        setText(1, col++, "Incheckad");
        setText(1, col++, "Id");
        setText(1, col++, "Nummer");
        setText(1, col++, "Förnamn");
        setText(1, col++, "Efternamn");
        setText(1, col++, "Grad");
        setText(1, col++, "K/M");


        setStyleName("taikaiweb-Table");
        getRowFormatter().setStyleName(0, "taikaiweb-TableCaption");
        getFlexCellFormatter().setColSpan(0, 0, col);
        
        getRowFormatter().setStyleName(1, "taikaiweb-TableHeader");
        getCellFormatter().setStyleName(1, col - 1, "taikaiweb-TableLastColumn");

    }

    public void reset() {
//        resize(2, getColumnCount());
        
        while (getRowCount() > 2) {
            removeRow(2);
        }
        
    }

    public void setCaption(String caption) {
        setText(0, 0, caption);
    }
    
    public void setPlayerList(List<PlayerProxy> playerList) {
        reset();
        if (playerList == null) {
            
            return;
        }

//        int columnCount = getColumnCount();

        int playerCount = playerList.size();
//        resize(playerCount + 1, columnCount);

        for (int i = 0; i < playerCount; ++i) {
            final PlayerProxy player = playerList.get(i);

            CheckBox checkedIn = new CheckBox();
            checkedIn.setChecked(player.isCheckedIn());
            checkedIn.setEnabled(false);
            setWidget(i + 2, 0, checkedIn);

            setText(i + 2, 1, String.valueOf(player.getId()));
            setText(i + 2, 2, String.valueOf(player.getNumber()));
            setText(i + 2, 3, player.getName());
            setText(i + 2, 4, player.getSurname());
            setText(i + 2, 5, player.getGrade().toString());
            
            String mfText = player.getGender() == Gender.FEMALE ? "K" : "M";
            setText(i + 2, 6, mfText);

        }
    }
    
    public void setShowCaption(boolean show) {
        getRowFormatter().setVisible(0, show);
    }
    
    public void setShowHeader(boolean show) {
        getRowFormatter().setVisible(1, show);
    }
}
