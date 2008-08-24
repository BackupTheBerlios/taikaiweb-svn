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

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.AsyncCallback;
import java.util.ArrayList;
import java.util.List;
import net.europa13.taikai.web.client.logging.Logger;
import net.europa13.taikai.web.proxy.TaikaiProxy;

/**
 *
 * @author Daniel Wentzel
 */
public class TaikaiControl {

    private TaikaiAdminServiceAsync taikaiService =
        GWT.create(TaikaiAdminService.class);
    private List<TaikaiView> views = new ArrayList<TaikaiView>();
    private List<TaikaiProxy> taikaiList = new ArrayList<TaikaiProxy>(0);

    public TaikaiControl() {
    }

    public void addTaikaiView(TaikaiView view) {
        views.add(view);
    }

    protected void fireTaikaiListUpdated() {
        for (TaikaiView view : views) {
            view.taikaiListUpdated(taikaiList);
        }
    }
    
    public TaikaiProxy getTaikai(int number) {
        return taikaiList.get(number);
    }
    
    public List<TaikaiProxy> getTaikaiList() {
        return taikaiList;
    }
    
    public void loadTaikai(int taikaiId, LoadCallback<TaikaiProxy> callback) {
        taikaiService.getTaikai(taikaiId, callback);
    }
    
    public void removeTaikaiView(TaikaiView view) {
        views.remove(view);
    }

    /**
     * Sends a TaikaiProxy object to the server for persistence.
     * @param proxy the proxy to be stored.
     */
    public void storeTaikai(TaikaiProxy proxy) {
        taikaiService.storeTaikai(proxy, new AsyncCallback() {

            public void onFailure(Throwable t) {
                Logger.error(t.getLocalizedMessage());
            }

            public void onSuccess(Object nothing) {
                updateTaikaiList();
            }
        });
    }
    
    /**
     * Updates the list of TaikaiProxies. Upon completion of the update all
     * registered views are signalled for update.
     */
    public void updateTaikaiList() {
        taikaiService.getTaikais(new AsyncCallback<List<TaikaiProxy>>() {

            public void onFailure(Throwable t) {
                Logger.error(t.getLocalizedMessage());
            }

            public void onSuccess(List<TaikaiProxy> taikaiList) {
                TaikaiControl.this.taikaiList = taikaiList;
                fireTaikaiListUpdated();
            }
        });
    }
}
