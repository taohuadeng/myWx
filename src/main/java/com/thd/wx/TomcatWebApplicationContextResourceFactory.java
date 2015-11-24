package com.thd.wx;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import javax.naming.Context;
import javax.naming.Name;
import javax.naming.RefAddr;
import javax.naming.Reference;
import javax.naming.spi.ObjectFactory;
import java.util.Hashtable;

public class TomcatWebApplicationContextResourceFactory implements
        ObjectFactory {
    private static final String PARAM = "contextConfigLocation";
    private static final String DEFAULT = "application-context.xml";
    private static ApplicationContext context; //被共享的spring上下文

    private void init(String confFile) {
        context = new ClassPathXmlApplicationContext(confFile);
    }

    public Object getObjectInstance(Object obj,
                                    Name name, Context nameCtx,
                                    Hashtable<?, ?> environment) throws Exception {
        if (null == context) {
            // Customize the bean properties from our attributes
            Reference ref = (Reference) obj;
            //从xml配置文件里取得contextConfigLocation元素的值
            RefAddr addr = ref.get(PARAM);
            if (null != addr) {
                String value = (String) addr.getContent();
                init(value);
            } else {
                init(DEFAULT);
            }
        }
        return context;
    }
}
