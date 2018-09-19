This is a software library that provides example client implementations of the
NEHTA Secure Message Delivery (SMD) specifications using Java.

The Secure Message Delivery specification is documented in Standards Australia's
Australian Technical Specification ATS 5822-2010 and is available from the
following URL:
    http://infostore.saiglobal.com/store/portal.aspx?portal=Informatics

Installation
============

To use the distributable package, the following must be installed:

This distribution
-----------------
1. Unpack the nehta-vendorlibrary-java-smdclient-<VERSION>.zip file to the
   desired location. <SMD_CLIENT_HOME> will be used in this document to refer
   to the root directory of this implementation.

2. Contents:

2.1 The <SMD_CLIENT_HOME>/api/nehta-vendorlibrary-java-smdclient-<VERSION>.jar
    file provides the class library.

2.2 The <SMD_CLIENT_HOME>/api/nehta-vendorlibrary-java-smdclient-<VERSION>-sources.jar
    file contains the source code for the library.

2.3 The <SMD_CLIENT_HOME>/api/nehta-vendorlibrary-java-smdclient-<VERSION>-javadocs.jar
    file contains the Javadoc documentation for the library.

2.4 The <SMD_CLIENT_HOME>/api/nehta-vendorlibrary-java-smdclient-sample-<VERSION>.jar
    file provides the sample class library.

2.5 The <SMD_CLIENT_HOME>/api/nehta-vendorlibrary-java-smdclient-sample-<VERSION>-sources.jar
    file contains the sample source code for the library.

2.6 The <SMD_CLIENT_HOME>/api/nehta-vendorlibrary-java-smdclient-sample-<VERSION>-javadocs.jar
    file contains the sample Javadoc documentation for the library.

2.7 The dependent software libraries (and their corresponding source code) are in the
    <SMD_CLIENT_HOME>/lib folder.

2.8 The license agreement for use of this library is in the
    <SMD_CLIENT_HOME/license.txt

2.9 This readme file is <SMD_CLIENT_HOME>/readme.txt

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
The SMD Client library requires the Metro Web Services toolkit and its associated
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
The SMD client library consists of the following five distinct Java Web Service clients classes:

  1. SealedMessageDeliveryClient
  2. SealedMessageRetrievalClient
  3. TransportResponseDeliveryClient
  4. TransportResponseRetrievalClient
  5. SealedImmediateMessageDeliveryClient

1. Requirements:

   a) A Transport Layer Security (TLS) public/private key pair and its associated digital certificate
      All clients require this key pair and certificate in order to authenticate the client to the SMD
      Web Service providers during the Transport Layer Security (TLS) handshake. This key pair is typically
      stored in a Java key store file.

   b) The digital certificate of the Certificate Authority (CA) which signed the SMD Web Service providers TLS certificate.
      This certificate is used to authenticate the SMD Web Service provider to the clients during the TLS handshake.
      This certificate is typically stored in a Java trust store file.

   c) Your organisation's fully qualified Healthcare Provider Identifier or HPI-O (hereinafter referred to as
      CLIENT_ORGANISATION_HPIO) and those to whom you wish to send and receive messages from.

2. Code example:

   The following code snippets demonstrate example instantiations of the five SMD Clients:
   Refer API javadoc from <SMD_CLIENT_HOME>/api/nehta-vendorlibrary-java-smdclient-sample-<VERSION>-javadocs.jar for details.

   -----------------------------------------------------------------------------------------------------------------
   // Sealed Message Delivery Client:
   SealedMessageDeliveryClient smdClient = new SealedMessageDeliveryClient(sslSocketFactory);

   // Sealed Message Retrieval Client:
   SealedMessageRetrievalClient smrClient = new SealedMessageRetrievalClient(sslSocketFactory);

   // Transport Response Delivery Client:
   TransportResponseDeliveryClient trdClient = new TransportResponseDeliveryClient(sslSocketFactory);
   
   // Transport Response Retrieval Client:   
   TransportResponseRetrievalClient trrClient = new TransportResponseRetrievalClient(sslSocketFactory);

   // Sealed Immediate Message Delivery Client:
   SealedImmediateMessageDeliveryClient simdClient = new SealedImmediateMessageDeliveryClient(sslSocketFactory);

   -----------------------------------------------------------------------------------------------------------------

Client Usage
============

