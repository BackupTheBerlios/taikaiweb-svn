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

import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import java.util.List;
import net.europa13.taikai.web.client.TaikaiWeb;
import net.europa13.taikai.web.client.logging.Logger;
import net.europa13.taikai.web.client.rpc.LoadTournamentsRequest;
import net.europa13.taikai.web.client.rpc.RpcScheduler;
import net.europa13.taikai.web.client.rpc.TournamentListTarget;
import net.europa13.taikai.web.proxy.TaikaiProxy;
import net.europa13.taikai.web.proxy.TournamentDetails;
import net.europa13.taikai.web.proxy.TournamentListKey;
import net.europa13.taikai.web.proxy.TournamentProxy;

/**
 *
 * @author daniel
 */
public class TournamentPanel extends VerticalPanel {

    private final Label tbId;
    private final TextBox tbName;
    private final Label tbTaikai;
    private final TournamentSeedPanel seedTable;
    private final TournamentPoolPanel poolBox;
//    private final TournamentAdvancementTable advancementTable;
//    private final Button btnSave;
    private TaikaiProxy taikai;
    private TournamentDetails tournament;

    public TournamentPanel() {

        final FlexTable table = new FlexTable();
        int row = 0;

        //*********************************************************************
        // Id and taikai info, mostly for debugging purposes.
        table.setText(row, 0, "Id");
        tbId = new Label();
//        tbId.setEnabled(false);
        table.setWidget(row++, 1, tbId);


        table.setText(row, 0, "Taikai");
        tbTaikai = new Label();
//        tbTaikai.setEnabled(false);
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

//        advancementTable = new TournamentAdvancementTable();
//        table.setWidget(row, 0, advancementTable);
//        table.getFlexCellFormatter().setColSpan(row, 0, table.getCellCount(0));
//        row++;
        

        add(table);
    }

//    public void addSaveListener(ClickListener listener) {
//        btnSave.addClickListener(listener);
//    }

    public TournamentDetails getTournament() {
        final TournamentDetails newTournament = new TournamentDetails();

        if (tournament != null) {
            newTournament.setId(tournament.getId());
        }
        newTournament.setTaikai(taikai);
        
        newTournament.setName(tbName.getText());
        newTournament.setPoolSize(poolBox.getPoolSize());
        newTournament.setPreferringLargerPools(poolBox.isPreferringLargerPools());
        
//        newTournament.setAdvancements(advancementTable.getAdvancements());
        
        return newTournament;
    }

//    public void removeSaveListener(ClickListener listener) {
//        btnSave.removeClickListener(listener);
//    }
    
//    @Override
//    public void onDetach() {
//        super.onDetach();
//        Logger.debug("Detached");
//    }

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
//        RpcScheduler.queueRequest(new LoadTournamentsRequest(new TournamentListKey(taikai.getId()), advancementTable));
    }

    public void setTournament(final TournamentDetails tournament) {
        if (tournament == null) {
            Logger.debug("setTournament in TournamentPanel: tournament == null");
            reset();
        }
        else {
            this.tournament = tournament;
            tbId.setText(String.valueOf(tournament.getId()));
            tbName.setText(tournament.getName());
            tbTaikai.setText(tournament.getTaikai().getName());

            poolBox.setPoolSize(tournament.getPoolSize());
            poolBox.setPreferringLargerPools(tournament.isPreferringLargerPools());

            for (int i = 0; i < 4; ++i) {
                seedTable.setSeededPlayer(i + 1, tournament.getSeededPlayer(i + 1));
            }
            
            
            
//            advancementTable.setAdvancements(tournament.getAdvancements());
        }
        
//        RpcScheduler.queueRequest(new LoadTournamentsRequest(new TournamentListKey(TaikaiWeb.getSession().getTaikaiId()), new TournamentListTarget() {
//
//            public void setTournaments(List<? extends TournamentProxy> tournaments) {
//                tournaments.remove(tournament);
////                Logger.debug("tournaments size = " + tournaments.size());
//                
//                advancementTable.setTournaments(tournaments);
//                if (tournament != null) {
//                    advancementTable.setTournament(tournament);
//                }
//            }
//        }));
    }
    
}
