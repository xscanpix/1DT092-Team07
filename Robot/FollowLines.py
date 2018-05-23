
#!/usr/bin/env python3

import time
import BasicFunctions as fct

#blue =20%
#and 2
# white is 6 and 100%
# red is 5 and 100
# 1 and 13% for black

fct.readGyro()
def followLine(seconds):
    timeOut=time.time()+seconds
    while time.time()<timeOut:
        while fct.readNameColour() != 1:
            if fct.readColour()<70 and fct.readColour()>=35:
                fct.goForward()
            elif fct.readColour()>=70 and fct.readColour()<80:
                fct.stop(9)
                fct.goSlowSpeedLeft()
                time.sleep(0.02)
                fct.stop(9)
            elif fct.readColour()>=80:
                fct.stop(9)
                fct.goFullSpeedLeft()
                time.sleep(0.01)
                fct.stop(9)
            elif fct.readColour()<35 and fct.readColour()>27:
                fct.stop(9)
                fct.goSlowSpeedRight()
                time.sleep(0.02)
                fct.stop(9)
            else:
                fct.stop(9)
                fct.goFullSpeedRight()
                time.sleep(0.01)
                fct.stop(9)
        fct.stop(9)
        break



def turnRight():
    fct.goForward()
    time.sleep(1.3)
    fct.stop(9)
    fct.turn90Right()
    fct.goForward()
    time.sleep(0.5)
    fct.stop(9)


def turnLeft():
    fct.goForward()
    time.sleep(0.4)
    fct.stop(9)
    fct.turn90Left()
    fct.goForward()
    time.sleep(0.5)
    fct.stop(9)


def takeLoads():
    loaded=False
    while loaded != True:
        if fct.readDistance()<250 and fct.readDistance()>150 :
            fct.trolleyDown()
            fct.goForward()
            time.sleep(2)
            fct.stop(9) 
            fct.trolleyLoaded()
            time.sleep(1)
            loaded=True
        elif fct.readDistance()<150 :
            fct.goBackward()
            while fct.readDistance()<150:
                True
            fct.stop(9)
        else : 
            fct.goForward()
            while fct.readDistance()>250:
                True
            fct.stop(9)



def leaveLoad():
    fct.goForward()
    time.sleep(1.7)
    fct.stop(9)
    fct.trolleyDown()
    fct.goBackward()
    time.sleep(2)
    fct.stop(9)
    fct.trolleyUp()
    followLine(10000)

def goForwardIntersection():
    fct.goForward()
    time.sleep(1.5)
    fct.stop(9)


fct.stop()
'''
fct.trolleyUp()
followLine(10000)
turnRight()
followLine(10000)
turnRight()

takeLoads()

fct.turn180Right()
followLine(10000)

goForwardIntersection()

followLine(10000)


leaveLoad()

turnLeft()
followLine(10000)
fct.trolleyDown()
'''

#fct.readColour()
#fct.readNameColour()


