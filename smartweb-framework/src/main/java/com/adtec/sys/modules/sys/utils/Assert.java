package com.adtec.sys.modules.sys.utils;

import java.util.Collection;
import java.util.Iterator;
import java.util.Map;
import java.util.regex.Pattern;

import org.apache.commons.lang3.ArrayUtils;

/**
 * 断言工具类<br>
 * 断言某些对象或值是否符合规定, 否则抛出异常.
 *
 * @author lijb
 * @since 2021-02-22
 */
public abstract class Assert {

    private static final String DEFAULT_EXCLUSIVE_BETWEEN_EX_MESSAGE = "The value %s is not in the specified exclusive range of %s to %s";
    private static final String DEFAULT_INCLUSIVE_BETWEEN_EX_MESSAGE = "The value %s is not in the specified inclusive range of %s to %s";
    private static final String DEFAULT_MATCHES_PATTERN_EX = "The string %s does not match the pattern %s";
    private static final String DEFAULT_IS_NULL_EX_MESSAGE = "The validated object is null";
    private static final String DEFAULT_IS_TRUE_EX_MESSAGE = "The validated expression is false";
    private static final String DEFAULT_NO_NULL_ELEMENTS_ARRAY_EX_MESSAGE = "The validated array contains null element at index: %d";
    private static final String DEFAULT_NO_NULL_ELEMENTS_COLLECTION_EX_MESSAGE = "The validated collection contains null element at index: %d";
    private static final String DEFAULT_NOT_BLANK_EX_MESSAGE = "The validated character sequence is blank";
    private static final String DEFAULT_NOT_EMPTY_ARRAY_EX_MESSAGE = "The validated array is empty";
    private static final String DEFAULT_NOT_EMPTY_CHAR_SEQUENCE_EX_MESSAGE = "The validated character sequence is empty";
    private static final String DEFAULT_NOT_EMPTY_COLLECTION_EX_MESSAGE = "The validated collection is empty";
    private static final String DEFAULT_NOT_EMPTY_MAP_EX_MESSAGE = "The validated map is empty";
    private static final String DEFAULT_VALID_INDEX_ARRAY_EX_MESSAGE = "The validated array index is invalid: %d";
    private static final String DEFAULT_VALID_INDEX_CHAR_SEQUENCE_EX_MESSAGE = "The validated character sequence index is invalid: %d";
    private static final String DEFAULT_VALID_INDEX_COLLECTION_EX_MESSAGE = "The validated collection index is invalid: %d";
    private static final String DEFAULT_VALID_STATE_EX_MESSAGE = "The validated state is false";
    private static final String DEFAULT_IS_ASSIGNABLE_EX_MESSAGE = "Cannot assign a %s to a %s";
    private static final String DEFAULT_IS_INSTANCE_OF_EX_MESSAGE = "Expected type: %s, actual: %s";

    // isTrue
    // ---------------------------------------------------------------------------------

    /**
     * <p>
     * 断言是否为真, 否则抛出异常.
     * </p>
     *
     * <pre>
     * Assert.isTrue(i &gt;= min &amp;&amp; i &lt;= max, "The value must be between &#37;d and &#37;d", min, max);
     * Assert.isTrue(myObject.isOk(), "The object is not okay");
     * </pre>
     *
     * @param expression 要验证的布尔表达式
     * @param message    {@link String#format(String, Object...)} 异常消息
     * @param values     格式化异常消息的可选值
     * @throws IllegalArgumentException 如果表达式为 {@code false}
     */
    public static void isTrue(final boolean expression, final String message, final Object... values) {
        if (!expression) {
            throw new IllegalArgumentException(String.format(message, values));
        }
    }

    /**
     * <p>
     * 断言是否为真, 否则抛出异常.
     * </p>
     *
     * <pre>
     * Assert.isTrue(i &gt; 0);
     * Assert.isTrue(myObject.isOk());
     * </pre>
     *
     * @param expression 要验证的布尔表达式
     * @throws IllegalArgumentException 如果表达式为 {@code false}
     */
    public static void isTrue(final boolean expression) {
        if (!expression) {
            throw new IllegalArgumentException(DEFAULT_IS_TRUE_EX_MESSAGE);
        }
    }

    // notNull
    // ---------------------------------------------------------------------------------

    /**
     * <p>
     * 断言对象不为 {@code null}; 否则抛出异常.
     *
     * <pre>
     * Assert.notNull(myObject, "The object must not be null");
     * </pre>
     *
     * @param <T>    对象类型
     * @param object 要验证的对象
     * @return 验证过的对象 (不为 {@code null} 用于链式编程)
     * @throws NullPointerException 如果对象为 {@code null}
     */
    public static <T> T notNull(final T object) {
        return notNull(object, DEFAULT_IS_NULL_EX_MESSAGE);
    }

