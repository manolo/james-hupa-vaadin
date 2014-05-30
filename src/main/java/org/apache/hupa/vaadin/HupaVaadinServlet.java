package org.apache.hupa.vaadin;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;

import org.apache.hupa.server.servlet.DownloadAttachmentServlet;
import org.apache.hupa.server.servlet.MessageSourceServlet;
import org.apache.hupa.shared.SConsts;
import org.apache.hupa.vaadin.hupa.HupaConnector;

import com.vaadin.server.ServiceException;
import com.vaadin.server.SessionInitEvent;
import com.vaadin.server.SessionInitListener;
import com.vaadin.server.UIClassSelectionEvent;
import com.vaadin.server.UIProvider;
import com.vaadin.server.VaadinServlet;
import com.vaadin.server.VaadinSession;
import com.vaadin.ui.UI;

@SuppressWarnings("serial")
@WebServlet(value = "/*", asyncSupported = true)
public class HupaVaadinServlet extends VaadinServlet {


    private static final String UA = "user-agent";
    private static final String MOBILE = "mobile";

    private static HupaConnector hupa = HupaConnector.create();

    @WebServlet(value = "/" + SConsts.SERVLET_DOWNLOAD)
    public static class AttachServlet extends DownloadAttachmentServlet {
        public AttachServlet() {
            super(hupa.getCache(), hupa.getLogger());
        }
    }

    @WebServlet(value = "/" + SConsts.SERVLET_SOURCE)
    public static class ViewSourceServlet extends MessageSourceServlet {
        public ViewSourceServlet() {
            super(hupa.getCache(), hupa.getLogger());
        }
    }

    @Override
    protected void servletInitialized() throws ServletException {
        super.servletInitialized();
        getService().addSessionInitListener(new SessionInitListener() {
            @Override
            public void sessionInit(SessionInitEvent event) throws ServiceException {
                event.getSession().addUIProvider(new UIProvider(){
                    public Class<? extends UI> getUIClass(UIClassSelectionEvent event) {
                        Boolean isMobile = false;
                        VaadinSession session = VaadinSession.getCurrent();
                        if (session != null) {
                            isMobile = event.getRequest().getParameter(MOBILE) != null;
                            if (!isMobile) {
                                isMobile = (Boolean)session.getAttribute(MOBILE);
                                if (isMobile == null) {
                                    isMobile = event.getRequest().getHeader(UA).toLowerCase().contains(MOBILE);
                                    session.setAttribute(MOBILE, isMobile);
                                }
                            }
                        }
                        return isMobile ? HupaTouchkitUI.class : HupaDesktopUI.class;
                    }
                });
            }
        });
    }

}
