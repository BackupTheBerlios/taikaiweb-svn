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
import com.google.gwt.user.client.ui.Widget;
import net.europa13.taikai.web.client.ContentHandlerNotFoundException;
import net.europa13.taikai.web.client.NavigationPath;
import net.europa13.taikai.web.client.TournamentAdminService;
import net.europa13.taikai.web.client.TournamentAdminServiceAsync;
import net.europa13.taikai.web.client.logging.Logger;
import net.europa13.taikai.web.proxy.TournamentGenerationInfo;

/**
 *
 * @author Daniel Wentzel
 */
public class TournamentConfirmGenerateContent extends Content {

    private final TournamentAdminServiceAsync tournamentService =
        GWT.create(TournamentAdminService.class);
    private final TournamentConfirmGeneratePanel panel;
    
    private final Button btnConfirm;
    
    private int tournamentId;

    public TournamentConfirmGenerateContent() {
        panel = new TournamentConfirmGeneratePanel();

        btnConfirm = new Button("Generera", new ClickListener() {

            public void onClick(Widget arg0) {
                generate();
            }
        });
        addControl(btnConfirm);

        Button btnCancel = new Button("Avbryt", new ClickListener() {

            public void onClick(Widget arg0) {
                History.back();
            }
        });
        addControl(btnCancel);
    }
    
    private void generate() {
        tournamentService.generate(tournamentId, new AsyncCallback<Void>() {

            public void onFailure(Throwable t) {
                Logger.debug("Generation failed");
                Logger.error(t.getLocalizedMessage());
            }

            public void onSuccess(Void nothing) {
                Logger.debug("Generation success");
            }
        });
    }

    @Override
    public Widget getPanel() {
        return panel;
    }

    @Override
    public Content handleState(NavigationPath path) throws ContentHandlerNotFoundException {
        if (path.isEmpty()) {
            throw new ContentHandlerNotFoundException("no tournament selected");
        }

        try {
            tournamentId = Integer.parseInt(path.getPathItem(0));

            tournamentService.getGenerationInfo(tournamentId, new AsyncCallback<TournamentGenerationInfo>() {

                public void onFailure(Throwable t) {
                    Logger.error("Det gick inte att hitta turnering " + tournamentId + ".");
                    Logger.debug(t.getLocalizedMessage());
                }

                public void onSuccess(TournamentGenerationInfo info) {
                    panel.setInfo(info);
                    btnConfirm.setEnabled(info.isGenerationPossible());
                }
            });

            return this;
        }
        catch (NumberFormatException ex) {
            throw new ContentHandlerNotFoundException("no tournament selected");
        }

    }
}