    /**
     * <p>
     * 断言对象不为 {@code null}; 否则抛出带有给定消息的异常.
     *
     * <pre>
     * Assert.notNull(myObject, "The object must not be null");
     * </pre>
     *
     * @param <T>     对象类型
     * @param object  要验证的对象
     * @param message {@link String#format(String, Object...)} 异常消息
     * @param values  格式化异常消息的可选值
     * @return 验证过的对象 (不为 {@code null} 用于链式编程)
     * @throws NullPointerException 如果对象为 {@code null}
     */
    public static <T> T notNull(final T object, final String message, final Object... values) {
        if (object == null) {
            throw new NullPointerException(String.format(message, values));
        }
        return object;
    }

    // notEmpty array
    // ---------------------------------------------------------------------------------

    /**
     * <p>
     * 验证给定数组不为 {@code null} 且长度不为 0; 否则抛出带有给定消息的异常.
     *
     * <pre>
     * Assert.notEmpty(myArray, "The array must not be empty");
     * </pre>
     *
     * @param <T>     数组类型
     * @param array   要验证的数组
     * @param message {@link String#format(String, Object...)} 异常消息
     * @param values  格式化异常消息的可选值
     * @return 验证过的数组 (不为 {@code null} 用于链式编程)
     * @throws NullPointerException     如果数组为 {@code null}
     * @throws IllegalArgumentException 如果数组长度为 0
     */
    public static <T> T[] notEmpty(final T[] array, final String message, final Object... values) {
        if (array == null) {
            throw new NullPointerException(String.format(message, values));
        }
        if (array.length == 0) {
            throw new IllegalArgumentException(String.format(message, values));
        }
        return array;
    }

    /**
     * <p>
     * 验证给定数组不为 {@code null} 且长度不为 0; 否则抛出异常.
     *
     * <pre>
     * Assert.notEmpty(myArray);
     * </pre>
     *
     * @param <T>   数组类型
     * @param array 要验证的数组
     * @return 验证过的数组 (不为 {@code null} 用于链式编程)
     * @throws NullPointerException     如果数组为 {@code null}
     * @throws IllegalArgumentException 如果数组长度为 0
     */
    public static <T> T[] notEmpty(final T[] array) {
        return notEmpty(array, DEFAULT_NOT_EMPTY_ARRAY_EX_MESSAGE);
    }

    // notEmpty collection
    // ---------------------------------------------------------------------------------

    /**
     * <p>
     * 验证给定集合不为 {@code null} 且有元素; 否则抛出带有给定消息的异常.
     *
     * <pre>
     * Assert.notEmpty(myCollection, "The collection must not be empty");
     * </pre>
     *
     * @param <T>        集合类型
     * @param collection 要验证的集合
     * @param message    {@link String#format(String, Object...)} 异常消息
     * @param values     格式化异常消息的可选值
     * @return 验证过的集合 (不为 {@code null} 用于链式编程)
     * @throws NullPointerException     如果集合为 {@code null}
     * @throws IllegalArgumentException 如果集合中没有元素
     */
    public static <T extends Collection<?>> T notEmpty(final T collection, final String message,
                                                       final Object... values) {
        if (collection == null) {
            throw new NullPointerException(String.format(message, values));
        }
        if (collection.isEmpty()) {
            throw new IllegalArgumentException(String.format(message, values));
        }
        return collection;
    }

    /**
     * <p>
     * 验证给定集合不为 {@code null} 且有元素; 否则抛出异常.
     *
     * <pre>
     * Assert.notEmpty(myCollection);
     * </pre>
     *
     * @param <T>        集合类型
     * @param collection 要验证的集合
     * @return 验证过的集合 (不为 {@code null} 用于链式编程)
     * @throws NullPointerException     如果集合为 {@code null}
     * @throws IllegalArgumentException 如果集合中没有元素
     */
    public static <T extends Collection<?>> T notEmpty(final T collection) {
        return notEmpty(collection, DEFAULT_NOT_EMPTY_COLLECTION_EX_MESSAGE);
    }

    // notEmpty map
    // ---------------------------------------------------------------------------------

    /**
     * <p>
     * 验证给定 Map不为 {@code null} 且有元素; 否则抛出带有给定消息的异常.
     *
     * <pre>
     * Assert.notEmpty(myMap, "The map must not be empty");
     * </pre>
     *
     * @param <T>     map 类型
     * @param map     要验证的 map
     * @param message {@link String#format(String, Object...)} 异常消息
     * @param values  格式化异常消息的可选值
     * @return 验证过的map (不为 {@code null} 用于链式编程)
     * @throws NullPointerException     如果map为 {@code null}
     * @throws IllegalArgumentException 如果map中没有元素
     */
    public static <T extends Map<?, ?>> T notEmpty(final T map, final String message, final Object... values) {
        if (map == null) {
            throw new NullPointerException(String.format(message, values));
        }
        if (map.isEmpty()) {
            throw new IllegalArgumentException(String.format(message, values));
        }
        return map;
    }

