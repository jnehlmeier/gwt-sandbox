/*
 * Copyright 2024 Google Inc.
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package com.google.gwt.user.client.ui;

import com.google.gwt.junit.client.GWTTestCase;

/**
 * Testing IntegerBox.
 */
public class IntegerBoxTest extends GWTTestCase {

  @Override
  public String getModuleName() {
    return "com.google.gwt.user.User";
  }

  public void testPrimaryStyleName() {
    IntegerBox box = new IntegerBox();
    assertEquals("gwt-IntegerBox", box.getStylePrimaryName());
    assertEquals("gwt-IntegerBox", box.getStyleName());
  }

  public void testReadOnlyStyleName() {
    IntegerBox box = new IntegerBox();
    
    // Initially should have only the primary style
    assertEquals("gwt-IntegerBox", box.getStyleName());
    assertFalse(box.isReadOnly());
    
    // Setting readonly should add the dependent style
    box.setReadOnly(true);
    assertTrue(box.isReadOnly());
    assertTrue(box.getStyleName().contains("gwt-IntegerBox-readonly"));
    assertTrue(box.getStyleName().contains("gwt-IntegerBox"));
    
    // Removing readonly should remove the dependent style
    box.setReadOnly(false);
    assertFalse(box.isReadOnly());
    assertEquals("gwt-IntegerBox", box.getStyleName());
  }

  public void testReadOnlyMultipleCalls() {
    IntegerBox box = new IntegerBox();
    
    // Multiple calls to setReadOnly(true) should not duplicate the style
    box.setReadOnly(true);
    String styleAfterFirst = box.getStyleName();
    box.setReadOnly(true);
    String styleAfterSecond = box.getStyleName();
    assertEquals(styleAfterFirst, styleAfterSecond);
    
    // Should still contain both styles but no duplicates
    assertTrue(box.getStyleName().contains("gwt-IntegerBox-readonly"));
    assertTrue(box.getStyleName().contains("gwt-IntegerBox"));
    
    // Verify no malformed style names like "-readonly"
    assertFalse(box.getStyleName().contains(" -readonly"));
    assertFalse(box.getStyleName().startsWith("-readonly"));
  }

  public void testValue() {
    IntegerBox box = new IntegerBox();
    
    // Test setting and getting integer values
    box.setValue(42);
    assertEquals(Integer.valueOf(42), box.getValue());
    
    box.setText("123");
    assertEquals(Integer.valueOf(123), box.getValue());
    
    // Test null value
    box.setValue(null);
    assertNull(box.getValue());
    assertEquals("", box.getText());
  }
}