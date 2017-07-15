/**
 * Copyright (C) 2015 Asterios Raptis
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package de.alpharogroup.springconfig;

import java.util.List;

import org.springframework.cache.CacheManager;
import org.springframework.cache.ehcache.EhCacheCacheManager;
import org.springframework.cache.ehcache.EhCacheManagerFactoryBean;
import org.springframework.cache.interceptor.CacheErrorHandler;
import org.springframework.cache.interceptor.CacheResolver;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.cache.interceptor.SimpleCacheErrorHandler;
import org.springframework.cache.interceptor.SimpleCacheResolver;
import org.springframework.cache.interceptor.SimpleKeyGenerator;
import org.springframework.cache.support.CompositeCacheManager;
import org.springframework.core.io.ClassPathResource;

import de.alpharogroup.collections.ListExtensions;
import lombok.experimental.UtilityClass;

/**
 * A factory class for creating cache configuration objects.
 */
@UtilityClass
public class SpringCacheConfigurationFactory
{

	/**
	 * Factory method for create the new composite {@link CacheManager} from the given
	 * {@link CacheManager}'s.
	 *
	 * @param cacheCacheManager
	 *            the cache cache manager
	 * @return the new {@link CacheManager}
	 */
	public static CacheManager newCacheManager(final CacheManager... cacheCacheManager)
	{

		final List<CacheManager> cacheManagers = ListExtensions.newArrayList();
		for (final CacheManager cm : cacheCacheManager)
		{
			if (cm != null)
			{
				cacheManagers.add(cm);
			}
		}

		final CompositeCacheManager cacheManager = new CompositeCacheManager();

		cacheManager.setCacheManagers(cacheManagers);
		cacheManager.setFallbackToNoOpCache(false);

		return cacheManager;
	}

	/**
	 * Factory method for create the new {@link SimpleKeyGenerator} object.
	 *
	 * @return the new {@link SimpleKeyGenerator}
	 */
	public static KeyGenerator newSimpleKeyGenerator()
	{
		return new SimpleKeyGenerator();
	}

	/**
	 * Factory method for create the new {@link SimpleCacheResolver} object.
	 *
	 * @return the new {@link SimpleCacheResolver}
	 */
	public static CacheResolver newSimpleCacheResolver()
	{
		return new SimpleCacheResolver();
	}

	/**
	 * Factory method for create the new {@link SimpleCacheErrorHandler} object.
	 *
	 * @return the new {@link SimpleCacheErrorHandler}
	 */
	public static CacheErrorHandler newSimpleCacheErrorHandler()
	{
		return new SimpleCacheErrorHandler();
	}

	/**
	 * Factory method for create the new {@link EhCacheManagerFactoryBean} object from the given
	 * file name as {@link String} object.
	 *
	 * @param ehcacheXmlFilename
	 *            the xml filename
	 * @return the new {@link EhCacheManagerFactoryBean}
	 */
	public static EhCacheManagerFactoryBean newEhCacheManagerFactoryBean(
		final String ehcacheXmlFilename)
	{
		final EhCacheManagerFactoryBean cacheManagerFactoryBean = new EhCacheManagerFactoryBean();
		cacheManagerFactoryBean.setConfigLocation(new ClassPathResource(ehcacheXmlFilename));
		cacheManagerFactoryBean.setShared(true);
		return cacheManagerFactoryBean;
	}

	/**
	 * Factory method for create the new {@link EhCacheCacheManager} object from the given
	 * {@link EhCacheManagerFactoryBean} object.
	 *
	 * @param cacheManagerFactoryBean
	 *            the {@link EhCacheManagerFactoryBean} object
	 * @return the new {@link EhCacheCacheManager}
	 */
	public static EhCacheCacheManager newEhCacheCacheManager(
		final EhCacheManagerFactoryBean cacheManagerFactoryBean)
	{
		final EhCacheCacheManager cacheManger = new EhCacheCacheManager(
			cacheManagerFactoryBean.getObject());
		return cacheManger;
	}

}
