This is a software library that provides an example implementation of a
NEHTA Endpoint Location Service (ELS) Web Services client using Java.

The ELS is documented in Standards Australia Technical Report TR 5823-2010
and is available from the following URL:
    http://infostore.saiglobal.com/store/portal.aspx?portal=Informatics

Installation
============

To use the distributable package, the following must be installed:

This distribution
-----------------
1. Unpack the nehta-vendorlibrary-java-elsclient-<VERSION>.zip file to the
   desired location. <ELS_CLIENT_HOME> will be used in this document to refer
   to the root directory of this implementation.

2. Contents:

2.1 The <ELS_CLIENT_HOME>\nehta-vendorlibrary-java-elsclient-<VERSION>.jar
    file provides the class library.

2.2 The <ELS_CLIENT_HOME>\nehta-vendorlibrary-java-elsclient-<VERSION>-sources.jar
    file contains the source code for the library.

2.3 The <ELS_CLIENT_HOME>\nehta-vendorlibrary-java-elsclient-<VERSION>-javadocs.jar
    file contains the Javadoc documentation for the library.

2.4 The dependent software libraries (and their corresponding source code) are in the
    <ELS_CLIENT_HOME>\lib folder.

2.5 The license agreement for use of this library is in the
    <ELS_CLIENT_HOME\license.txt

2.6 This readme file is <ELS_CLIENT_HOME>\readme.txt

Java Development Kit (JDK)
--------------------------
1. Download and install JDK 6 Update 24 or later.
      URL: http://java.sun.com/javase/downloads/index.jsp

2. Unpack the JDK distribution into a directory of your choice.

   This directory will be your <JDK_HOME>and will be used in this document
   to refer to the root directory of the JDK installation.

   <JRE_HOME> will be used in this document to refer to <JDK_HOME>/jre.

3. Create a JAVA_HOME environment variable pointing to the <JDK_HOME>
   directory in 2.

4. Add <JDK_HOME>/bin to the system path.

JCE Policy Files
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
   <JRE_HOME>/lib/security directories. Overwrite the existing JAR files in the directory.

Metro Files
-----------
The ELS Client library requires the Metro Web Services toolkit and its associated
libraries to be setup correctly within your Java Runtime Environment. The following
steps will assist you in setting up Metro.

1. Download the Metro 2.1 distribution file (metro-standalone-2.1.zip) from http://metro.java.net/2.1/

2. Unpack the downloaded ZIP file.

3. Copy the following files from the ZIP file:

     lib/webservices-api.jar
     lib/webservices-rt.jar

   to your <JDK_HOME>/lib/endorsed and <JRE_HOME>/lib/endorsed directories.

Client instantiation
=====================
The ELS client may be instantiated in "Lookup Only" or "Lookup and Publish" modes. In "Lookup Only"
mode, the client may query an ELS service but not publish to it. In "Lookup and Publish" mode,
both queries and publishing of interactions my be done.

1. Requirements:

   a) A public/private key pair and its associated public certificate
      These are used to authenticate the client to an ELS Server during the Transport Layer Security (TLS)
      handshake. They are typically stored in a Java key store file.

   b) The certificate of the Certificate Authority (CA) which signed the ELS server's TLS certificate.
      This certificate is used to authenticate the ELS server during the TLS handshake. This certificate is typically
      stored in a Java trust store file.

   c) Based on the key pair in (a), an SSLSocketFactory object needs to be instantiated as an argument
      to instantiate the ELSClient.

   d) The Lookup and Publish TLS Web Service endpoint URLs for your ELS server(s)

2. Code example:

   The following code snippets demonstrate example instantiation of the ELS Client:

   -----------------------------------------------------------------------------------------------------------------
   // "Lookup Only" mode:
   final String ELS_LOOKUP_ENDPOINT_URL = "https://www.example.com/els/Lookup";
   ELSClient testClient = new ELSClient(ELS_LOOKUP_ENDPOINT_URL, sslSocketFactory);

   // "Lookup and Publish" mode:
   final String ELS_LOOKUP_ENDPOINT_URL = "https://www.example.com/els/Lookup";
   final String ELS_PUBLISH_ENDPOINT_URL = "https://www.example.com/els/Publish";
   ELSClient testClient = new ELSClient(ELS_LOOKUP_ENDPOINT_URL, ELS_PUBLISH_ENDPOINT_URL, sslSocketFactory);

Licensing
=========
Copyright 2011 NEHTA

Licensed under the NEHTA Open Source (Apache) License; you may not use this
file except in compliance with the License. A copy of the License is in the
'license.txt' file, which should be provided with this work.

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
License for the specific language governing permissions and limitations
under the License.