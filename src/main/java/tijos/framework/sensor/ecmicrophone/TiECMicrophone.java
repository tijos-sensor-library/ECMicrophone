package tijos.framework.sensor.ecmicrophone;

import java.io.IOException;

import tijos.framework.devicecenter.TiADC;
import tijos.framework.util.Formatter;

/**
 * Hello world!
 *
 */
public class TiECMicrophone 
{
	
	TiADC adc;
	int  channel; // channel of the adc

	int sensitivity = -42; //default  sensitivity -42db
	
	String msg ;
	
	/**
	 * initialize with ADC and channel
	 * @param adc ADC 
	 * @param chn channel number of the ADC
	 */
	public TiECMicrophone(TiADC adc, int chn)
	{
		this.adc = adc;
	}
	
	/**
	 * Set sound pressure sensitivity 
	 * @param sensitivity
	 */
	public void setSensitivity(int  sensitivity)
	{
		this.sensitivity =  sensitivity;
	}
	
	/**
	 * Get the sound pressure level from ADC
	 * @return sound pressure level
	 * @throws IOException
	 */
	public double getSoundPressure() throws IOException 
	{
		//计算1Pa声压下输出的电压
		double Vpa= Math.pow(10.0, this.sensitivity/20.0); // Vpa=10^(S/20)
		
		//计算当前采集到的电压Vc转换成声压Pa，Pa=Vc/Vpa - 采集到的电压在无声音时候是500mv，即：基准电压500mv 
		double Vc = this.adc.getVoltageValue(this.channel) - 0.5;   //remove base voltage (500mv)
		double Pa = Vc / Vpa;//Pa=Vc/Vpa
		
		//计算SPL声压级(即：我们常用的声音分贝), SPL=20*lg(Pa/0.00002), 单位dB SPL
		double SPL = 20 * Math.log10(Pa/0.0002);
		
		msg = " Vc " + Formatter.format(Vc, ".##") + " Pa " + Formatter.format(Pa, ".##") + " SPL " + Formatter.format(SPL, ".##");
		
		return SPL;
	}
	
	
	public String getMsg() {
		return msg;
	}
}