This section describes how the five different clients may be used. This section assumes that you have met the
requirements above and have successfully instantiated the relevant clients. Please refer to the Javadoc (see 2.3)
for more detailed descriptions of the client classes, methods and their arguments.

Sealed Message Delivery Client
------------------------------

1. Requirements
   a) The endpoint URLs for your Sealed Message Delivery Web Service providers i.e. the endpoint URL of the receiver of
      the Sealed Message or that of the receiver's intermediary.

   b) A valid SealedMessageType object to be delivered. This object's metadata should include a valid route record
      to receive transport responses related to this message.

2. Code example:

   The following code snippets demonstrates an example usage of the SealedMessageDeliveryClient's deliver method:

   -----------------------------------------------------------------------------------------------------------------
   DeliverStatusType deliverStatusType = smdClient.deliver(sealedMessage,
                                                           smdEndpointURL);
   -----------------------------------------------------------------------------------------------------------------

Sealed Message Retrieval Client
-------------------------------

1. Requirements
   a) The endpoint URL for your Sealed Message Retrieval Web Service providers i.e. the endpoint URL of the client
      system's intermediary.

2. Code examples:

   The following code snippets demonstrates an example usage of the SealedMessageRetrievalClient's list method:

   --------------------------------------------------------------------------------------------------------------------
   MessageListType messageListType = smrClient.list(CLIENT_ORGANISATION_HPIO,
                                                    ALL_AVAILABLE,
                                                    MAX_RESULTS,
                                                    smrEndpointURL);
   --------------------------------------------------------------------------------------------------------------------

   The following code snippets demonstrates an example usage of the SealedMessageRetrievalClient's retrieve method:

   -----------------------------------------------------------------------------------------------------------------
   SealedMessageType retrievedMessage = smrClient.retrieve(CLIENT_ORGANISATION_HPIO,
                                                           INVOCATION_IDS,
                                                           smrEndpointURL);
   -----------------------------------------------------------------------------------------------------------------

Transport Response Delivery Client
----------------------------------

1. Requirements
   a) The endpoint URLs for your Transport Response Delivery Web Service providers i.e. the endpoint URL of the
      the receiver of the Transport Response or that of its intermediary.

   b) A valid TransportResponseType object to be delivered. This object's metadata should include a valid digest value
      and Transport Response code to deliver the Transport Response.

2. Code example:

   The following code snippets demonstrates an example usage of the TransportResponseDeliveryClient's deliver method:

   -----------------------------------------------------------------------------------------------------------------
   DeliverStatusType deliverStatusType = trdClient.deliver(transportResponseMetadataTypeList,
                                                           trdEndpointURL);
   -----------------------------------------------------------------------------------------------------------------   
   
Transport Response Retrieval Client
------------------------------

1. Requirements
   a) The endpoint URLs for your Transport Response Retrieval Web Service providers i.e. the endpoint URL of the
      client's intermediary.

2. Code example:

   The following code snippets demonstrates an example usage of the TransportResponseRetrievalClient's remove method:

   --------------------------------------------------------------------------------------------------------------------
   List<RemoveResultType> removeResultTypes = trrClient.remove(forceRemoval,
                                                               trrReceiverOrganisation,
                                                               transportResponseIdList,
                                                               trrEndpointURL);
   --------------------------------------------------------------------------------------------------------------------
   
   The following code snippets demonstrates an example usage of the TransportResponseRetrievalClient's retrieve method:
   --------------------------------------------------------------------------------------------------------------------
   TransportResponseListType transportResponseListType = trrClient.retrieve(trrReceiverOrganisation,
                                                                            allAvailable,
                                                                            limit,
                                                                            trrEndpointURL);
   --------------------------------------------------------------------------------------------------------------------
      
Sealed Immediate Message Delivery Client
----------------------------------------

1. Requirements
   a) The endpoint URLs for your Sealed Immediate Message Delivery Web Service providers i.e. the receiver of your
      sealed message who will synchronously respond with a Sealed Message.

2. Code examples:

   The following code snippets demonstrates an example usage of the SealedImmediateMessageDeliveryClient's deliver
   method:
   ---------------------------------------------------------------------------------------------------------------
   SealedMessageType responseMessage = simdClient.deliver(SEALED_MESSAGE,
                                                          simdEndpointURL)
   ---------------------------------------------------------------------------------------------------------------

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
