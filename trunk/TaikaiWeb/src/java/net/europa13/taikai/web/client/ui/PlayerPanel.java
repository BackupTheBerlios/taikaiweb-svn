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

import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.CheckBox;
import com.google.gwt.user.client.ui.ClickListener;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;
import net.europa13.taikai.web.client.TaikaiWeb;
import net.europa13.taikai.web.client.logging.Logger;
import net.europa13.taikai.web.proxy.PlayerProxy;
import net.europa13.taikai.web.proxy.TaikaiProxy;

/**
 *
 * @author daniel
 */
public class PlayerPanel extends VerticalPanel {

//    private final Panel panel = new SimplePanel();
    private final TextBox tbName;
    private final TextBox tbSurname;
    private final CheckBox cbCheckedIn;
    private final Button btnSave;
    private PlayerProxy player;
    private TaikaiProxy taikai;

    public PlayerPanel() {

        FlexTable table = new FlexTable();

        tbName = new TextBox();
        table.setText(0, 0, "Förnamn");
        table.setWidget(0, 1, tbName);

        tbSurname = new TextBox();
        table.setText(1, 0, "Efternamn");
        table.setWidget(1, 1, tbSurname);

        cbCheckedIn = new CheckBox();
        table.setText(2, 0, "Incheckad");
        table.setWidget(2, 1, cbCheckedIn);

        
        FlowPanel buttonPanel = new FlowPanel();
        
        btnSave = new Button("Spara");
        buttonPanel.add(btnSave);

        int buttonRow = table.getRowCount();
        table.setWidget(buttonRow, 0, buttonPanel);
        table.getFlexCellFormatter().setColSpan(buttonRow, 0, table.getCellCount(0));

        add(table);


    }

    public void addSaveListener(ClickListener listener) {
        btnSave.addClickListener(listener);
    }

    public PlayerProxy getPlayer() {
        PlayerProxy newPlayer = new PlayerProxy();

        if (player != null) {
            newPlayer.setId(player.getId());
        }
        newPlayer.setTaikaiId(taikai.getId());
        newPlayer.setName(tbName.getText());
        newPlayer.setSurname(tbSurname.getText());
        newPlayer.setCheckedIn(cbCheckedIn.isChecked());

        return newPlayer;
    }

    public void removeSaveListener(ClickListener listener) {
        btnSave.removeClickListener(listener);
    }

    public void reset() {
        this.player = null;
        tbName.setText("");
        tbSurname.setText("");
        cbCheckedIn.setChecked(false);
    }

    public void setPlayer(PlayerProxy player) {
        if (player == null) {
            reset();
            return;
        }

        this.player = player;
        tbName.setText(player.getName());
        tbSurname.setText(player.getSurname());
        cbCheckedIn.setChecked(player.isCheckedIn());
    }

    public void setTaikai(TaikaiProxy taikai) {
        this.taikai = taikai;
    }
}