    /**
     * <p>
     * 验证给定 Map不为 {@code null} 且有元素; 否则抛出异常.
     *
     * <pre>
     * Assert.notEmpty(myMap);
     * </pre>
     *
     * @param <T> map 类型
     * @param map 要验证的 map
     * @return 验证过的map (不为 {@code null} 用于链式编程)
     * @throws NullPointerException     如果map为 {@code null}
     * @throws IllegalArgumentException 如果map中没有元素
     */
    public static <T extends Map<?, ?>> T notEmpty(final T map) {
        return notEmpty(map, DEFAULT_NOT_EMPTY_MAP_EX_MESSAGE);
    }

    // notEmpty string
    // ---------------------------------------------------------------------------------

    /**
     * <p>
     * 验证给定字符序列不为 {@code null} 且长度不为 0; 否则抛出带有给定消息的异常.
     *
     * <pre>
     * Assert.notEmpty(myString, "The string must not be empty");
     * </pre>
     *
     * @param <T>     字符序列类型
     * @param chars   要验证的字符序列
     * @param message {@link String#format(String, Object...)} 异常消息
     * @param values  格式化异常消息的可选值
     * @return 验证过的字符序列 (不为 {@code null} 用于链式编程)
     * @throws NullPointerException     如果字符序列为 {@code null}
     * @throws IllegalArgumentException 如果字符序列中没有元素
     */
    public static <T extends CharSequence> T notEmpty(final T chars, final String message, final Object... values) {
        if (chars == null) {
            throw new NullPointerException(String.format(message, values));
        }
        if (chars.length() == 0) {
            throw new IllegalArgumentException(String.format(message, values));
        }
        return chars;
    }

    /**
     * <p>
     * 验证给定字符序列不为 {@code null} 且长度不为 0; 否则抛出异常.
     *
     * <pre>
     * Assert.notEmpty(myString);
     * </pre>
     *
     * @param <T>   字符序列类型
     * @param chars 要验证的字符序列
     * @return 验证过的字符序列 (不为 {@code null} 用于链式编程)
     * @throws NullPointerException     如果字符序列为 {@code null}
     * @throws IllegalArgumentException 如果字符序列中没有元素
     */
    public static <T extends CharSequence> T notEmpty(final T chars) {
        return notEmpty(chars, DEFAULT_NOT_EMPTY_CHAR_SEQUENCE_EX_MESSAGE);
    }

    // notBlank string
    // ---------------------------------------------------------------------------------

    /**
     * <p>
     * 验证给定字符序列不为 {@code null} ,长度不为 0 且不全是空格; 否则抛出带有给定消息的异常.
     *
     * <pre>
     * Assert.notBlank(myString, "The string must not be blank");
     * </pre>
     *
     * @param <T>     字符序列类型
     * @param chars   要验证的字符序列
     * @param message {@link String#format(String, Object...)} 异常消息
     * @param values  格式化异常消息的可选值
     * @return 验证过的字符序列 (不为 {@code null} 用于链式编程)
     * @throws NullPointerException     如果字符序列为 {@code null}
     * @throws IllegalArgumentException 如果字符序列中没有元素
     */
    public static <T extends CharSequence> T notBlank(final T chars, final String message, final Object... values) {
        if (chars == null) {
            throw new NullPointerException(String.format(message, values));
        }
        if (StringUtil.isBlank(chars)) {
            throw new IllegalArgumentException(String.format(message, values));
        }
        return chars;
    }

    /**
     * <p>
     * 验证给定字符序列不为 {@code null} ,长度不为 0 且不全是空格; 否则抛出异常.
     *
     * <pre>
     * Assert.notBlank(myString);
     * </pre>
     *
     * @param <T>   字符序列类型
     * @param chars 要验证的字符序列
     * @return 验证过的字符序列 (不为 {@code null} 用于链式编程)
     * @throws NullPointerException     如果字符序列为 {@code null}
     * @throws IllegalArgumentException 如果字符序列中没有元素
     */
    public static <T extends CharSequence> T notBlank(final T chars) {
        return notBlank(chars, DEFAULT_NOT_BLANK_EX_MESSAGE);
    }

    // noNullElements array
    // ---------------------------------------------------------------------------------

    /**
     * <p>
     * 验证给定数组不为 {@code null} ,长度不为 0 且不包含 {@code null} 元素; 否则抛出带有给定消息的异常.
     *
     * <pre>
     * Assert.noNullElements(myArray, "The array contain null at position %d");
     * </pre>
     *
     * <p>
     * 如果数组中有一个{@code null}元素，则迭代的无效的元素的索引将被添加到{@code values}参数中.
     * </p>
     *
     * @param <T>     数组类型
     * @param array   要验证的数组
     * @param message {@link String#format(String, Object...)} 异常消息
     * @param values  格式化异常消息的可选值
     * @return 验证过的数组 (不为 {@code null} 用于链式编程)
     * @throws NullPointerException     如果数组为 {@code null}
     * @throws IllegalArgumentException 如果数组中包含 {@code null} 元素
     */
    public static <T> T[] noNullElements(final T[] array, final String message, final Object... values) {
        notNull(array);
        for (int i = 0; i < array.length; i++) {
            if (array[i] == null) {
                final Object[] values2 = ArrayUtils.add(values, i);
                throw new IllegalArgumentException(String.format(message, values2));
            }
        }
        return array;
    }

