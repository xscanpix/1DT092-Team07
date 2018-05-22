
# from Adrien Clotteau, Uppsala University for Global Software Product Development project. 


import bluetooth
import struct
import time

# The firsts functions of the EV3 class were made by Christoph Gaukel at http://ev3directcommands.blogspot.se/2016/01/no-title-specified-page-table-border_94.html
# You should have a look at this website if you want to understand every line of code of this file.


class EV3():
    
    def __init__(self, host: str):
        self._socket = bluetooth.BluetoothSocket(bluetooth.RFCOMM)
        self._socket.connect((host, 1))

    def __del__(self):
        if isinstance(self._socket, bluetooth.BluetoothSocket):
            self._socket.close()


    def send_direct_cmd(self, ops: bytes, local_mem: int=0, global_mem: int=0) -> bytes:
        cmd = b''.join([
            struct.pack('<h', len(ops) + 5),
            struct.pack('<h', 42),
            DIRECT_COMMAND_REPLY,
            struct.pack('<h', local_mem*1024 + global_mem),
            ops
        ])
        self._socket.send(cmd)
        print_hex('Sent', cmd)
        reply = self._socket.recv(5 + global_mem)
        print_hex('Recv', reply)
        return reply



    def send_cmd(self, reply: bytes, ops: int, local_mem: int, global_mem: int, message: bytes) -> bytes:

        cmd = b''.join([
            struct.pack('<h', len(message) + 6),
            struct.pack('<h', 42),
            reply,
            struct.pack('<h', local_mem*1024 + global_mem),
            struct.pack('<B', ops),
            message
        ])
        self._socket.send(cmd)
        print_hex('Sent', cmd)
        reply = self._socket.recv(5 + global_mem)
        print_hex('Recv', reply)
        return reply

def print_hex(desc: str, data: bytes) -> None:
    print(desc + ' 0x|' + ':'.join('{:02X}'.format(byte) for byte in data) + '|')


DIRECT_COMMAND_REPLY = b'\x00'
NO_REPLY = b'\x80'
my_ev3 = EV3('00:16:53:4B:09:89')




def isConnected():
    opNop = b'\x01'
    return my_ev3.send_direct_cmd(opNop)
#isConnected()


"""
Format of bluetooth messages sent to EV3 :

__LE_|_cnt_|ty|_hd__|op|cd|_______Name_________
0E:00:2A:00:00:00:00:D4:08:84:6D:79:45:56:33:00


LE : the length of the whole message in bytes without the two first bytes (here 14)
ty : the message type, it may have two values : 0x|00| if it waits for a response from the brick or 0x|80| if not
hd : header, it is a combination of two numbers, which define the memory sizes of the direct command
op : the type of command sent to EV3, D4
6D:79:45:56:33 means myEV3 in hexa

"""

def changingName(name : str):
    encode=name.encode('ASCII')
    message=[8]
    message.append(132)
    for i in range(len(encode)):
        message.append(encode[i])
    message.append(0)
    return my_ev3.send_cmd(DIRECT_COMMAND_REPLY, 212, 0, 0, bytes(message))
#changingName("EV3team007")




"""

Format of bluetooth messages sent to EV3 to power on the motors :

__LE_|_cnt_|ty|_hd__|op|la|no|power|op|la|no|
0D:00|2A:00|80|00:00|A4|00|0F|81:64|A6|00|0F|             


LE : the length of the whole message in bytes without the two first bytes (here 13)
ty : the message type, it may have two values : 0x|00| if it waits for a response from the brick or 0x|80| if not.
hd : header, it is a combination of two numbers, which define the memory sizes of the direct command
op : the type of command sent to EV3. A4 means "output power". A6 means "output start".
no : port.  A=0001, B=0010, C=0100 et D=1000. So if you want to power on the motors plugged on ports A and D, A+D=0001+1000=1001=09 in hexa. If you want to activate every motors, A+B+C+D=1111=0F in hexa.
power : I am not sure yet what is the first byte but the second is the percentage of max power. 81:64 is max power because 64 in hexa is 100 in decimal.

"""


