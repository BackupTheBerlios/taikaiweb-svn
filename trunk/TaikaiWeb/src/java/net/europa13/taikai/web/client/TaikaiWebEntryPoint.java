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
import com.google.gwt.user.client.ui.RootPanel;
import net.europa13.taikai.web.client.ui.MainPanel;

/**
 *
 * @author daniel
 */
public class TaikaiWebEntryPoint implements EntryPoint {

    /** Creates a new instance of TaikaiWebEntryPoint */
    public TaikaiWebEntryPoint() {
    }

    /** 
        The entry point method, called automatically by loading a module
        that declares an implementing class as an entry-point
    */
    public void onModuleLoad() {

        
        
        
        RootPanel.get().add(MainPanel.getInstance());
        
    }

}
