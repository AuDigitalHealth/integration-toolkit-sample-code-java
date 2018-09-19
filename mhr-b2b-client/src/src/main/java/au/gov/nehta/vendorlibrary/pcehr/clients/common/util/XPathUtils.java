/*
 * Copyright 2012 NEHTA
 *
 * Licensed under the NEHTA Open Source (Apache) License; you may not use this
 * file except in compliance with the License. A copy of the License is in the
 * 'license.txt' file, which should be provided with this work.
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations
 * under the License.
 */
package au.gov.nehta.vendorlibrary.pcehr.clients.common.util;

import org.w3c.dom.Document;

import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

/**
 * XPath utilities.
 */
public final class XPathUtils {

  /**
   * Private constructor to prevent class instantiation.
   */
  private XPathUtils() {
  }

  /**
   * Evaluate an XPath expression.
   *
   * @param expression Expression to validate.
   * @param doc        DOM to evaluate against.
   * @return Result of evaluation.
   * @throws XPathExpressionException Thrown in the event of a failure to validate an XPath expression.
   */
  public static String evaluateXPath(String expression, Document doc) throws XPathExpressionException {

    XPath xPath = XPathFactory.newInstance().newXPath();
    xPath.setNamespaceContext(new MetadataNamespaceContext());

    XPathExpression xPathExpression = xPath.compile(expression);
 
    return xPathExpression.evaluate(doc);
  }
}