    /**
     * <p>
     * 验证给定数组不为 {@code null} ,长度不为 0 且不包含 {@code null} 元素; 否则抛出异常.
     * </p>
     *
     * <pre>
     * Assert.noNullElements(myArray);
     * </pre>
     *
     * <p>
     * 如果数组中有一个{@code null}元素，则迭代的无效的元素的索引将被添加异常消息中.
     * </p>
     *
     * @param <T>   数组类型
     * @param array 要验证的数组
     * @return 验证过的数组 (不为 {@code null} 用于链式编程)
     * @throws NullPointerException     如果数组为 {@code null}
     * @throws IllegalArgumentException 如果数组中包含 {@code null} 元素
     */
    public static <T> T[] noNullElements(final T[] array) {
        return noNullElements(array, DEFAULT_NO_NULL_ELEMENTS_ARRAY_EX_MESSAGE);
    }

    // noNullElements iterable
    // ---------------------------------------------------------------------------------

    /**
     * <p>
     * 验证给定可迭代对象不为 {@code null} ,包含元素且不包含 {@code null} 元素; 否则抛出带有给定消息的异常.
     *
     * <pre>
     * Assert.noNullElements(myCollection, "The collection contains null at position %d");
     * </pre>
     *
     * <p>
     * 如果可迭代对象中有一个{@code null}元素，则迭代的无效的元素的索引将被添加到{@code values}参数中.
     * </p>
     *
     * @param <T>      可迭代对象类型
     * @param iterable 要验证的可迭代对象
     * @param message  {@link String#format(String, Object...)} 异常消息
     * @param values   格式化异常消息的可选值
     * @return 验证过的可迭代对象 (不为 {@code null} 用于链式编程)
     * @throws NullPointerException     如果可迭代对象为 {@code null}
     * @throws IllegalArgumentException 如果可迭代对象中包含 {@code null} 元素
     */
    public static <T extends Iterable<?>> T noNullElements(final T iterable, final String message,
                                                           final Object... values) {
        notNull(iterable);
        int i = 0;
        for (final Iterator<?> it = iterable.iterator(); it.hasNext(); i++) {
            if (it.next() == null) {
                final Object[] values2 = ArrayUtils.addAll(values, i);
                throw new IllegalArgumentException(String.format(message, values2));
            }
        }
        return iterable;
    }

    /**
     * <p>
     * 验证给定可迭代对象不为 {@code null} ,包含元素且不包含 {@code null} 元素; 否则抛出异常.
     *
     * <pre>
     * Assert.noNullElements(myCollection);
     * </pre>
     *
     * <p>
     * 如果可迭代对象中有一个{@code null}元素，则迭代的无效的元素的索引将被添加异常消息中.
     * </p>
     *
     * @param <T>      可迭代对象类型
     * @param iterable 要验证的可迭代对象
     * @return 验证过的可迭代对象 (不为 {@code null} 用于链式编程)
     * @throws NullPointerException     如果可迭代对象为 {@code null}
     * @throws IllegalArgumentException 如果可迭代对象中包含 {@code null} 元素
     */
    public static <T extends Iterable<?>> T noNullElements(final T iterable) {
        return noNullElements(iterable, DEFAULT_NO_NULL_ELEMENTS_COLLECTION_EX_MESSAGE);
    }

    // validIndex array
    // ---------------------------------------------------------------------------------

    /**
     * <p>
     * 验证给定索引是否在参数数组范围内; 否则抛出带有给定消息的异常.
     * </p>
     *
     * <pre>
     * Assert.validIndex(myArray, 2, "The array index is invalid: ");
     * </pre>
     *
     * @param <T>     数组类型
     * @param array   要验证的数组
     * @param index   要验证的索引
     * @param message {@link String#format(String, Object...)} 异常消息
     * @param values  格式化异常消息的可选值
     * @return 验证过的数组 (不为 {@code null} 用于链式编程)
     * @throws NullPointerException      如果数组为 {@code null}
     * @throws IndexOutOfBoundsException 如果索引无效
     */
    public static <T> T[] validIndex(final T[] array, final int index, final String message, final Object... values) {
        notNull(array);
        if (index < 0 || index >= array.length) {
            throw new IndexOutOfBoundsException(String.format(message, values));
        }
        return array;
    }

