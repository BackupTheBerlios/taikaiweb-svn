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
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;
import java.util.List;
import net.europa13.taikai.web.client.Controllers;
import net.europa13.taikai.web.client.TaikaiControl;
import net.europa13.taikai.web.client.TaikaiView;
import net.europa13.taikai.web.client.TaikaiWeb;
import net.europa13.taikai.web.proxy.TaikaiProxy;
import net.europa13.taikai.web.proxy.TournamentProxy;

/**
 *
 * @author daniel
 */
public class TournamentPanel extends VerticalPanel implements TaikaiView {

    private TextBox tbTournamentName;
    private ListBox lbTaikaiList;
    private List<TaikaiProxy> taikaiList;
    private TaikaiControl taikaiControl;
    private boolean active;

    public TournamentPanel() {
        this.taikaiControl = Controllers.taikaiControl;
        taikaiControl.addTaikaiView(this);

        tbTournamentName = new TextBox();
        add(tbTournamentName);

        lbTaikaiList = new ListBox();
        add(lbTaikaiList);
        
        Button btnSave = new Button("Spara");
        btnSave.addClickListener(new ClickListener() {

            public void onClick(Widget arg0) {
                TournamentProxy tournament = new TournamentProxy();
                tournament.setTaikai(TaikaiWeb.getSession().getTaikai());
                tournament.setName(tbTournamentName.getText());
                Controllers.tournamentControl.storeTournament(tournament);
            }
        });
        add(btnSave);

//        taikaiControl = new TaikaiControl();
//        taikaiControl.addTaikaiView(this);
//        taikaiControl.updateTaikaiList();
    }
    
       public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        if (this.active == active) {
            return;
        }
        
        this.active = active;
        
        if (active) {
            taikaiControl.addTaikaiView(this);
        }
        else {
            taikaiControl.removeTaikaiView(this);
        }
    }

    public void taikaiListUpdated(List<TaikaiProxy> taikaiList) {

        if (!taikaiList.equals(this.taikaiList)) {

            this.taikaiList = taikaiList;
            updateTaikaiListBox();
        }

    }

    private void updateTaikaiListBox() {

        lbTaikaiList.clear();

        for (TaikaiProxy taikai : taikaiList) {
            lbTaikaiList.addItem(taikai.getName());
        }

    }

    public Panel getPanel() {
        return this;
    }
}
