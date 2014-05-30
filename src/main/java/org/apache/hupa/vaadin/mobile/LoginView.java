package org.apache.hupa.vaadin.mobile;

import com.vaadin.addon.touchkit.ui.NavigationButton;
import com.vaadin.addon.touchkit.ui.NavigationButton.NavigationButtonClickEvent;
import com.vaadin.addon.touchkit.ui.NavigationButton.NavigationButtonClickListener;
import com.vaadin.addon.touchkit.ui.NavigationView;
import com.vaadin.addon.touchkit.ui.VerticalComponentGroup;

@SuppressWarnings("serial")
public class LoginView extends NavigationView {

    public LoginView() {
        setCaption("Menu");
        
        final VerticalComponentGroup content = new VerticalComponentGroup();
        NavigationButton button = new NavigationButton("Form");
        button.addClickListener(new NavigationButtonClickListener() {
            @Override
            public void buttonClick(NavigationButtonClickEvent event) {
                getNavigationManager().navigateTo(new MobileLoginView());                
            }
        });
        content.addComponent(button);
        setContent(content);
    };
}