    /**
     * <p>
     * 验证给定索引是否在参数数组范围内; 否则抛出异常.
     * </p>
     *
     * <pre>
     * Assert.validIndex(myArray, 2);
     * </pre>
     *
     * @param <T>   数组类型
     * @param array 要验证的数组
     * @param index 要验证的索引
     * @return 验证过的数组 (不为 {@code null} 用于链式编程)
     * @throws NullPointerException      如果数组为 {@code null}
     * @throws IndexOutOfBoundsException 如果索引无效
     */
    public static <T> T[] validIndex(final T[] array, final int index) {
        return validIndex(array, index, DEFAULT_VALID_INDEX_ARRAY_EX_MESSAGE, index);
    }

    // validIndex collection
    // ---------------------------------------------------------------------------------

    /**
     * <p>
     * 验证给定索引是否在参数集合范围内; 否则抛出带有给定消息的异常.
     * </p>
     *
     * <pre>
     * Assert.validIndex(myCollection, 2, "The collection index is invalid: ");
     * </pre>
     *
     * @param <T>        集合类型
     * @param collection 要验证的集合
     * @param index      要验证的索引
     * @param message    {@link String#format(String, Object...)} 异常消息
     * @param values     格式化异常消息的可选值
     * @return 验证过的集合 (不为 {@code null} 用于链式编程)
     * @throws NullPointerException      如果集合为 {@code null}
     * @throws IndexOutOfBoundsException 如果索引无效
     */
    public static <T extends Collection<?>> T validIndex(final T collection, final int index, final String message,
                                                         final Object... values) {
        notNull(collection);
        if (index < 0 || index >= collection.size()) {
            throw new IndexOutOfBoundsException(String.format(message, values));
        }
        return collection;
    }

    /**
     * <p>
     * 验证给定索引是否在参数集合范围内; 否则抛出异常.
     * </p>
     *
     * <pre>
     * Assert.validIndex(myCollection, 2);
     * </pre>
     *
     * @param <T>        集合类型
     * @param collection 要验证的集合
     * @param index      要验证的索引
     * @return 验证过的集合 (不为 {@code null} 用于链式编程)
     * @throws NullPointerException      如果集合为 {@code null}
     * @throws IndexOutOfBoundsException 如果索引无效
     */
    public static <T extends Collection<?>> T validIndex(final T collection, final int index) {
        return validIndex(collection, index, DEFAULT_VALID_INDEX_COLLECTION_EX_MESSAGE, index);
    }

    // validIndex string
    // ---------------------------------------------------------------------------------

    /**
     * <p>
     * 验证给定索引是否在给定字符序列范围内; 否则抛出带有给定消息的异常.
     * </p>
     *
     * <pre>
     * Assert.validIndex(myStr, 2, "The string index is invalid: ");
     * </pre>
     *
     * @param <T>     字符序列类型
     * @param chars   要验证的字符序列
     * @param index   要验证的索引
     * @param message {@link String#format(String, Object...)} 异常消息
     * @param values  格式化异常消息的可选值
     * @return 验证过的字符序列 (不为 {@code null} 用于链式编程)
     * @throws NullPointerException      如果字符序列为 {@code null}
     * @throws IndexOutOfBoundsException 如果索引无效
     */
    public static <T extends CharSequence> T validIndex(final T chars, final int index, final String message,
                                                        final Object... values) {
        notNull(chars);
        if (index < 0 || index >= chars.length()) {
            throw new IndexOutOfBoundsException(String.format(message, values));
        }
        return chars;
    }

    /**
     * <p>
     * 验证给定索引是否在给定字符序列范围内; 否则抛出异常.
     * </p>
     *
     * <pre>
     * Assert.validIndex(myStr, 2);
     * </pre>
     *
     * @param <T>   字符序列类型
     * @param chars 要验证的字符序列
     * @param index 要验证的索引
     * @return 验证过的字符序列 (不为 {@code null} 用于链式编程)
     * @throws NullPointerException      如果字符序列为 {@code null}
     * @throws IndexOutOfBoundsException 如果索引无效
     */
    public static <T extends CharSequence> T validIndex(final T chars, final int index) {
        return validIndex(chars, index, DEFAULT_VALID_INDEX_CHAR_SEQUENCE_EX_MESSAGE, index);
    }

    // validState
    // ---------------------------------------------------------------------------------

    /**
     * <p>
     * 验证布尔表达式为 {@code true}; 否则抛出带有给定消息的异常.
     * </p>
     *
     * <pre>
     * Assert.validState(field &gt; 0);
     * Assert.validState(this.isOk());
     * </pre>
     *
     * @param expression 要验证的布尔表达式
     * @throws IllegalStateException 如果表达式为 {@code false}
     */
    public static void validState(final boolean expression) {
        if (!expression) {
            throw new IllegalStateException(DEFAULT_VALID_STATE_EX_MESSAGE);
        }
    }

