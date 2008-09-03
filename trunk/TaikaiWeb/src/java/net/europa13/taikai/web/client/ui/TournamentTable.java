/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.europa13.taikai.web.client.ui;

import com.google.gwt.user.client.ui.Grid;
import java.util.List;
import net.europa13.taikai.web.proxy.TournamentProxy;

/**
 *
 * @author daniel
 */
public class TournamentTable extends Grid {

    public TournamentTable() {
        super(1, 4);

        setText(0, 0, "Id");
        setText(0, 1, "Namn");
        setText(0, 2, "Deltagare");
        setText(0, 3, "");

        setStyleName("taikaiweb-Table");
        getRowFormatter().setStyleName(0, "taikaiweb-TableHeader");
        getCellFormatter().setStyleName(0, 3, "taikaiweb-TableLastColumn");
    }

    public void reset() {
        resize(1, getColumnCount());
    }
    
    public void setTournamentList(List<TournamentProxy> tournamentList) {

        if (tournamentList == null) {
            reset();
            return;
        }
        
        int columnCount = getColumnCount();
        
        CellFormatter formatter = getCellFormatter();
        
        resize(tournamentList.size() + 1, columnCount);
        for (int i = 0; i < tournamentList.size(); ++i) {
            final TournamentProxy tournament = tournamentList.get(i);

//            ClickListener listener = new ClickListener() {
//
//                public void onClick(Widget arg0) {
//                    tournamentContent.setTournament(tournament);
//                    MainPanel.setContent(tournamentContent);
//                }
//            };

            setText(i + 1, 0, String.valueOf(tournament.getId()));
//            Hyperlink name = new Hyperlink(tournament.getName(), "editTournament");
//            name.addClickListener(listener);
            setText(i + 1, 1, tournament.getName());
            formatter.setWordWrap(i + 1, 1, false);
//            tournamentGrid.setText(i + 1, 2, String.valueOf(tournament.getPlayerCount()));
//            tournamentGrid.setText(i + 1, 3, String.valueOf(tournament.getTournamentCount()));
        }
    }
}
