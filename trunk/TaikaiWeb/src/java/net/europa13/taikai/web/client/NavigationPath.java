/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package net.europa13.taikai.web.client;

import java.util.List;

/**
 *
 * @author daniel
 */
public class NavigationPath {

    private List<String> navPath;
    
    public NavigationPath(List<String> navPath) {
        this.navPath = navPath;
    }
    
    public String getPathItem(int item) {
        return navPath.get(item);
    }
    
    public NavigationPath getSubPath() {
        return new NavigationPath(navPath.subList(1, navPath.size()));
    }
    
    public boolean isDominating(NavigationPath other) {
        if (navPath.size() < other.navPath.size()) {
            return false;
        }
        else {
            for (int i = 0; i < navPath.size(); ++i) {
                if (!navPath.get(i).equals(other.navPath.get(i))) {
                    return false;
                }
            }
            return true;
        }
    }
    
    public boolean isEmpty() {
        return navPath.isEmpty();
    }
}
