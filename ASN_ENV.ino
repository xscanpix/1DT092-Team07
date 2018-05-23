#include <SoftwareSerial.h>


/********************DHT Library Definition********************************
 *****You need to include DHT22 Sensor library and Adafruit Unified Sensor*
 *****Library. The URL are listed below. You can do that from *************
 ***** By going to Sketch option in Arduino IDE and Include library and**** 
 ***************Add Zip.***************************************************
 *****DHT22 URL:https://github.com/adafruit/DHT-sensor-library   ********** 
 *****Adafruit URL: https://github.com/adafruit/Adafruit_Sensor   *********
*/




/****************************************************************************
 * **************************DHT Pin Definition and Library Include *********
 * **************************************************************************
 */
#include "DHT.h"
#define DHTPIN 2    
#define DHTTYPE DHT22   // DHT 22 
DHT dht(DHTPIN, DHTTYPE); //Initialize DHT sensor for normal 16mhz Arduino



/*****************************************************************************
 *******Software Serial Definition for Serial Bluetooth Communication*********
 *****************************************************************************
 */
SoftwareSerial bluetooth(10,11);//Rx,TX

String rec;

void setup() {
  // open the serial port:
  Serial.begin(9600);
  // Change the baudrate depending on your module's baudrate
  bluetooth.begin(9600);
  dht.begin();
}

void loop() {
    if (bluetooth.available()) {
      rec = bluetooth.readString();
      if (rec=="ASN1"){ //You need to send ASN1 from PC's serial to the arduino to get answer back.  
        float hum = dht.readHumidity();
        Serial.println("Test");
        float temp= dht.readTemperature();
        bluetooth.print("Temperature: "+String(temp)+" Humidity: "+String(hum)+"\r\n");
        Serial.print("Temperature: "+String(temp)+" Humidity: "+String(hum)+"\r\n");
        }
    }
}


