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
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RadioButton;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import java.util.List;
import net.europa13.taikai.web.client.logging.Logger;
import net.europa13.taikai.web.proxy.Gender;
import net.europa13.taikai.web.proxy.Grade;
import net.europa13.taikai.web.proxy.PlayerDetails;
import net.europa13.taikai.web.proxy.TaikaiProxy;
import net.europa13.taikai.web.proxy.TournamentProxy;
import net.europa13.taikai.web.proxy.TournamentSeedProxy;

/**
 *
 * @author daniel
 */
public class PlayerPanel extends VerticalPanel {

//    private final Panel panel = new SimplePanel();
    private final Label tbId;
    private final Label tbTaikai;
    private final TextBox tbName;
    private final TextBox tbSurname;
    private final CheckBox cbCheckedIn;
    private final TextBox tbAge;
    private final Label tbNumber;
//    private final Button btnSave;
    private PlayerDetails player;
    private TaikaiProxy taikai;
    private final RadioButton rbGenderMale;
    private final RadioButton rbGenderFemale;
    private final GradeSelector lbGrade;
    private PlayerTournamentsList activeTournamentsTable;

    private FlexTable table;

    public PlayerPanel() {

        table = new FlexTable();

        int row = 0;

        tbId = new Label();
//        tbId.setEnabled(false);
        table.setText(row, 0, "Id");
        table.setWidget(row++, 1, tbId);

        tbTaikai = new Label();
//        tbTaikai.setEnabled(false);
        table.setText(row, 0, "Taikai");
        table.setWidget(row++, 1, tbTaikai);
        
        tbNumber = new Label();
//        tbNumber.setEnabled(false);
        table.setText(row, 0, "Nummer");
        table.setWidget(row++, 1, tbNumber);

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
        
//        int ggrow = 0;
        int ggcol = 0;
        FlowPanel genderPanel = new FlowPanel();
        rbGenderMale = new RadioButton("rgGender", "Man");
        rbGenderFemale = new RadioButton("rgGender", "Kvinna");
        rbGenderMale.setChecked(true);
        genderPanel.add(rbGenderMale);
        genderPanel.add(rbGenderFemale);
        table.setText(row, ggcol, "Kön");
//        table.setWidget(row, ggcol + 1, rbGenderMale);
//        table.setWidget(row++, ggcol + 2, rbGenderFemale);

        table.setWidget(row++, ggcol + 1, genderPanel);
        
        lbGrade = new GradeSelector();
        lbGrade.setSelectedGrade(Grade.Dan1);
        
//        lbGrade.setVisibleItemCount(1);
//        lbGrade.setItemSelected(10, true);
        table.setText(row, ggcol, "Grad");
        table.setWidget(row++, ggcol + 1, lbGrade);

        int ttrow = 0;
        int ttcol = ggcol + 3;

        activeTournamentsTable = new PlayerTournamentsList();
        table.setText(ttrow, ttcol, "Aktiv i turnering");
        table.setWidget(ttrow, ttcol + 1, activeTournamentsTable);

        table.getFlexCellFormatter().setRowSpan(ttrow, ttcol + 1, row);
        table.getFlexCellFormatter().setVerticalAlignment(ttrow, ttcol + 1, ALIGN_TOP);
        add(table);


    }

    public PlayerDetails getPlayer() {
        PlayerDetails newPlayer = new PlayerDetails();

        if (player != null) {
            newPlayer.setId(player.getId());
            newPlayer.setNumber(player.getNumber());
        }
        newPlayer.setTaikai(taikai);
        newPlayer.setName(tbName.getText());
        newPlayer.setSurname(tbSurname.getText());
        newPlayer.setCheckedIn(cbCheckedIn.isChecked());
        newPlayer.setAge(Integer.parseInt(tbAge.getText()));
        
        Gender gender = rbGenderMale.isChecked() ? Gender.MALE : Gender.FEMALE;
        newPlayer.setGender(gender);
        
        newPlayer.setGrade(lbGrade.getSelectedGrade());
        
        List<TournamentProxy> tournaments = activeTournamentsTable.getSelectedTournaments();
        
        newPlayer.setTournaments(tournaments);

        List<TournamentSeedProxy> seeds = activeTournamentsTable.getSeeds();
        for (TournamentSeedProxy seed : seeds) {
            Logger.debug("Seed added: " + seed.getTournament().getName() + " " + seed.getSeedNumber());
            seed.setPlayer(newPlayer);
        }
        newPlayer.setSeeds(seeds);
        
        
        return newPlayer;
    }

    public void reset() {
        this.player = null;
        tbId.setText("");
        tbTaikai.setText("");
        tbName.setText("");
        tbSurname.setText("");
        cbCheckedIn.setChecked(false);
        tbAge.setText("");
        tbNumber.setText("");
        rbGenderMale.setChecked(true);
        rbGenderFemale.setChecked(false);
        lbGrade.setSelectedGrade(Grade.Dan1);
        activeTournamentsTable.reset();
    }

    public void setPlayer(PlayerDetails player) {
        Logger.trace("entering setPlayer in PlayerPanel");
        
        if (player == null) {
            reset();
            return;
        }

        this.player = player;
        tbId.setText(String.valueOf(player.getId()));
        tbTaikai.setText(player.getTaikai().getName());
        tbName.setText(player.getName());
        tbSurname.setText(player.getSurname());
        cbCheckedIn.setChecked(player.isCheckedIn());
        tbAge.setText(String.valueOf(player.getAge()));
        tbNumber.setText(String.valueOf(player.getNumber()));
        
        if (Gender.FEMALE.equals(player.getGender())) {
            rbGenderFemale.setChecked(true);
            rbGenderMale.setChecked(false);
        }
        else {
            rbGenderFemale.setChecked(false);
            rbGenderMale.setChecked(true);
        }
        
        lbGrade.setSelectedGrade(player.getGrade());
        
        Logger.trace("exiting setPlayer in PlayerPanel");
    }

    public void setTaikai(TaikaiProxy taikai) {
        this.taikai = taikai;
    }

    public void setTournamentList(List<? extends TournamentProxy> tournaments) {
        Logger.trace("entering setTournamentList in PlayerPanel");
        
        activeTournamentsTable.setData(tournaments, player);
        
        Logger.trace("exiting setTournamentList in PlayerPanel");
    }

}
