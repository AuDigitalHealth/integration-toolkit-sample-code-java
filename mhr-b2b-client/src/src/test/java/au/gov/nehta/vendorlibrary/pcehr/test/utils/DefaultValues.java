package au.gov.nehta.vendorlibrary.pcehr.test.utils;

import au.net.electronichealth.ns.pcehr.xsd.common.commoncoreelements._1.PCEHRHeader;

public class DefaultValues {
   /**
   * Default test values.
   */
  public static final PCEHRHeader.User.IDType ID_TYPE = PCEHRHeader.User.IDType.HPII;
  public static final String ID = "8003630000011111";
  public static final String ROLE = "Default Role";
  public static final String USER_NAME = "Default User Name";
  public static final Boolean USE_ROLE_FOR_AUDIT = true;
  public static final String IHI_NUMBER = "8003600300001119";
  public static final String VENDOR = "Vendor X";
  public static final String PRODUCT_NAME = "Product Name";
  public static final String PRODUCT_VERSION = "Version 1.2.1";
  public static final String PLATFORM = "Platform X";
  public static final PCEHRHeader.ClientSystemType CLIENT_SYSTEM_TYPE = PCEHRHeader.ClientSystemType.CPP;
  public static final String ORGANISATION_ID = "8003620000020052";
  public static final String ORGANISATION_NAME = "Organisation X";
  public static final String ALTERNATE_ORGANISATION_NAME = "Organisation X-2";
}
