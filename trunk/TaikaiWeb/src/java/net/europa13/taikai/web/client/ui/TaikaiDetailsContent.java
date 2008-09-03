/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.europa13.taikai.web.client.ui;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.ClickListener;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.Widget;
import net.europa13.taikai.web.client.ContentHandlerNotFoundException;
import net.europa13.taikai.web.client.NavigationPath;
import net.europa13.taikai.web.client.TaikaiAdminService;
import net.europa13.taikai.web.client.TaikaiAdminServiceAsync;
import net.europa13.taikai.web.client.logging.Logger;
import net.europa13.taikai.web.proxy.TaikaiProxy;

/**
 *
 * @author daniel
 */
public class TaikaiDetailsContent extends Content {

    private TaikaiAdminServiceAsync taikaiService =
        GWT.create(TaikaiAdminService.class);
    private final TaikaiPanel panel;
    
    public TaikaiDetailsContent() {
        panel = new TaikaiPanel();
        Button btnSave = new Button("Spara", new ClickListener() {

            public void onClick(Widget sender) {
                TaikaiProxy taikai = panel.getTaikai();
                storeTaikai(taikai);

            }
        });
        
        addControl(btnSave);
    }
    
    public Panel getPanel() {
        return panel;
    }
    
    @Override
    public Content handleState(NavigationPath path) throws ContentHandlerNotFoundException {
        
        if (path.isEmpty()) {
            panel.reset();
            return this;
        }
        else {
            try {
                final int taikaiId = Integer.parseInt(path.getPathItem(0));

                taikaiService.getTaikai(taikaiId, new AsyncCallback<TaikaiProxy>() {

                    public void onFailure(Throwable t) {
                        Logger.error("Det gick inte att hitta evenemang " + taikaiId + ".");
                        Logger.debug(t.getLocalizedMessage());
                    }

                    public void onSuccess(TaikaiProxy taikai) {
                        panel.setTaikai(taikai);
//                        panel.setWidget(taikaiPanel);
                    }
                });

            }
            catch (NumberFormatException ex) {
                Logger.error(path + " är ett ogiltigt värde för evenemang.");
                throw new ContentHandlerNotFoundException(ex);
            }
            return this;
        }
    }
    
    private void storeTaikai(final TaikaiProxy proxy) {
        taikaiService.storeTaikai(proxy, new AsyncCallback() {

            public void onFailure(Throwable t) {
                Logger.error("Det gick inte att spara evenemang " + proxy.getId() + ".");
                Logger.debug(t.getLocalizedMessage());
            }
            
            public void onSuccess(Object nothing) {
                Logger.info(proxy.getName() + " sparad.");
//                updateTaikaiList();
            }
        });
    }
}
