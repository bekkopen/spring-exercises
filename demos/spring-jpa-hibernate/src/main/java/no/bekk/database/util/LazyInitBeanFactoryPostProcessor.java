package no.bekk.database.util;

import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.config.MethodInvokingFactoryBean;

import com.google.common.collect.Sets;

public class LazyInitBeanFactoryPostProcessor implements BeanFactoryPostProcessor {

	private static final Logger LOG = LoggerFactory.getLogger(LazyInitBeanFactoryPostProcessor.class);

	private static final Set<String> EAGER_BEAN_NAMES = Sets.newHashSet(MethodInvokingFactoryBean.class.getSimpleName());

	@Override
	public void postProcessBeanFactory(final ConfigurableListableBeanFactory beanFactory) {
		final Set<String> sortedbeans = Sets.newTreeSet();
		for (String beanName : beanFactory.getBeanDefinitionNames()) {
			final BeanDefinition beanDefinition = beanFactory.getBeanDefinition(beanName);
			if (!isEagerBean(beanName)) {
				beanDefinition.setLazyInit(true);
			}
			sortedbeans.add(beanDefinition.toString());
		}

		if (LOG.isDebugEnabled()) {
			for (String string : sortedbeans) {
				LOG.debug(string);
			}
		}
	}

	private boolean isEagerBean(final String beanName) {
		for (String eagerBeanName : EAGER_BEAN_NAMES) {
			if (StringUtils.containsIgnoreCase(beanName, eagerBeanName)) {
				return true;
			}
		}
		return false;
	}

}
