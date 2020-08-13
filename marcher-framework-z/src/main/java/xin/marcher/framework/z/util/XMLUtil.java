package xin.marcher.framework.z.util;

import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;

import javax.xml.transform.*;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import java.io.*;
import java.net.URL;
import java.util.Properties;

/**
 * @author: Jack
 * @Description: XML工具类
 * @Date: Create by 14:19 2017/11/30
 * @Modified By: marcher
 */
public final class XMLUtil {
	/**
	 * 把XML按照给定的XSL进行转换，返回转换后的值
	 * @param xml	xml
	 * @param xsl	xsl
	 * @return
	 * @throws Exception
	 */
	public static String xml2xsl(String xml, URL xsl) throws Exception {
		if (StringUtil.isEmpty(xml)) {
			throw new Exception("xml string is empty");
		}
		if (xsl == null) {
			throw new Exception("xsl string is empty");
		}

		StringWriter writer = new StringWriter();
		Source xmlSource = null;
		Source xslSource = null;
		Result result = null;

		try {
			xmlSource = new StreamSource(new StringReader(xml));
			xslSource = new StreamSource(xsl.openStream());
			result = new StreamResult(writer);

			TransformerFactory transFact = TransformerFactory.newInstance();
			Transformer trans = transFact.newTransformer(xslSource);
			trans.transform(xmlSource, result);
			return writer.toString();
		} catch (Exception ex) {
			throw new Exception(ex);
		} finally {
			writer.close();
			writer = null;
			xmlSource = null;
			xslSource = null;
			result = null;
		}
	}

	/**
	 * 把XML按用户定义好的XSL样式进行输出
	 * @param xmlFilePath	XML文档
	 * @param xsl	XSL样式
	 * @return
	 * 		样式化后的字段串
	 */
	public static String xml2xsl(String xmlFilePath, String xsl) throws Exception {
		if (StringUtil.isEmpty(xmlFilePath)) {
			throw new Exception("xml string is empty");
		}
		if (StringUtil.isEmpty(xsl)) {
			throw new Exception("xsl string is empty");
		}

		StringWriter writer = new StringWriter();
		Source xmlSource = new StreamSource(new File(xmlFilePath));
		Source xslSource = new StreamSource(new File(xsl));
		Result result = new StreamResult(writer);

		try {
			TransformerFactory transFact = TransformerFactory.newInstance();
			Transformer trans = transFact.newTransformer(xslSource);
			Properties properties = trans.getOutputProperties();
			properties.setProperty(OutputKeys.ENCODING, "UTF-8");
			properties.put(OutputKeys.METHOD, "html");
			trans.setOutputProperties(properties);

			trans.transform(xmlSource, result);
			return writer.toString();
		} finally {
			writer.close();
			writer = null;
			xmlSource = null;
			xslSource = null;
			result = null;
		}
	}

	/**
	 * 读取XML文档，返回Document对象.<br>
	 * @param xmlFile	XML文件路径
	 * @return
	 * 		Document 对象
	 */
	public static Document getDocument(String xmlFile) throws Exception {
		if (StringUtil.isEmpty(xmlFile)) {
			return null;
		}

		File file = null;
		SAXReader saxReader = new SAXReader();

		file = new File(xmlFile);
		return saxReader.read(file);
	}

	/**
	 * 读取XML文档，返回Document对象.<br>
	 * @param xmlFile	file对象
	 * @return
	 * 		Document 对象
	 */
	public static Document getDocument(File xmlFile) {
		try {
			SAXReader saxReader = new SAXReader();
			return saxReader.read(xmlFile);
		} catch (Exception ex) {
			ex.printStackTrace();
			System.err.println("读取xml文件出错，返回null");
			return null;
		}
	}

	/**
	 * 读取XML字串，返回Document对象
	 * @param xmlString	XML文件路径
	 * @return
	 * 		Document 对象
	 */
	public static Document getDocumentFromString(String xmlString) {
		if (StringUtil.isEmpty(xmlString)) {
			return null;
		}
		try {
			SAXReader saxReader = new SAXReader();
			return saxReader.read(new StringReader(xmlString));
		} catch (Exception ex) {
			ex.printStackTrace();
			System.err.println("读取xml文件出错，返回null");
			return null;
		}
	}

