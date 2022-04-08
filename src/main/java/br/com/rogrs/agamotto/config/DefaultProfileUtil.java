package br.com.rogrs.agamotto.config;

import java.util.HashMap;
import java.util.Map;

import org.springframework.boot.SpringApplication;
import org.springframework.core.env.Environment;

public final class DefaultProfileUtil {

    private static final String SPRING_PROFILE_DEFAULT = "spring.profiles.default";

    private DefaultProfileUtil() {
    }

    public static void addDefaultProfile(SpringApplication app) {
        Map<String, Object> defProperties = new HashMap<>();
       
       // defProperties.put(SPRING_PROFILE_DEFAULT, JHipsterConstants.SPRING_PROFILE_DEVELOPMENT);
        app.setDefaultProperties(defProperties);
    }

    public static String[] getActiveProfiles(Environment env) {
        String[] profiles = env.getActiveProfiles();
        if (profiles.length == 0) {
            return env.getDefaultProfiles();
        }
        return profiles;
    }
}