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
package net.europa13.taikai.web.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.Cookies;
import com.google.gwt.user.client.History;
import com.google.gwt.user.client.HistoryListener;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.ClickListener;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HasVerticalAlignment;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;
import net.europa13.taikai.web.client.logging.LogLevel;
import net.europa13.taikai.web.client.logging.Logger;
import net.europa13.taikai.web.client.logging.PanelHtmlLogTarget;
import net.europa13.taikai.web.client.ui.Content;
import net.europa13.taikai.web.client.ui.MainPanel;
import net.europa13.taikai.web.client.ui.PlayerContent;
import net.europa13.taikai.web.client.ui.SessionContent;
import net.europa13.taikai.web.client.ui.TaikaiListContent;
import net.europa13.taikai.web.client.ui.TournamentContent;
import net.europa13.taikai.web.proxy.TaikaiProxy;

/**
 *
 * @author Daniel Wentzel
 */
public class TaikaiWebEntryPoint implements EntryPoint {

    private HistoryListener historyListener = new HistoryListener() {

        public void onHistoryChanged(String historyToken) {
            Content content = Navigator.getContent(historyToken);
            if (content == null) {
                return;
            }
            MainPanel.setContent(content);
        }
    };

    /** Creates a new instance of TaikaiWebEntryPoint */
    public TaikaiWebEntryPoint() {
    }

    private void initContentHandlers() {
        //*********************************************************************
        // Session Content
        Content sessionContent = new SessionContent(TaikaiWeb.getSession());
        MainPanel.getInstance().registerContent(sessionContent, MainPanel.Subsystem.SESSION, "session");

        //*********************************************************************
        // Taikai Content
        Content taikaiListContent = new TaikaiListContent("events");
        MainPanel.getInstance().registerContent(taikaiListContent, MainPanel.Subsystem.ADMIN, "events");


        //*********************************************************************
        // Tournament Content
        Content tournamentListContent =
            new TournamentContent("tournaments");
        MainPanel.getInstance().registerContent(tournamentListContent, MainPanel.Subsystem.ADMIN, "tournaments");

        //*********************************************************************
        // Player Content
        Content playerListContent =
            new PlayerContent("players");
        MainPanel.getInstance().registerContent(playerListContent, MainPanel.Subsystem.ADMIN, "players");
    }

    private void initLogger() {
        //*********************************************************************
        // Logger
        VerticalPanel logPanel = new VerticalPanel();
        VerticalPanel logPanelContents = new VerticalPanel();
        logPanel.setWidth("100%");
        logPanel.setBorderWidth(1);
        Button logClearBTN = new Button("Töm logg", new ClickListener() {

            public void onClick(Widget source) {
                Logger.clear();
            }
        });
        HorizontalPanel logPanelHeader = new HorizontalPanel();
        logPanelHeader.setVerticalAlignment(HasVerticalAlignment.ALIGN_MIDDLE);
        logPanelHeader.add(new HTML("<h2>Logg</h2>"));
        logPanelHeader.add(logClearBTN);

        logPanel.add(logPanelHeader);
        logPanel.add(logPanelContents);
        RootPanel.get("root_bottom").add(logPanel);


        Logger.setTarget(new PanelHtmlLogTarget(logPanelContents));
        Logger.setLevel(LogLevel.TRACE);
    }

    private void initState() {
        //*********************************************************************
        // Restore application state from cookies.
        final String taikaiCookie = Cookies.getCookie("taikaiId");
        if (taikaiCookie != null) {

//            final ServiceWaiter waiter = new ServiceWaiter();

            TaikaiAdminServiceAsync service =
                GWT.create(TaikaiAdminService.class);
            service.getTaikai(Integer.parseInt(taikaiCookie), new AsyncCallback<TaikaiProxy>() {

                public void onFailure(Throwable arg0) {
                    Logger.error("Evenemang " + taikaiCookie + " kan inte laddas in.");
                    start();
                    //                    waiter.stop();
                }

                public void onSuccess(TaikaiProxy taikai) {
                    TaikaiWeb.getSession().setTaikai(taikai);
                    start();
//                    waiter.stop();
                }
            });

//            DeferredCommand.addCommand(waiter);
        }
    }

    /** 
    The entry point method, called automatically by loading a module
    that declares an implementing class as an entry-point
     */
    public void onModuleLoad() {

        initLogger();
        initContentHandlers();
        initState();
        
    }

    private void start() {
        RootPanel.get("root_top").add(MainPanel.getInstance());

        History.addHistoryListener(historyListener);
        historyListener.onHistoryChanged(History.getToken());
    }

}

