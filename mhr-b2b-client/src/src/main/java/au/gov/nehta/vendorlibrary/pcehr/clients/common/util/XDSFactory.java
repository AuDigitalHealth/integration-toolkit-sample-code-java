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

import au.gov.nehta.vendorlibrary.pcehr.clients.common.constant.XDSConstants;
import au.gov.nehta.vendorlibrary.pcehr.clients.common.type.CodedValue;
import oasis.names.tc.ebxml_regrep.xsd.rim._3.ClassificationType;
import oasis.names.tc.ebxml_regrep.xsd.rim._3.ExternalIdentifierType;
import oasis.names.tc.ebxml_regrep.xsd.rim._3.InternationalStringType;
import oasis.names.tc.ebxml_regrep.xsd.rim._3.LocalizedString;
import oasis.names.tc.ebxml_regrep.xsd.rim._3.Slot;
import oasis.names.tc.ebxml_regrep.xsd.rim._3.ValueList;
import org.apache.commons.lang.Validate;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * A factory class for creating instances of XDS objects.
 */
public final class XDSFactory {

  /**
   * Hide the constructor - this class only contains static methods.
   */
  private XDSFactory() {
  }

  /**
   * Create a {@link Slot} containing one or more {@link String} values, stored in a multivalued fashion.
   *
   * @param slotName Name of the slot.
   * @param values   One or more {@link String} values.
   * @return An instance of a {@link Slot}.
   */
  public static Slot createSlot(String slotName, String... values) {
    Validate.notEmpty(slotName, "'slotName' must be specified.");
    Validate.isTrue(values.length > 0, "'values' must contain at least one value.");

    Slot slot = new Slot();
    slot.setName(slotName);

    ValueList valueList = new ValueList();
    for (String value : values) {
      valueList.getValues().add(value);
    }
    slot.setValueList(valueList);

    return slot;
  }

  /**
   * Create a {@link Slot} containing one or more {@link String} values, stored in a single valued fashion.
   *
   * @param slotName Name of the slot.
   * @param values   One or more {@link String} values.
   * @return An instance of a {@link Slot}.
   */
  public static Slot createSingleValuedSlot(String slotName, String... values) {
    Validate.notEmpty(slotName, "'slotName' must be specified.");
    Validate.isTrue(values.length == 1, "'values' must contain at least one value.");

    Slot slot = new Slot();
    slot.setName(slotName);

    ValueList valueList = new ValueList();
    valueList.getValues().add("'" + values[0] + "'");
    slot.setValueList(valueList);

    return slot;
  }

  /**
   * Create a {@link Slot} to be used in an ad-hoc query that contains
   * one or more values using the 'OR' semantics.
   *
   * @param slotName Name of the slot.
   * @param values   One or more {@link String} values.
   * @return An instance of a {@link Slot}.
   */
  public static Slot createQuerySlot(String slotName, String... values) {
    Validate.notEmpty(slotName, "'slotName' must be specified.");
    Validate.isTrue(values.length > 0, "'values' must contain at least one value.");

    Slot slot = new Slot();
    slot.setName(slotName);

    ValueList valueList = new ValueList();
    for (String value : values) {
      valueList.getValues().add("('" + value + "')");
    }
    slot.setValueList(valueList);

    return slot;
  }

  /**
   * Create {@link Slot}s to be used in an ad-hoc query that contains
   * one or more values using the 'OR' semantics.
   *
   * @param slotName Name of the slot.
   * @param values   One or more {@link CodedValue} values.
   * @return A list of zero or two {@link Slot}s.
   */
  public static List<Slot> createQuerySlots(String slotName, CodedValue... values) {
    Validate.notEmpty(slotName, "'slotName' must be specified.");
    Validate.isTrue(values.length > 0, "'values' must contain at least one value.");

    List<Slot> slots = new ArrayList<Slot>();
    if (values.length > 0) {
      Slot valueSlot = new Slot();
      valueSlot.setName(slotName);

     // Slot codeSystemSlot = new Slot();
     // codeSystemSlot.setName(slotName + "Scheme");

      ValueList valueList = new ValueList();
      //ValueList codeSystemList = new ValueList();
      for (CodedValue value : values) {
        valueList.getValues().add("('" + value.getValue() + "^^" + value.getCodeSystem() + "')");
       // codeSystemList.getValues().add("('" + value.getCodeSystem() + "')");
      }

      valueSlot.setValueList(valueList);
      //codeSystemSlot.setValueList(codeSystemList);

      slots.add(valueSlot);
      //slots.add(codeSystemSlot);
    }

    return slots;
  }

