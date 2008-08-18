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
import com.google.gwt.user.client.ui.FormPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;
import net.europa13.taikai.web.client.TaikaiControl;
import net.europa13.taikai.web.client.logging.Logger;

/**
 *
 * @author jonatan
 */
public class CreateTaikaiPanel extends VerticalPanel implements ClickListener {
    private TaikaiControl control;
    private TextBox tournamentNameTB;
    private Button saveTournamentBTN;
    
    public CreateTaikaiPanel(TaikaiControl control) {
        this.control = control;
        
        tournamentNameTB = new TextBox();
        tournamentNameTB.setVisibleLength(20);
        add(tournamentNameTB);
        
        saveTournamentBTN = new Button("Save tournament", this);        
        add(saveTournamentBTN);
        
    }
    
    public void onClick(Widget source) {
        control.createTaikai(tournamentNameTB.getText());
        Logger.trace("'Save tournament' clicked. Tournament name is:" +
                tournamentNameTB.getText());
    }
    
}
