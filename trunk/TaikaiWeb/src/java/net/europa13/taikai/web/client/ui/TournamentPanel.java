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
import com.google.gwt.user.client.ui.ClickListener;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import net.europa13.taikai.web.proxy.TaikaiProxy;
import net.europa13.taikai.web.proxy.TournamentDetails;
import net.europa13.taikai.web.proxy.TournamentProxy;

/**
 *
 * @author daniel
 */
public class TournamentPanel extends VerticalPanel {

    private final TextBox tbId;
    private final TextBox tbName;
    private final TextBox tbTaikaiId;
    private final Button btnSave;
//    private final ListBox lbTaikaiList;
//    private List<TaikaiProxy> taikaiList;
    private TaikaiProxy taikai;
    private TournamentDetails tournament;
    
//    private final Panel panel = new VerticalPanel();

    public TournamentPanel() {

        FlexTable table = new FlexTable();
        
        table.setText(0, 0, "Id");
        tbId = new TextBox();
        tbId.setEnabled(false);
        table.setWidget(0, 1, tbId);
        
        table.setText(1, 0, "Namn");
        tbName = new TextBox();
        table.setWidget(1, 1, tbName);
        
        table.setText(2, 0, "Taikai id");
        tbTaikaiId = new TextBox();
        tbTaikaiId.setEnabled(false);
        table.setWidget(2, 1, tbTaikaiId);
        
        FlowPanel buttonPanel = new FlowPanel();
        table.setWidget(3, 0, buttonPanel);
        table.getFlexCellFormatter().setColSpan(3, 0, table.getCellCount(0));
        
        btnSave = new Button("Spara");
        buttonPanel.add(btnSave);
        add(table);
    }

//    private void addControl(Widget control, String label) {
//        
//    }
    
    public void addSaveListener(ClickListener listener) {
        btnSave.addClickListener(listener);
    }
    
    public TournamentDetails getTournament() {
        TournamentDetails newTournament = new TournamentDetails();
        
        if (tournament != null) {
            newTournament.setId(tournament.getId());
        }
        
        newTournament.setName(tbName.getText());
        newTournament.setTaikaiId(taikai.getId());
        return newTournament;
    }
    
    public void removeSaveListener(ClickListener listener) {
        btnSave.removeClickListener(listener);
    }
    
    public void reset() {
        tournament = null;
        tbId.setText("");
        tbName.setText("");
        tbTaikaiId.setText("");
    }
    
    public void setTaikai(TaikaiProxy taikai) {
        this.taikai = taikai;
    }

    public void setTournament(TournamentDetails tournament) {
        if (tournament == null) {
            reset();
        }
        else {
            this.tournament = tournament;
            tbId.setText(String.valueOf(tournament.getId()));
            tbName.setText(tournament.getName());
            tbTaikaiId.setText(String.valueOf(tournament.getTaikaiId()));
        }
    }

}
