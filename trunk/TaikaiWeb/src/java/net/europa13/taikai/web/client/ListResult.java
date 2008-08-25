/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
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
