package org.apache.hupa.vaadin.mobile;

import org.apache.hupa.vaadin.ui.MessageListDisplay;

import com.vaadin.addon.touchkit.ui.NavigationView;
import com.vaadin.ui.Table;

@SuppressWarnings("serial")
public class MobileMessageListView extends NavigationView implements MessageListDisplay {
    
    Table table;

    public MobileMessageListView() {
        table = new Table();
    }

    @Override
    public Table getTableMsgs() {
        return table;
    }

}