	/**
	 * 描述：把xml输出成为html
	 * @param xmlDoc	xmlDoc
	 * @param xslFile	xslFile
	 * @param encoding	编码
	 * @return
	 * @throws Exception
	 */
	public static String xml2html(String xmlDoc, String xslFile, String encoding) throws Exception {
		if (StringUtil.isEmpty(xmlDoc)) {
			throw new Exception("xml string is empty");
		}
		if (StringUtil.isEmpty(xslFile)) {
			throw new Exception("xslt file is empty");
		}

		StringWriter writer = new StringWriter();
		Source xmlSource = null;
		Source xslSource = null;
		Result result = null;
		String html = null;
		try {
			xmlSource = new StreamSource(new StringReader(xmlDoc));
			xslSource = new StreamSource(new File(xslFile));

			result = new StreamResult(writer);

			TransformerFactory transFact = TransformerFactory.newInstance();
			Transformer trans = transFact.newTransformer(xslSource);
			Properties properties = trans.getOutputProperties();
			properties.put(OutputKeys.METHOD, "html");
			properties.setProperty(OutputKeys.ENCODING, encoding);
			trans.setOutputProperties(properties);

			trans.transform(xmlSource, result);

			html = writer.toString();
			return html;
		} catch (Exception ex) {
			throw new Exception(ex);
		} finally {
			writer.close();
			writer = null;
			xmlSource = null;
			xslSource = null;
			result = null;
		}
	}

	/**
	 * 描述：把xml输出成为html
	 * @param xmlFile	xmlFile
	 * @param xslFile	xslFile
	 * @param encoding	编码
	 * @return
	 * @throws Exception
	 */
	public static String xmlFile2html(String xmlFile, String xslFile, String encoding) throws Exception {
		if (StringUtil.isEmpty(xmlFile)) {
			throw new Exception("xml string is empty");
		}
		if (StringUtil.isEmpty(xslFile)) {
			throw new Exception("xslt file is empty");
		}

		StringWriter writer = new StringWriter();
		Source xmlSource = null;
		Source xslSource = null;
		Result result = null;
		String html = null;
		try {
			xmlSource = new StreamSource(new File(xmlFile));
			xslSource = new StreamSource(new File(xslFile));

			result = new StreamResult(writer);

			TransformerFactory transFact = TransformerFactory.newInstance();
			Transformer trans = transFact.newTransformer(xslSource);
			Properties properties = trans.getOutputProperties();
			properties.put(OutputKeys.METHOD, "html");
			properties.setProperty(OutputKeys.ENCODING, encoding);
			trans.setOutputProperties(properties);

			trans.transform(xmlSource, result);

			html = writer.toString();
			writer.close();

			return html;
		} catch (Exception ex) {
			throw new Exception(ex);
		} finally {
			writer = null;
			xmlSource = null;
			xslSource = null;
			result = null;
		}
	}

	/**
	 * 描述：
	 * @param name	名
	 * @param element	元素
	 * @return
	 */
	public static String getString(String name, Element element) {
		return (element.valueOf(name) == null) ? "" : element.valueOf(name);
	}

	/**
	 * 将一个XML文档保存至文件中.
	 * @param doc			要保存的XML文档对象.
	 * @param filePathName	要保存到的文档路径.
	 * @param format		要保存的输出格式
	 * @return
	 * 		true代表保存成功，否则代表不成功.
	 */
	public static boolean savaToFile(Document doc, String filePathName, OutputFormat format) {
		XMLWriter writer;
		try {
			String filePath = FileUtil.getFullPath(filePathName);
			// 若目录不存在，则建立目录
			if (!FileUtil.exists(filePath)) {
				if (!FileUtil.createDirectory(filePath)) {
					return false;
				}
			}

			writer = new XMLWriter(new FileWriter(new File(filePathName)), format);
			writer.write(doc);
			writer.close();
			return true;
		} catch (IOException ex) {
			ex.printStackTrace();
			System.err.println("写文件出错");
		}

		return false;
	}

	/**
	 * 将一个XML文档保存至文件中.
	 * @param filePathName	要保存到的文档路径.
	 * @param doc			要保存的XML文档对象.
	 * @return
	 * 		true代表保存成功，否则代表不成功.
	 */
	public static boolean writeToXml(String filePathName, Document doc) {
		OutputFormat format = OutputFormat.createCompactFormat();
		format.setEncoding("UTF-8");
		return savaToFile(doc, filePathName, format);
	}
}