/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.europa13.taikai.web.client.ui;

import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.SourcesTableEvents;
import com.google.gwt.user.client.ui.TableListener;
import java.util.List;
import net.europa13.taikai.web.proxy.TaikaiProxy;

/**
 *
 * @author daniel
 */
public class TaikaiTable extends Grid {

//    private final Grid taikaiGrid;
    private int selectedRow;
    
    public static interface Listener {
        public void rowSelected(int row);
    }

    public TaikaiTable() {
        super(1, 4);

        setText(0, 0, "Id");
        setText(0, 1, "Namn");
        setText(0, 2, "Deltagare");
        setText(0, 3, "Turneringar");


        setStyleName("taikaiweb-Table");
        getRowFormatter().setStyleName(0, "taikaiweb-TableHeader");
        getCellFormatter().setStyleName(0, 3, "taikaiweb-TableLastColumn");

        addTableListener(new TableListener() {

            public void onCellClicked(SourcesTableEvents sender, int row, int col) {
                selectRow(row);
            }
        });
    }
    
    private void selectRow(int row) {
        
        styleRow(selectedRow, false);
        styleRow(row, true);
        selectedRow = row;
        
    }
    
    private void styleRow(int row, boolean selected) {
        if (row > 0) {
            if (selected) {
                getRowFormatter().addStyleName(row, "taikaiweb-SelectedRow");
            }
            else {
                getRowFormatter().removeStyleName(row, "taikaiweb-SelectedRow");
            }
        }
    }
    
    protected void setTaikaiList(List<TaikaiProxy> taikaiList) {
        
        int columnCount = getColumnCount();
        
        resize(taikaiList.size() + 1, columnCount);
        for (int i = 0; i < taikaiList.size(); ++i) {
            TaikaiProxy taikai = taikaiList.get(i);
            
            setText(i + 1, 0, String.valueOf(taikai.getId()));
            setText(i + 1, 1, taikai.getName());
            setText(i + 1, 2, String.valueOf(taikai.getPlayerCount()));
            setText(i + 1, 3, String.valueOf(taikai.getTournamentCount()));
        }
    }
}
