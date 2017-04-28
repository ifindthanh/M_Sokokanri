package vn.com.nsmv.common;

import java.math.*;

import org.apache.commons.lang3.math.*;

public class NumberUtil
{
	/**
	 * 非インスタンス化のための private コンストラクター。
	 */
	private NumberUtil()
	{
	}

	/**
	 * 指定された文字列が数字だけを含むかどうかを返す。
	 *
	 * <pre>
	 * Commons-Lang の {@link NumberUtils#isDigits(String)} をそのまま呼び出し。
	 * </pre>
	 *
	 * @param str 文字列
	 * @return 数字だけを含むかどうか
	 */
	public static boolean isDigits(String str)
	{
		return NumberUtils.isDigits(str);
	}

	/**
	 * 指定された文字列 を Integer型 へ変換する。<br>
	 * Commons-Lang の {@link NumberUtils#toInteger(String)} で、Double 型へ変換後、Integer へ。<br>
	 * <br>
	 * null の場合、０を返す。
	 *
	 * @param str 文字列
	 * @return 変換した数値
	 */
	public static Integer toInteger(String str)
	{
		if (!isDigits(str))
		{
			return Integer.valueOf(0);
		}

		Integer ret = Integer.valueOf(NumberUtils.toInt(str));
		return nullToZero(ret);
	}

	/**
	 * 指定された文字列 を BigDecimal型 へ変換する。<br>
	 * Commons-Lang の {@link NumberUtils#toInteger(String)} で、Double 型へ変換後、BigDecimal へ。<br>
	 * <br>
	 * null の場合、０を返す。
	 *
	 * @param str 文字列
	 * @return 変換した数値
	 */
	public static BigDecimal toBigDecimal(String str)
	{
		if (!isDigits(str))
		{
			return BigDecimal.valueOf(0);
		}

		BigDecimal ret = BigDecimal.valueOf(NumberUtils.toDouble(str));
		return nullToZero(ret);
	}

	/**
	 * null を 0 に変換して返す。
	 *
	 * <pre>
	 * null でない場合はそのまま返す。
	 * </pre>
	 *
	 * @param num 変換する数値
	 * @return 変換した数値
	 */
	public static Integer nullToZero(Integer num)
	{

		Integer result = num;

		if (num == null)
		{
			result = Integer.valueOf(0);
		}

		return result;
	}

	/**
	 * null を 0 に変換して返す。
	 *
	 * <pre>
	 * null でない場合はそのまま返す。
	 * </pre>
	 *
	 * @param num 変換する数値
	 * @return 変換した数値
	 */
	public static BigDecimal nullToZero(BigDecimal num)
	{

		BigDecimal result = num;

		if (num == null)
		{
			result = BigDecimal.ZERO;
		}

		return result;
	}
}
