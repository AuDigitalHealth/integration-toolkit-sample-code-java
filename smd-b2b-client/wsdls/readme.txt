This is an example ANT WSIMPORT project that generate WSDL to JAVA artefacts for the
NEHTA Secure Message Delivery (SMD) webservices using java and ant.

The Secure Message Delivery specification is documented in Standards Australia's
Australian Technical Specification ATS 5822-2010 and is available from the
following URL:
    http://infostore.saiglobal.com/store/portal.aspx?portal=Informatics

Installation
============

To use the distributable package, the following must be installed:

This distribution
-----------------
1. Unpack the nehta-vendorlibrary-java-smd-wsdl-<VERSION>.zip file to a
   desired directory location. <SMD_WSDL_WSIMPORT> will be used in this
   document to refer to this directory.

2. Contents:

2.1 The <SMD_WSDL_WSIMPORT>\wsdl folder provides the SMD WSDL files.

2.2 The <SMD_WSDL_WSIMPORT>\xsd folder provides the SMD schema files for the WSDLs 
    in step 2.2.

2.3 The <SMD_WSDL_WSIMPORT>\binding folder contains the jaxws and jaxb binding files
    for package redirection and class files customisation.

2.4 The dependent software libraries are in the <SMD_WSDL_WSIMPORT>\lib folder.
    Refer Metro files step 4 for details.

2.5 The generated jar files are in the <SMD_WSDL_WSIMPORT>\dist folder.    

2.6 The license agreement for use of this library is in the
    <SMD_WSDL_WSIMPORT>\license.txt file.

2.7 This readme file is <SMD_WSDL_WSIMPORT>\readme.txt

Java Development Kit (JDK)
--------------------------
1. Download and install JDK 6 Update 24 or later.
      URL: http://java.sun.com/javase/downloads/index.jsp

2. Unpack the JDK distribution into a directory of your choice.

   This directory will be your <JDK_HOME> and will be used in this document
   to refer to the root directory of the JDK installation.

   <JRE_HOME> will be used in this document to refer to <JDK_HOME>\jre.

3. Create a JAVA_HOME environment variable pointing to the <JDK_HOME>
   directory in 2.

4. Add <JDK_HOME>\bin to your system path.

JCE Policy Files (Optional. Expected only for running webservice client / service)
----------------
The Java Cryptography Extension (JCE) provides cryptography services in the JDK.
The JCE policy files in the JDK download are limited in strength due to the
import control restrictions for some countries. The "unlimited strength"
capabilities are enabled by installing certain policy files into the JRE.

1. Download the JCE Unlimited Strength Jurisdiction Policy Files for the
   installed JDK version.
      URL: http://java.sun.com/javase/downloads/index.jsp

2. Unpack the downloaded ZIP file.

3. Copy the two JAR files (local_policy.jar and US_export_policy.jar) to the
   <JRE_HOME>\lib\security directories. Overwrite the existing JAR files in the directory.

ANT Files
----------
The SMD WSDL project requires the ANT libraries to compile/recomiple the provided ANT project.

1. Download the ANT 1.8.2 or later from http://ant.apache.org/bindownload.cgi

2. Unpack the downloaded zip file.

3. Set environemental variable ANT_HOME to the directoru you uncompressed the ANT to.

4. Add ${ANT_HOME}/bin (UNIX) or %ANT_HOME%/bin (WINDOWS) to your PATH variable.

Metro Files
-----------
The SMD WSDL project requires the Metro Web Services toolkit and its associated
libraries to be setup correctly within your Java Runtime Environment. The following
steps will assist you in setting up Metro.

1. Download the Metro 2.1 distribution file (metro-standalone-2.1.zip) from http://metro.java.net/2.1/

2. Unpack the downloaded ZIP file.

3. Copy the following files from the ZIP file:

     lib\webservices-api.jar
     lib\webservices-rt.jar

   to your <JDK_HOME>\lib\endorsed and <JRE_HOME>\lib\endorsed directories.

4. Copy the following files to <SMD_WSDL_WSIMPORT>\lib. 
   lib\webservices-rt.jar
   lib\webservices-tools.jar 


WSDl JAR GENERATION STEPS
=====================
Run ANT command
 ant -f build.xml dist
 
You can regenerate the JAR files with the following steps 
 Run the ant file located in nehta-vendorlibrary-java-smd-wsdl-1.0.2 folder. [ "ant clean" followed by "ant dist" ]
	(or)
 Optionally you can customize/redirect the generated Java artefacts to custom package/s names using the following steps.
    i)  Update/Add JaxB custom binding  file/s to customise the generated schema class package names. 
	    [Refer: smdJaxbBindings.xml and smdMessageJaxbBindings.xml in nehta-vendorlibrary-java-smd-wsdl-1.0.2/binding folder]
    ii)  Redirect webservice interface and service class files to custom package/s using nehta-vendorlibrary-java-smd-wsdl-1.0.2/build.properties
    settings. [Example: ismd.SIMD.tls.packageName=au.net.electronichealth.ns.simd.tls.v2010].
    iii) Clean the existing artefacts by running ant clean [build.xml from  MCAWsdlWsimpot ]
    iv)  Run ant followed by default 'dist' target using this command "ant -f build.xml dist" to regenerate the Java artefacts for webservice operations.
