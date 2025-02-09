package io.frjufvjn.featuretoggles.orchestration;

import io.frjufvjn.featuretoggles.FeatureOption;
import org.springframework.aop.framework.AopProxyUtils;

public class ProxyBeanExtractor {
    public static FeatureOption getFeatureOptionIfProxyBean(Object proxy) {
        return AopProxyUtils.ultimateTargetClass(proxy).getAnnotation(FeatureOption.class);
    }
}
