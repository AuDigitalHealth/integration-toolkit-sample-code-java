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
package au.gov.nehta.vendorlibrary.pcehr.sample.common.constants;

import au.gov.nehta.vendorlibrary.pcehr.clients.common.type.TemplateServiceRequestorOption;
import au.net.electronichealth.ns.pcehr.xsd.common.commoncoreelements._1.PCEHRHeader;
import au.net.electronichealth.ns.pcehr.xsd.interfaces.pcehrprofile._1.GainPCEHRAccess;
import au.net.electronichealth.ns.pcehr.xsd.interfaces.removedocument._1.RemoveDocument;
import au.net.electronichealth.ns.tplt.xsd.common.templatescoreelements._1.TemplateMetadataType;

/**
 * Sample header values used in sample code. These values should be replaced with valid details in
 * accordance with the technical documentation.
 */
public final class SampleValues {

  /**
   * User - ID type.
   */
  public static final PCEHRHeader.User.IDType USER_ID_TYPE = PCEHRHeader.User.IDType.HPII;

  /**
   * User - ID.
   */
  public static final String USER_ID = "0000000000000000";

  /**
   * User - Name.
   */
  public static final String USER_NAME = "Dr John Citizen";

  /**
   * User - User role.
   */
  public static final String USER_ROLE = null;

  /**
   * User - Use role for audit.
   */
  public static final boolean USER_USE_ROLE_FOR_AUDIT = false;

  /**
   * IHI number.
   */
  public static final String IHI_NUMBER = "0000000000000000";

  /**
   * Product Type - Vendor.
   */
  public static final String PRODUCT_TYPE_VENDOR = "Vendor X";

  /**
   * Product Type - Product name.
   */
  public static final String PRODUCT_NAME = "Product X";

  /**
   * Product Type - Product version.
   */
  public static final String PRODUCT_VERSION = "0.0.0";

  /**
   * Product Type - Platform.
   */
  public static final String PRODUCT_PLATFORM = "Windows 7";

  /**
   * Client system type - CIS.
   */
  public static final PCEHRHeader.ClientSystemType CLIENT_SYSTEM_TYPE_CIS = PCEHRHeader.ClientSystemType.CIS;

  /**
   * Client system type - CRP.
   */
  public static final PCEHRHeader.ClientSystemType CLIENT_SYSTEM_TYPE_CRP = PCEHRHeader.ClientSystemType.CRP;

  /**
   * Accessing Organisation - Organisation ID.
   */
  public static final String ACCESSING_ORGANISATION_ID = "0000000000000000";

  /**
   * Accessing Organisation - Organisation name.
   */
  public static final String ACCESSING_ORGANISATION_NAME = "Org X";

  /**
   * Accessing Organisation - Alternate organisation name.
   */
  public static final String ACCESSING_ORGANISATION_ALTERNATE_NAME = "Org Y";

  /**
   * Authorisation Details - Access type.
   */
  public static final GainPCEHRAccess.PCEHRRecord.AuthorisationDetails.AccessType AUTHORISATION_DETAILS_ACCESS_TYPE = GainPCEHRAccess.PCEHRRecord.AuthorisationDetails.AccessType.EMERGENCY_ACCESS;

  /**
   * Authorisation Details - Access code.
   */
  public static final String AUTHORISATION_DETAILS_ACCESS_CODE = null;

  /**
   * Document Request - Document unique ID.
   */
  public static final String DOCUMENT_REQUEST_DOCUMENT_ID = "0.0.00.0.00000.0000000000000";

  /**
   * Document Request - Repository unique ID.
   */
  public static final String DOCUMENT_REQUEST_REPOSITORY_ID = "0.0.00.0.0000.0000.0.0.0.0";

  /**
   * Remove Document - Document ID.
   */
  public static final String REMOVE_DOCUMENT_ID = "0.0.000000000000000000000000000000000000000";

  /**
   * Remove Document - Removal reason.
   */
  public static final RemoveDocument.DocumentRemovalReason REMOVE_DOCUMENT_REASON = RemoveDocument.DocumentRemovalReason.WITHDRAWN;

  /**
   * Search Template - Template ID.
   */
  public static final String SEARCH_TEMPLATE_ID = "000";

  /**
   * Search Template - Template metadata.
   */
  public static final TemplateMetadataType SEARCH_TEMPLATE_METADATA = null;

  /**
   * Get Template - Template ID.
   */
  public static final String GET_TEMPLATE_ID = "000";

  /**
   * Get Template - Template service requestor option.
   */
  public static final TemplateServiceRequestorOption GET_TEMPLATE_REQUESTOR_OPTION = TemplateServiceRequestorOption.FullPackage;

  /**
   * Get Audit View - From date.
   */
  public static final String AUDIT_VIEW_FROM_DATE = "000000000000";

  /**
   * Get Audit View - To date.
   */
  public static final String AUDIT_VIEW_TO_DATE = "000000000000";

  /**
   * Get Change History View - Document ID.
   */
  public static final String GET_CHANGE_HISTORY_VIEW_DOCUMENT_ID = "urn:uuid:00000000-0000-0000-000000000000";

  /**
   * Upload Document Metadata - Repository Unique ID.
   */
  public static final String REPOSITORY_UNIQUE_ID = "00000000";

  /**
   * Private constructor to prevent instantiation.
   */
  private SampleValues() {
  }
}

