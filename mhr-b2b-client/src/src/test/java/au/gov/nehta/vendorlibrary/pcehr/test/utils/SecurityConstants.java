package au.gov.nehta.vendorlibrary.pcehr.test.utils;


/**
 *
 */
public final class SecurityConstants {
  public static final String PRIVATE_KEY_STORE_PASSWORD = "Password";
  public static final String PRIVATE_KEY_PASSWORD = "Password";
  public static final String RESOURCES_DIR = String.format(".%1$ssrc%1$stest%1$sresources%1$ssecurity%1$s", "/");
  public static final String PRIVATE_KEY_STORE_TYPE = "JKS";
  public static final String PRIVATE_KEY_STORE_FILE = "keystore.jks";
  public static final String PRIVATE_KEY_STORE_PATH = String.format("%s%s", RESOURCES_DIR, PRIVATE_KEY_STORE_FILE);
  public static final String ALIAS_8003620833337558 = "general.8003620833337558.id.electronichealth.net.au";//expired
  public static final String ALIAS_8003622500001608 = "general.8003622500001608.id.electronichealth.net.au";//expired
  public static final String ALIAS_8003627500001433 ="general.8003627500001433.id.electronichealth.net.au";//expired
  public static final String ALIAS_8003627500000575 = "general.8003627500000575.id.electronichealth.net.au";//expired
  public static final String ALIAS_8003620833335909 = "general.8003620833335909.id.electronichealth.net.au";//expired
  public static final String ALIAS_8003626566667756 = "general.8003626566667756.id.electronichealth.net.au";//expired
  public static final String ALIAS_8003624166667177 = "general.8003624166667177.id.electronichealth.net.au";//expired
  public static final String ALIAS_8003627500001540 = "general.8003627500001540.id.electronichealth.net.au";//expired
  public static final String ALIAS_8003628233352432 =  "general.8003628233352432.id.electronichealth.net.au";

  public static final String ALIAS_8003629900024122 =  "general.8003629900024122.id.electronichealth.net.au";
  public static final String ALIAS_LATEST_WORKING = ALIAS_8003629900024122;
  
  public static final String REVOKED_ALIAS_8003628233351004 = "general.8003628233351004.id.electronichealth.net.au";
  public static final String TRUST_STORE_TYPE = "JKS";
  public static final String TRUST_STORE_FILE = "truststore.jks";
  public static final String TRUST_STORE_PASSWORD = "Password";
  public static final String TRUST_STORE_PATH = String.format("%s%s", RESOURCES_DIR, TRUST_STORE_FILE);
  
}
