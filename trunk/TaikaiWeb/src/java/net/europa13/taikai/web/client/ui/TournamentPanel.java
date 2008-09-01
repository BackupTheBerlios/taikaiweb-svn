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
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.RadioButton;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import java.util.List;
import net.europa13.taikai.web.proxy.TaikaiProxy;
import net.europa13.taikai.web.proxy.TournamentDetails;
import net.europa13.taikai.web.proxy.TournamentSeedProxy;

/**
 *
 * @author daniel
 */
public class TournamentPanel extends VerticalPanel {

    private final TextBox tbId;
    private final TextBox tbName;
    private final TextBox tbTaikai;
    private final ListBox lbPoolSize;
    private final RadioButton rbPreferLarger;
    private final RadioButton rbPreferSmaller;
    private final TournamentSeedTable seedTable;
    
    private final Button btnSave;
//    private final ListBox lbTaikaiList;
//    private List<TaikaiProxy> taikaiList;
    private TaikaiProxy taikai;
    private TournamentDetails tournament;
    
//    private final Panel panel = new VerticalPanel();

    public TournamentPanel() {

        FlexTable table = new FlexTable();
        
        int row = 0;
        
        
        table.setText(row, 0, "Id");
        tbId = new TextBox();
        tbId.setEnabled(false);
        table.setWidget(row++, 1, tbId);
        
        table.setText(row, 0, "Namn");
        tbName = new TextBox();
        table.setWidget(row++, 1, tbName);
        
        table.setText(row, 0, "Poolstorlek");
        lbPoolSize = new ListBox();
        lbPoolSize.addItem("2", "2");
        lbPoolSize.addItem("3", "3");
        lbPoolSize.setSelectedIndex(1);
        table.setWidget(row++, 1, lbPoolSize);
        
        table.setText(row, 0, "Vid udda deltagare");
        FlowPanel prefersLargerPanel = new FlowPanel();
        rbPreferLarger = new RadioButton("rePreferringLarger", "Föredrar större pooler");
        rbPreferLarger.setChecked(true);
        rbPreferSmaller = new RadioButton("rePreferringLarger", "Föredrar mindre pooler");
        prefersLargerPanel.add(rbPreferLarger);
        prefersLargerPanel.add(rbPreferSmaller);
        table.setWidget(row++, 1, prefersLargerPanel);
        
        table.setText(row, 0, "Taikai");
        tbTaikai = new TextBox();
        tbTaikai.setEnabled(false);
        table.setWidget(row++, 1, tbTaikai);
        
        seedTable = new TournamentSeedTable();
        table.setWidget(row, 0, seedTable);
        table.getFlexCellFormatter().setColSpan(row, 0, table.getCellCount(0));
        row++;
        
        FlowPanel buttonPanel = new FlowPanel();
        table.setWidget(row, 0, buttonPanel);
        table.getFlexCellFormatter().setColSpan(row, 0, table.getCellCount(0));
        
        btnSave = new Button("Spara");
        buttonPanel.add(btnSave);
        add(table);
    }
    
    public void addSaveListener(ClickListener listener) {
        btnSave.addClickListener(listener);
    }
    
    public TournamentDetails getTournament() {
        TournamentDetails newTournament = new TournamentDetails();
        
        if (tournament != null) {
            newTournament.setId(tournament.getId());
        }
        
        newTournament.setName(tbName.getText());
        newTournament.setPoolSize(Integer.parseInt(lbPoolSize.getValue(lbPoolSize.getSelectedIndex())));
        if (rbPreferLarger.isChecked()) {
            newTournament.setPreferringLargerPools(true);
        }
        else {
            newTournament.setPreferringLargerPools(false);
        }
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
        lbPoolSize.setSelectedIndex(1);
        rbPreferLarger.setChecked(true);
        rbPreferSmaller.setChecked(false);
        tbTaikai.setText("");
        seedTable.reset();
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
            tbTaikai.setText(String.valueOf(tournament.getTaikaiId()));
            
//            List<TournamentSeedProxy> seeds = tournament.get
            for (int i = 0 ; i < 4; ++i) {
                seedTable.setSeededPlayer(i + 1, tournament.getSeededPlayer(i + 1));
            }
        }
    }

}
