package org.apache.hupa.vaadin.ui;

import com.vaadin.ui.AbstractField;
import com.vaadin.ui.Table;

public interface MessageDisplay {

    AbstractField<String> getTextMsg();

    Table getTableAttachments();

}