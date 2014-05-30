package org.apache.hupa.vaadin.mobile;

import org.apache.hupa.vaadin.ui.MessageDisplay;

import com.vaadin.addon.touchkit.ui.NavigationView;
import com.vaadin.ui.AbstractField;
import com.vaadin.ui.Table;
import com.vaadin.ui.TextField;

@SuppressWarnings("serial")
public class MobileMessageView extends NavigationView implements MessageDisplay {

    AbstractField<String> msg;
    Table table;
    
    public MobileMessageView() {
        msg = new TextField();
        table = new Table();
    }

    @Override
    public AbstractField<String> getTextMsg() {
        return msg;
    }

    @Override
    public Table getTableAttachments() {
        return table;
    }

}
