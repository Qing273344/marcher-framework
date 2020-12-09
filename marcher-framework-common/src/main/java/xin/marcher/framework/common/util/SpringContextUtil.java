package xin.marcher.framework.common.util;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

/**
 * Spring Context 工具类
 * 
 * @author marcher
 */
@Component
public class SpringContextUtil implements ApplicationContextAware {

	private static ApplicationContext applicationContext;

	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		if (SpringContextUtil.applicationContext == null) {
			SpringContextUtil.applicationContext = applicationContext;
		}
	}

	public static Object getBean(String name) {
		return applicationContext.getBean(name);
	}

	public static <T> T getBean(Class<T> clazz) {
		return applicationContext.getBean(clazz);
	}

	/**
	 * 通过name和class返回指定的Bean
	 * @param name	name
	 * @param clazz	class
	 * @param <T>	T
	 * @return
	 * 		指定的Bean
	 */
	public static <T> T getBean(String name, Class<T> clazz) {
		return applicationContext.getBean(name, clazz);
	}

	public static boolean containsBean(String name) {
		return applicationContext.containsBean(name);
	}

	public static boolean isSingleton(String name) {
		return applicationContext.isSingleton(name);
	}

	public static Class<? extends Object> getType(String name) {
		return applicationContext.getType(name);
	}


	public static String getAppName() {
		return applicationContext.getEnvironment().getProperty("spring.application.name");
	}

	public static boolean isDevProfile() {
		String[] activeProfiles = applicationContext.getEnvironment().getActiveProfiles();
		if (activeProfiles.length > 0) {
			for (String profile : activeProfiles) {
				if (EnvironmentEnum.DEV.getProfile().equals(profile)) {
					return true;
				}
			}
		}

		return false;
	}

	public static boolean isLocalProfile() {
		String[] activeProfiles = applicationContext.getEnvironment().getActiveProfiles();
		if (activeProfiles.length > 0) {
			for (String profile : activeProfiles) {
				if (EnvironmentEnum.LOCAL.getProfile().equals(profile)) {
					return true;
				}
			}
		}

		return false;
	}

	public static boolean isTestProfile() {
		String[] activeProfiles = applicationContext.getEnvironment().getActiveProfiles();
		if (activeProfiles.length > 0) {
			for (String profile : activeProfiles) {
				if (EnvironmentEnum.TEST.getProfile().equals(profile)) {
					return true;
				}
			}
		}

		return false;
	}

	public static boolean isProdProfile() {
		String[] activeProfiles = applicationContext.getEnvironment().getActiveProfiles();
		if (activeProfiles.length > 0) {
			for (String profile : activeProfiles) {
				if (EnvironmentEnum.PROD.getProfile().equals(profile)) {
					return true;
				}
			}
		}

		return false;
	}

	public static String getProfile() {
		String[] activeProfiles = applicationContext.getEnvironment().getActiveProfiles();
		if (activeProfiles.length > 0) {
			for (String profile : activeProfiles) {
				if (EnvironmentEnum.PROD.getProfile().equals(profile)) {
					return EnvironmentEnum.PROD.getProfile();
				}
				if (EnvironmentEnum.TEST.getProfile().equals(profile)) {
					return EnvironmentEnum.TEST.getProfile();
				}
				if (EnvironmentEnum.MOCK.getProfile().equals(profile)) {
					return EnvironmentEnum.MOCK.getProfile();
				}
			}
		}

		return "";
	}

	private static enum EnvironmentEnum {
		/** */
		DEV("dev"),
		LOCAL("local"),
		TEST("test"),
		PROD("prod"),
		MOCK("mock"),
		MOBILE_PRO("mobile-pro");

		private String profile;

		public String getProfile() {
			return this.profile;
		}

		public void setProfile(String profile) {
			this.profile = profile;
		}

		EnvironmentEnum(String profile) {
			this.profile = profile;
		}
	}
}