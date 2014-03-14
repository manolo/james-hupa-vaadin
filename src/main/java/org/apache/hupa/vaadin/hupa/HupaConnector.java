package org.apache.hupa.vaadin.hupa;

import java.io.Serializable;
import java.util.List;
import java.util.Properties;

import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.hupa.server.IMAPStoreCache;
import org.apache.hupa.server.ioc.GuiceServerModule;
import org.apache.hupa.server.preferences.UserPreferencesStorage;
import org.apache.hupa.server.service.FetchFoldersService;
import org.apache.hupa.server.service.FetchMessagesService;
import org.apache.hupa.server.service.GetMessageDetailsService;
import org.apache.hupa.server.service.LoginUserService;
import org.apache.hupa.server.service.LogoutUserService;
import org.apache.hupa.server.service.SendForwardMessageService;
import org.apache.hupa.server.service.SendMessageService;
import org.apache.hupa.server.service.SendReplyMessageService;
import org.apache.hupa.shared.data.FetchMessagesActionImpl;
import org.apache.hupa.shared.data.GenericResultImpl;
import org.apache.hupa.shared.data.GetMessageDetailsActionImpl;
import org.apache.hupa.shared.domain.FetchMessagesResult;
import org.apache.hupa.shared.domain.GenericResult;
import org.apache.hupa.shared.domain.GetMessageDetailsAction;
import org.apache.hupa.shared.domain.ImapFolder;
import org.apache.hupa.shared.domain.Message;
import org.apache.hupa.shared.domain.MessageDetails;
import org.apache.hupa.shared.domain.SendForwardMessageAction;
import org.apache.hupa.shared.domain.SendMessageAction;
import org.apache.hupa.shared.domain.SendReplyMessageAction;
import org.apache.hupa.shared.domain.Settings;
import org.apache.hupa.shared.domain.User;
import org.apache.hupa.shared.exception.HupaException;

import com.google.inject.Guice;
import com.google.inject.Inject;
import com.google.inject.Injector;
import com.google.inject.Module;
import com.google.inject.Provides;
import com.google.inject.Singleton;
import com.vaadin.server.VaadinService;
import com.vaadin.server.WrappedHttpSession;

@Singleton
@SuppressWarnings("serial")
public class HupaConnector implements Serializable{
    
    private static HupaConnector instance;
    
    private User user;
    
    @Inject LoginUserService loginUserService;
    @Inject LogoutUserService logoutUserService;
    @Inject FetchFoldersService fetchFoldersService;
    @Inject FetchMessagesService fetchMessagesService;
    @Inject GetMessageDetailsService getMessageDetailsService;
    @Inject SendMessageService sendMessageService;
    @Inject SendReplyMessageService sendReplyMessageService;
    @Inject SendForwardMessageService sendForwardMessageService;
    @Inject IMAPStoreCache cache;
    @Inject UserPreferencesStorage userPreferencesStorage;
    @Inject Log log;

    public Settings getSettings(String email) {
        return loginUserService.getSettings(email);
    }
    
    public static HupaConnector create() {
        if (instance == null) {
            
            Module module = new GuiceServerModule(createDefaultHupaProperties()) {
                StubHttpSession stub = new StubHttpSession();
                @Provides
                public HttpSession getHttpSession() {
                    return VaadinService.getCurrentRequest() != null &&
                            VaadinService.getCurrentRequest().getWrappedSession() != null ? 
                            ((WrappedHttpSession)VaadinService.getCurrentRequest().getWrappedSession()).getHttpSession() :
                            stub;
                }                
            };
            
            Injector injector = Guice.createInjector(module);
            instance = injector.getInstance(HupaConnector.class); 
        }
        return instance;
    }
    
    public boolean doLogin(String email, String pass, String imapServer,
            String imapPort, Boolean imapSecure, String smtpServer, String smtpPort,
            Boolean smtpSecure) {
        Settings s = HupaConnector.create().getSettings(email);
        s.setImapServer(imapServer);
        s.setImapPort(Integer.valueOf(imapPort));
        s.setImapSecure(imapSecure);
        s.setSmtpServer(smtpServer);
        s.setSmtpPort(Integer.valueOf(smtpPort));
        s.setSmtpSecure(smtpSecure);
        try {
            user = loginUserService.login(email, pass, s);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
    
    public User getUser() {
        return user;
    }
    
    public void doLogout() {
        try {
            logoutUserService.logout();
        } catch (HupaException e) {
            e.printStackTrace();
        }
    }

    public List<ImapFolder> fetchFolders() {
        try {
            return fetchFoldersService.fetch(null, true);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    
    public FetchMessagesResult fetchMessages(ImapFolder f, String search) {
        FetchMessagesActionImpl action = new FetchMessagesActionImpl(f, 0, 100, search);
        try {
            return fetchMessagesService.fetch(action);
        } catch (HupaException e) {
            e.printStackTrace();
            return null;
        }
    }

    public MessageDetails loadMessage(ImapFolder folder, Message msg) {
        GetMessageDetailsAction action = new GetMessageDetailsActionImpl(folder, msg.getUid());
        try {
            return getMessageDetailsService.get(action).getMessageDetails();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    
    private static Properties createDefaultHupaProperties() {
        Properties p = new Properties();
        p.setProperty("IMAPConnectionPoolSize", "2");
        p.setProperty("IMAPConnectionPoolTimeout", "60");
        p.setProperty("SessionDebug", "false");
        p.setProperty("TrustStore", "");
        p.setProperty("TrustStorePassword", "");
        p.setProperty("TrustSSL", "false");
        
        p.setProperty("Username", "false");
        p.setProperty("Password", "false");
        p.setProperty("IMAPServerAddress", "localhost");
        p.setProperty("IMAPServerPort", "143");
        p.setProperty("IMAPS", "false");
        p.setProperty("SMTPServerAddress", "localhost");
        p.setProperty("SMTPServerPort", "25");
        p.setProperty("SMTPS", "false");
        p.setProperty("SMTPAuth", "true");

        p.setProperty("DefaultInboxFolder", "INBOX");
        p.setProperty("DefaultTrashFolder", "Trash");
        p.setProperty("DefaultSentFolder", "Sent");
        p.setProperty("DefaultDraftsFolder", "Drafts");
        p.setProperty("PostFetchMessageCount", "0");
        return p;
    }

    public IMAPStoreCache getCache() {
        return cache;
    }

    public Log getLogger() {
        return log;
    }

    public UserPreferencesStorage getUserStorage() {
        return userPreferencesStorage;
    }

    public GenericResult sendMessage(SendMessageAction action) {
        GenericResult ret = new GenericResultImpl();
        try {
            ret =  sendMessageService.send(action);
        } catch (Exception e) {
            ret.setSuccess(false);
            ret.setError(e.getMessage());
        }
        return ret;
    }

    public GenericResult sendReplyMessage(SendReplyMessageAction action) {
        GenericResult ret = new GenericResultImpl();
        try {
            sendReplyMessageService.send(action);
        } catch (Exception e) {
            ret.setSuccess(false);
            ret.setError(e.getMessage());
        }
        return ret;
    }

    public GenericResult sendForwardMessage(SendForwardMessageAction action) {
        GenericResult ret = new GenericResultImpl();
        try {
            sendForwardMessageService.send(action);
        } catch (Exception e) {
            ret.setSuccess(false);
            ret.setError(e.getMessage());
        }
        return ret;
    }
}
