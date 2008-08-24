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

import com.google.gwt.user.client.ui.ChangeListener;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.Widget;
import java.util.ArrayList;
import java.util.List;
import net.europa13.taikai.web.client.Controllers;
import net.europa13.taikai.web.client.Session;
import net.europa13.taikai.web.client.TaikaiView;
import net.europa13.taikai.web.proxy.CourtProxy;
import net.europa13.taikai.web.proxy.TaikaiProxy;
import net.europa13.taikai.web.proxy.TournamentProxy;

/**
 *
 * @author daniel
 */
public class SessionContent extends Content implements TaikaiView {
    
    private Session session;
    final private ListBox lbTaikai;
    final private ListBox lbTournament;
    final private ListBox lbCourt;
    
    private List<TaikaiProxy> taikaiList;
    private List<TournamentProxy> tournamentList;
    private List<CourtProxy> courtList;
    
    private Panel panel = new SimplePanel();
    
    public SessionContent(Session session) {
        this.session = session;
        
        Grid grid = new Grid(2, 6);
        grid.setText(0, 1, "Taikai");
        grid.setText(0, 3, "Turnering");
        grid.setText(0, 5, "Bana");
        grid.setText(1, 0, "/");
        lbTaikai = new ListBox();
        lbTaikai.addChangeListener(new ChangeListener() {

            public void onChange(Widget arg0) {
                taikaiChanged();
            }
        });
        grid.setWidget(1, 1, lbTaikai);
        grid.setText(1, 2, "/");
        lbTournament = new ListBox();
        lbTournament.addChangeListener(new ChangeListener() {

            public void onChange(Widget arg0) {
                tournamentChanged();
            }
        });
        grid.setWidget(1, 3, lbTournament);
        grid.setText(1, 4, "/");
        lbCourt = new ListBox();
        lbCourt.addChangeListener(new ChangeListener() {

            public void onChange(Widget arg0) {
                courtChanged();
            }
        });
        grid.setWidget(1, 5, lbCourt);
        
        grid.setStyleName("taikaiweb-Table");
        grid.getRowFormatter().setStyleName(0, "taikaiweb-TableHeader");
        grid.getCellFormatter().setStyleName(0, 5, "taikaiweb-TableLastColumn");
        panel.add(grid);
    }
    
    private void clearList(ListBox lb) {
        lb.clear();
        lb.addItem("-");
    }
    
    private void courtChanged() {
        int courtIndex = lbCourt.getSelectedIndex() - 1;
        if (courtIndex >= 0) {
            CourtProxy court = courtList.get(courtIndex);
            session.setCourt(court);
        }
        else {
            session.setCourt(null);
        }
    }
    
    public Session getSession() {
        return session;
    }

    public void setSession(Session session) {
        this.session = session;
    }
    
    private void taikaiChanged() {
        int taikaiIndex = lbTaikai.getSelectedIndex() - 1;
        if (taikaiIndex >= 0) {
            TaikaiProxy taikai = taikaiList.get(taikaiIndex);
            session.setTaikai(taikai);
        }
        else {
            session.setTaikai(null);
        }
            
    }
    
    private void tournamentChanged() {
        int tournamentIndex = lbTournament.getSelectedIndex() - 1;
        if (tournamentIndex >= 0) {
            TournamentProxy tournament = tournamentList.get(tournamentIndex);
            session.setTournament(tournament);
        }
        else {
            session.setTournament(null);
        }
            
    }
    
    public void taikaiListUpdated(List<TaikaiProxy> taikaiList) {

        if (lbTaikai.getSelectedIndex() - 1 >= 0) {
            int newSelectedIndex = 0;
            TaikaiProxy selectedTaikai = this.taikaiList.get(lbTaikai.getSelectedIndex() - 1);
            this.taikaiList = new ArrayList<TaikaiProxy>(taikaiList);
            clearList(lbTaikai);
            for (int i = 0; i < this.taikaiList.size(); ++i) {
                TaikaiProxy taikai = this.taikaiList.get(i);
                if (taikai.getId() == selectedTaikai.getId()) {
                    newSelectedIndex = i + 1;
                }
                lbTaikai.addItem(taikai.getName());
            }
            lbTaikai.setSelectedIndex(newSelectedIndex);
        }
        else {
            this.taikaiList = new ArrayList<TaikaiProxy>(taikaiList);
            clearList(lbTaikai);
            for (TaikaiProxy taikai : this.taikaiList) {
                lbTaikai.addItem(taikai.getName());
            }
        }
        
    }

    @Override
    public void setActive(boolean active) {
        super.setActive(active);
        
        if (active) {
            Controllers.taikaiControl.addTaikaiView(this);
            taikaiListUpdated(Controllers.taikaiControl.getTaikaiList());
        }
        else {
            Controllers.taikaiControl.removeTaikaiView(this);
        }
    }

    public Panel getPanel() {
        return panel;
    }
            
}
