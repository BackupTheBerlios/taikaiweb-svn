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

import com.google.gwt.user.client.History;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.Hyperlink;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.SourcesTableEvents;
import com.google.gwt.user.client.ui.TableListener;
import net.europa13.taikai.web.client.logging.Logger;
import net.europa13.taikai.web.proxy.PlayerProxy;
import net.europa13.taikai.web.proxy.TournamentGenerationInfo;

/**
 *
 * @author Daniel Wentzel
 */
public class TournamentConfirmGeneratePanel extends FlexTable {

    private final TournamentSeedPanel seedPanel;
    private final TournamentPoolPanel poolPanel;
    private final TournamentAdvancementTable advancementPanel;
    private final PlayerTable playerTable;
    private final Hyperlink lnkTournamentName;
    private final Label lblPlayerCount;
    private TournamentGenerationInfo info;

    public TournamentConfirmGeneratePanel() {

        seedPanel = new TournamentSeedPanel();
        poolPanel = new TournamentPoolPanel();
        playerTable = new PlayerTable();
        playerTable.setCaption("Ej incheckade anmälda deltagare");
        playerTable.addTableListener(new TableListener() {

            public void onCellClicked(SourcesTableEvents source, int row, int col) {
                if (row > 1) {
                    PlayerProxy player = info.getUncheckedPlayers().get(row - 2);
                    History.newItem("players/" + player.getId());
                }
            }
        });

        setText(0, 0, "Generering av");
        lnkTournamentName = new Hyperlink();
        setWidget(0, 1, lnkTournamentName);

        setText(1, 0, "Incheckade deltagare");
        lblPlayerCount = new Label();
        setWidget(1, 1, lblPlayerCount);

//        setText(2, 0, "Ej incheckade anmälda deltagare");
//        getFlexCellFormatter().setColSpan(2, 0, 2);

        setWidget(2, 0, playerTable);
        getFlexCellFormatter().setColSpan(2, 0, 2);

        setWidget(3, 0, seedPanel);
        getFlexCellFormatter().setColSpan(3, 0, 2);

        setWidget(4, 0, poolPanel);
        getFlexCellFormatter().setColSpan(4, 0, 2);
        
        advancementPanel = new TournamentAdvancementTable();
        setWidget(5, 0, advancementPanel);
        getFlexCellFormatter().setColSpan(5, 0, 2);

    }

    void setInfo(TournamentGenerationInfo info) {

        this.info = info;

        lnkTournamentName.setText(info.getTournament().getName());
        lnkTournamentName.setTargetHistoryToken("tournaments/" + info.getTournament().getId());
        lblPlayerCount.setText(String.valueOf(info.getPlayerCount()));


        playerTable.setPlayerList(info.getUncheckedPlayers());
        playerTable.setVisible(info.getUncheckedPlayers().size() > 0);


        for (int i = 0; i < 4; ++i) {
            seedPanel.setSeededPlayer(i + 1, info.getTournament().getSeededPlayer(i + 1));
        }

        poolPanel.setEnabled(false);
        poolPanel.setPoolSize(info.getTournament().getPoolSize());
        poolPanel.setPreferringLargerPools(info.getTournament().isPreferringLargerPools());
        for (int i = 2; i < 5; ++i) {
            Logger.debug("info poolSize = " + i + " poolCount = " + info.getPoolCount(i));
            poolPanel.setPoolCount(i, info.getPoolCount(i));
        }

        advancementPanel.setEnabled(false);

    }
}
