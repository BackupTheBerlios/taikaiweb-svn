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

import java.io.Serializable;
import java.util.List;

/**
 *
 * @author Daniel Wentzel
 */
public class ListResult<C> implements Serializable {
    
    private List<C> list;
    private int firstResult;
    private int maxResults;
    
    private ListResult() {
        
    }
    
    public ListResult(List<C> list, int firstResult, int maxResults) {
        this.list = list;
        this.maxResults = maxResults;
    }
    
    public List<C> getList() {
        return list;
    }
    
    public int getFirstResult() {
        return firstResult;
    }
    
    public int getMaxResults() {
        return maxResults;
    }

}
