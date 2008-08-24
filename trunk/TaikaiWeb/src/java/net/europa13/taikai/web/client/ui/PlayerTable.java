/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.europa13.taikai.web.client.ui;

import com.google.gwt.user.client.ui.CheckBox;
import com.google.gwt.user.client.ui.Grid;
import java.util.List;
import net.europa13.taikai.web.proxy.PlayerProxy;

/**
 *
 * @author daniel
 */
public class PlayerTable extends Grid {

    public PlayerTable() {
        super(1, 4);
        setText(0, 0, "Id");
        setText(0, 1, "Förnamn");
        setText(0, 2, "Efternamn");
        setText(0, 3, "Incheckad");

        setStyleName("taikaiweb-Table");
        getRowFormatter().setStyleName(0, "taikaiweb-TableHeader");
        getCellFormatter().setStyleName(0, 3, "taikaiweb-TableLastColumn");

    }

    public void setPlayerList(List<PlayerProxy> playerList) {
        int columnCount = getColumnCount();

        int playerCount = playerList.size();
        resize(playerCount + 1, columnCount);

        for (int i = 0; i < playerCount; ++i) {
            final PlayerProxy player = playerList.get(i);

//            ClickListener listener = new ClickListener() {
//
//                public void onClick(Widget arg0) {
//                    playerContent.setPlayer(player);
//                    MainPanel.setContent(playerContent);
//                }
//            };

            setText(i + 1, 0, String.valueOf(player.getId()));
//            Hyperlink name = new Hyperlink(player.getName(), "editPlayer");
//            name.addClickListener(listener);
            setText(i + 1, 1, player.getName());
//            Hyperlink surname = new Hyperlink(player.getSurname(), "editPlayer");
//            surname.addClickListener(listener);
            setText(i + 1, 2, player.getSurname());

            CheckBox checkedIn = new CheckBox();
            checkedIn.setChecked(player.isCheckedIn());
            checkedIn.setEnabled(false);
            setWidget(i + 1, 3, checkedIn);

        }

    }
}
