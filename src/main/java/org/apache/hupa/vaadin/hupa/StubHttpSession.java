package org.apache.hupa.vaadin.hupa;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionContext;

import com.google.inject.Inject;

@SuppressWarnings("deprecation")
public class StubHttpSession implements HttpSession{
    private Map<String,Object> attributeMap = new HashMap<String, Object>();
    private Map<String,Object> valueMap = new HashMap<String, Object>();
    private long cTime;
    private String id;
    private static int seq = 0;
    
    @Inject
    public StubHttpSession() {
        cTime = System.currentTimeMillis();
        this.id = "MockID" + "-" + seq++;
    }
    
    public Object getAttribute(String name) {
        return attributeMap.get(name);
    }

    @SuppressWarnings({ "rawtypes", "unchecked" })
	public Enumeration getAttributeNames() {
        return new Enumeration() {
            Iterator it = attributeMap.keySet().iterator();
            public boolean hasMoreElements() {
                return it.hasNext();
            }

            public Object nextElement() {
                return it.next();
            }
            
        };
    }

    public long getCreationTime() {
        return cTime;
    }

    public String getId() {
        return id;
    }

    public long getLastAccessedTime() {
        return 0;
    }

    public int getMaxInactiveInterval() {
        return 0;
    }

    public ServletContext getServletContext() {
        return null;
    }

    public HttpSessionContext getSessionContext() {
        return null;
    }

    public Object getValue(String name) {
        return valueMap.get(name);
    }

    public String[] getValueNames() {
        List<String> names = new ArrayList<String>();
        Iterator<String> it = valueMap.keySet().iterator();
        while (it.hasNext()) {
            names.add(it.next());
        }
        return names.toArray(new String[names.size()]);
    }

    public void invalidate() {
    }

    public boolean isNew() {
        return false;
    }

    public void putValue(String name, Object value) {
        valueMap.put(name, value);
    }

    public void removeAttribute(String name) {
        attributeMap.remove(name);
    }

    public void removeValue(String name) {
        valueMap.remove(name);
    
    }

    public void setAttribute(String name, Object value) {
        attributeMap.put(name, value);
    }

    public void setMaxInactiveInterval(int interval) {
    }

}
