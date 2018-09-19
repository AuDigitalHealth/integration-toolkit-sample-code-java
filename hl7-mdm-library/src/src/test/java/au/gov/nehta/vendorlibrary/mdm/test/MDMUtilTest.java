package au.gov.nehta.vendorlibrary.mdm.test;

import au.gov.nehta.vendorlibrary.mdm.core.Message;
import au.gov.nehta.vendorlibrary.mdm.util.MDMUtil;
import org.junit.Assert;
import org.junit.Test;

import java.io.File;
import java.io.IOException;

public class MDMUtilTest {
  @Test
  public void testWriteMDMMessageToFile() throws Exception {
    Message in1 = MDMUtil.readMDMMessageFromFile("src\\test\\resources\\Example-HL7-MDM-20111117.txt");
    String outPath = "C:\\projects\\MDM\\src\\test\\resources\\out.txt";
    MDMUtil.writeMDMMessageToFile(in1, outPath);
    String compareVal = MDMUtil.readMDMMessageFromFile(outPath).toString();
    // Must run this to get allow deletion of temp file.
    System.gc();
    System.out.println("Temp file deletable: " + new File(outPath).canWrite());
    System.out.println("Temp file deleted: " + new File(outPath).delete());
    Assert.assertEquals(in1.toString(), compareVal);
  }

  
  @Test
  public void testRead_NetMDMMessage() throws IOException{
	  //read a mdm created by .net
	  Message in1 = MDMUtil.readMDMMessageFromFile("src\\test\\resources\\netExample.hl7");
	  
  }
  
  
  @Test
  public void testWriteMDMMessageToFileEmpty() throws Exception {
    Message msg = MDMUtil.readMDMMessageFromFile("src\\test\\resources\\Empty.txt");
    Assert.assertNull(msg);
  }

  @Test
  public void testReadMDMMessageFromFile() throws Exception {
    Message message = MDMUtil.readMDMMessageFromFile("C:\\projects\\MDM\\src\\test\\resources\\Example-HL7-MDM-20111117.txt");
    Assert.assertNotNull(message);
  }
}
