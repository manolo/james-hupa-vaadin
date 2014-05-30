package org.apache.hupa.vaadin;

import javax.servlet.http.HttpSession;

import org.apache.hupa.server.ioc.GuiceServerModule;
import org.apache.hupa.shared.domain.Settings;
import org.apache.hupa.vaadin.hupa.HupaConnector;
import org.apache.hupa.vaadin.hupa.StubHttpSession;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.Module;
import com.google.inject.Provides;

public class HupaFactoryTest {

    @BeforeClass
    public static void setup() {
        Module module = new GuiceServerModule(
                HupaConnector.createDefaultHupaProperties()) {
            @Provides
            public HttpSession getHttpSession() {
                return new StubHttpSession();
            }
        };
        Injector injector = Guice.createInjector(module);
        HupaConnector.setInstance(injector.getInstance(HupaConnector.class));
    }

    @Test
    public void test() {
        HupaConnector hupa = HupaConnector.create();

        Settings s = hupa.getSettings("mcm@escor.alcala.org");
        Assert.assertNotNull(s);

        Boolean b = hupa.doLogin("mcm@escor.alcala.org", "mcm",
                s.getImapServer(), "" + s.getImapPort(), s.getImapSecure(),
                s.getSmtpServer(), "" + s.getSmtpPort(), s.getSmtpSecure());
        Assert.assertTrue(b);

        System.out.println(hupa.fetchFolders());

    }

}
