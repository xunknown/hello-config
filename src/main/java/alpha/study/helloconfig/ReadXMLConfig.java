package alpha.study.helloconfig;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.configuration2.XMLConfiguration;
import org.apache.commons.configuration2.builder.BuilderParameters;
import org.apache.commons.configuration2.builder.FileBasedConfigurationBuilder;
import org.apache.commons.configuration2.builder.fluent.Parameters;
import org.apache.commons.configuration2.ex.ConfigurationException;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ReadXMLConfig {
	private static final Logger LOGGER = LoggerFactory.getLogger(ReadXMLConfig.class);

	public static void readConfigFile(String fileName) {
		try {
	        BuilderParameters params = new Parameters().xml().setFileName(fileName);
	        FileBasedConfigurationBuilder<XMLConfiguration> builder =
	        		new FileBasedConfigurationBuilder<XMLConfiguration>(XMLConfiguration.class).configure(params);
	        XMLConfiguration config = builder.getConfiguration();
			Iterator<String> keyIterator = config.getKeys();

			while (keyIterator.hasNext()) {
				String key = keyIterator.next();
				LOGGER.info("keys: {}", key);
			}

			// 对于单独的元素，可以直接通过元素名称获取值
			String str = config.getString("boy");
			LOGGER.info("boy: {}", str);

			// 对于循环出现的嵌套元素，可以通过父元素.子元素来获取集合值
			List<Object> names = config.getList("student.name");
			LOGGER.info("student.name: {}", Arrays.toString(names.toArray()));

			// 对于单独的元素包含的值有多个的话如：a,b,c,d 可以通过获取集合
			List<Object> titles = config.getList("title");
			LOGGER.info("title: {}", Arrays.toString(titles.toArray()));

			// 对于标签元素的属性，可以通过 标签[@属性名]这样的方式来获取
			String size = config.getString("ball[@size]");
			LOGGER.info("ball[@size]: {}", size);

			// 对于嵌套标签的话，想获得某一项的话可以通过 标签名(索引名)这样的方式来获取
			String id = config.getString("student(1)[@id]");
			LOGGER.info("student(1)[@id]: {}", id);

			// 对于标签里面的属性名称可以这么取
			String go = config.getString("student(0).name[@go]");
			LOGGER.info("student(0).name[@go]: {}", go);

			// 对于标签里面的属性名称还可以这么取
			go = config.getString("student.name(0)[@go]");
			LOGGER.info("student.name(0)[@go]: {}", go);
		} catch (ConfigurationException e) {
			LOGGER.error(ExceptionUtils.getStackTrace(e));
		}
	}
}
