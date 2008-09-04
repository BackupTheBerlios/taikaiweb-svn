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
import com.google.gwt.user.client.ui.ChangeListener;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.RadioButton;
import com.google.gwt.user.client.ui.Widget;
import net.europa13.taikai.web.client.logging.Logger;

/**
 *
 * @author daniel
 */
public class TournamentPoolPanel extends CaptionPanel {

    private final FlexTable poolGrid;
    private final ListBox lbPoolSize;
    private final RadioButton rbPreferLarger;
    private final RadioButton rbPreferSmaller;
    
    private final Label lblPoolCountTotal;
    private final Label lblPoolCount2;
    private final Label lblPoolCount3;
    private final Label lblPoolCount4;
    
    private final int[] poolCounts;

    private boolean enabled = true;
    
    public TournamentPoolPanel() {
        super("Pooler");

        poolCounts = new int[3];
        lblPoolCountTotal = new Label();
        lblPoolCount2 = new Label();
        lblPoolCount3 = new Label();
        lblPoolCount4 = new Label();
        
        poolGrid = new FlexTable();

        poolGrid.setText(0, 0, "Poolstorlek");
        lbPoolSize = new ListBox();
        lbPoolSize.addItem("2", "2");
        lbPoolSize.addItem("3", "3");
        lbPoolSize.setSelectedIndex(1);
        lbPoolSize.addChangeListener(new ChangeListener() {

            public void onChange(Widget sender) {
                updateControls();
            }
        });
        poolGrid.setWidget(0, 1, lbPoolSize);
        poolGrid.getFlexCellFormatter().setColSpan(0, 1, 3);

        poolGrid.setText(1, 0, "Vid udda deltagare");
        FlowPanel prefersLargerPanel = new FlowPanel();
        rbPreferLarger = new RadioButton("rePreferringLarger", "Föredrar större pooler");
        rbPreferLarger.setChecked(true);
        rbPreferSmaller = new RadioButton("rePreferringLarger", "Föredrar mindre pooler");
        prefersLargerPanel.add(rbPreferLarger);
        prefersLargerPanel.add(rbPreferSmaller);
        poolGrid.setWidget(1, 1, prefersLargerPanel);
        poolGrid.getFlexCellFormatter().setColSpan(1, 1, 3);
        
        poolGrid.setText(2, 0, "Antal pooler");
        poolGrid.getFlexCellFormatter().setColSpan(2, 0, 4);
        
        poolGrid.setText(3, 0, "Totalt");
        poolGrid.setText(3, 1, "2-man");
        poolGrid.setText(3, 2, "3-man");
        poolGrid.setText(3, 3, "4-man");
        poolGrid.setWidget(4, 0, lblPoolCountTotal);
        poolGrid.setWidget(4, 1, lblPoolCount2);
        poolGrid.setWidget(4, 2, lblPoolCount3);
        poolGrid.setWidget(4, 3, lblPoolCount4);
            
        for (int i = 0; i < 3; ++i) {
            poolGrid.getColumnFormatter().setWidth(i, "25%");
        }
            
        
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
    
    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
        
        lbPoolSize.setEnabled(enabled);
        rbPreferLarger.setEnabled(enabled);
        rbPreferSmaller.setEnabled(enabled);
    }
    
    public void setPoolCount(int poolSize, int poolCount) {
        if (poolSize < 2 || poolSize > 4) {
            Logger.debug("Incorrect poolSize " + poolSize);
            return;
        }
        
        Logger.debug("Setting pool count " + poolCount + " for pool size " + poolSize);
        
        poolCounts[poolSize - 2] = poolCount;
        
        updatePoolCounts();
        
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
        updateControls();
    }

    public void setPreferringLargerPools(boolean preferringLargerPools) {
        rbPreferLarger.setChecked(preferringLargerPools);
        rbPreferSmaller.setChecked(!preferringLargerPools);
    }
    
    private void updateControls() {
        int poolSize = getPoolSize();
        
        if (poolSize == 2) {
            rbPreferSmaller.setEnabled(false);
            rbPreferSmaller.setChecked(false);
            rbPreferLarger.setChecked(true);
        }
        else {
            rbPreferSmaller.setEnabled(enabled);
        }
    }
    
    private void updatePoolCounts()  {
        int totalCount = 0;
        
        for (int i = 0; i < 3; ++i) {
            totalCount += poolCounts[i];
        }
        
        lblPoolCountTotal.setText(String.valueOf(totalCount));
        lblPoolCount2.setText(String.valueOf(poolCounts[0]));
        lblPoolCount3.setText(String.valueOf(poolCounts[1]));
        lblPoolCount4.setText(String.valueOf(poolCounts[2]));
        
    }
        
}
