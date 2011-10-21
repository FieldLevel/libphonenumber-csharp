/*
 * Copyright (C) 2011 The Libphonenumber Authors
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.google.i18n.phonenumbers;

import junit.framework.TestCase;

import java.io.InputStream;

/**
 * @author Shaopeng Jia
 */
public class ShortNumberUtilTest extends TestCase {
  private ShortNumberUtil shortUtil;
  static final String TEST_META_DATA_FILE_PREFIX =
      "/com/google/i18n/phonenumbers/data/PhoneNumberMetadataProtoForTesting";

  public ShortNumberUtilTest() {
    PhoneNumberUtil phoneUtil = PhoneNumberUtil.getInstance(
        TEST_META_DATA_FILE_PREFIX,
        CountryCodeToRegionCodeMapForTesting.getCountryCodeToRegionCodeMap());
    shortUtil = new ShortNumberUtil(phoneUtil);
  }

  public void testConnectsToEmergencyNumber_US() {
    assertTrue(shortUtil.connectsToEmergencyNumber("911", RegionCode.US));
    assertTrue(shortUtil.connectsToEmergencyNumber("119", RegionCode.US));
    assertFalse(shortUtil.connectsToEmergencyNumber("999", RegionCode.US));
  }

  public void testConnectsToEmergencyNumberLongNumber_US() {
    assertTrue(shortUtil.connectsToEmergencyNumber("9116666666", RegionCode.US));
    assertTrue(shortUtil.connectsToEmergencyNumber("1196666666", RegionCode.US));
    assertFalse(shortUtil.connectsToEmergencyNumber("9996666666", RegionCode.US));
  }

  public void testConnectsToEmergencyNumberWithFormatting_US() {
    assertTrue(shortUtil.connectsToEmergencyNumber("9-1-1", RegionCode.US));
    assertTrue(shortUtil.connectsToEmergencyNumber("1-1-9", RegionCode.US));
    assertFalse(shortUtil.connectsToEmergencyNumber("9-9-9", RegionCode.US));
  }

  public void testConnectsToEmergencyNumberWithPlusSign_US() {
    assertFalse(shortUtil.connectsToEmergencyNumber("+911", RegionCode.US));
    assertFalse(shortUtil.connectsToEmergencyNumber("\uFF0B911", RegionCode.US));
    assertFalse(shortUtil.connectsToEmergencyNumber(" +911", RegionCode.US));
    assertFalse(shortUtil.connectsToEmergencyNumber("+119", RegionCode.US));
    assertFalse(shortUtil.connectsToEmergencyNumber("+999", RegionCode.US));
  }

  public void testConnectsToEmergencyNumber_BR() {
    assertTrue(shortUtil.connectsToEmergencyNumber("911", RegionCode.BR));
    assertTrue(shortUtil.connectsToEmergencyNumber("190", RegionCode.BR));
    assertFalse(shortUtil.connectsToEmergencyNumber("999", RegionCode.BR));
  }

  public void testConnectsToEmergencyNumberLongNumber_BR() {
    // Brazilian emergency numbers don't work when additional digits are appended.
    assertFalse(shortUtil.connectsToEmergencyNumber("9111", RegionCode.BR));
    assertFalse(shortUtil.connectsToEmergencyNumber("1900", RegionCode.BR));
    assertFalse(shortUtil.connectsToEmergencyNumber("9996", RegionCode.BR));
  }
}
