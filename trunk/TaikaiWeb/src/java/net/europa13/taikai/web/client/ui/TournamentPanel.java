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
import net.europa13.taikai.web.client.logging.Logger;
import net.europa13.taikai.web.proxy.TaikaiProxy;
import net.europa13.taikai.web.proxy.TournamentDetails;

/**
 *
 * @author daniel
 */
public class TournamentPanel extends VerticalPanel {

    private final TextBox tbId;
    private final TextBox tbName;
    private final TextBox tbTaikai;
    private final TournamentSeedPanel seedTable;
    private final TournamentPoolPanel poolBox;
    private final Button btnSave;
    private TaikaiProxy taikai;
    private TournamentDetails tournament;

    public TournamentPanel() {

        final FlexTable table = new FlexTable();
        int row = 0;

        //*********************************************************************
        // Id and taikai info, mostly for debugging purposes.
        table.setText(row, 0, "Id");
        tbId = new TextBox();
        tbId.setEnabled(false);
        table.setWidget(row++, 1, tbId);


        table.setText(row, 0, "Taikai");
        tbTaikai = new TextBox();
        tbTaikai.setEnabled(false);
        table.setWidget(row++, 1, tbTaikai);

        //*********************************************************************
        // General info
        table.setText(row, 0, "Namn");
        tbName = new TextBox();
        table.setWidget(row++, 1, tbName);

        //*********************************************************************
        // Pool controls
        poolBox = new TournamentPoolPanel();
        
        table.setWidget(row, 0, poolBox);
        table.getFlexCellFormatter().setColSpan(row, 0, table.getCellCount(0));
        row++;

        seedTable = new TournamentSeedPanel();
        table.setWidget(row, 0, seedTable);
        table.getFlexCellFormatter().setColSpan(row, 0, table.getCellCount(0));
        row++;

        //*********************************************************************
        // Button panel
        final FlowPanel buttonPanel = new FlowPanel();
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
        final TournamentDetails newTournament = new TournamentDetails();

        if (tournament != null) {
            newTournament.setId(tournament.getId());
        }
        newTournament.setTaikaiId(taikai.getId());
        
        newTournament.setName(tbName.getText());
        newTournament.setPoolSize(poolBox.getPoolSize());
        newTournament.setPreferringLargerPools(poolBox.isPreferringLargerPools());
        
        return newTournament;
    }

    public void removeSaveListener(ClickListener listener) {
        btnSave.removeClickListener(listener);
    }

    public void reset() {
        tournament = null;
        tbId.setText("");
        tbName.setText("");
        poolBox.reset();
        tbTaikai.setText("");
        seedTable.reset();
    }

    public void setTaikai(TaikaiProxy taikai) {
        if (taikai == null) {
            Logger.debug("setTaikai in TournamentPanel: taikai == null");
        }
        
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

            poolBox.setPoolSize(tournament.getPoolSize());
            poolBox.setPreferringLargerPools(tournament.isPreferringLargerPools());

            for (int i = 0; i < 4; ++i) {
                seedTable.setSeededPlayer(i + 1, tournament.getSeededPlayer(i + 1));
            }
        }
    }
}