# A4 is Output_Power, A6 is Output_Start, OF is all, power[1] in % of max power
def motorOn(la1, no1, power, op, la2, no2):
    message=[la1, no1, power[0], power[1], op, la2, no2]
    return my_ev3.send_cmd(DIRECT_COMMAND_REPLY, 164, 0, 0, bytes(message))




"""

Format of bluetooth messages sent to EV3 to power off the motors :

__LE_|_cnt_|ty|_hd__|op|la|no|br|
09:00|2A:00|00|00:00|A3|00|0F|00|          


LE : the length of the whole message in bytes without the two first bytes (here 13)
ty : the message type, it may have two values : 0x|00| if it waits for a response from the brick or 0x|80| if not.
hd : header, it is a combination of two numbers, which define the memory sizes of the direct command
op : the type of command sent to EV3. A3 means "output stop".
no : port.  A=0001, B=0010, C=0100 et D=1000. So if you want to power on the motors plugged on ports A and D, A+D=0001+1000=1001=09 in hexa. If you want to activate every motors, A+B+C+D=1111=0F in hexa.

"""

# A3 is Output_Stop
def motorOff(la, no, br):
    message=[la, no, br]
    return my_ev3.send_cmd(DIRECT_COMMAND_REPLY, 163, 0, 0, bytes(message))

#motorOff(0,15,0)
#motorOn(0, 15, [129,100], 166, 0, 15)




"""

Format of bluetooth messages sent to EV3 to switch on the motors (the max power seems to be weaker with A5 than with A4) :

__LE_|_cnt_|ty|_hd__|op|la|no|sp|op|la|no|
0C:00|2A:00|80|00:00|A5|00|09|3D|A6|00|09|         

    
LE : the length of the whole message in bytes without the two first bytes (here 13)
ty : the message type, it may have two values : 0x|00| if it waits for a response from the brick or 0x|80| if not.
hd : header, it is a combination of two numbers, which define the memory sizes of the direct command
op : the type of command sent to EV3. A5 means "output speed". A6 means "output start".
no : port.  A=0001, B=0010, C=0100 et D=1000. So if you want to power on the motors plugged on ports A and D, A+D=0001+1000=1001=09 in hexa. If you want to activate every motors, A+B+C+D=1111=0F in hexa.
sp : speed value of the motors. It is positive from 00 to 1F and negative from 20 to 3F. 1F and 20 are the maximum in 
"""

# A5 is motor_Speed
def motorSpeed(la1,no1,sp,op,la2,no2):
    message=[la1,no1,sp,op,la2,no2]
    return my_ev3.send_cmd(DIRECT_COMMAND_REPLY, 165, 0, 0, bytes(message))

#####################################""



# Motors A and D are activated at the speed 1F (maximum for this mode).
def goForward():
    motorSpeed(0,9,31,166,0,9)


# Miror symetrie of goForward()
def goBackward():
    motorSpeed(0,9,32,166,0,9)


# It stops the motor you want (1 for left wheel, 8 for right wheel, 2 for the trolley)
def stop(motor: int=15):
    motorOff(0,motor,0)


# Motor A (left wheel) is activated at the speed 2A (backward) and motor D (right wheel) is activated at the speed 16 (forward at the oposite speed of A).
def goFullSpeedLeft():
    motorSpeed(0,8,21,166,0,8)
    motorSpeed(0,1,42,166,0,1)


# Miror symetrie of goFullSpeedLeft()
def goFullSpeedRight():
    motorSpeed(0,8,42,166,0,8)
    motorSpeed(0,1,21,166,0,1)


# Same as before but at a slower speed
def goSlowSpeedLeft():
    motorSpeed(0,8,10,166,0,8)
    motorSpeed(0,1,53,166,0,1)


# Same as before but at a slower speed
def goSlowSpeedRight():
    motorSpeed(0,1,10,166,0,8)
    motorSpeed(0,8,53,166,0,1)




