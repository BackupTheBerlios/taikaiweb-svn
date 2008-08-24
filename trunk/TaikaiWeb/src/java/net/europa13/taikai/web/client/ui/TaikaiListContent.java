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

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.History;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.ClickListener;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.SourcesTableEvents;
import com.google.gwt.user.client.ui.TableListener;
import com.google.gwt.user.client.ui.Widget;
import java.util.List;
import net.europa13.taikai.web.client.CustomCallback;
import net.europa13.taikai.web.client.TaikaiAdminService;
import net.europa13.taikai.web.client.TaikaiAdminServiceAsync;
import net.europa13.taikai.web.client.logging.Logger;
import net.europa13.taikai.web.proxy.TaikaiProxy;

/**
 *
 * @author Daniel Wentzel
 */
public class TaikaiListContent extends Content {

    private final TaikaiPanel taikaiPanel;
    private final TaikaiTable taikaiTable;
    private final SimplePanel panel;

    private TaikaiAdminServiceAsync taikaiService =
        GWT.create(TaikaiAdminService.class);
    private List<TaikaiProxy> taikaiList;
    
    /**
     * Constructor.
     */
    public TaikaiListContent() {

        setTitle("Evenemang");
        
        panel = new SimplePanel();
        
        createToolbar();
        
        taikaiTable = new TaikaiTable();
        taikaiTable.addTableListener(new TableListener() {
            public void onCellClicked(SourcesTableEvents sender, int row, int col) {
                TaikaiProxy taikai = taikaiList.get(row - 1);
                History.newItem("events/" + taikai.getId());
            }
        });
        
        taikaiPanel = new TaikaiPanel();
        taikaiPanel.addSaveListener(new ClickListener() {

            public void onClick(Widget arg0) {
                TaikaiProxy taikai = taikaiPanel.getTaikai();
                storeTaikai(taikai);
                
            }
        });

        panel.setWidget(taikaiTable);
        
    }
    
    private void createToolbar() {

        Button btnCreateTaikai = new Button("Nytt evenemang...");
        btnCreateTaikai.addClickListener(new ClickListener() {

            public void onClick(Widget w) {
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
            updateTaikaiList();
        }
    }
    
    private void storeTaikai(TaikaiProxy proxy) {
        taikaiService.storeTaikai(proxy, new CustomCallback() {

            public void onSuccess(Object nothing) {
                updateTaikaiList();
                History.newItem("events");
            }
        });
    }
    
//    public void taikaiLoaded

//    public void taikaiListUpdated(List<TaikaiProxy> taikaiList) {
//        taikaiTable.setTaikaiList(taikaiList);
//    }

    private void updateTaikaiList() {
        taikaiService.getTaikais(new CustomCallback<List<TaikaiProxy>>() {

            public void onSuccess(List<TaikaiProxy> taikaiList) {
                TaikaiListContent.this.taikaiList = taikaiList;
                taikaiTable.setTaikaiList(taikaiList);
            }
        });
    }
    
    @Override
    public void handleState(String state) {
        if ("new".equals(state)) {
            taikaiPanel.reset();
            panel.setWidget(taikaiPanel);
        }
        else if (state.isEmpty()) {
            panel.setWidget(taikaiTable);
        }
        else {
            int taikaiId = Integer.parseInt(state);
            taikaiService.getTaikai(taikaiId, new CustomCallback<TaikaiProxy>() {

                public void onSuccess(TaikaiProxy taikai) {
                    taikaiPanel.setTaikai(taikai);
                    panel.setWidget(taikaiPanel);
                }
            });
            
        }
        
        Logger.debug("TaikaiList" + state);
    }
}
