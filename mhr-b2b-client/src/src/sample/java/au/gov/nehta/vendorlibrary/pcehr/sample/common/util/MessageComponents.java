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
package au.gov.nehta.vendorlibrary.pcehr.sample.common.util;

import au.net.electronichealth.ns.pcehr.xsd.common.commoncoreelements._1.PCEHRHeader;
import au.net.electronichealth.ns.pcehr.xsd.interfaces.pcehrprofile._1.GainPCEHRAccess;

/**
 * Helper class to create common components of a SOAP message.
 */
public final class MessageComponents {

  private static final String DEFAULT_DATE_FORMAT = "dd/MM/yyyy";

  /**
   * Private constructor to prevent instantiation.
   */
  private MessageComponents() {
  }

  /**
   * Create PCEHR record with no individual records.
   *
   * @param authorisationDetails authorisation details.
   * @return populated {@link GainPCEHRAccess.PCEHRRecord} object.
   */
  public static GainPCEHRAccess.PCEHRRecord createGainPCEHRRecord
  (
    GainPCEHRAccess.PCEHRRecord.AuthorisationDetails authorisationDetails
  ) {
    GainPCEHRAccess.PCEHRRecord record = new GainPCEHRAccess.PCEHRRecord();
    record.setIndividual(null);
    record.setAuthorisationDetails(authorisationDetails);
    return record;
  }

  /**
   * Create authorisation details.
   *
   * @param accessType Authorisation access type.
   * @param accessCode Authorisation access code.
   * @return populated {@link GainPCEHRAccess.PCEHRRecord.AuthorisationDetails} object.
   */
  public static GainPCEHRAccess.PCEHRRecord.AuthorisationDetails createAuthorisationDetails(
    GainPCEHRAccess.PCEHRRecord.AuthorisationDetails.AccessType accessType,
    String accessCode
  ) {
    GainPCEHRAccess.PCEHRRecord.AuthorisationDetails authorisationDetails = new GainPCEHRAccess.PCEHRRecord.AuthorisationDetails();
    authorisationDetails.setAccessType(accessType);
    authorisationDetails.setAccessCode(accessCode);
    return authorisationDetails;
  }

  /**
   * Create a PCEHRHeader request with custom pre-populated objects.
   *
   * @param user                  Populated user.
   * @param ihiNumber             IHI number.
   * @param productType           Populated product type.
   * @param clientSystemType      Client system type.
   * @param accessingOrganisation Populated accessing organisation.
   * @return Populated {@link au.net.electronichealth.ns.pcehr.xsd.common.commoncoreelements._1.PCEHRHeader}
   */
  public static PCEHRHeader createRequest(
    PCEHRHeader.User user,
    String ihiNumber,
    PCEHRHeader.ProductType productType,
    PCEHRHeader.ClientSystemType clientSystemType,
    PCEHRHeader.AccessingOrganisation accessingOrganisation
  ) {
    // Instantiate new request header.
    PCEHRHeader request = new PCEHRHeader();

    // Populate request.
    request.setUser(user);
    request.setIhiNumber(ihiNumber);
    request.setProductType(productType);
    request.setClientSystemType(clientSystemType);
    request.setAccessingOrganisation(accessingOrganisation);

    // Return populated request header object.
    return request;
  }

  /**
   * Create a header user.
   *
   * @param idType          User id type.
   * @param id              User id.
   * @param role            User role.
   * @param userName        User name.
   * @param useRoleForAudit Whether or not to use user role for audit purposes.
   * @return Populated {@link PCEHRHeader.User} object.
   */
  public static PCEHRHeader.User createUser(
    PCEHRHeader.User.IDType idType,
    String id,
    String role,
    String userName,
    boolean useRoleForAudit
  ) {
    // Populate user.
    PCEHRHeader.User user = new PCEHRHeader.User();
    user.setIDType(idType);
    user.setID(id);
    user.setRole(role);
    user.setUserName(userName);
    user.setUseRoleForAudit(useRoleForAudit);
    return user;
  }

  /**
   * Create a header product type.
   *
   * @param vendor         Product vendor.
   * @param productName    Product name.
   * @param productVersion Product version.
   * @param platform       Product platform.
   * @return Populated {@link PCEHRHeader.ProductType} object.
   */
  public static PCEHRHeader.ProductType createProductType(
    String vendor,
    String productName,
    String productVersion,
    String platform
  ) {
    PCEHRHeader.ProductType productType = new PCEHRHeader.ProductType();
    productType.setVendor(vendor);
    productType.setProductName(productName);
    productType.setProductVersion(productVersion);
    productType.setPlatform(platform);
    return productType;
  }

  /**
   * Create a header accessing organisation.
   *
   * @param organisationId            Accessing organisation id.
   * @param organisationName          Accessing organisation name.
   * @param alternateOrganisationName Access organisation alternate name.
   * @return Populated {@link PCEHRHeader.AccessingOrganisation} object.
   */
  public static PCEHRHeader.AccessingOrganisation createAccessingOrganisation(
    String organisationId,
    String organisationName,
    String alternateOrganisationName
  ) {
    // Populate accessing organisation.
    PCEHRHeader.AccessingOrganisation accessingOrganisation = new PCEHRHeader.AccessingOrganisation();
    accessingOrganisation.setOrganisationID(organisationId);
    accessingOrganisation.setOrganisationName(organisationName);
    accessingOrganisation.setAlternateOrganisationName(alternateOrganisationName);
    return accessingOrganisation;
  }
}