# Motor 2 is switch on at full power to lift 
def trolleyUp():
    motorOn(0, 2, [129,100], 166, 0, 2)
    time.sleep(0.2)
    stop(2)
    motorSpeed(0,2,0,166,0,2)

def trolleyDown():
    motorSpeed(0,2,32,166,0,2)
    time.sleep(0.1)
    stop(2)


# Reading type and mode of a sensor
def type(port):
    message=[5, 0, port, 96, 97]
    my_ev3.send_cmd(DIRECT_COMMAND_REPLY, 153, 0, 2, bytes(message))
# question is :  \len(2)\cnt(2)\ty\hd(2)\op\cd\la\no\ty\mo\  
# answer is : \len(2)\cnt(2)\rs\ty\mo\  

"""
type(19)
"""

# https://le-www-live-s.legocdn.com/sc/media/files/ev3-developer-kit/lego%20mindstorms%20ev3%20firmware%20developer%20kit-7be073548547d99f7df59ddfd57c0088.pdf?la=en-us
# page 5
#
#        no
# port 1 (0) -> 7E 00 
# port 2 (1) -> 20 00 Gyro
# port 3 (2) -> 1D 00 color
# port 4 (3) -> 1E 00 Ultrasonic
# port A (16) -> 07 00 Large motor  -> Roue gauche
# port B (17) -> 00 7E Medium motor
# port C (18) -> 7E 00 
# port D (19) -> 07 00 Large motor  -> Roue droite



# reading the actual position of a motor / value of a sensor
def read(port, typ):
    message=[28, 0, port, typ, 0, 1, 96]
    answer =my_ev3.send_cmd(DIRECT_COMMAND_REPLY, 153, 0, 4, bytes(message))
    return answer
# question is :  \len(2)\cnt(2)\ty\hd(2)\op\cd\la\no\ty\mo\  
# answer is : \len(2)\cnt(2)\rs\ty\mo\  

"""
read(16, 7)
"""


# The displayed value is in percentage. 
def readColour():
    colour= read(2, 29)
    print("colour is", colour[5], "%")
    return colour[5]

"""
readColour()
"""


# The displayed value is in milimeters. Limit is 2 meters.
def readDistance():
    distance=read(3, 30)
    print("distance is", distance[5]+distance[6]*256, "mm")
    return distance[5]+distance[6]*256 
"""
readDistance()
"""

# The displayed value is in degrees.
def readGyro():
    angle=read(1, 32)
    degrees=angle[5]+angle[6]*256+angle[7]*65536+angle[8]*16777216
    if degrees>30000:
        degrees=degrees-16777216*256
    print("angle is", degrees, "°")
    return degrees




# When the Gyro reach
def turn90Right():
    Gyro=readGyro()
    goFullSpeedRight()
    while readGyro()<Gyro+70:
        True
    stop(9)
    readGyro()
    while(readGyro()-Gyro)!=90 and (readGyro()-Gyro)!=89 and (readGyro()-Gyro)!=91:
        time.sleep(0.01)
        if (readGyro()-Gyro)>90:
            goSlowSpeedLeft()
            time.sleep(0.1)
            stop(9)
        if (readGyro()-Gyro)<90:
            goSlowSpeedRight()
            time.sleep(0.1)
            stop(9)


def turn90Left():
    Gyro=readGyro()
    goFullSpeedLeft()
    while readGyro()>Gyro-70:
        True
    stop(9)
    readGyro()
    while(readGyro()-Gyro)!=-90 and (readGyro()-Gyro)!=-89 and (readGyro()-Gyro)!=-91:
        time.sleep(0.01)
        if (readGyro()-Gyro)<-90:
            goSlowSpeedRight()
            time.sleep(0.02)
            stop(9)
        if (readGyro()-Gyro)>-90:
            goSlowSpeedLeft()
            time.sleep(0.02)
            stop(9)

"""
goForward()
time.sleep(0.5)
stop(9)
turn90Left()
goForward()
time.sleep(0.5)
stop(9)
turn90Right()
goForward()
time.sleep(0.5)
stop(9)
"""


readColour()
