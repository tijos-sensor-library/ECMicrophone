package tijos.framework.sensor.ecmicrophone;

import java.io.IOException;

import tijos.framework.devicecenter.TiADC;
import tijos.framework.devicecenter.TiI2CMaster;
import tijos.framework.transducer.oled.TiOLED_UG2864;
import tijos.framework.util.Delay;
import tijos.framework.util.Formatter;

/**
 * Hello world!
 *
 */
public class TiECMicrophoneSample
{	
    public static void main( String[] args )
    {
        System.out.println( "Hello World!" );
        
		int adcPort = 0;
		
		int adcChn = 0;
        
		try {
			int i2cPort0 = 0;
			TiI2CMaster i2c0 = TiI2CMaster.open(i2cPort0);
			
			TiOLED_UG2864 oled = new TiOLED_UG2864(i2c0, 0x3c);
			
			TiADC adc = TiADC.open(adcPort, adcChn);
			adc.setRefVoltageValue(1, 1);
			
			TiECMicrophone mic = new TiECMicrophone(adc, 0);
			
			oled.turnOn();
			oled.clear();

			oled.print(0, 0, "ECMic Demo");
			
			while(true)
			{
				double spl = mic.getSoundPressure();
				System.out.println(" spl " + spl);
				
				oled.print(2, 0, mic.getMsg());
				oled.print(3, 0, "spl " + Formatter.format(spl, ".##"));
				Delay.msDelay(1000);
			}
			
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
        
    }
}
