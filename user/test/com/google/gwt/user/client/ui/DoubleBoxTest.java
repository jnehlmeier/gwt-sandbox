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
 * Testing DoubleBox.
 */
public class DoubleBoxTest extends GWTTestCase {

  @Override
  public String getModuleName() {
    return "com.google.gwt.user.User";
  }

  public void testPrimaryStyleName() {
    DoubleBox box = new DoubleBox();
    assertEquals("gwt-DoubleBox", box.getStylePrimaryName());
    assertEquals("gwt-DoubleBox", box.getStyleName());
  }

  public void testReadOnlyStyleName() {
    DoubleBox box = new DoubleBox();
    
    // Initially should have only the primary style
    assertEquals("gwt-DoubleBox", box.getStyleName());
    assertFalse(box.isReadOnly());
    
    // Setting readonly should add the dependent style
    box.setReadOnly(true);
    assertTrue(box.isReadOnly());
    assertTrue(box.getStyleName().contains("gwt-DoubleBox-readonly"));
    assertTrue(box.getStyleName().contains("gwt-DoubleBox"));
    
    // Removing readonly should remove the dependent style
    box.setReadOnly(false);
    assertFalse(box.isReadOnly());
    assertEquals("gwt-DoubleBox", box.getStyleName());
  }

  public void testReadOnlyMultipleCalls() {
    DoubleBox box = new DoubleBox();
    
    // Multiple calls to setReadOnly(true) should not duplicate the style
    box.setReadOnly(true);
    String styleAfterFirst = box.getStyleName();
    box.setReadOnly(true);
    String styleAfterSecond = box.getStyleName();
    assertEquals(styleAfterFirst, styleAfterSecond);
    
    // Should still contain both styles but no duplicates
    assertTrue(box.getStyleName().contains("gwt-DoubleBox-readonly"));
    assertTrue(box.getStyleName().contains("gwt-DoubleBox"));
    
    // Verify no malformed style names like "-readonly"
    assertFalse(box.getStyleName().contains(" -readonly"));
    assertFalse(box.getStyleName().startsWith("-readonly"));
  }

  public void testValue() {
    DoubleBox box = new DoubleBox();
    
    // Test setting and getting double values
    box.setValue(42.5);
    assertEquals(Double.valueOf(42.5), box.getValue());
    
    box.setText("123.456");
    assertEquals(Double.valueOf(123.456), box.getValue());
    
    // Test null value
    box.setValue(null);
    assertNull(box.getValue());
    assertEquals("", box.getText());
  }
}