    /**
     * <p>
     * 验证布尔表达式为 {@code true}; 否则抛出异常.
     * </p>
     *
     * <pre>
     * Assert.validState(this.isOk(), "The state is not OK: %s", myObject);
     * </pre>
     *
     * @param expression 要验证的布尔表达式
     * @param message    {@link String#format(String, Object...)} 异常消息
     * @param values     格式化异常消息的可选值
     * @throws IllegalStateException 如果表达式为 {@code false}
     */
    public static void validState(final boolean expression, final String message, final Object... values) {
        if (!expression) {
            throw new IllegalStateException(String.format(message, values));
        }
    }

    // matchesPattern
    // ---------------------------------------------------------------------------------

    /**
     * <p>
     * 验证给定字符序列是否匹配给定的正则表达式; 否则抛出带有给定消息的异常.
     * </p>
     *
     * <pre>
     * Assert.matchesPattern("hi", "[a-z]*");
     * </pre>
     *
     * <p>
     * 该模式的语法与{@link Pattern}类中的语法相同.
     * </p>
     *
     * @param input   要验证的字符序列
     * @param pattern 正则表达式
     * @throws IllegalArgumentException 如果字符序列不匹配给定正则表达式
     */
    public static void matchesPattern(final CharSequence input, final String pattern) {
        // TODO when breaking BC, consider returning input
        if (!Pattern.matches(pattern, input)) {
            throw new IllegalArgumentException(String.format(DEFAULT_MATCHES_PATTERN_EX, input, pattern));
        }
    }

    /**
     * <p>
     * 验证给定字符序列是否匹配给定的正则表达式; 否则抛出异常.
     * </p>
     *
     * <pre>
     * Assert.matchesPattern("hi", "[a-z]*", "%s does not match %s", "hi" "[a-z]*");
     * </pre>
     *
     * <p>
     * 该模式的语法与{@link Pattern}类中的语法相同.
     * </p>
     *
     * @param input   要验证的字符序列
     * @param pattern 正则表达式
     * @param message {@link String#format(String, Object...)} 异常消息
     * @param values  格式化异常消息的可选值
     * @throws IllegalArgumentException 如果字符序列不匹配给定正则表达式
     */
    public static void matchesPattern(final CharSequence input, final String pattern, final String message,
                                      final Object... values) {
        // TODO when breaking BC, consider returning input
        if (!Pattern.matches(pattern, input)) {
            throw new IllegalArgumentException(String.format(message, values));
        }
    }

    // inclusiveBetween
    // ---------------------------------------------------------------------------------

    /**
     * <p>
     * 验证给定对象是否位于两个包含项之间; 否则抛出异常.
     * </p>
     *
     * <pre>
     * Assert.inclusiveBetween(0, 2, 1);
     * </pre>
     *
     * @param <T>   要验证的对象类型
     * @param start 包含起始值
     * @param end   包含最终值
     * @param value 要验证的对象
     * @throws IllegalArgumentException 如果值超出边界(包含)
     */
    public static <T> void inclusiveBetween(final T start, final T end, final Comparable<T> value) {
        // TODO when breaking BC, consider returning value
        if (value.compareTo(start) < 0 || value.compareTo(end) > 0) {
            throw new IllegalArgumentException(String.format(DEFAULT_INCLUSIVE_BETWEEN_EX_MESSAGE, value, start, end));
        }
    }

    /**
     * <p>
     * 验证给定对象是否位于两个包含项之间; 否则抛出带有给定消息的异常.
     * </p>
     *
     * <pre>
     * Assert.inclusiveBetween(0, 2, 1, "Not in boundaries");
     * </pre>
     *
     * @param <T>     要验证的对象类型
     * @param start   包含起始值
     * @param end     包含最终值
     * @param value   要验证的对象
     * @param message {@link String#format(String, Object...)} 异常消息
     * @param values  格式化异常消息的可选值
     * @throws IllegalArgumentException 如果值超出边界(包含)
     */
    public static <T> void inclusiveBetween(final T start, final T end, final Comparable<T> value, final String message,
                                            final Object... values) {
        // TODO when breaking BC, consider returning value
        if (value.compareTo(start) < 0 || value.compareTo(end) > 0) {
            throw new IllegalArgumentException(String.format(message, values));
        }
    }

    /**
     * 验证给定数值是否位于两个包含项之间; 否则抛出异常.
     *
     * <pre>
     * Assert.inclusiveBetween(0, 2, 1);
     * </pre>
     *
     * @param start 包含起始值
     * @param end   包含最终值
     * @param value 要验证的数值
     * @throws IllegalArgumentException 如果值超出边界(包含)
     */
    @SuppressWarnings("boxing")
    public static void inclusiveBetween(long start, long end, long value) {
        // TODO when breaking BC, consider returning value
        if (value < start || value > end) {
            throw new IllegalArgumentException(String.format(DEFAULT_INCLUSIVE_BETWEEN_EX_MESSAGE, value, start, end));
        }
    }

