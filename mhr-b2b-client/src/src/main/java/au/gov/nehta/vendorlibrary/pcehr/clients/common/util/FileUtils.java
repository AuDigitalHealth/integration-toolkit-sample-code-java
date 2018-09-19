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


import org.apache.commons.io.IOUtils;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

/**
 * File handling utilities.
 */
public final class FileUtils {

  private static final int BUFFER_SIZE = 16384;

  /**
   * Private constructor to prevent class instantiation.
   */
  private FileUtils() {
  }

  /**
   * Load the contents of a file.
   *
   * @param file {@link File} object to load.
   * @return The contents of the file.
   * @throws java.io.IOException thrown in the event the file cannot be read in to memory.
   */
  public static byte[] loadFile(File file) throws IOException {
    if (!file.exists()) {
      throw new IllegalArgumentException("The file [" + file + "] does not exist.");
    }

    FileInputStream is = new FileInputStream(file);

    try {

      ByteArrayOutputStream buffer = new ByteArrayOutputStream();
      int nRead;
      byte[] data = new byte[BUFFER_SIZE];

      while ((nRead = is.read(data, 0, data.length)) != -1) {
        buffer.write(data, 0, nRead);
      }

      buffer.flush();
      return buffer.toByteArray();
    } finally {
      is.close();
    }
  }

  /**
   * Load an XML file into a Java object.
   *
   * @param file  {@link File} object to load.
   * @param clazz class to attempt to marshal to.
   * @return The Java object unmarshalled from the contents of the XML file.
   * @throws javax.xml.bind.JAXBException thrown in the event the file contents cannot be unmarshalled.
   */
  @SuppressWarnings("unchecked")
  public static <T> T loadXml(File file, Class<? extends T> clazz) throws JAXBException {
    if (!file.exists()) {
      throw new IllegalArgumentException("The file [" + file + "] does not exist.");
    }

    JAXBContext context = JAXBContext.newInstance(clazz);
    Unmarshaller unmarshaller = context.createUnmarshaller();
    return (T) unmarshaller.unmarshal(file);
  }

  /**
   * Retrieve root CDA document file content from a CDA package.
   *
   * @param packageContent byte array representation of the CDA package.
   * @return byte[].
   */
  public static byte[] getCdaDocument(final byte[] packageContent) {

    Map<String, byte[]> files;
    try {
      files = extractZipEntries(packageContent);
    } catch (IOException e) {
      throw new IllegalArgumentException("Failed to read package content.", e);
    }

    for (Map.Entry<String, byte[]> file : files.entrySet()) {
      String filePath = file.getKey();
      Pattern pattern = Pattern.compile("[/\\\\]?[^/\\\\]+[/\\\\][^/\\\\]+[/\\\\]CDA_ROOT.XML", Pattern.CASE_INSENSITIVE);

      if (pattern.matcher(filePath).matches()) {
        return file.getValue();
      }
    }

    throw new IllegalArgumentException("Failed to retrieve root document from package.");
  }

  /**
   * Reads {@link java.util.zip.ZipEntry}  from the zip file's input stream.
   *
   * @param zis {@link java.io.InputStream}
   * @return File content as a byte array.
   * @throws IOException Thrown if ZIP I/O operations fail.
   */
  private static byte[] readZipEntry(final InputStream zis) throws IOException {

    ByteArrayOutputStream baos = new ByteArrayOutputStream();
    byte[] buffer = new byte[BUFFER_SIZE];
    int count;

    count = zis.read(buffer);
    while (count != -1) {
      baos.write(buffer, 0, count);
      count = zis.read(buffer);
    }
    return baos.toByteArray();
  }

  /**
   * Read ZIP entries and file content into memory.
   *
   * @param packageContent byte array containing package content.
   * @return {@link java.util.Map} containing list of matching file names and file content byte arrays, or null.
   * @throws IOException Thrown if ZIP I/O operations fail.
   */
  public static Map<String, byte[]> extractZipEntries(final byte[] packageContent) throws IOException {

    Map<String, byte[]> result = new HashMap<String, byte[]>();
    ZipInputStream zipInputStream = new ZipInputStream(new ByteArrayInputStream(packageContent));
    ZipEntry zipEntry = null;

    try {
      while ((zipEntry = zipInputStream.getNextEntry()) != null) {
        // Skip directories and only read files
        if (!zipEntry.isDirectory()) {
          result.put(
            zipEntry.getName(),
            readZipEntry(zipInputStream)
          );
        }
      }
    } catch (IOException e) {
      throw new IOException("Unable to extract ZIP entries.", e);
    } finally {
      IOUtils.closeQuietly(zipInputStream);
    }

    return result;
  }
}
