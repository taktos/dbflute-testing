/*
 * Copyright 2015 Toshio Takiguchi.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND,
 * either express or implied. See the License for the specific language
 * governing permissions and limitations under the License.
 */
package org.dbflute.testing;

import static org.dbflute.testing.cb.IsColumnExpressed.expressed;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.mockito.Matchers.argThat;

import org.dbflute.testing.cb.ComparisonOperator;
import org.dbflute.testing.cb.HasCondition;
import org.dbflute.testing.cb.IsColumnExpressed;
import org.dbflute.testing.cb.IsColumnIsNotNull;
import org.dbflute.testing.cb.IsColumnIsNull;
import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.mockito.ArgumentMatcher;
import org.mockito.Matchers;
import org.seasar.dbflute.cbean.ConditionBean;
import org.seasar.dbflute.cbean.cvalue.ConditionValue;

/**
 * Static factory of custom matchers.
 *
 * @author taktos
 *
 */
public final class DBFluteMatchers {

	private DBFluteMatchers() {}

	/**
	 * Allows creating custom argument matcher that evaluates ConditionBean.
	 * @param cbclass class of ConditionBean implementation
	 * @param matcher the matcher to apply to ConditionBean
	 * @return <code>null</code>
	 * @see Matchers#argThat(Matcher)
	 */
	public static <T extends ConditionBean> T argCB(Class<T> cbclass, final Matcher<?> matcher) {
		return argThat(new ArgumentMatcher<T>() {
			@Override
			public boolean matches(Object argument) {
				return matcher.matches(argument);
			}
			@Override
			public void describeTo(Description description) {
				description.appendDescriptionOf(matcher);
			}
			@Override
			public void describeMismatch(Object item, Description description) {
				matcher.describeMismatch(item, description);
			}
		});
	}

	/**
	 * Creates a matcher that gets a {@link ConditionValue} of specified column
	 * and pass it to subsequent matcher.
	 * @param column the name of column which evaluates
	 * @param matcher the matcher that evaluates {@link ConditionValue}
	 */
	public static <T extends ConditionBean> HasCondition<T> hasCondition(String column, Matcher<?> matcher) {
		return new HasCondition<T>(column, matcher);
	}

	/**
	 * Creates a matcher that matches when the examined column has
	 * EQUAL condition with value matched with the specified {@code matcher}.
	 * <p>Example:
	 * <pre>{@code
	 * cb.query.setMemberName_Equal("John Doe");
	 * assertThat(cb, hasCondition("memberName", equal(startsWith("J"))));
	 * }</pre>
	 *
	 * @param matcher a matcher that evaluates the condition value
	 */
	public static IsColumnExpressed equal(Matcher<?> matcher) {
		return expressed(ComparisonOperator.EQUAL, matcher);
	}

	/**
	 * A shortcut to {@code equal(equalTo(value))}.
	 * <p>Example:
	 * <pre>{@code
	 * cb.query.setMemberId_Equal(10);
	 * assertThat(cb, hasCondition("memberId", equal(10)));
	 * }</pre>
	 * @param value the value of condition
	 */
	public static IsColumnExpressed equal(Object value) {
		return equal(equalTo(value));
	}

	/**
	 * Creates a matcher that matches when the examined column has
	 * GREATER_THAN condition with value matched with the specified {@code matcher}.
	 * <p>Example:
	 * <pre>{@code
	 * cb.query.setMemberName_GreaterThan("John Doe");
	 * assertThat(cb, hasCondition("memberName", greaterThan(startsWith("J"))));
	 * }</pre>
	 *
	 * @param matcher a matcher that evaluates the condition value
	 */
	public static IsColumnExpressed greaterThan(Matcher<?> matcher) {
		return expressed(ComparisonOperator.GREATER_THAN, matcher);
	}

	/**
	 * A shortcut to {@code greaterThan(equalTo(value))}.
	 * <p>Example:
	 * <pre>{@code
	 * cb.query.setMemberId_GreaterThan(10);
	 * assertThat(cb, hasCondition("memberId", greaterThan(10)));
	 * }</pre>
	 * @param value the value of condition
	 */
	public static IsColumnExpressed greaterThan(Object value) {
		return greaterThan(equalTo(value));
	}

