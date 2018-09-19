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
package au.gov.nehta.vendorlibrary.mdm.sample;

import au.gov.nehta.vendorlibrary.mdm.core.Message;
import au.gov.nehta.vendorlibrary.mdm.util.MDMValidationException;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.text.ParseException;

/**
 * {@link MDMExtractSample}
 * Sample code for populating and generating an MDM message.
 */
public class MDMExtractSample {

  public static void main(String[] args) throws MDMValidationException, IOException, ParseException {

    Message message1 = Message.parse(new String(readFile("C:/tmp/41b592ec-bc5f-4a4c-8073-f3e3cbdfbd23.hl7")));

    System.out.println();
  }

  /**
   * Helper read file method.
   *
   * @param path Location of the file.
   * @return byte array.
   * @throws IOException           Unable to read file.
   * @throws FileNotFoundException Unable to located file.
   */
  private static byte[] readFile(final String path) throws IOException, FileNotFoundException {

    ByteArrayOutputStream baos = new ByteArrayOutputStream();
    InputStream is = new FileInputStream(path);
    byte[] buffer = new byte[2048];

    int count = 0;

    count = is.read(buffer);
    while (count != -1) {
      baos.write(buffer, 0, count);
      count = is.read(buffer);
    }
    return baos.toByteArray();
  }
}
