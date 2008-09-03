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

import com.google.gwt.user.client.ui.CaptionPanel;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.RadioButton;
import net.europa13.taikai.web.client.logging.Logger;

/**
 *
 * @author daniel
 */
public class TournamentPoolPanel extends CaptionPanel {

    final ListBox lbPoolSize;
    final RadioButton rbPreferLarger;
    final RadioButton rbPreferSmaller;

    public TournamentPoolPanel() {
        super("Pooler");

//        final CaptionPanel poolBox = new CaptionPanel("Pooler");
        final Grid poolGrid = new Grid(2, 2);

        poolGrid.setText(0, 0, "Poolstorlek");
        lbPoolSize = new ListBox();
        lbPoolSize.addItem("2", "2");
        lbPoolSize.addItem("3", "3");
        lbPoolSize.setSelectedIndex(1);
        poolGrid.setWidget(0, 1, lbPoolSize);

        poolGrid.setText(1, 0, "Vid udda deltagare");
        FlowPanel prefersLargerPanel = new FlowPanel();
        rbPreferLarger = new RadioButton("rePreferringLarger", "Föredrar större pooler");
        rbPreferLarger.setChecked(true);
        rbPreferSmaller = new RadioButton("rePreferringLarger", "Föredrar mindre pooler");
        prefersLargerPanel.add(rbPreferLarger);
        prefersLargerPanel.add(rbPreferSmaller);
        poolGrid.setWidget(1, 1, prefersLargerPanel);
        add(poolGrid);
    }

    public int getPoolSize() {
        return Integer.parseInt(lbPoolSize.getValue(lbPoolSize.getSelectedIndex()));
    }

    public boolean isPreferringLargerPools() {
        return rbPreferLarger.isChecked();
    }

    public void reset() {
        lbPoolSize.setSelectedIndex(1);
        rbPreferLarger.setChecked(true);
        rbPreferSmaller.setChecked(false);
    }
    
    public void setPoolSize(int poolSize) {
        if (poolSize < 2 || poolSize > 3) {
            Logger.debug("Incorrect poolSize " + poolSize);
            lbPoolSize.setSelectedIndex(1);
            return;
        }
        switch (poolSize) {
            case 2:
                lbPoolSize.setSelectedIndex(0);
                break;
            case 3:
                lbPoolSize.setSelectedIndex(1);
                break;
        }
    }

    public void setPreferringLargerPools(boolean preferringLargerPools) {
        rbPreferLarger.setChecked(preferringLargerPools);
        rbPreferSmaller.setChecked(!preferringLargerPools);
    }
}
