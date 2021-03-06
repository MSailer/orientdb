/* Generated By:JJTree: Do not edit this line. OProjectionItem.java Version 4.3 */
/* JavaCCOptions:MULTI=true,NODE_USES_PARSER=false,VISITOR=true,TRACK_TOKENS=true,NODE_PREFIX=O,NODE_EXTENDS=,NODE_FACTORY=,SUPPORT_CLASS_VISIBILITY_PUBLIC=true */
package com.orientechnologies.orient.core.sql.parser;

import com.orientechnologies.orient.core.command.OCommandContext;
import com.orientechnologies.orient.core.db.record.OIdentifiable;
import com.orientechnologies.orient.core.db.record.ridbag.ORidBag;
import com.orientechnologies.orient.core.exception.OCommandExecutionException;
import com.orientechnologies.orient.core.id.ORID;
import com.orientechnologies.orient.core.record.impl.OEdgeToVertexIterable;
import com.orientechnologies.orient.core.record.impl.OEdgeToVertexIterator;
import com.orientechnologies.orient.core.sql.executor.AggregationContext;
import com.orientechnologies.orient.core.sql.executor.OResult;
import com.orientechnologies.orient.core.sql.executor.OResultInternal;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class OProjectionItem extends SimpleNode {

  protected boolean all = false;

  protected OIdentifier alias;

  protected OExpression expression;

  protected Boolean aggregate;

  protected ONestedProjection nestedProjection;

  public OProjectionItem(OExpression expression, OIdentifier alias, ONestedProjection nestedProjection) {
    super(-1);
    this.expression = expression;
    this.alias = alias;
    this.nestedProjection = nestedProjection;
  }

  public OProjectionItem(int id) {
    super(id);
  }

  public OProjectionItem(OrientSql p, int id) {
    super(p, id);
  }

  /**
   * Accept the visitor.
   **/
  public Object jjtAccept(OrientSqlVisitor visitor, Object data) {
    return visitor.visit(this, data);
  }

  public boolean isAll() {
    if (all) {
      return true;
    }
    if (expression != null && "*".equals(expression.toString())) {
      return true;
    }
    return false;
  }

  public void setAll(boolean all) {
    this.all = all;
  }

  public OIdentifier getAlias() {
    return alias;
  }

  public void setAlias(OIdentifier alias) {
    this.alias = alias;
  }

  public OExpression getExpression() {
    return expression;
  }

  public void setExpression(OExpression expression) {
    this.expression = expression;
  }

  public void toString(Map<Object, Object> params, StringBuilder builder) {
    if (all) {
      builder.append("*");
    } else {
      if (expression != null) {
        expression.toString(params, builder);
      }
      if (nestedProjection != null) {
        builder.append(" ");
        nestedProjection.toString(params, builder);
      }
      if (alias != null) {

        builder.append(" AS ");
        alias.toString(params, builder);
      }
    }
  }

  public Object execute(OIdentifiable iCurrentRecord, OCommandContext ctx) {
    Object result;
    if (all) {
      result = iCurrentRecord;
    } else {
      result = expression.execute(iCurrentRecord, ctx);
    }
    if (nestedProjection != null) {
      result = nestedProjection.apply(expression, result, ctx);
    }
    return convert(result);
  }

  public Object convert(Object value) {
    if (value instanceof ORidBag) {
      List result = new ArrayList();
      ((ORidBag) value).forEach(x -> result.add(x));
      return result;
    }
    if (value instanceof OEdgeToVertexIterable) {
      value = ((OEdgeToVertexIterable) value).iterator();
    }
    if (value instanceof OEdgeToVertexIterator) {
      List<ORID> result = new ArrayList<>();
      while (((OEdgeToVertexIterator) value).hasNext()) {
        result.add(((OEdgeToVertexIterator) value).next().getIdentity());
      }
      return result;
    }
    return value;
  }

  public Object execute(OResult iCurrentRecord, OCommandContext ctx) {
    Object result;
    if (all) {
      result = iCurrentRecord;
    } else {
      result = expression.execute(iCurrentRecord, ctx);
    }
    if (nestedProjection != null) {
      result = nestedProjection.apply(expression, result, ctx);
    }
    return convert(result);
  }

  /**
   * returns the final alias for this projection item (the explicit alias, if defined, or the default alias)
   *
   * @return the final alias for this projection item
   */
  public String getProjectionAliasAsString() {
    return getProjectionAlias().getStringValue();
  }

  public OIdentifier getProjectionAlias() {
    if (alias != null) {
      return alias;
    }
    OIdentifier result;
    if (all) {
      result = new OIdentifier("*");
    } else {
      result = expression.getDefaultAlias();
    }
    return result;
  }

  public boolean isExpand() {
    return expression.isExpand();
  }

  public OProjectionItem getExpandContent() {
    OProjectionItem result = new OProjectionItem(-1);
    result.setExpression(expression.getExpandContent());
    return result;
  }

  public boolean isAggregate() {
    if (aggregate != null) {
      return aggregate;
    }
    if (all) {
      aggregate = false;
      return false;
    }
    if (expression.isAggregate()) {
      aggregate = true;
      return true;
    }
    aggregate = false;
    return false;
  }

  /**
   * INTERNAL USE ONLY this has to be invoked ONLY if the item is aggregate!!!
   *
   * @param aggregateSplit
   */
  public OProjectionItem splitForAggregation(AggregateProjectionSplit aggregateSplit, OCommandContext ctx) {
    if (isAggregate()) {
      OProjectionItem result = new OProjectionItem(-1);
      result.alias = getProjectionAlias();
      result.expression = expression.splitForAggregation(aggregateSplit, ctx);
      result.nestedProjection = nestedProjection;
      return result;
    } else {
      return this;
    }
  }

  public AggregationContext getAggregationContext(OCommandContext ctx) {
    if (expression == null) {
      throw new OCommandExecutionException("Cannot aggregate on this projection: " + toString());
    }
    return expression.getAggregationContext(ctx);
  }

  public OProjectionItem copy() {
    OProjectionItem result = new OProjectionItem(-1);
    result.all = all;
    result.alias = alias == null ? null : alias.copy();
    result.expression = expression == null ? null : expression.copy();
    result.nestedProjection = nestedProjection == null ? null : nestedProjection.copy();
    result.aggregate = aggregate;
    return result;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o)
      return true;
    if (o == null || getClass() != o.getClass())
      return false;

    OProjectionItem that = (OProjectionItem) o;

    if (all != that.all)
      return false;
    if (alias != null ? !alias.equals(that.alias) : that.alias != null)
      return false;
    if (expression != null ? !expression.equals(that.expression) : that.expression != null)
      return false;
    if (nestedProjection != null ? !nestedProjection.equals(that.nestedProjection) : that.nestedProjection != null)
      return false;
    if (aggregate != null ? !aggregate.equals(that.aggregate) : that.aggregate != null)
      return false;

    return true;
  }

  @Override
  public int hashCode() {
    int result = (all ? 1 : 0);
    result = 31 * result + (alias != null ? alias.hashCode() : 0);
    result = 31 * result + (expression != null ? expression.hashCode() : 0);
    result = 31 * result + (nestedProjection != null ? nestedProjection.hashCode() : 0);
    result = 31 * result + (aggregate != null ? aggregate.hashCode() : 0);
    return result;
  }

  public void extractSubQueries(SubQueryCollector collector) {
    if (expression != null) {
      expression.extractSubQueries(collector);
    }
  }

  public boolean refersToParent() {
    if (expression != null) {
      return expression.refersToParent();
    }
    return false;
  }

  public OResult serialize() {
    OResultInternal result = new OResultInternal();
    result.setProperty("all", all);
    if (alias != null) {
      result.setProperty("alias", alias.serialize());
    }
    if (expression != null) {
      result.setProperty("expression", expression.serialize());
    }
    result.setProperty("aggregate", aggregate);
    if (nestedProjection != null) {
      result.setProperty("nestedProjection", nestedProjection.serialize());
    }
    return result;
  }

  public void deserialize(OResult fromResult) {
    all = fromResult.getProperty("all");
    if (fromResult.getProperty("alias") != null) {
      alias = OIdentifier.deserialize(fromResult.getProperty("alias"));
    }
    if (fromResult.getProperty("expression") != null) {
      expression = new OExpression(-1);
      expression.deserialize(fromResult.getProperty("expression"));
    }
    aggregate = fromResult.getProperty("aggregate");
    if (fromResult.getProperty("nestedProjection") != null) {
      nestedProjection = new ONestedProjection(-1);
      nestedProjection.deserialize(fromResult.getProperty("nestedProjection"));
    }
  }

  public void setNestedProjection(ONestedProjection nestedProjection) {
    this.nestedProjection = nestedProjection;
  }

  public boolean isCacheable() {
    if (expression != null) {
      return expression.isCacheable();
    }
    return true;
  }
}
/* JavaCC - OriginalChecksum=6d6010734c7434a6f516e2eac308e9ce (do not edit this line) */
