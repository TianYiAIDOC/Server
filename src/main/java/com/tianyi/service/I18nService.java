package com.tianyi.service;

import com.alibaba.fastjson.JSON;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Service;

import java.util.Locale;

@Service("i18nService")
public class I18nService {

	private final Log logger = LogFactory.getLog(this.getClass());

	private static final String MSGPREFIX = "res.message.";

	private static final String TEMPLATEPREFIX = "res.template.";

	@Autowired
    MessageSource messageSource;

	/**
	 * 无参数消息，使用默认语言资源文件
	 * 
	 * @param code
	 * @return
	 */
	public String getMessage(String code) {
		return this.getMessage(code, new Object[] {});
	}

	/**
	 * 无参数消息，使用locale確定資源文件，返回code對應的message
	 * 
	 * @param code
	 * @param locale
	 * @return
	 */
	public String getMessage(String code, Locale locale) {
		return this.getMessage(code, new Object[] {}, locale);
	}

	public String getMessage(String code, String lang) {
		if(StringUtils.isEmpty(lang)){
			lang = "en";
		}

		Locale locale = Locale.forLanguageTag(lang);
		logger.debug(locale.getCountry() + "_" + locale.getLanguage());
		return this.getMessage(code, new Object[] {}, locale);
	}

	/**
	 * 参数消息，使用默认语言资源文件
	 * 
	 * @param code：对应messages配置的key.
	 * @param args:
	 *            数组参数.
	 * @return
	 */
	public String getMessage(String code, Object[] args) {
		Locale locale = LocaleContextHolder.getLocale();
		return this.getMessage(code, args, locale);
	}

	/**
	 * 参数消息，使用lang指定的语言资源文件
	 * 
	 * @param code
	 * @param args
	 * @param lang
	 * @return
	 */
	public String getMessage(String code, Object[] args, String lang) {
		Locale locale = Locale.forLanguageTag(lang);
		return this.getMessage(code, args, locale);
	}

	/**
	 * 参数消息，使用locale语言资源文件
	 * 
	 * @param code
	 * @param args
	 * @param locale
	 * @return
	 */
	public String getMessage(String code, Object[] args, Locale locale) {

		System.out.println("-----------"+JSON.toJSONString(messageSource));
		return messageSource.getMessage(MSGPREFIX + code, args, locale);
	}

	/**
	 * 无参数消息，使用默认语言资源文件
	 * 
	 * @param code
	 * @return
	 */
	public String getTemplate(String code) {
		return this.getTemplate(code, new Object[] {});
	}

	/**
	 * 无参数消息，使用locale確定資源文件，返回code對應的message
	 * 
	 * @param code
	 * @param locale
	 * @return
	 */
	public String getTemplate(String code, Locale locale) {
		return this.getTemplate(code, new Object[] {}, locale);
	}

	public String getTemplate(String code, String lang) {
		Locale locale = Locale.forLanguageTag(lang);
		logger.debug(locale.getCountry() + "_" + locale.getLanguage());
		return this.getTemplate(code, new Object[] {}, locale);
	}

	/**
	 * 参数消息，使用默认语言资源文件
	 * 
	 * @param code：对应messages配置的key.
	 * @param args:
	 *            数组参数.
	 * @return
	 */
	public String getTemplate(String code, Object[] args) {
		Locale locale = LocaleContextHolder.getLocale();
		return this.getTemplate(code, args, locale);
	}

	/**
	 * 参数消息，使用lang指定的语言资源文件
	 * 
	 * @param code
	 * @param args
	 * @param lang
	 * @return
	 */
	public String getTemplate(String code, Object[] args, String lang) {
		Locale locale = Locale.forLanguageTag(lang);
		return this.getTemplate(code, args, locale);
	}

	/**
	 * 参数消息，使用locale语言资源文件
	 * 
	 * @param code
	 * @param args
	 * @param locale
	 * @return
	 */
	public String getTemplate(String code, Object[] args, Locale locale) {
		return messageSource.getMessage(TEMPLATEPREFIX + code, args, locale);
	}

}
