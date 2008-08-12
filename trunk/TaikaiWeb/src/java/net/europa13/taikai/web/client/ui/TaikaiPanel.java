/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package net.europa13.taikai.web.client.ui;

import net.europa13.taikai.web.client.*;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.VerticalPanel;
import java.util.List;
import net.europa13.taikai.web.proxy.TaikaiProxy;

/**
 *
 * @author daniel
 */
public class TaikaiPanel extends VerticalPanel implements TaikaiView {

    private Grid taikaiGrid;
    private TaikaiControl control;
    
    public TaikaiPanel(TaikaiControl control) {
        
        this.control = control;
        
        taikaiGrid = new Grid(1, 4);
    
        taikaiGrid.setWidget(0, 0, new HTML("<h3>Id</h3>"));
        taikaiGrid.setWidget(0, 1, new HTML("<h3>Namn</h3>"));
        taikaiGrid.setWidget(0, 2, new HTML("<h3>Deltagare</h3>"));
        taikaiGrid.setWidget(0, 3, new HTML("<h3>Turneringar</h3>"));
        
        add(taikaiGrid);
        
        control.addTaikaiView(this);
        control.updateTaikaiList();
    }

    public void taikaiListUpdated(List<TaikaiProxy> taikaiList) {
        setTaikaiList(taikaiList);
    }
    
    protected void setTaikaiList(List<TaikaiProxy> taikaiList) {
        taikaiGrid.resize(taikaiList.size() + 1, 4);
        for (int i = 0; i < taikaiList.size(); ++i) {
            TaikaiProxy taikai = taikaiList.get(i);
            taikaiGrid.setText(i + 1, 0, String.valueOf(taikai.getId()));
            taikaiGrid.setText(i + 1, 1, taikai.getName());
            taikaiGrid.setText(i + 1, 2, String.valueOf(taikai.getPlayerCount()));
            taikaiGrid.setText(i + 1, 3, String.valueOf(taikai.getTournamentCount()));
        }
    }
    
    
    
}
