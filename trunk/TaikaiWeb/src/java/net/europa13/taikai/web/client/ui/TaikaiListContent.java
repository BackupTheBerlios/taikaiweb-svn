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
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.ClickListener;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.SourcesTableEvents;
import com.google.gwt.user.client.ui.TableListener;
import com.google.gwt.user.client.ui.Widget;
import java.util.List;
import net.europa13.taikai.web.client.ContentHandlerNotFoundException;
import net.europa13.taikai.web.client.CustomCallback;
import net.europa13.taikai.web.client.ListResult;
import net.europa13.taikai.web.client.NavigationPath;
import net.europa13.taikai.web.client.TaikaiAdminService;
import net.europa13.taikai.web.client.TaikaiAdminServiceAsync;
import net.europa13.taikai.web.client.logging.Logger;
import net.europa13.taikai.web.proxy.TaikaiProxy;

/**
 *
 * @author Daniel Wentzel
 */
public class TaikaiListContent extends Content {

    private final TaikaiTable taikaiTable;
    private final SimplePanel panel;
    private TaikaiAdminServiceAsync taikaiService =
        GWT.create(TaikaiAdminService.class);
    private List<TaikaiProxy> taikaiList;
    private final String historyToken;
    private final TaikaiDetailsContent taikaiDetailsContent;

    /**
     * Constructor.
     */
    public TaikaiListContent(String historyToken) {


        setTitle("Evenemang");

        this.historyToken = historyToken;

        panel = new SimplePanel();

        createToolbar();

        taikaiTable = new TaikaiTable();
        taikaiTable.addTableListener(new TableListener() {

            public void onCellClicked(SourcesTableEvents sender, int row, int col) {
                TaikaiProxy taikai = taikaiList.get(row - 1);
                History.newItem(TaikaiListContent.this.historyToken + "/" + taikai.getId());
            }
        });

//        taikaiPanel = new TaikaiPanel();

        taikaiDetailsContent = new TaikaiDetailsContent();

        panel.setWidget(taikaiTable);

    }

    private void createToolbar() {

        Button btnCreateTaikai = new Button("Nytt evenemang...");
        btnCreateTaikai.addClickListener(new ClickListener() {

            public void onClick(Widget w) {
                History.newItem(historyToken + "/new");
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

    private void updateTaikaiList() {
        taikaiService.getTaikais(new CustomCallback<ListResult<TaikaiProxy>>() {

            public void onSuccess(ListResult<TaikaiProxy> result) {
                taikaiList = result.getList();
                taikaiTable.setTaikaiList(taikaiList);
            }
        });
    }

    @Override
    public Content handleState(NavigationPath path) throws ContentHandlerNotFoundException {
        if ("new".equals(path.getPathItem(0))) {
            return taikaiDetailsContent.handleState(path.getSubPath());
        }
        else if (path.isEmpty()) {
            panel.setWidget(taikaiTable);
            return this;
        }
        else {
            try {
                return taikaiDetailsContent.handleState(path);
            }
            catch (ContentHandlerNotFoundException ex) {
                return this;
            }
        }
    }
}
