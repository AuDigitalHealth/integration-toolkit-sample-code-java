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

package au.gov.nehta.vendorlibrary.pcehr.test.unittests;

import au.gov.nehta.vendorlibrary.pcehr.clients.common.util.OIDUtil;
import org.junit.Assert;
import org.junit.Test;

/** Unit tests for the OIDUtil Utility class. */
public class OIDUtilTest {

  /** Tests UUID with uuid:urn prefix. */
  @Test
  public final void testConvertUUIDtoOIDIntegerPair1() {

    String pair = OIDUtil.convertUUIDToOIDIntegerPair("2.25",
      "urn:uuid:a7b7c3b7-4639-43a9-8bb1-7cb8c91216c1");
    Assert.assertEquals("2.25.222935235211552455402395562399683974849", pair);
  }

  /** Tests UUID with hyphens. */
  @Test
  public final void testConvertUUIDtoOIDIntegerPair2() {

    String pair = OIDUtil.convertUUIDToOIDIntegerPair("2.25",
      "a7b7c3b7-4639-43a9-8bb1-7cb8c91216c1");
    Assert.assertEquals("2.25.222935235211552455402395562399683974849", pair);
  }

  /** Tests UUID without hyphens. */
  @Test (expected = IllegalArgumentException.class)
  public final void testConvertUUIDtoOIDIntegerPair3() {

    String pair = OIDUtil.convertUUIDToOIDIntegerPair("2.25",
      "a7b7c3b7463943a98bb17cb8c91216c1");
    Assert.assertEquals("2.25.222935235211552455402395562399683974849", pair);
  }

  /** Tests if method fails when passed empty oid. */
  @Test(expected = IllegalArgumentException.class)
  public final void testConvertUUIDtoOIDIntegerPairWithNoOid1() {

    OIDUtil.convertUUIDToOIDIntegerPair("", "a7b7c3b7463943a98bb17cb8c91216c1");
  }

  /** Tests if method fails when passed a null oid. */
  @Test(expected = IllegalArgumentException.class)
  public final void testConvertUUIDtoOIDIntegerPairWithNoOid2() {

    OIDUtil.convertUUIDToOIDIntegerPair(null,
      "a7b7c3b7463943a98bb17cb8c91216c1");
  }

  /** Tests if method fails when passed an invalid uuid. */
  @Test(expected = IllegalArgumentException.class)
  public final void testConvertUUIDtoOIDIntegerPairWithInvalidUUID() {

    OIDUtil.convertUUIDToOIDIntegerPair("2.25",
      "xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx");
  }

  /** Tests if method fails when passed a null uuid. */
  @Test(expected = IllegalArgumentException.class)
  public final void testConvertUUIDtoOIDIntegerPairWithNullUUID() {

    OIDUtil.convertUUIDToOIDIntegerPair("2.25", null);
  }

}
