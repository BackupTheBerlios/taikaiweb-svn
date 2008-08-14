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

import net.europa13.taikai.web.client.*;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.VerticalPanel;
import java.util.List;
import net.europa13.taikai.web.proxy.TaikaiProxy;

/**
 *
 * @author daniel
 */
public class TaikaiPanel extends VerticalPanel implements TaikaiView {

    private Grid taikaiGrid;
    private TaikaiControl control;
    
    public TaikaiPanel(TaikaiControl control) {
        
        this.control = control;
        
        taikaiGrid = new Grid(1, 4);
    
        taikaiGrid.setWidget(0, 0, new HTML("<h3>Id</h3>"));
        taikaiGrid.setWidget(0, 1, new HTML("<h3>Namn</h3>"));
        taikaiGrid.setWidget(0, 2, new HTML("<h3>Deltagare</h3>"));
        taikaiGrid.setWidget(0, 3, new HTML("<h3>Turneringar</h3>"));
        
        add(taikaiGrid);
        
        control.addTaikaiView(this);
        control.updateTaikaiList();
    }

    public void taikaiListUpdated(List<TaikaiProxy> taikaiList) {
        setTaikaiList(taikaiList);
    }
    
    protected void setTaikaiList(List<TaikaiProxy> taikaiList) {
        taikaiGrid.resize(taikaiList.size() + 1, 4);
        for (int i = 0; i < taikaiList.size(); ++i) {
            TaikaiProxy taikai = taikaiList.get(i);
            taikaiGrid.setText(i + 1, 0, String.valueOf(taikai.getId()));
            taikaiGrid.setText(i + 1, 1, taikai.getName());
            taikaiGrid.setText(i + 1, 2, String.valueOf(taikai.getPlayerCount()));
            taikaiGrid.setText(i + 1, 3, String.valueOf(taikai.getTournamentCount()));
        }
    }
    
    
    
}