	/**
	 * Creates a matcher that matches when the examined column has
	 * GREATER_EQUAL condition with value matched with the specified {@code matcher}.
	 * <p>Example:
	 * <pre>{@code
	 * cb.query.setMemberName_GreaterEqual("John Doe");
	 * assertThat(cb, hasCondition("memberName", greaterEqual(startsWith("J"))));
	 * }</pre>
	 *
	 * @param matcher a matcher that evaluates the condition value
	 */
	public static IsColumnExpressed greaterEqual(Matcher<?> matcher) {
		return expressed(ComparisonOperator.GREATER_EQUAL, matcher);
	}

	/**
	 * A shortcut to {@code greaterEqual(equalTo(value))}.
	 * <p>Example:
	 * <pre>{@code
	 * cb.query.setMemberId_GreaterEqual(10);
	 * assertThat(cb, hasCondition("memberId", greaterEqual(10)));
	 * }</pre>
	 * @param value the value of condition
	 */
	public static IsColumnExpressed greaterEqual(Object value) {
		return greaterEqual(equalTo(value));
	}

	/**
	 * Creates a matcher that matches when the examined column has
	 * GREATER_THAN condition with value matched with the specified {@code matcher}.
	 * <p>Example:
	 * <pre>{@code
	 * cb.query.setMemberName_LessThan("John Doe");
	 * assertThat(cb, hasCondition("memberName", lessThan(startsWith("J"))));
	 * }</pre>
	 *
	 * @param matcher a matcher that evaluates the condition value
	 */
	public static IsColumnExpressed lessThan(Matcher<?> matcher) {
		return expressed(ComparisonOperator.LESS_THAN, matcher);
	}

	/**
	 * A shortcut to {@code lessThan(equalTo(value))}.
	 * <p>Example:
	 * <pre>{@code
	 * cb.query.setMemberId_LessThan(10);
	 * assertThat(cb, hasCondition("memberId", lessThan(10)));
	 * }</pre>
	 * @param value the value of condition
	 */
	public static IsColumnExpressed lessThan(Object value) {
		return lessThan(equalTo(value));
	}

	/**
	 * Creates a matcher that matches when the examined column has
	 * LESS_EQUAL condition with value matched with the specified {@code matcher}.
	 * <p>Example:
	 * <pre>{@code
	 * cb.query.setMemberName_LessEqual("John Doe");
	 * assertThat(cb, hasCondition("memberName", lessEqual(startsWith("J"))));
	 * }</pre>
	 *
	 * @param matcher a matcher that evaluates the condition value
	 */
	public static IsColumnExpressed lessEqual(Matcher<?> matcher) {
		return expressed(ComparisonOperator.LESS_EQUAL, matcher);
	}

	/**
	 * A shortcut to {@code lessEqual(equalTo(value))}.
	 * <p>Example:
	 * <pre>{@code
	 * cb.query.setMemberId_LessEqual(10);
	 * assertThat(cb, hasCondition("memberId", lessEqual(10)));
	 * }</pre>
	 * @param value the value of condition
	 */
	public static IsColumnExpressed lessEqual(Object value) {
		return lessEqual(equalTo(value));
	}

	/**
	 * Creates a matcher that matches when the examined column has
	 * IS_NULL condition.
	 * <p>Example:
	 * <pre>{@code
	 * cb.query.setMemberName_IsNull();
	 * assertThat(cb, hasCondition("memberName", isNull()));
	 * }</pre>
	 *
	 * @param matcher a matcher that evaluates the condition value
	 */
	public static IsColumnIsNull isNull() {
		return new IsColumnIsNull();
	}

	/**
	 * Creates a matcher that matches when the examined column has
	 * IS_NOT_NULL condition.
	 * <p>Example:
	 * <pre>{@code
	 * cb.query.setMemberName_IsNotNull();
	 * assertThat(cb, hasCondition("memberName", isNotNull()));
	 * }</pre>
	 *
	 * @param matcher a matcher that evaluates the condition value
	 */
	public static IsColumnIsNotNull isNotNull() {
		return new IsColumnIsNotNull();
	}

}