  /**
   * Create a {@link Slot} to be used in an ad-hoc query that contains
   * one or more values using the 'OR' semantics.
   *
   * @param slotName Name of the slot.
   * @param values   One or more {@link String} values.
   * @return An instance of a {@link Slot}.
   */
  public static Slot createQuerySlot(String slotName, Collection<String> values) {
    return createQuerySlot(slotName, values.toArray(new String[values.size()]));
  }

  /**
   * Create {@link Slot}s to be used in an ad-hoc query that contains
   * one or more values using the 'OR' semantics.
   *
   * @param slotName Name of the slot.
   * @param values   One or more {@link CodedValue} values.
   * @return An instance of a {@link Slot}.
   */
  public static List<Slot> createQuerySlots(String slotName, Collection<CodedValue> values) {
    return createQuerySlots(slotName, values.toArray(new CodedValue[values.size()]));
  }

  /**
   * Create an {@link InternationalStringType}.
   *
   * @param value String value
   * @return An instance of an {@link InternationalStringType}.
   */
  public static InternationalStringType createInternationalString(String value) {
    Validate.notEmpty(value, "'value' must be specified.");

    LocalizedString ls = new LocalizedString();
    ls.setValue(value);

    InternationalStringType is = new InternationalStringType();
    is.getLocalizedStrings().add(ls);
    return is;
  }

  /**
   * Create an instance of a {@link ClassificationType} object.
   *
   * @param classificationScheme classification scheme.
   * @param classifiedObject     classified object.
   * @param nodeRepresentation   node representation.
   * @param idNum                id number.
   * @return An instance of a {@link ClassificationType} object.
   */
  public static ClassificationType createClassification(String classificationScheme,
                                                        String classifiedObject,
                                                        String nodeRepresentation,
                                                        int idNum) {
    Validate.notEmpty(classificationScheme, "'classificationScheme' must be specified.");
    Validate.notEmpty(classifiedObject, "'classifiedObject' must be specified.");
    Validate.isTrue(idNum > 0, "'idNum' must be a positive integer.");

    ClassificationType classification = new ClassificationType();
    classification.setNodeRepresentation(nodeRepresentation);
    classification.setClassificationScheme(classificationScheme);
    classification.setClassifiedObject(classifiedObject);
    classification.setObjectType(XDSConstants.OBJECT_TYPE_CLASSIFICATION);
    classification.setId(toClassificationIdString(idNum));
    return classification;
  }

  /**
   * Create an instance of an {@link ExternalIdentifierType} object.
   *
   * @param identificationScheme identification scheme.
   * @param registryObject       registry object.
   * @param name                 name.
   * @param value                value.
   * @param idNum                id number.
   * @return An instance of an {@link ExternalIdentifierType} object.
   */
  public static ExternalIdentifierType createExternalIdentifier(String identificationScheme,
                                                                String registryObject,
                                                                String name,
                                                                String value,
                                                                int idNum) {
    ExternalIdentifierType externalId = new ExternalIdentifierType();
    externalId.setObjectType(XDSConstants.OBJECT_TYPE_EXTERNAL_IDENTIFIER);
    externalId.setIdentificationScheme(identificationScheme);
    externalId.setRegistryObject(registryObject);
    externalId.setName(createInternationalString(name));
    externalId.setValue(value);
    externalId.setId(toExternalIdentifierIdString(idNum));
    return externalId;
  }

  /**
   * Format id number as string.
   *
   * @param idNum id number to format.
   * @return formatted string.
   */
  public static String toClassificationIdString(int idNum) {
    return String.format("cl%02d", idNum);
  }

  public static String toExternalIdentifierIdString(int idNum) {
    return String.format("ei%02d", idNum);
  }

  public static String toAssociationIdentifierString(int idNum) {
    return String.format("as%02d", idNum);
  }
}
