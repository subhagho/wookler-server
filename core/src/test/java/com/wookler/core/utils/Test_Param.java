package com.wookler.core.utils;

import static org.junit.Assert.*;

import java.io.InputStream;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathFactory;

import org.junit.Test;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import com.wookler.utils.InstanceParam;
import com.wookler.utils.XMLUtils;

public class Test_Param {

	@Test
	public void test() {
		try {
			String resource = "/param-test-config.xml";
			InputStream is = Test_Param.class.getResourceAsStream(resource);
			if (is == null)
				throw new Exception("Failed to load resource [" + resource
						+ "]");
			DocumentBuilderFactory df = DocumentBuilderFactory.newInstance();
			DocumentBuilder db = df.newDocumentBuilder();

			Document doc = db.parse(is);

			XPath xpath = XPathFactory.newInstance().newXPath();
			// XPath Query for showing all nodes value
			XPathExpression expr = xpath.compile("/test/data/params/instance");

			NodeList nodes = (NodeList) expr.evaluate(doc,
					XPathConstants.NODESET);
			if (nodes != null && nodes.getLength() > 0) {
				for (int ii = 0; ii < nodes.getLength(); ii++) {
					Element elm = (Element) nodes.item(ii);
					String vk = elm.getAttribute(XMLUtils._PARAM_ATTR_NAME_);
					String cn = elm
							.getAttribute(InstanceParam._PARAM_ATTR_CLASS_);
					InstanceParam ip = new InstanceParam(vk, cn);
					ip.parse(elm);
					System.out.println(ip.toString() + "\n");
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			fail(e.getLocalizedMessage());
		}
	}

}
