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
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.ClickListener;
import com.google.gwt.user.client.ui.Panel;
import net.europa13.taikai.web.client.*;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.SourcesTableEvents;
import com.google.gwt.user.client.ui.TableListener;
import com.google.gwt.user.client.ui.Widget;
import java.util.List;
import net.europa13.taikai.web.client.logging.Logger;
import net.europa13.taikai.web.proxy.TaikaiProxy;

/**
 *
 * @author Daniel Wentzel
 */
public class TaikaiListContent extends Content implements TaikaiView {

    
    private final TaikaiControl control;
    private Panel panel;
    private final TaikaiPanel taikaiPanel;
    
    private final TaikaiTable taikaiListPanel =
            new TaikaiTable();
    
    /**
     * Constructor.
     */
    public TaikaiListContent() {

        setTitle("Evenemang");

        this.control = Controllers.taikaiControl;
        
        createToolbar();
        
        taikaiListPanel.addTableListener(new TableListener() {
            public void onCellClicked(SourcesTableEvents sender, int row, int col) {
                //selectRow(row);
                TaikaiProxy taikai = control.getTaikai(row - 1);
                History.newItem("events/" + taikai.getId());
            }
        });
        
        taikaiPanel = new TaikaiPanel();
        taikaiPanel.addSaveListener(new ClickListener() {

            public void onClick(Widget arg0) {
                TaikaiProxy taikai = taikaiPanel.getTaikai();
                control.storeTaikai(taikai);
                History.newItem("events");
            }
        });
        
        panel = taikaiListPanel;

    }
    
    private void createToolbar() {

        Button btnCreateTaikai = new Button("Nytt evenemang...");
        btnCreateTaikai.addClickListener(new ClickListener() {

            public void onClick(Widget w) {
//                taikaiContent.clear();
//                MainPanel.setContent(taikaiContent);
                History.newItem("events/new");
            }
        });

        addControl(btnCreateTaikai);
    }

    /**
     * 
     * @return the Panel used to display this content.
     */
    public Panel getPanel() {
        return panel;
    }

    @Override
    public void setActive(boolean active) {
        super.setActive(active);

        if (active) {
            control.addTaikaiView(this);
            Controllers.taikaiControl.updateTaikaiList();
        }
        else {
            control.removeTaikaiView(this);
        }
    }
    
//    public void taikaiLoaded

    public void taikaiListUpdated(List<TaikaiProxy> taikaiList) {
        taikaiListPanel.setTaikaiList(taikaiList);
    }

    private LoadCallback<TaikaiProxy> loader = new LoadCallback<TaikaiProxy>() {

        @Override
        public void objectLoaded(TaikaiProxy object) {
            taikaiPanel.setTaikai(object);
        }
        
    };

    @Override
    public void handleState(String state) {
        if ("new".equals(state)) {
            taikaiPanel.reset();
            panel = taikaiPanel;
        }
        else if (state.isEmpty()) {
            panel = taikaiListPanel;
        }
        else {
            int taikaiId = Integer.parseInt(state);
            control.loadTaikai(taikaiId, loader);
            panel = taikaiPanel;
        }
        
        Logger.debug("TaikaiList" + state);
    }
}