    /**
     * 验证给定数值是否位于两个包含项之间; 否则抛出带有给定消息的异常.
     *
     * <pre>
     * Assert.inclusiveBetween(0, 2, 1, "Not in range");
     * </pre>
     *
     * @param start   包含起始值
     * @param end     包含最终值
     * @param value   要验证的数值
     * @param message 异常消息
     * @throws IllegalArgumentException 如果值超出边界(包含)
     */
    public static void inclusiveBetween(long start, long end, long value, String message) {
        // TODO when breaking BC, consider returning value
        if (value < start || value > end) {
            throw new IllegalArgumentException(message);
        }
    }

    /**
     * 验证给定数值是否位于两个包含项之间; 否则抛出异常.
     *
     * <pre>
     * Assert.inclusiveBetween(0.1, 2.1, 1.1);
     * </pre>
     *
     * @param start 包含起始值
     * @param end   包含最终值
     * @param value 要验证的数值
     * @throws IllegalArgumentException 如果值超出边界(包含)
     */
    @SuppressWarnings("boxing")
    public static void inclusiveBetween(double start, double end, double value) {
        // TODO when breaking BC, consider returning value
        if (value < start || value > end) {
            throw new IllegalArgumentException(String.format(DEFAULT_INCLUSIVE_BETWEEN_EX_MESSAGE, value, start, end));
        }
    }

    /**
     * 验证给定数值是否位于两个包含项之间; 否则抛出带有给定消息的异常.
     *
     * <pre>
     * Assert.inclusiveBetween(0.1, 2.1, 1.1, "Not in range");
     * </pre>
     *
     * @param start   包含起始值
     * @param end     包含最终值
     * @param value   要验证的数值
     * @param message 异常消息
     * @throws IllegalArgumentException 如果值超出边界(包含)
     */
    public static void inclusiveBetween(double start, double end, double value, String message) {
        // TODO when breaking BC, consider returning value
        if (value < start || value > end) {
            throw new IllegalArgumentException(message);
        }
    }

    // exclusiveBetween
    // ---------------------------------------------------------------------------------

    /**
     * <p>
     * 验证给定对象是否位于两个不包含项之间; 否则抛出异常.
     * </p>
     *
     * <pre>
     * Assert.exclusiveBetween(0, 2, 1);
     * </pre>
     *
     * @param <T>   要验证的对象类型
     * @param start 不包含起始值
     * @param end   不包含最终值
     * @param value 要验证的对象
     * @throws IllegalArgumentException 如果值超出边界(不包含)
     */
    public static <T> void exclusiveBetween(final T start, final T end, final Comparable<T> value) {
        // TODO when breaking BC, consider returning value
        if (value.compareTo(start) <= 0 || value.compareTo(end) >= 0) {
            throw new IllegalArgumentException(String.format(DEFAULT_EXCLUSIVE_BETWEEN_EX_MESSAGE, value, start, end));
        }
    }

    /**
     * <p>
     * 验证给定对象是否位于两个不包含项之间; 否则抛出带有给定消息的异常.
     * </p>
     *
     * <pre>
     * Assert.exclusiveBetween(0, 2, 1, "Not in boundaries");
     * </pre>
     *
     * @param <T>     要验证的对象类型
     * @param start   不包含起始值
     * @param end     不包含最终值
     * @param value   要验证的对象
     * @param message {@link String#format(String, Object...)} 异常消息
     * @param values  格式化异常消息的可选值
     * @throws IllegalArgumentException 如果值超出边界(不包含)
     */
    public static <T> void exclusiveBetween(final T start, final T end, final Comparable<T> value, final String message,
                                            final Object... values) {
        // TODO when breaking BC, consider returning value
        if (value.compareTo(start) <= 0 || value.compareTo(end) >= 0) {
            throw new IllegalArgumentException(String.format(message, values));
        }
    }

    /**
     * 验证给定数值是否位于两个不包含项之间; 否则抛出异常.
     *
     * <pre>
     * Assert.exclusiveBetween(0, 2, 1);
     * </pre>
     *
     * @param start 不包含起始值
     * @param end   不包含最终值
     * @param value 要验证的数值
     * @throws IllegalArgumentException 如果值超出边界(不包含)
     */
    @SuppressWarnings("boxing")
    public static void exclusiveBetween(long start, long end, long value) {
        // TODO when breaking BC, consider returning value
        if (value <= start || value >= end) {
            throw new IllegalArgumentException(String.format(DEFAULT_EXCLUSIVE_BETWEEN_EX_MESSAGE, value, start, end));
        }
    }

    /**
     * 验证给定数值是否位于两个不包含项之间; 否则抛出带有给定消息的异常.
     *
     * <pre>
     * Assert.exclusiveBetween(0, 2, 1, "Not in range");
     * </pre>
     *
     * @param start   不包含起始值
     * @param end     不包含最终值
     * @param value   要验证的数值
     * @param message 异常消息
     * @throws IllegalArgumentException 如果值超出边界(不包含)
     */
    public static void exclusiveBetween(long start, long end, long value, String message) {
        // TODO when breaking BC, consider returning value
        if (value <= start || value >= end) {
            throw new IllegalArgumentException(message);
        }
    }

