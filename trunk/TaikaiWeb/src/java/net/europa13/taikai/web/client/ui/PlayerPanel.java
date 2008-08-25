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
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import net.europa13.taikai.web.proxy.PlayerProxy;
import net.europa13.taikai.web.proxy.TaikaiProxy;

/**
 *
 * @author daniel
 */
public class PlayerPanel extends VerticalPanel {

//    private final Panel panel = new SimplePanel();
    private final TextBox tbId;
    private final TextBox tbTaikaiId;
    private final TextBox tbName;
    private final TextBox tbSurname;
    private final CheckBox cbCheckedIn;
    private final TextBox tbAge;
    private final TextBox tbNumber;
    private final Button btnSave;
    private PlayerProxy player;
    private TaikaiProxy taikai;

    public PlayerPanel() {

        FlexTable table = new FlexTable();

        int row = 0;
        
        tbId = new TextBox();
        tbId.setEnabled(false);
        table.setText(row, 0, "Id");
        table.setWidget(row++, 1, tbId);
        
        tbTaikaiId = new TextBox();
        tbTaikaiId.setEnabled(false);
        table.setText(row, 0, "TaikaiId");
        table.setWidget(row++, 1, tbTaikaiId);
        
        tbName = new TextBox();
        table.setText(row, 0, "Förnamn");
        table.setWidget(row++, 1, tbName);

        tbSurname = new TextBox();
        table.setText(row, 0, "Efternamn");
        table.setWidget(row++, 1, tbSurname);

        cbCheckedIn = new CheckBox();
        table.setText(row, 0, "Incheckad");
        table.setWidget(row++, 1, cbCheckedIn);

        tbAge = new TextBox();
        table.setText(row, 0, "Ålder");
        table.setWidget(row++, 1, tbAge);
        
        tbNumber = new TextBox();
        tbNumber.setEnabled(false);
        table.setText(row, 0, "Nummer");
        table.setWidget(row++, 1, tbNumber);
        
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
        newPlayer.setAge(Integer.parseInt(tbAge.getText()));
        newPlayer.setNumber(Integer.parseInt(tbNumber.getText()));

        return newPlayer;
    }

    public void removeSaveListener(ClickListener listener) {
        btnSave.removeClickListener(listener);
    }

    public void reset() {
        this.player = null;
        tbId.setText("");
        tbTaikaiId.setText("");
        tbName.setText("");
        tbSurname.setText("");
        cbCheckedIn.setChecked(false);
        tbAge.setText("");
        tbNumber.setText("");
    }

    public void setPlayer(PlayerProxy player) {
        if (player == null) {
            reset();
            return;
        }

        this.player = player;
        tbId.setText(String.valueOf(player.getId()));
        tbTaikaiId.setText(String.valueOf(player.getTaikaiId()));
        tbName.setText(player.getName());
        tbSurname.setText(player.getSurname());
        cbCheckedIn.setChecked(player.isCheckedIn());
        tbAge.setText(String.valueOf(player.getAge()));
        tbNumber.setText(String.valueOf(player.getNumber()));
    }

    public void setTaikai(TaikaiProxy taikai) {
        this.taikai = taikai;
    }
}
