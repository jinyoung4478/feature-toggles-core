package io.frjufvjn.featuretoggles.orchestration;

import io.frjufvjn.featuretoggles.FeatureInstanceNames;
import io.frjufvjn.featuretoggles.FeatureOption;
import io.frjufvjn.featuretoggles.router.ManagedToggleType;
import io.micrometer.common.util.StringUtils;

import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

public class InstanceNames {
    /**
     * 인스턴스를 가져오기 위한 key를 규칙에 따라 반환
     */
    protected static <T> String getInstanceName(T instance) {
        FeatureOption featureOption = Optional.ofNullable(instance.getClass().getAnnotation(FeatureOption.class))
                .orElse(ProxyBeanExtractor.getFeatureOptionIfProxyBean(instance));
        if (featureOption != null) {
            // @FeatureOption 애노테이션의 name이 정의되어 있으면 name으로 정의
            String featureName = StringUtils.isBlank(featureOption.name()) ? instance.getClass().getSimpleName() : featureOption.name();
            // @FeatureOption 애노테이션의 defaultFeature가 true이면 'default'로 정의
            if (featureOption.defaultFeature()) {
                featureName = ManagedToggleType.DEFAULT.getCode();
            }
            return featureName;
        }
        return instance.getClass().getSimpleName();
    }

    protected static <T> void applyFeatureInstanceNames(String feature, Map<String, T> instances) {
        FeatureInstanceNames.put(feature, getFeatureNamesInInstances(instances));
    }

    private static <T> Set<String> getFeatureNamesInInstances(Map<String, T> instances) {
        return instances.keySet().stream()
                .collect(Collectors.toUnmodifiableSet());
    }
}
