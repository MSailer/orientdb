/*
 * Copyright 2018 OrientDB.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.orientechnologies.orient.core.metadata.sequence;

/**
 *
 * @author marko
 */
public class OSequenceAction {
  
  public static final int CREATE = 1;
  public static final int REMOVE = 2;
  public static final int CURRENT = 3;
  public static final int NEXT = 4;
  public static final int RESET = 5;

  private final int actionType;
  private final String sequenceName;
  private final OSequence.CreateParams parameters;

  public OSequenceAction(int actionType, String sequenceName, OSequence.CreateParams params){
    this.actionType = actionType;
    this.sequenceName = sequenceName;
    this.parameters = params;
  }

  public int getActionType() {
    return actionType;
  }

  public String getSequenceName() {
    return sequenceName;
  }

  public OSequence.CreateParams getParameters() {
    return parameters;
  }  
  
}
