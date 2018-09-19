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

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import java.io.OutputStream;

/**
 * JAXB helper utility methods.
 */
public final class JAXBUtils {

  /**
   * Private constructor to prevent class instantiation.
   */
  private JAXBUtils() {
  }

  /**
   * Marshal JAXB element.
   *
   * @param obj element to marshal.
   * @param os  output of marshaled element.
   * @throws JAXBException thrown in the event that the object cannot be marshaled.
   */
  public static void marshal(JAXBElement<?> obj, OutputStream os) throws JAXBException {
    JAXBContext context = JAXBContext.newInstance(obj.getValue().getClass());
    Marshaller marshaller = context.createMarshaller();
    marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
    marshaller.marshal(obj, os);
  }

  /**
   * Marshal object.
   *
   * @param obj object to marshal.
   * @param os  output of marshaled element.
   * @throws JAXBException thrown in the event that the object cannot be marshaled.
   */
  public static void marshal(Object obj, OutputStream os) throws JAXBException {
    JAXBContext context = JAXBContext.newInstance(obj.getClass());
    Marshaller marshaller = context.createMarshaller();
    marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
    marshaller.marshal(obj, os);
  }
}