    /**
     * 验证给定数值是否位于两个不包含项之间; 否则抛出异常.
     *
     * <pre>
     * Assert.exclusiveBetween(0.1, 2.1, 1.1);
     * </pre>
     *
     * @param start 不包含起始值
     * @param end   不包含最终值
     * @param value 要验证的数值
     * @throws IllegalArgumentException 如果值超出边界(不包含)
     */
    @SuppressWarnings("boxing")
    public static void exclusiveBetween(double start, double end, double value) {
        // TODO when breaking BC, consider returning value
        if (value <= start || value >= end) {
            throw new IllegalArgumentException(String.format(DEFAULT_EXCLUSIVE_BETWEEN_EX_MESSAGE, value, start, end));
        }
    }

    /**
     * 验证给定数值是否位于两个不包含项之间; 否则抛出带有给定消息的异常.
     *
     * <pre>
     * Assert.exclusiveBetween(0.1, 2.1, 1.1, "Not in range");
     * </pre>
     *
     * @param start   不包含起始值
     * @param end     不包含最终值
     * @param value   要验证的数值
     * @param message 异常消息
     * @throws IllegalArgumentException 如果值超出边界(不包含)
     */
    public static void exclusiveBetween(double start, double end, double value, String message) {
        // TODO when breaking BC, consider returning value
        if (value <= start || value >= end) {
            throw new IllegalArgumentException(message);
        }
    }

    // isInstanceOf
    // ---------------------------------------------------------------------------------

    /**
     * 验证对象是否指定类型的实例; 否则抛出异常.
     *
     * <pre>
     * Assert.isInstanceOf(OkClass.class, object);
     * </pre>
     *
     * @param type 验证对象类型
     * @param obj  要验证的对象
     * @throws IllegalArgumentException 要过给定对象不是给定类型
     */
    public static void isInstanceOf(final Class<?> type, final Object obj) {
        // TODO when breaking BC, consider returning obj
        if (!type.isInstance(obj)) {
            throw new IllegalArgumentException(String.format(DEFAULT_IS_INSTANCE_OF_EX_MESSAGE, type.getName(),
                    obj == null ? "null" : obj.getClass().getName()));
        }
    }

    /**
     * <p>
     * 验证对象是否指定类型的实例; 否则抛出带有给定消息的异常.
     * </p>
     *
     * <pre>
     * Assert.isInstanceOf(OkClass.classs, object, "Wrong class, object is of class %s", object.getClass().getName());
     * </pre>
     *
     * @param type    验证对象类型
     * @param obj     要验证的对象
     * @param message {@link String#format(String, Object...)} 异常消息
     * @param values  格式化异常消息的可选值
     * @throws IllegalArgumentException 要过给定对象不是给定类型
     */
    public static void isInstanceOf(final Class<?> type, final Object obj, final String message,
                                    final Object... values) {
        // TODO when breaking BC, consider returning obj
        if (!type.isInstance(obj)) {
            throw new IllegalArgumentException(String.format(message, values));
        }
    }

    // isAssignableFrom
    // ---------------------------------------------------------------------------------

    /**
     * 验证给定类型是否可以转换为指定类型; 否则抛出异常.
     *
     * <pre>
     * Assert.isAssignableFrom(SuperClass.class, object.getClass());
     * </pre>
     *
     * @param superType 要转换的类型
     * @param type      要验证的类型
     * @throws IllegalArgumentException if type argument is not assignable to the
     *                                  specified superType
     */
    public static void isAssignableFrom(final Class<?> superType, final Class<?> type) {
        // TODO when breaking BC, consider returning type
        if (!superType.isAssignableFrom(type)) {
            throw new IllegalArgumentException(String.format(DEFAULT_IS_ASSIGNABLE_EX_MESSAGE,
                    type == null ? "null" : type.getName(), superType.getName()));
        }
    }

    /**
     * 验证给定类型是否可以转换为指定类型; 否则抛出异常.
     *
     * <pre>
     * Assert.isAssignableFrom(SuperClass.class, object.getClass());
     * </pre>
     *
     * @param superType 要转换的类型
     * @param type      要验证的类型
     * @param message   {@link String#format(String, Object...)} 异常消息
     * @param values    格式化异常消息的可选值
     * @throws IllegalArgumentException if argument can not be converted to the
     *                                  specified class
     */
    public static void isAssignableFrom(final Class<?> superType, final Class<?> type, final String message,
                                        final Object... values) {
        // TODO when breaking BC, consider returning type
        if (!superType.isAssignableFrom(type)) {
            throw new IllegalArgumentException(String.format(message, values));
        }
    }
}
