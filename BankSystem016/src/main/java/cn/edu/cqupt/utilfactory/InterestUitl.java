package cn.edu.cqupt.utilfactory;

import java.math.BigDecimal;

public class InterestUitl {
	public static final BigDecimal i_month=new BigDecimal(30);
	/**
	 * 
	 * @param capital 当前本金
	 * @param rate 办理定期储蓄，开户之日的日利率
	 * @param depositTerm 约定存储期限（以月为单位）
	 * @return 到期后应得的利息
	 */
	public static BigDecimal RegularSaving(double capital,double rate,int depositTerm)
	{
		BigDecimal i_capital=new BigDecimal((int)capital);
		BigDecimal i_rate=new BigDecimal(rate);
		BigDecimal i_depositTerm=new BigDecimal(depositTerm);
		BigDecimal i_interest=((i_rate.multiply(i_month)).multiply(i_depositTerm)).multiply(i_capital);
		return i_interest;
	}
	/**
	 * 
	 * @param capital  当前本金
	 * @param rate  取款之日的日利率
	 * @param dates 调用方法之日距离上一次“结息”所经过的天数
	 * @return 这段时间内应得的利息
	 */
	public static BigDecimal CurrentSaving(double capital,double rate,int dates){
		BigDecimal csr_interest;
		BigDecimal i_rate=new BigDecimal(rate);
		BigDecimal i_capital=new BigDecimal(capital);
		BigDecimal i_dates=new BigDecimal(dates);
		csr_interest = (i_capital.multiply(i_rate)).multiply(i_dates);
		return csr_interest;
	}
}
