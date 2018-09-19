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

import au.gov.nehta.vendorlibrary.pcehr.clients.common.constant.XMLNamespaces;

import javax.xml.XMLConstants;
import javax.xml.namespace.NamespaceContext;
import java.util.Iterator;

/**
 * {@link au.gov.nehta.vendorlibrary.pcehr.clients.common.util.MetadataNamespaceContext}
 * {@link javax.xml.namespace.NamespaceContext} implementation for packaging namespaces.
 */
public final class MetadataNamespaceContext implements NamespaceContext {

  /**
   * Retrieve a namespace URI by its prefix.
   * @param prefix Namespace prefix.
   * @return Namespace URI.
   */
  public String getNamespaceURI(final String prefix) {

    XMLNamespaces result = XMLNamespaces.findByPrefix(prefix);
    if (result == null) {
      return XMLConstants.NULL_NS_URI;
    } else {
      return result.getNamespace();
    }
  }

  /**
   * Retrieve a namespace prefix by its namespace URI.
   * @param namespace Namespace URI.
   * @return Namespace prefix.
   */
  public String getPrefix(final String namespace) {
    return XMLNamespaces.findByNamespace(namespace).getPrefix();
  }

  /**
   * Not implemented.
   * @param namespaceUri Namespace URI.
   * @return Iterator.
   */
  public Iterator getPrefixes(final String namespaceUri) {
    throw new UnsupportedOperationException();
  }
}

