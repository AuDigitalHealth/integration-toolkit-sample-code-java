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
package au.gov.nehta.vendorlibrary.mdm.util;

import au.gov.nehta.common.utils.ArgumentUtils;
import au.gov.nehta.vendorlibrary.mdm.core.Message;

import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.Writer;

/**
 * {@link MDMUtil}
 * Utility class for MDM messages.
 */
public final class MDMUtil {

  /**
   * Private constructor.
   */
  private MDMUtil() {
    // To prevent calling the constructor.
  }

  /**
   * Helper utility method to write an MDM message to file for test purposes.
   *
   * @param message Populated MDM message object.
   * @param path    Output file path.
   * @throws IOException Thrown if writing fails.
   */
  public static void writeMDMMessageToFile(Message message, String path) throws IOException {

    ArgumentUtils.checkNotNull(message, "message");
    ArgumentUtils.checkNotNull(path, "path");

    Writer out = new BufferedWriter(new FileWriter(path));
    out.write(message.toString());
    out.close();
  }

  /**
   * Helper utility method to read an MDM message from file for test purposes.
   *
   * @param path Path to MDM message file.
   * @return Populated {@link Message}/null.
   * @throws IOException Thrown if reading of the file fails.
   */
  public static Message readMDMMessageFromFile(String path) throws IOException {
    ByteArrayOutputStream baos = new ByteArrayOutputStream();
    InputStream is = new FileInputStream(path);
    byte[] buffer = new byte[MDMConstants.BUFFER];

    int count = is.read(buffer);
    while (count != -1) {
      baos.write(buffer, 0, count);
      count = is.read(buffer);
    }

    String fileContent = new String(baos.toByteArray());

    if (!fileContent.isEmpty()) {
      return Message.parse(fileContent);
    } else {
      return null;
    }
  }
}

