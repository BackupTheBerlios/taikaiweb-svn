/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.europa13.taikai.web.client.ui;

import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import java.util.List;
import net.europa13.taikai.web.client.TaikaiControl;
import net.europa13.taikai.web.client.TaikaiView;
import net.europa13.taikai.web.client.TournamentView;
import net.europa13.taikai.web.proxy.TaikaiProxy;
import net.europa13.taikai.web.proxy.TournamentProxy;

/**
 *
 * @author daniel
 */
public class TournamentPanel extends VerticalPanel implements TaikaiView {

    private TextBox tbTournamentName;
    private ListBox lbTaikaiList;
    private List<TaikaiProxy> taikaiList;

    private TaikaiControl taikaiControl;
    
    public TournamentPanel() {
        tbTournamentName = new TextBox();
        add(tbTournamentName);

        lbTaikaiList = new ListBox();
        add(lbTaikaiList);

        taikaiControl = new TaikaiControl();
        taikaiControl.addTaikaiView(this);
        taikaiControl.updateTaikaiList();
    }

    public void taikaiListUpdated(List<TaikaiProxy> taikaiList) {
        
        if (!taikaiList.equals(this.taikaiList)) {
        
            this.taikaiList = taikaiList;
            updateTaikaiListBox();
        }

    }

    private void updateTaikaiListBox() {
    
        lbTaikaiList.clear();
        
        for (TaikaiProxy taikai : taikaiList) {
            lbTaikaiList.addItem(taikai.getName());
        }
        
    }
}
