/* Generated By:JJTree: Do not edit this line. OBinaryCondition.java Version 4.3 */
/* JavaCCOptions:MULTI=true,NODE_USES_PARSER=false,VISITOR=true,TRACK_TOKENS=true,NODE_PREFIX=O,NODE_EXTENDS=,NODE_FACTORY=,SUPPORT_CLASS_VISIBILITY_PUBLIC=true */
package com.orientechnologies.orient.core.sql.parser;

import com.orientechnologies.orient.core.command.OCommandContext;
import com.orientechnologies.orient.core.db.ODatabaseDocumentInternal;
import com.orientechnologies.orient.core.db.record.OIdentifiable;
import com.orientechnologies.orient.core.metadata.schema.OClass;
import com.orientechnologies.orient.core.sql.executor.OResult;

import java.util.*;

public class OBinaryCondition extends OBooleanExpression {
  protected OExpression            left;
  protected OBinaryCompareOperator operator;
  protected OExpression            right;

  public OBinaryCondition(int id) {
    super(id);
  }

  public OBinaryCondition(OrientSql p, int id) {
    super(p, id);
  }

  /** Accept the visitor. **/
  public Object jjtAccept(OrientSqlVisitor visitor, Object data) {
    return visitor.visit(this, data);
  }

  @Override
  public boolean evaluate(OIdentifiable currentRecord, OCommandContext ctx) {
    return operator.execute(left.execute(currentRecord, ctx), right.execute(currentRecord, ctx));
  }

  @Override
  public boolean evaluate(OResult currentRecord, OCommandContext ctx) {
    return operator.execute(left.execute(currentRecord, ctx), right.execute(currentRecord, ctx));
  }

  public void toString(Map<Object, Object> params, StringBuilder builder) {
    left.toString(params, builder);
    builder.append(" ");
    builder.append(operator.toString());
    builder.append(" ");
    right.toString(params, builder);
  }

  protected boolean supportsBasicCalculation() {
    if (!operator.supportsBasicCalculation()) {
      return false;
    }
    return left.supportsBasicCalculation() && right.supportsBasicCalculation();

  }

  @Override
  protected int getNumberOfExternalCalculations() {
    int total = 0;
    if (!operator.supportsBasicCalculation()) {
      total++;
    }
    if (!left.supportsBasicCalculation()) {
      total++;
    }
    if (!right.supportsBasicCalculation()) {
      total++;
    }
    return total;
  }

  @Override
  protected List<Object> getExternalCalculationConditions() {
    List<Object> result = new ArrayList<Object>();
    if (!operator.supportsBasicCalculation()) {
      result.add(this);
    }
    if (!left.supportsBasicCalculation()) {
      result.add(left);
    }
    if (!right.supportsBasicCalculation()) {
      result.add(right);
    }
    return result;
  }

  public OBinaryCondition isIndexedFunctionCondition(OClass iSchemaClass, ODatabaseDocumentInternal database) {
    if (left.isIndexedFunctionCal()) {
      return this;
    }
    return null;
  }

  public long estimateIndexed(OFromClause target, OCommandContext context) {
    return left.estimateIndexedFunction(target, context, operator, right.execute((OResult)null, context));
  }

  public Iterable<OIdentifiable> executeIndexedFunction(OFromClause target, OCommandContext context) {
    return left.executeIndexedFunction(target, context, operator, right.execute((OResult)null, context));
  }

  /**
   * tests if current expression involves an indexed funciton AND that function can also be executed without using the index
   * @param target the query target
   * @param context the execution context
   * @return true if current expression involves an indexed function AND that function can be used on this target, false otherwise
   */
  public boolean canExecuteIndexedFunctionWithoutIndex(OFromClause target, OCommandContext context){
    return left.canExecuteIndexedFunctionWithoutIndex(target, context, operator, right.execute((OResult)null, context));
  }

  /**
   * tests if current expression involves an indexed function AND that function can be used on this target
   * @param target the query target
   * @param context the execution context
   * @return true if current expression involves an indexed function AND that function can be used on this target, false otherwise
   */
  public boolean allowsIndexedFunctionExecutionOnTarget(OFromClause target, OCommandContext context){
    return left.allowsIndexedFunctionExecutionOnTarget(target, context, operator, right.execute((OResult)null, context));
  }

  /**
   * tests if current expression involves an indexed function AND the function has also to be executed after the index search.
   * In some cases, the index search is accurate, so this condition can be excluded from further evaluation. In other cases
   * the result from the index is a superset of the expected result, so the function has to be executed anyway for further filtering
   * @param target the query target
   * @param context the execution context
   * @return true if current expression involves an indexed function AND the function has also to be executed after the index search.
   */
  public boolean executeIndexedFunctionAfterIndexSearch(OFromClause target, OCommandContext context){
    return left.executeIndexedFunctionAfterIndexSearch(target, context, operator, right.execute((OResult)null, context));
  }


  public List<OBinaryCondition> getIndexedFunctionConditions(OClass iSchemaClass, ODatabaseDocumentInternal database) {
    if (left.isIndexedFunctionCal()) {
      return Collections.singletonList(this);
    }
    return null;
  }

  @Override public boolean needsAliases(Set<String> aliases) {
    if(left.needsAliases(aliases)){
      return true;
    }
    if(right.needsAliases(aliases)){
      return true;
    }
    return false;
  }

  @Override public OBinaryCondition copy() {
    OBinaryCondition result = new OBinaryCondition(-1);
    result.left = left.copy();
    result.operator = (OBinaryCompareOperator) operator.copy();
    result.right = right.copy();
    return result;
  }

  @Override public void extractSubQueries(SubQueryCollector collector) {
    left.extractSubQueries(collector);
    right.extractSubQueries(collector);
  }

  @Override public boolean refersToParent() {
    return left.refersToParent() || right.refersToParent();
  }

  public OExpression getLeft() {
    return left;
  }

  public OBinaryCompareOperator getOperator() {
    return operator;
  }

  public OExpression getRight() {
    return right;
  }

  public void setLeft(OExpression left) {
    this.left = left;
  }

  public void setOperator(OBinaryCompareOperator operator) {
    this.operator = operator;
  }

  public void setRight(OExpression right) {
    this.right = right;
  }

  @Override public boolean equals(Object o) {
    if (this == o)
      return true;
    if (o == null || getClass() != o.getClass())
      return false;

    OBinaryCondition that = (OBinaryCondition) o;

    if (left != null ? !left.equals(that.left) : that.left != null)
      return false;
    if (operator != null ? !operator.equals(that.operator) : that.operator != null)
      return false;
    if (right != null ? !right.equals(that.right) : that.right != null)
      return false;

    return true;
  }

  @Override public int hashCode() {
    int result = left != null ? left.hashCode() : 0;
    result = 31 * result + (operator != null ? operator.hashCode() : 0);
    result = 31 * result + (right != null ? right.hashCode() : 0);
    return result;
  }
}
/* JavaCC - OriginalChecksum=99ed1dd2812eb730de8e1931b1764da5 (do not edit this line) */
