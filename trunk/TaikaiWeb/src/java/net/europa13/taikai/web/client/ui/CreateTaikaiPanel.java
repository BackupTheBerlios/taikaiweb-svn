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
import com.google.gwt.user.client.ui.DisclosurePanel;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;
import net.europa13.taikai.web.client.Controllers;
import net.europa13.taikai.web.client.TaikaiControl;
import net.europa13.taikai.web.client.View;
import net.europa13.taikai.web.client.logging.Logger;
import net.europa13.taikai.web.proxy.TaikaiProxy;

/**
 *
 * @author jonatan
 */
public class CreateTaikaiPanel extends VerticalPanel implements ClickListener, View {
    private TaikaiControl control;
    private Grid dataGrid;
    private TextBox tournamentNameTB;
    private TextBox tournamentLocationTB;
    private Button saveTournamentBTN;
    private DisclosurePanel infoPanel;
    
    private boolean active;
    
    public CreateTaikaiPanel() {
        this.control = Controllers.taikaiControl;
        
        add(new HTML("<h2>Edit Taikai</h2>"));
        
        infoPanel = new DisclosurePanel("Information message");
        add(infoPanel);
        
        tournamentNameTB = new TextBox();
        tournamentNameTB.setVisibleLength(20);
        
        tournamentLocationTB = new TextBox();
        tournamentLocationTB.setVisibleLength(20);
        
        dataGrid = new Grid(4, 2);
        dataGrid.setWidget(0, 0, new Label("Name"));
        dataGrid.setWidget(0, 1, tournamentNameTB);
        dataGrid.setWidget(1, 0, new Label("Date"));
        
        dataGrid.setWidget(2, 0, new Label("Location"));
        dataGrid.setWidget(2, 1, tournamentLocationTB);
        
        
        add(dataGrid);
        
        saveTournamentBTN = new Button("Save Taikai", this);        
        add(saveTournamentBTN);
        
    }
    
    public void onClick(Widget source) {
        infoPanel.setContent(new Label("The Taikai was updated"));
        infoPanel.setOpen(true);

        // Nytt sätt...
        TaikaiProxy taikai = new TaikaiProxy();
        taikai.setName(tournamentNameTB.getText());
        control.storeTaikai(taikai);

        // control.createTaikai(tournamentNameTB.getText());
        Logger.trace("'Save Taikai' clicked. Taikai name is:" +
                tournamentNameTB.getText());
    }

    public boolean isActive() {
        return active;
//        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void setActive(boolean active) {
        if (this.active = active) {
            return;
        }
        
        this.active = active;
//        throw new UnsupportedOperationException("Not supported yet.");
    }
    
    public Panel getPanel() {
        return this;
    }
    
}
