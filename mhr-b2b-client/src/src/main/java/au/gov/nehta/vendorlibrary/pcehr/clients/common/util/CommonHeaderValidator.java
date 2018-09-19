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

import au.net.electronichealth.ns.pcehr.xsd.common.commoncoreelements._1.PCEHRHeader;
import org.apache.commons.lang.Validate;

import java.math.BigInteger;

/**
 * Utility class used to validate the common header of a PCEHR client request.
 */
public final class CommonHeaderValidator {

  private static final int EXPECTED_IHI_LENGTH = 16;

  /**
   * Private constructor.
   */
  private CommonHeaderValidator() {
  }

  /**
   * Validate the contents of the {@link PCEHRHeader}.
   *
   * @param commonHeader     A {@link PCEHRHeader}.
   * @param requireIHINumber Is the IHINumber required?
   */
  public static void validate(PCEHRHeader commonHeader, boolean requireIHINumber) {

    Validate.notNull(commonHeader, "'commonHeader' cannot be null.");

    if (requireIHINumber) {
      Validate.notEmpty(commonHeader.getIhiNumber(), "'commonHeader.IHINumber' cannot be null nor empty.");
      validateIhi(commonHeader.getIhiNumber());
    }

    Validate.notNull(commonHeader.getUser(), "'commonHeader.user' cannot be null.");
    Validate.notNull(commonHeader.getUser().getIDType(), "'commonHeader.user.idType' cannot be null.");
    Validate.notEmpty(commonHeader.getUser().getID(), "'commonHeader.user.id' cannot be null nor empty.");
    Validate.notEmpty(commonHeader.getUser().getUserName(), "'commonHeader.user.userName' cannot be null nor empty.");
    if (commonHeader.getUser().isUseRoleForAudit()) {
      Validate.notEmpty(
        commonHeader.getUser().getRole(),
        "'commonHeader.user.role' must be specified as 'commonHeader.user.userRoleForAudit' is true."
      );
    }

    Validate.notNull(commonHeader.getProductType(), "'commonHeader.productType' cannot be null.");
    Validate.notEmpty(commonHeader.getProductType().getVendor(), "'commonHeader.productType.vendor' cannot be null nor empty.");
    Validate.notEmpty(commonHeader.getProductType().getProductName(), "'commonHeader.productType.productName' cannot be null nor empty.");
    Validate.notEmpty(commonHeader.getProductType().getProductVersion(), "'commonHeader.productType.productVersion' cannot be null nor empty.");
    Validate.notEmpty(commonHeader.getProductType().getPlatform(), "'commonHeader.productType.platform' cannot be null nor empty.");

    Validate.notNull(commonHeader.getClientSystemType(), "'commonHeader.clientSystemType' cannot be null.");

    if (commonHeader.getAccessingOrganisation() != null) {
      Validate.notEmpty(
        commonHeader.getAccessingOrganisation().getOrganisationID(),
        "'commonHeader.accessingOrganisation.organisationId' cannot be null nor empty."
      );
      Validate.notEmpty(
        commonHeader.getAccessingOrganisation().getOrganisationName(),
        "'commonHeader.accessingOrganisation.organisationName' cannot be null nor empty."
      );
    }
  }

  private static void validateIhi(final String ihi) {
    Validate.notEmpty(ihi, "'ihi' must not be null nor empty.");

    // check length.
    if (ihi.length() != EXPECTED_IHI_LENGTH) {
      throw new IllegalArgumentException("'ihi' length must be 16 digits.");
    }

    // check digits.
    try {
      new BigInteger(ihi);
    } catch (NumberFormatException e) {
      throw new IllegalArgumentException("'ihi' must only contain digits.", e);
    }

    // check format.
    if (!ihi.startsWith("800360")) {
      throw new IllegalArgumentException("'ihi' must be of the format 800360XXXXXXXXXX.");
    }
  }
}
