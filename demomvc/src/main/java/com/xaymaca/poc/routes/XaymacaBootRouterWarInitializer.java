package com.xaymaca.poc.routes;

import org.apache.camel.spring.boot.FatJarRouter;
import org.apache.camel.spring.boot.FatWarInitializer;

/**
 * Created by Vincent Stoessel on August 01, 2016.
 */
public class XaymacaBootRouterWarInitializer extends FatWarInitializer {
    @Override
    protected Class<? extends FatJarRouter> routerClass() {
        return XaymacaBootServices.class;
    }
}
